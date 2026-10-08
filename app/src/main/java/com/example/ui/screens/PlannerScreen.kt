package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.JavaneseCalendarEngine
import com.example.data.PlannerEvent
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.MainViewModel
import com.example.ui.components.AddEditEventDialog
import com.example.ui.components.JavaneseDatePickerDialog
import com.example.ui.components.JavaneseHeaderBanner
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PlannerScreen(
    viewModel: MainViewModel,
    onNavigateToCalendar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val selectedJavaneseDate by viewModel.selectedJavaneseDate.collectAsStateWithLifecycle()
    val eventsOnDate by viewModel.eventsOnSelectedDate.collectAsStateWithLifecycle()
    val allUpcomingEvents by viewModel.allUpcomingEvents.collectAsStateWithLifecycle()

    var showDatePicker by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var eventToEdit by remember { mutableStateOf<PlannerEvent?>(null) }
    var showSedaDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KremJawa)
    ) {
        // Top Banner (FIXED / TIDAK IKUT SCROLL NAIK-TURUN)
        JavaneseHeaderBanner(
            contentPaddingBottom = 12.dp
        ) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(KeratonGoldContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.EventNote,
                                contentDescription = null,
                                tint = SoganDark,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = StringResources.get("tab_planner", language),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Cathetan Weton, Slametan & Kagiyatan"
                                else if (language == AppLanguage.INDONESIAN) "Jadwal, Selapanan Weton & Hajatan"
                                else "Schedule, Weton Milestones & Events",
                                style = MaterialTheme.typography.bodySmall,
                                color = KeratonGold,
                                fontSize = 11.sp
                            )
                        }
                    }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onNavigateToCalendar,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = "Buka Kalender",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

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
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Date Selector Container (Fitur Ganti Tanggal untuk Rencana Kegiatan)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Tombol Hari Sebelumnya (<)
                    IconButton(
                        onClick = {
                            viewModel.selectDate(selectedDate.minusDays(1))
                        },
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.18f))
                            .testTag("planner_prev_day_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Hari Sebelumnya",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Tengah: Area Tanggal Terpilih (Dapat diketuk untuk membuka DatePicker)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { showDatePicker = true }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                            .testTag("planner_change_date_button"),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = selectedDate.format(
                                    DateTimeFormatter.ofPattern(
                                        "EEEE, d MMMM yyyy",
                                        when (language) {
                                            AppLanguage.JAVANESE -> Locale("id")
                                            AppLanguage.INDONESIAN -> Locale("id")
                                            AppLanguage.ENGLISH -> Locale.ENGLISH
                                        }
                                    )
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.5.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${selectedJavaneseDate.wetonName} (Neptu ${selectedJavaneseDate.neptuTotal}) • ${selectedJavaneseDate.day} ${selectedJavaneseDate.monthNameJv} ${selectedJavaneseDate.yearJavanese} AJ",
                            style = MaterialTheme.typography.bodySmall,
                            color = KeratonGold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.5.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Badge Indikator "Ganti Tanggal"
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = KeratonGoldContainer.copy(alpha = 0.28f),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = KeratonGold,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (language) {
                                    AppLanguage.JAVANESE -> "Gantos Tanggal"
                                    AppLanguage.INDONESIAN -> "Ganti Tanggal"
                                    AppLanguage.ENGLISH -> "Change Date"
                                },
                                color = Color.White,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Tombol Hari Berikutnya (>)
                    IconButton(
                        onClick = {
                            viewModel.selectDate(selectedDate.plusDays(1))
                        },
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.18f))
                            .testTag("planner_next_day_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Hari Berikutnya",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Tombol Pintas "Kembali ke Hari Ini" jika tanggal dipilih bukan hari ini
            if (selectedDate != LocalDate.now()) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = KeratonGoldContainer,
                        border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.6f)),
                        modifier = Modifier
                            .clickable { viewModel.goToToday() }
                            .testTag("planner_today_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Today,
                                contentDescription = null,
                                tint = SoganDark,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (language) {
                                    AppLanguage.JAVANESE -> "Wangsul dhateng Dinten Niki"
                                    AppLanguage.INDONESIAN -> "Kembali ke Hari Ini"
                                    AppLanguage.ENGLISH -> "Return to Today"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = SoganDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showAddDialog = true }
                            .testTag("add_event_main_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = SoganPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = StringResources.get("add_activity", language),
                                style = MaterialTheme.typography.labelLarge,
                                color = SoganPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = KeratonGoldContainer,
                        modifier = Modifier
                            .clickable { showSedaDialog = true }
                            .testTag("calculator_seda_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Calculate,
                                contentDescription = null,
                                tint = SoganDark,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Petungan Seda" else if (language == AppLanguage.INDONESIAN) "Hitung Slametan" else "Memorial Calc",
                                style = MaterialTheme.typography.labelLarge,
                                color = SoganDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
        }

        // SCROLLABLE CONTENT (HANYA BAGIAN INI YANG BERGERAK)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Section Title: Kegiatan untuk Tanggal Terpilih
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = StringResources.get("activities_title", language),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                        Text(
                            text = selectedDate.format(
                                DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", when (language) {
                                    AppLanguage.JAVANESE -> Locale("id")
                                    AppLanguage.INDONESIAN -> Locale("id")
                                    AppLanguage.ENGLISH -> Locale.ENGLISH
                                })
                            ) + " (${selectedJavaneseDate.wetonName})",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganPrimary,
                            fontSize = 11.5.sp
                        )
                    }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = KeratonGoldContainer
                ) {
                    Text(
                        text = "${eventsOnDate.size} kegiatan",
                        style = MaterialTheme.typography.labelSmall,
                        color = SoganDark,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }

        if (eventsOnDate.isEmpty()) {
            item {
                Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = StringResources.get("no_activities", language),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF8D6E63)
                            )
                        }
                    }
                }
            }
        } else {
            items(eventsOnDate) { event ->
                Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                    PlannerEventCardBright(
                        event = event,
                        language = language,
                        onToggleComplete = { viewModel.toggleEventCompleted(event) },
                        onEdit = { eventToEdit = event },
                        onDelete = { viewModel.deletePlannerEvent(event) }
                    )
                }
            }
        }

        // Section: Semua Agenda Rencana Mendatang
        if (allUpcomingEvents.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = StringResources.get("all_plans", language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = SoganPrimary
                    )
                }
            }

            items(allUpcomingEvents.filter { it.gregorianDate != selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) }) { event ->
                Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                    PlannerEventCardBright(
                        event = event,
                        language = language,
                        showDate = true,
                        onToggleComplete = { viewModel.toggleEventCompleted(event) },
                        onEdit = { eventToEdit = event },
                        onDelete = { viewModel.deletePlannerEvent(event) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
    }

    if (showDatePicker) {
        JavaneseDatePickerDialog(
            initialDate = selectedDate,
            language = language,
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { newDate ->
                viewModel.selectDate(newDate)
                showDatePicker = false
            }
        )
    }

    if (showAddDialog) {
        val datePattern = when (language) {
            AppLanguage.JAVANESE -> "EEEE, d MMMM yyyy"
            AppLanguage.INDONESIAN -> "EEEE, d MMMM yyyy"
            AppLanguage.ENGLISH -> "EEEE, MMMM d, yyyy"
        }
        val locale = when (language) {
            AppLanguage.JAVANESE -> Locale("id")
            AppLanguage.INDONESIAN -> Locale("id")
            AppLanguage.ENGLISH -> Locale.ENGLISH
        }
        val formattedDateStr = selectedDate.format(DateTimeFormatter.ofPattern(datePattern, locale))
        AddEditEventDialog(
            dateLabel = "$formattedDateStr (${selectedJavaneseDate.wetonName})",
            language = language,
            onDismiss = { showAddDialog = false },
            onSave = { title, desc, time, category, hasReminder ->
                viewModel.addPlannerEvent(title, desc, time, category, hasReminder)
                showAddDialog = false
            }
        )
    }

    eventToEdit?.let { event ->
        AddEditEventDialog(
            initialEvent = event,
            dateLabel = event.gregorianDate + " (" + event.hebrewDateString + ")",
            language = language,
            onDismiss = { eventToEdit = null },
            onSave = { title, desc, time, category, hasReminder ->
                viewModel.updatePlannerEvent(
                    event.copy(
                        title = title,
                        description = desc,
                        time = time,
                        category = category,
                        hasReminder = hasReminder
                    )
                )
                eventToEdit = null
            }
        )
    }

    if (showSedaDialog) {
        val sedaList = remember(selectedDate) {
            viewModel.getPengetanSedaList(selectedDate)
        }
        AlertDialog(
            onDismissRequest = { showSedaDialog = false },
            title = {
                Column {
                    Text(
                        text = StringResources.get("calculator_seda_title", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SoganDark
                    )
                    Text(
                        text = "Tanggal Wiwitan: ${selectedDate.format(DateTimeFormatter.ofPattern("d MMM yyyy"))} (${selectedJavaneseDate.wetonName})",
                        style = MaterialTheme.typography.bodySmall,
                        color = SoganPrimary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sedaList.forEach { item ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KremJawa,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (language == AppLanguage.JAVANESE) item.labelJv else item.labelId,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SoganDark
                                    )
                                    Text(
                                        text = "${item.javaneseDate.wetonName} • ${item.javaneseDate.day} ${item.javaneseDate.monthNameJv} ${item.javaneseDate.yearJavanese} AJ",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SoganPrimary
                                    )
                                }
                                Text(
                                    text = item.targetDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6D4C41)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSedaDialog = false }) {
                    Text(StringResources.get("close", language), color = SoganPrimary)
                }
            }
        )
    }
}

@Composable
fun PlannerEventCardBright(
    event: PlannerEvent,
    language: AppLanguage,
    showDate: Boolean = false,
    onToggleComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (event.isCompleted) Color(0xFFF5F5F4) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (showDate) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = event.gregorianDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = SoganPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = event.hebrewDateString,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFD97706),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = event.isCompleted,
                    onCheckedChange = { onToggleComplete() }
                )

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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (event.hasReminder) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Reminder",
                            tint = Color(0xFFD97706),
                            modifier = Modifier
                                .size(18.dp)
                                .padding(end = 4.dp)
                        )
                    }
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = SoganPrimary, modifier = Modifier.size(18.dp))
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = BataMerah, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}
