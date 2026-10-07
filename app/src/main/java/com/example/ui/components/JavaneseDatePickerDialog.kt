package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.JavaneseCalendarEngine
import com.example.calendar.JavaneseDate
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganLight
import com.example.ui.theme.SoganMedium
import com.example.ui.theme.SoganPrimary
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Material 3 Date Picker Component tailored for Javanese Calendar Calculations.
 *
 * Features:
 * - Material 3 DatePicker with custom Javanese Keraton & Sogan styling
 * - Live dynamic preview of Javanese date, Weton, Neptu, Pasaran, Sultan Agungan year, Wuku, and Pranata Mangsa
 * - Indicator for traditional holidays/sacred events falling on the selected date
 * - Quick "Dinten Niki / Hari Ini" shortcut
 * - Full multi-language support (Javanese, Indonesian, English)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JavaneseDatePickerDialog(
    initialDate: LocalDate = LocalDate.now(),
    title: String? = null,
    language: AppLanguage = AppLanguage.INDONESIAN,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val initialMillis = remember(initialDate) {
        initialDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis
    )

    // Derived active LocalDate from picker state
    val selectedLocalDate by remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let { millis ->
                Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
            } ?: initialDate
        }
    }

    // Derived Javanese date details for live preview
    val selectedJavaneseDate by remember {
        derivedStateOf {
            JavaneseCalendarEngine.fromLocalDate(selectedLocalDate)
        }
    }

    // Traditional holidays on selected date (if any)
    val holidaysOnDate by remember {
        derivedStateOf {
            JavaneseCalendarEngine.getHolidaysForDate(selectedLocalDate)
        }
    }

    val dialogTitle = title ?: when (language) {
        AppLanguage.JAVANESE -> "Pilih Tanggal Petungan Jawa"
        AppLanguage.INDONESIAN -> "Pilih Tanggal Perhitungan Jawa"
        AppLanguage.ENGLISH -> "Select Date for Javanese Calculation"
    }

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedLocalDate)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SoganPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .testTag("date_picker_confirm_button")
                    .padding(end = 8.dp, bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = KeratonGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = when (language) {
                        AppLanguage.JAVANESE -> "Pilih Tanggal"
                        AppLanguage.INDONESIAN -> "Pilih Tanggal"
                        AppLanguage.ENGLISH -> "Select Date"
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismissRequest,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, SoganLight.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = SoganPrimary),
                modifier = Modifier
                    .testTag("date_picker_dismiss_button")
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = StringResources.get("cancel", language),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = KremJawa
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.testTag("javanese_date_picker_dialog")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. TOP SOGAN HEADER WITH LIVE JAVANESE PREVIEW
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SoganDark,
                                SoganPrimary,
                                Color(0xFF6D4C41)
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = dialogTitle,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        IconButton(
                            onClick = onDismissRequest,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Tutup",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Gregorian Date Display
                    Text(
                        text = selectedLocalDate.format(
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
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Live Weton & Neptu Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                color = KeratonGold.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = KeratonGold,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${selectedJavaneseDate.wetonName} • Neptu ${selectedJavaneseDate.neptuTotal}",
                            fontWeight = FontWeight.ExtraBold,
                            color = KeratonGold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = " (${selectedJavaneseDate.pasaran.aksara})",
                            color = Color(0xFFFFE082),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Javanese Sultan Agungan Year
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${selectedJavaneseDate.day} ${selectedJavaneseDate.monthNameJv} ${selectedJavaneseDate.yearJavanese} AJ (${selectedJavaneseDate.yearNameWindu}) • Windu ${selectedJavaneseDate.winduName}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.95f)
                        )
                    }

                    // Wuku & Mangsa
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bedtime,
                            contentDescription = null,
                            tint = Color(0xFFFFE082),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Wuku ${selectedJavaneseDate.wukuName} (${selectedJavaneseDate.wukuNumber}) • Mangsa ${selectedJavaneseDate.pranataMangsa.name}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFFFE082)
                        )
                    }

                    // Holiday / Traditional Sacred Alert if detected
                    if (holidaysOnDate.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = BataMerah.copy(alpha = 0.35f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = Color(0xFFFFCDD2),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = holidaysOnDate.first().holiday.getName(language),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Quick Shortcut Row (Hari Ini)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (language) {
                        AppLanguage.JAVANESE -> "Pilih dinten ing kalender:"
                        AppLanguage.INDONESIAN -> "Pilih tanggal di kalender:"
                        AppLanguage.ENGLISH -> "Pick a date on calendar:"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = SoganMedium,
                    fontWeight = FontWeight.Medium
                )

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = KeratonGoldContainer,
                    border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.5f)),
                    modifier = Modifier.clickable {
                        val todayMillis = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                        datePickerState.selectedDateMillis = todayMillis
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Today,
                            contentDescription = null,
                            tint = SoganDark,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (language) {
                                AppLanguage.JAVANESE -> "Dinten Niki"
                                AppLanguage.INDONESIAN -> "Hari Ini"
                                AppLanguage.ENGLISH -> "Today"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                    }
                }
            }

            // 2. MATERIAL 3 DATE PICKER CALENDAR GRID
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = KremJawa,
                    titleContentColor = SoganDark,
                    headlineContentColor = SoganDark,
                    weekdayContentColor = SoganMedium,
                    subheadContentColor = SoganDark,
                    yearContentColor = SoganDark,
                    currentYearContentColor = KeratonGold,
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = SoganPrimary,
                    dayContentColor = SoganDark,
                    disabledDayContentColor = Color.LightGray,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = SoganPrimary,
                    disabledSelectedDayContentColor = Color.White,
                    disabledSelectedDayContainerColor = SoganLight,
                    todayDateBorderColor = KeratonGold,
                    todayContentColor = SoganDark
                ),
                showModeToggle = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("material3_date_picker")
            )
        }
    }
}
