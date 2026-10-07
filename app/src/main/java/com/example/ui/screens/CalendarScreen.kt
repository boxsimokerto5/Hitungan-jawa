package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.calendar.JavaneseCalendarEngine
import com.example.calendar.JavaneseHoliday
import com.example.calendar.JavaneseHolidayCategory
import com.example.calendar.JavaneseHolidayInstance
import com.example.calendar.Pasaran
import com.example.data.PlannerEvent
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.MainViewModel
import com.example.ui.components.AddEditEventDialog
import com.example.ui.components.HolidayDetailDialog
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganLight
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    viewModel: MainViewModel,
    onNavigateToWeton: (LocalDate) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val selectedJavaneseDate by viewModel.selectedJavaneseDate.collectAsStateWithLifecycle()
    val viewYear by viewModel.viewYear.collectAsStateWithLifecycle()
    val viewMonth by viewModel.viewMonth.collectAsStateWithLifecycle()
    val holidaysOnDate by viewModel.holidaysOnSelectedDate.collectAsStateWithLifecycle()
    val eventsOnDate by viewModel.eventsOnSelectedDate.collectAsStateWithLifecycle()
    val datesWithEvents by viewModel.datesWithEvents.collectAsStateWithLifecycle()

    var showAddEventDialog by remember { mutableStateOf(false) }
    var holidayToShowDetail by remember { mutableStateOf<JavaneseHoliday?>(null) }
    var holidayInstanceDetail by remember { mutableStateOf<JavaneseHolidayInstance?>(null) }
    var showHintBanner by remember { mutableStateOf(true) }

    val currentYearMonth = YearMonth.of(viewYear, viewMonth)
    val daysInMonth = currentYearMonth.lengthOfMonth()
    val firstDayOfMonth = currentYearMonth.atDay(1)
    val startDayOffset = (firstDayOfMonth.dayOfWeek.value % 7) // 0 = Sunday, 6 = Saturday

    val firstJavaneseDate = remember(viewYear, viewMonth) {
        JavaneseCalendarEngine.fromLocalDate(firstDayOfMonth)
    }
    val lastJavaneseDate = remember(viewYear, viewMonth) {
        JavaneseCalendarEngine.fromLocalDate(currentYearMonth.atDay(daysInMonth))
    }

    val javaneseMonthHeader = remember(firstJavaneseDate, lastJavaneseDate, language) {
        val name1 = when (language) {
            AppLanguage.JAVANESE -> firstJavaneseDate.monthNameJv
            AppLanguage.INDONESIAN -> firstJavaneseDate.monthNameId
            AppLanguage.ENGLISH -> firstJavaneseDate.monthNameEn
        }
        val name2 = when (language) {
            AppLanguage.JAVANESE -> lastJavaneseDate.monthNameJv
            AppLanguage.INDONESIAN -> lastJavaneseDate.monthNameId
            AppLanguage.ENGLISH -> lastJavaneseDate.monthNameEn
        }
        if (firstJavaneseDate.monthCode == lastJavaneseDate.monthCode) {
            "${firstJavaneseDate.day} $name1 - ${lastJavaneseDate.day} $name1 ${firstJavaneseDate.yearJavanese} AJ (${firstJavaneseDate.yearNameWindu})"
        } else {
            "${firstJavaneseDate.day} $name1 - ${lastJavaneseDate.day} $name2 ${lastJavaneseDate.yearJavanese} AJ (${lastJavaneseDate.yearNameWindu})"
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(KremJawa),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 1. SOGAN & KERATON GOLD TOP BANNER
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SoganDark,
                                SoganPrimary,
                                Color(0xFF6D4C41),
                                Color(0xFF795548)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Column {
                    // Profile & Top Icon Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_hitungan_jawa_logo_1791383285970),
                                contentDescription = "App Logo",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Rahayu! Almanak Jawa"
                                    else if (language == AppLanguage.INDONESIAN) "Rahayu! Kalender Jawa"
                                    else "Javanese Calendar & Weton",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Sultan Agungan • Weton • Pawukon",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = KeratonGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    val nextLang = when (language) {
                                        AppLanguage.JAVANESE -> AppLanguage.INDONESIAN
                                        AppLanguage.INDONESIAN -> AppLanguage.ENGLISH
                                        AppLanguage.ENGLISH -> AppLanguage.JAVANESE
                                    }
                                    viewModel.setLanguage(nextLang)
                                },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Text(
                                    text = when (language) {
                                        AppLanguage.JAVANESE -> "JV"
                                        AppLanguage.INDONESIAN -> "ID"
                                        AppLanguage.ENGLISH -> "EN"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = KeratonGold
                                )
                            }

                            IconButton(
                                onClick = { showAddEventDialog = true },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    Icons.Default.EditNote,
                                    contentDescription = "Catat Jadwal",
                                    tint = Color.White
                                )
                            }

                            IconButton(
                                onClick = { viewModel.goToToday() },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    Icons.Default.Today,
                                    contentDescription = "Hari Ini",
                                    tint = KeratonGold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Gregorian Date in Big White Font
                    Text(
                        text = selectedDate.format(
                            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", when (language) {
                                AppLanguage.JAVANESE -> Locale("id")
                                AppLanguage.INDONESIAN -> Locale("id")
                                AppLanguage.ENGLISH -> Locale.ENGLISH
                            })
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Prominent Weton & Neptu Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = "Weton",
                            tint = KeratonGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${selectedJavaneseDate.wetonName} • Neptu ${selectedJavaneseDate.neptuTotal}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = KeratonGold
                        )
                        Text(
                            text = " (${selectedJavaneseDate.pasaran.aksara})",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFFFE082),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Javanese Sultan Agungan Date
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.WbSunny,
                            contentDescription = "Tanggal Jawa",
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${selectedJavaneseDate.day} ${selectedJavaneseDate.monthNameJv} ${selectedJavaneseDate.yearJavanese} AJ (${selectedJavaneseDate.yearNameWindu}) • Windu ${selectedJavaneseDate.winduName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.95f),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Wuku & Pranata Mangsa subtitle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.Bedtime,
                            contentDescription = "Wuku & Mangsa",
                            tint = Color(0xFFFFE082),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Wuku ${selectedJavaneseDate.wukuName} (${selectedJavaneseDate.wukuNumber}) • Mangsa ${selectedJavaneseDate.pranataMangsa.name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFFE082),
                            fontSize = 11.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick Actions Row: Catat Jadwal & Cek Weton Kelahiran
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier
                                .clickable { showAddEventDialog = true }
                                .testTag("banner_catat_jadwal")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Cathet Jadwal"
                                    else if (language == AppLanguage.INDONESIAN) "Catat Jadwal"
                                    else "Add Schedule",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = KeratonGold.copy(alpha = 0.35f),
                            modifier = Modifier
                                .clickable { onNavigateToWeton(selectedDate) }
                                .testTag("banner_cek_weton")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = KeratonGold,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Cek Weton Lair"
                                    else if (language == AppLanguage.INDONESIAN) "Cek Weton Kelahiran"
                                    else "Check Weton",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. HELPFUL INFO STRIP
        if (showHintBanner) {
            item {
                Surface(
                    color = SoganPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Tutul tanggal kalender kanggo mirsani weton, neptu, wuku, mangsa & tradisi"
                            else if (language == AppLanguage.INDONESIAN) "Ketuk tanggal pada kalender untuk melihat weton, neptu, wuku, mangsa & tradisi"
                            else "Tap any date to inspect weton, neptu, wuku, season & Javanese traditions",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            fontSize = 11.sp
                        )
                        IconButton(
                            onClick = { showHintBanner = false },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close hint",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. MONTH & YEAR NAVIGATION HEADER
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.prevMonth() },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("prev_month_btn")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous Month",
                            tint = SoganDark
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentYearMonth.month.getDisplayName(
                                TextStyle.FULL,
                                when (language) {
                                    AppLanguage.JAVANESE -> Locale("id")
                                    AppLanguage.INDONESIAN -> Locale("id")
                                    AppLanguage.ENGLISH -> Locale.ENGLISH
                                }
                            ).uppercase() + " $viewYear",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = SoganDark,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = javaneseMonthHeader,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF6D4C41)
                        )
                    }

                    IconButton(
                        onClick = { viewModel.nextMonth() },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("next_month_btn")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next Month",
                            tint = SoganDark
                        )
                    }
                }
            }
        }

        // 4. COLORFUL PILL DAY HEADERS (MIN, SEN, SEL, RAB, KAM, JUM, SAB)
        item {
            val weekDayPills = when (language) {
                AppLanguage.JAVANESE -> listOf(
                    Triple("RAD", BataMerah, "Radite (Minggu)"),
                    Triple("SOM", Color(0xFF5D4037), "Soma (Senen)"),
                    Triple("ANG", Color(0xFF5D4037), "Anggara (Selasa)"),
                    Triple("BUD", Color(0xFF5D4037), "Buda (Rebo)"),
                    Triple("RES", Color(0xFF5D4037), "Respati (Kemis)"),
                    Triple("SUK", Color(0xFF2E7D32), "Sukra (Jemuwah)"),
                    Triple("TUM", Color(0xFF1565C0), "Tumpak (Setu)")
                )
                AppLanguage.INDONESIAN -> listOf(
                    Triple("MIN", BataMerah, "Minggu"),
                    Triple("SEN", Color(0xFF5D4037), "Senin"),
                    Triple("SEL", Color(0xFF5D4037), "Selasa"),
                    Triple("RAB", Color(0xFF5D4037), "Rabu"),
                    Triple("KAM", Color(0xFF5D4037), "Kamis"),
                    Triple("JUM", Color(0xFF2E7D32), "Jumat"),
                    Triple("SAB", Color(0xFF1565C0), "Sabtu")
                )
                AppLanguage.ENGLISH -> listOf(
                    Triple("SUN", BataMerah, "Sunday"),
                    Triple("MON", Color(0xFF5D4037), "Monday"),
                    Triple("TUE", Color(0xFF5D4037), "Tuesday"),
                    Triple("WED", Color(0xFF5D4037), "Wednesday"),
                    Triple("THU", Color(0xFF5D4037), "Thursday"),
                    Triple("FRI", Color(0xFF2E7D32), "Friday"),
                    Triple("SAT", Color(0xFF1565C0), "Saturday")
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                weekDayPills.forEach { (label, pillColor, _) ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(26.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = pillColor
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // 5. THE JAVANESE WALL CALENDAR GRID
        item {
            val totalCells = startDayOffset + daysInMonth
            val totalRows = (totalCells + 6) / 7
            val today = LocalDate.now()

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (rowIndex in 0 until totalRows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            for (colIndex in 0 until 7) {
                                val cellIndex = rowIndex * 7 + colIndex
                                val dayNumber = cellIndex - startDayOffset + 1

                                if (dayNumber in 1..daysInMonth) {
                                    val cellDate = currentYearMonth.atDay(dayNumber)
                                    val cellJavanese = JavaneseCalendarEngine.fromLocalDate(cellDate)
                                    val isSelected = cellDate == selectedDate
                                    val isToday = cellDate == today
                                    val cellHolidays = JavaneseCalendarEngine.getHolidaysForDate(cellDate)
                                    val isSunday = colIndex == 0
                                    val isFriday = colIndex == 5
                                    val isSaturday = colIndex == 6
                                    val isKliwon = cellJavanese.pasaran == Pasaran.KLIWON
                                    val isJumatKliwon = isFriday && isKliwon
                                    val isSelasaKliwon = colIndex == 2 && isKliwon
                                    val hasUserEvents = datesWithEvents.contains(cellDate.format(DateTimeFormatter.ISO_LOCAL_DATE))

                                    val numberColor = when {
                                        isSunday || cellHolidays.any { it.holiday.category == JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA } -> BataMerah
                                        isJumatKliwon || isSelasaKliwon -> Color(0xFF7B1FA2)
                                        isFriday -> Color(0xFF2E7D32)
                                        isSaturday -> Color(0xFF1565C0)
                                        else -> SoganDark
                                    }

                                    val subText = when {
                                        isJumatKliwon -> "J.Kliwon"
                                        isSelasaKliwon -> "Sl.Kliwon"
                                        cellHolidays.isNotEmpty() -> {
                                            val primary = cellHolidays.first().holiday
                                            when (primary.id) {
                                                "satu_sura" -> "1 Sura"
                                                "sepuluh_sura" -> "10 Sura"
                                                "rebo_wekasan" -> "R.Wekasan"
                                                "sekaten_miyos_gongso" -> "Sekaten"
                                                "grebeg_mulud" -> "Gr.Mulud"
                                                "rajaban" -> "Rajaban"
                                                "ruwahan" -> "Ruwahan"
                                                "megengan_pasa" -> "Megengan"
                                                "maleman_selikuran" -> "Selikuran"
                                                "grebeg_sawal" -> "Gr.Sawal"
                                                "bakda_kupat" -> "Kupatan"
                                                "grebeg_besar" -> "Gr.Besar"
                                                else -> cellJavanese.pasaran.idName
                                            }
                                        }
                                        else -> "${cellJavanese.day} ${cellJavanese.pasaran.idName}"
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(0.85f)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                when {
                                                    isSelected -> KeratonGoldContainer
                                                    isToday -> Color(0xFFFFF9C4)
                                                    isJumatKliwon || isSelasaKliwon -> Color(0xFFF3E5F5)
                                                    else -> Color.Transparent
                                                }
                                            )
                                            .border(
                                                width = if (isSelected) 1.5.dp else if (isToday) 1.dp else 0.dp,
                                                color = if (isSelected) KeratonGold else if (isToday) Color(0xFFFBC02D) else Color.Transparent,
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .clickable {
                                                viewModel.selectDate(cellDate)
                                            }
                                            .testTag("wall_calendar_day_$dayNumber"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 2.dp, vertical = 2.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            // Top Row: Gregorian Number + Javanese Script Numerals
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.Top
                                            ) {
                                                Text(
                                                    text = dayNumber.toString(),
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontSize = 16.sp,
                                                    color = numberColor
                                                )

                                                Text(
                                                    text = cellJavanese.javaneseDayAksara,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 9.5.sp,
                                                    color = if (isJumatKliwon || isSelasaKliwon) Color(0xFF7B1FA2) else SoganLight,
                                                    modifier = Modifier.padding(start = 1.dp, top = 0.dp)
                                                )
                                            }

                                            // Bottom Subtitle: Pasaran / Weton / Hari Adat
                                            Text(
                                                text = subText,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontSize = 8.5.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontWeight = if (cellHolidays.isNotEmpty() || isJumatKliwon) FontWeight.Bold else FontWeight.Normal,
                                                color = if (cellHolidays.isNotEmpty() || isJumatKliwon) numberColor else Color(0xFF795548),
                                                textAlign = TextAlign.Center
                                            )

                                            // Planned Event Dot Indicator
                                            if (hasUserEvents) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(5.dp)
                                                        .clip(CircleShape)
                                                        .background(SoganPrimary)
                                                )
                                            } else {
                                                Spacer(modifier = Modifier.height(2.dp))
                                            }
                                        }
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. SELECTED DATE DETAIL CARD (Weton, Neptu, Wuku, Pranata Mangsa)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .testTag("javanese_selected_info_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${selectedJavaneseDate.wetonName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                            Text(
                                text = "${selectedJavaneseDate.day} ${selectedJavaneseDate.monthNameJv} ${selectedJavaneseDate.yearJavanese} AJ (${selectedJavaneseDate.yearNameWindu})",
                                style = MaterialTheme.typography.bodySmall,
                                color = SoganPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = KeratonGoldContainer
                        ) {
                            Text(
                                text = "Neptu ${selectedJavaneseDate.neptuTotal} (${selectedJavaneseDate.dayOfWeekNeptu} + ${selectedJavaneseDate.pasaran.neptu})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            color = KremJawa
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "Pawukon / Wuku:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoganLight
                                )
                                Text(
                                    text = "${selectedJavaneseDate.wukuName} (${selectedJavaneseDate.wukuNumber})",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            color = KremJawa
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "Pranata Mangsa:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoganLight
                                )
                                Text(
                                    text = selectedJavaneseDate.pranataMangsa.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Candramengku: \"${selectedJavaneseDate.pranataMangsa.candramengku}\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6D4C41),
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = KeratonGoldContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onNavigateToWeton(selectedDate) }
                            .testTag("btn_cek_weton_lengkap")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = SoganPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${StringResources.get("check_weton_quick", language)}: ${selectedJavaneseDate.wetonName}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                            }
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = SoganPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // 7. BOTTOM "CATAT JADWAL" CARD
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clickable { showAddEventDialog = true }
                    .testTag("catat_jadwal_box")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(SoganPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Catat Jadwal",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Cathet Jadwal / Slametan"
                            else if (language == AppLanguage.INDONESIAN) "Catat Jadwal / Rencana Kegiatan"
                            else "Record Activity / Schedule",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganPrimary
                        )
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Tutul ing kene kanggo nambah rancangan ing tanggal iki"
                            else if (language == AppLanguage.INDONESIAN) "Silakan ketuk di sini untuk tanggal terpilih"
                            else "Tap here to plan for selected date",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF8D6E63)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = KeratonGoldContainer
                    ) {
                        Text(
                            text = selectedDate.format(DateTimeFormatter.ofPattern("d MMM")),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 8. HOLIDAYS / TRADITIONS ON SELECTED DATE
        if (holidaysOnDate.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = StringResources.get("javanese_holidays", language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = BataMerah
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    holidaysOnDate.forEach { instance ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable {
                                    holidayToShowDetail = instance.holiday
                                    holidayInstanceDetail = instance
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Celebration,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = instance.holiday.getName(language),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SoganDark
                                    )
                                    Text(
                                        text = instance.holiday.getCategoryName(language),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SoganPrimary
                                    )
                                }
                                Text(
                                    text = StringResources.get("view_details", language),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoganPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 9. SCHEDULED ACTIVITIES FOR SELECTED DATE
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = StringResources.get("activities_title", language) + " (${eventsOnDate.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
            }
        }

        if (eventsOnDate.isEmpty()) {
            item {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = StringResources.get("no_activities", language),
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganLight
                        )
                    }
                }
            }
        } else {
            items(eventsOnDate) { event ->
                Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                    EventItemCard(
                        event = event,
                        language = language,
                        onToggleComplete = { viewModel.toggleEventCompleted(event) },
                        onDelete = { viewModel.deletePlannerEvent(event) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showAddEventDialog) {
        AddEditEventDialog(
            dateLabel = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + " (" + selectedJavaneseDate.wetonName + ")",
            language = language,
            onDismiss = { showAddEventDialog = false },
            onSave = { title, desc, time, category, hasReminder ->
                viewModel.addPlannerEvent(title, desc, time, category, hasReminder)
                showAddEventDialog = false
            }
        )
    }

    holidayToShowDetail?.let { holiday ->
        HolidayDetailDialog(
            holiday = holiday,
            instance = holidayInstanceDetail,
            language = language,
            onDismiss = {
                holidayToShowDetail = null
                holidayInstanceDetail = null
            }
        )
    }
}

@Composable
fun EventItemCard(
    event: PlannerEvent,
    language: AppLanguage,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (event.isCompleted) Color(0xFFF5F5F4) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = event.isCompleted,
                onCheckedChange = { onToggleComplete() }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (event.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (event.isCompleted) Color(0xFF9E9E9E) else SoganDark
                )
                if (event.time.isNotEmpty()) {
                    Text(
                        text = event.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = SoganPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (event.description.isNotEmpty()) {
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6D4C41)
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = BataMerah, modifier = Modifier.size(18.dp))
            }
        }
    }
}
