package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.JavaneseCalendarEngine
import com.example.calendar.JavaneseDate
import com.example.calendar.JavaneseHolidayCategory
import com.example.calendar.JavaneseHolidayInstance
import com.example.calendar.JodohPetunganResult
import com.example.calendar.WetonKelahiran
import com.example.calendar.WetonKelahiranEngine
import com.example.data.AppDatabase
import com.example.data.PlannerEvent
import com.example.data.PlannerRepository
import com.example.localization.AppLanguage
import com.example.localization.PreferencesManager
import com.example.localization.StringResources
import com.example.notification.NotificationHelper
import com.example.notification.NotificationScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

enum class JavaneseHolidayFilterTab {
    ALL, KERATON, ISLAM_JAWA, SAKRAL
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PreferencesManager(application)
    private val repository: PlannerRepository

    init {
        val dao = AppDatabase.getDatabase(application).plannerEventDao()
        repository = PlannerRepository(dao)
        NotificationHelper.createNotificationChannels(application)
    }

    private val _currentLanguage = MutableStateFlow<AppLanguage>(prefs.language)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    // Calendar view year and month (Gregorian)
    private val _viewYear = MutableStateFlow(LocalDate.now().year)
    val viewYear: StateFlow<Int> = _viewYear.asStateFlow()

    private val _viewMonth = MutableStateFlow(LocalDate.now().monthValue)
    val viewMonth: StateFlow<Int> = _viewMonth.asStateFlow()

    private val _holidayFilter = MutableStateFlow(JavaneseHolidayFilterTab.ALL)
    val holidayFilter: StateFlow<JavaneseHolidayFilterTab> = _holidayFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isHolidayNotifEnabled = MutableStateFlow<Boolean>(prefs.isHolidayNotifEnabled)
    val isHolidayNotifEnabled: StateFlow<Boolean> = _isHolidayNotifEnabled.asStateFlow()

    private val _isActivityNotifEnabled = MutableStateFlow<Boolean>(prefs.isActivityNotifEnabled)
    val isActivityNotifEnabled: StateFlow<Boolean> = _isActivityNotifEnabled.asStateFlow()

    // Derived Javanese date for currently selected date
    val selectedJavaneseDate: StateFlow<JavaneseDate> = _selectedDate.map { date ->
        JavaneseCalendarEngine.fromLocalDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = JavaneseCalendarEngine.fromLocalDate(LocalDate.now())
    )

    // Holidays on selected date
    val holidaysOnSelectedDate: StateFlow<List<JavaneseHolidayInstance>> = _selectedDate.map { date ->
        JavaneseCalendarEngine.getHolidaysForDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = JavaneseCalendarEngine.getHolidaysForDate(LocalDate.now())
    )

    // Events on selected date
    @OptIn(ExperimentalCoroutinesApi::class)
    val eventsOnSelectedDate: StateFlow<List<PlannerEvent>> = _selectedDate.flatMapLatest { date ->
        val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        repository.getEventsForDate(dateStr)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Dates that have events (for calendar day dots)
    val datesWithEvents: StateFlow<Set<String>> = repository.getDatesWithEvents().map { list ->
        list.toSet()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
    )

    // All upcoming events
    val allUpcomingEvents: StateFlow<List<PlannerEvent>> = repository.getUpcomingEvents(
        LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Upcoming holidays catalog for current year
    val upcomingHolidays: StateFlow<List<JavaneseHolidayInstance>> = _selectedDate.map {
        JavaneseCalendarEngine.getUpcomingHolidays(LocalDate.now(), count = 30)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = JavaneseCalendarEngine.getUpcomingHolidays(LocalDate.now(), count = 30)
    )

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
        prefs.language = language
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        _viewYear.value = date.year
        _viewMonth.value = date.monthValue
    }

    fun nextMonth() {
        if (_viewMonth.value == 12) {
            _viewMonth.value = 1
            _viewYear.value += 1
        } else {
            _viewMonth.value += 1
        }
    }

    fun prevMonth() {
        if (_viewMonth.value == 1) {
            _viewMonth.value = 12
            _viewYear.value -= 1
        } else {
            _viewMonth.value -= 1
        }
    }

    fun setViewYearMonth(year: Int, month: Int) {
        val clampedYear = year.coerceIn(1900, 2100)
        val clampedMonth = month.coerceIn(1, 12)
        _viewYear.value = clampedYear
        _viewMonth.value = clampedMonth
        val maxDays = YearMonth.of(clampedYear, clampedMonth).lengthOfMonth()
        val currentDay = _selectedDate.value.dayOfMonth.coerceAtMost(maxDays)
        _selectedDate.value = LocalDate.of(clampedYear, clampedMonth, currentDay)
    }

    fun nextYear() {
        val newYear = (_viewYear.value + 1).coerceAtMost(2100)
        _viewYear.value = newYear
        val maxDays = YearMonth.of(newYear, _viewMonth.value).lengthOfMonth()
        val currentDay = _selectedDate.value.dayOfMonth.coerceAtMost(maxDays)
        _selectedDate.value = LocalDate.of(newYear, _viewMonth.value, currentDay)
    }

    fun prevYear() {
        val newYear = (_viewYear.value - 1).coerceAtLeast(1900)
        _viewYear.value = newYear
        val maxDays = YearMonth.of(newYear, _viewMonth.value).lengthOfMonth()
        val currentDay = _selectedDate.value.dayOfMonth.coerceAtMost(maxDays)
        _selectedDate.value = LocalDate.of(newYear, _viewMonth.value, currentDay)
    }

    fun goToToday() {
        val today = LocalDate.now()
        _selectedDate.value = today
        _viewYear.value = today.year
        _viewMonth.value = today.monthValue
    }

    fun setHolidayFilter(tab: JavaneseHolidayFilterTab) {
        _holidayFilter.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setHolidayNotifEnabled(enabled: Boolean) {
        _isHolidayNotifEnabled.value = enabled
        prefs.isHolidayNotifEnabled = enabled
        if (enabled) {
            NotificationScheduler.scheduleUpcomingHolidayReminders(getApplication(), _currentLanguage.value)
        }
    }

    fun setActivityNotifEnabled(enabled: Boolean) {
        _isActivityNotifEnabled.value = enabled
        prefs.isActivityNotifEnabled = enabled
    }

    fun addPlannerEvent(
        title: String,
        description: String,
        time: String,
        category: String,
        hasReminder: Boolean
    ) {
        viewModelScope.launch {
            val date = _selectedDate.value
            val jvDate = JavaneseCalendarEngine.fromLocalDate(date)
            val event = PlannerEvent(
                gregorianDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                hebrewDateString = "${jvDate.wetonName} (${jvDate.getFormattedJavanese(_currentLanguage.value)})",
                title = title.trim(),
                description = description.trim(),
                time = time.trim(),
                category = category,
                hasReminder = hasReminder && _isActivityNotifEnabled.value
            )
            val id = repository.insertEvent(event)
            if (event.hasReminder) {
                NotificationScheduler.scheduleActivityReminder(getApplication(), event.copy(id = id))
            }
        }
    }

    fun toggleEventCompleted(event: PlannerEvent) {
        viewModelScope.launch {
            repository.updateEvent(event.copy(isCompleted = !event.isCompleted))
        }
    }

    fun updatePlannerEvent(event: PlannerEvent) {
        viewModelScope.launch {
            repository.updateEvent(event)
            if (event.hasReminder && _isActivityNotifEnabled.value) {
                NotificationScheduler.scheduleActivityReminder(getApplication(), event)
            }
        }
    }

    fun deletePlannerEvent(event: PlannerEvent) {
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    fun sendTestNotification() {
        val context = getApplication<Application>()
        val lang = _currentLanguage.value
        val title: String = when (lang) {
            AppLanguage.JAVANESE -> "Pangeling Kalender Jawa Aktif!"
            AppLanguage.INDONESIAN -> "Pengingat Kalender Jawa Aktif!"
            AppLanguage.ENGLISH -> "Javanese Calendar Reminder Active!"
            else -> "Javanese Calendar Reminder Active!"
        }
        val message: String = when (lang) {
            AppLanguage.JAVANESE -> "Notifikasi otomatis pèngetan dinten tradisi & kagiyatan lumaku kanthi sae. Rahayu!"
            AppLanguage.INDONESIAN -> "Notifikasi otomatis hari besar, malam sakral & agenda Anda berfungsi dengan sempurna!"
            AppLanguage.ENGLISH -> "Automated Javanese traditional alerts and activities are running smoothly!"
            else -> "Automated Javanese traditional alerts and activities are running smoothly!"
        }
        NotificationHelper.showHolidayNotification(
            context = context,
            notificationId = 9999,
            title = title,
            message = message,
            details = StringResources.get("test_notification_success", lang)
        )
    }

    fun getPengetanSedaList(date: LocalDate): List<JavaneseCalendarEngine.PengetanSeda> {
        return JavaneseCalendarEngine.calculatePengetanSeda(date)
    }

    // --- Weton Kelahiran Calculation & Persistence ---
    private val initialBirthDate: LocalDate = prefs.userBirthDate?.let { str: String ->
        try { LocalDate.parse(str) } catch (e: Exception) { null }
    } ?: LocalDate.of(1995, 8, 17)

    private val _birthDateForWeton = MutableStateFlow(initialBirthDate)
    val birthDateForWeton: StateFlow<LocalDate> = _birthDateForWeton.asStateFlow()

    val wetonKelahiranResult: StateFlow<WetonKelahiran> = _birthDateForWeton.map { date ->
        WetonKelahiranEngine.calculate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WetonKelahiranEngine.calculate(initialBirthDate)
    )

    private val _savedUserBirthDate = MutableStateFlow<LocalDate?>(
        prefs.userBirthDate?.let { str: String ->
            try { LocalDate.parse(str) } catch (e: Exception) { null }
        }
    )
    val savedUserBirthDate: StateFlow<LocalDate?> = _savedUserBirthDate.asStateFlow()

    // Partner birth date for Jodoh petungan
    private val _partnerBirthDate = MutableStateFlow<LocalDate?>(null)
    val partnerBirthDate: StateFlow<LocalDate?> = _partnerBirthDate.asStateFlow()

    val partnerWetonResult: StateFlow<WetonKelahiran?> = _partnerBirthDate.map { date ->
        date?.let { WetonKelahiranEngine.calculate(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun setBirthDateForWeton(date: LocalDate) {
        _birthDateForWeton.value = date
    }

    fun saveUserBirthDate(date: LocalDate) {
        prefs.userBirthDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        _savedUserBirthDate.value = date
    }

    fun clearSavedUserBirthDate() {
        prefs.userBirthDate = null
        _savedUserBirthDate.value = null
    }

    fun setPartnerBirthDate(date: LocalDate?) {
        _partnerBirthDate.value = date
    }

    fun calculateWetonForDate(date: LocalDate): WetonKelahiran {
        return WetonKelahiranEngine.calculate(date)
    }

    fun hitungKecocokanJodoh(weton1: WetonKelahiran, weton2: WetonKelahiran): JodohPetunganResult {
        return WetonKelahiranEngine.hitungKecocokanJodoh(weton1, weton2)
    }

    // ==========================================
    // USADA JAWA (HERBAL & REMEDIES) STATE
    // ==========================================
    private val _favoriteUsadaIds = MutableStateFlow<Set<String>>(prefs.favoriteUsadaIds)
    val favoriteUsadaIds: StateFlow<Set<String>> = _favoriteUsadaIds.asStateFlow()

    fun toggleFavoriteUsada(recipeId: String) {
        val current = _favoriteUsadaIds.value.toMutableSet()
        if (current.contains(recipeId)) {
            current.remove(recipeId)
        } else {
            current.add(recipeId)
        }
        prefs.favoriteUsadaIds = current
        _favoriteUsadaIds.value = current
    }

    fun isUsadaFavorite(recipeId: String): Boolean {
        return _favoriteUsadaIds.value.contains(recipeId)
    }
}
