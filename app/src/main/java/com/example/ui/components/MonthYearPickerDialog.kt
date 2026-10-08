package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.calendar.JavaneseCalendarEngine
import com.example.localization.AppLanguage
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganMedium
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate
import java.time.YearMonth

/**
 * Material 3 Dialog for picking Month & Year with fast decade jumpers,
 * year grid picker, and live Javanese almanac context preview.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonthYearPickerDialog(
    initialYear: Int,
    initialMonth: Int,
    language: AppLanguage,
    onDismissRequest: () -> Unit,
    onConfirm: (year: Int, month: Int) -> Unit
) {
    var selectedYear by remember { mutableIntStateOf(initialYear.coerceIn(1900, 2100)) }
    var selectedMonth by remember { mutableIntStateOf(initialMonth.coerceIn(1, 12)) }
    var showYearGrid by remember { mutableStateOf(false) }

    val today = remember { LocalDate.now() }
    val yearsRange = remember { (1900..2100).toList() }
    val gridState = rememberLazyGridState()

    // Scroll to selected year when grid opens
    LaunchedEffect(showYearGrid) {
        if (showYearGrid) {
            val targetIndex = yearsRange.indexOf(selectedYear)
            if (targetIndex >= 0) {
                // Offset to bring the year roughly to the center of grid view
                gridState.scrollToItem((targetIndex - 6).coerceAtLeast(0))
            }
        }
    }

    // Live preview of Javanese almanac details for selected month & year
    val javanesePreview by remember(selectedYear, selectedMonth) {
        derivedStateOf {
            val ym = YearMonth.of(selectedYear, selectedMonth)
            val firstDate = ym.atDay(1)
            val lastDate = ym.atDay(ym.lengthOfMonth())
            val jStart = JavaneseCalendarEngine.fromLocalDate(firstDate)
            val jEnd = JavaneseCalendarEngine.fromLocalDate(lastDate)

            val monthSpan = if (jStart.monthCode == jEnd.monthCode) {
                "${jStart.day} - ${jEnd.day} ${jStart.monthNameId}"
            } else {
                "${jStart.day} ${jStart.monthNameId} - ${jEnd.day} ${jEnd.monthNameId}"
            }

            Triple(
                monthSpan,
                "${jStart.yearJavanese} AJ (${jStart.yearNameWindu})",
                "Windu ${jStart.winduName} • Wuku ${jStart.wukuName} .. ${jEnd.wukuName}"
            )
        }
    }

    val monthNames = when (language) {
        AppLanguage.JAVANESE -> listOf(
            "Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"
        )
        AppLanguage.INDONESIAN -> listOf(
            "Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"
        )
        AppLanguage.ENGLISH -> listOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )
    }

    val decadePresets = listOf(1945, 1965, 1980, 1990, 2000, 2010, 2020, 2026, 2030, 2045)

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header: Title & Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = KeratonGoldContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = SoganPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = when (language) {
                                    AppLanguage.JAVANESE -> "Pilih Wulan & Warsa"
                                    AppLanguage.INDONESIAN -> "Pilih Bulan & Tahun"
                                    AppLanguage.ENGLISH -> "Select Month & Year"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                            Text(
                                text = when (language) {
                                    AppLanguage.JAVANESE -> "Tanggal lampau utawa mangsa ngarep (1900 - 2100)"
                                    AppLanguage.INDONESIAN -> "Jelajahi tanggal lampau & masa depan (1900 - 2100)"
                                    AppLanguage.ENGLISH -> "Explore past and future dates (1900 - 2100)"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Year Navigation Controller Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = KremJawa),
                    border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = when (language) {
                                AppLanguage.JAVANESE -> "PILIHAN WARSA (TAHUN)"
                                AppLanguage.INDONESIAN -> "PILIHAN TAHUN"
                                AppLanguage.ENGLISH -> "SELECT YEAR"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = SoganMedium,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Year Stepper Row (-10, -1, YEAR DISPLAY, +1, +10)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    selectedYear = (selectedYear - 10).coerceAtLeast(1900)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("step_minus_10_years")
                            ) {
                                Icon(
                                    Icons.Default.KeyboardDoubleArrowLeft,
                                    contentDescription = "-10 Tahun",
                                    tint = SoganPrimary
                                )
                            }

                            IconButton(
                                onClick = {
                                    selectedYear = (selectedYear - 1).coerceAtLeast(1900)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("step_minus_1_year")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "-1 Tahun",
                                    tint = SoganPrimary
                                )
                            }

                            // Clickable Year Display Button to toggle year grid list
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SoganPrimary,
                                modifier = Modifier
                                    .clickable { showYearGrid = !showYearGrid }
                                    .testTag("toggle_year_grid_btn")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$selectedYear",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KeratonGold
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        if (showYearGrid) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Pilih dari daftar tahun",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    selectedYear = (selectedYear + 1).coerceAtMost(2100)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("step_plus_1_year")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "+1 Tahun",
                                    tint = SoganPrimary
                                )
                            }

                            IconButton(
                                onClick = {
                                    selectedYear = (selectedYear + 10).coerceAtMost(2100)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("step_plus_10_years")
                            ) {
                                Icon(
                                    Icons.Default.KeyboardDoubleArrowRight,
                                    contentDescription = "+10 Tahun",
                                    tint = SoganPrimary
                                )
                            }
                        }

                        // Expandable Year Grid Picker (1900..2100)
                        AnimatedVisibility(visible = showYearGrid) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                Text(
                                    text = "Ketuk tahun di bawah untuk memilih langsung:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(4),
                                    state = gridState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .background(Color.White, RoundedCornerShape(10.dp))
                                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(10.dp))
                                        .padding(6.dp),
                                    contentPadding = PaddingValues(4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    items(yearsRange) { year ->
                                        val isCurrent = year == selectedYear
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isCurrent) SoganPrimary else Color(0xFFF5F5F5),
                                            modifier = Modifier
                                                .clickable {
                                                    selectedYear = year
                                                    showYearGrid = false
                                                }
                                                .testTag("year_chip_$year")
                                        ) {
                                            Box(
                                                modifier = Modifier.padding(vertical = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "$year",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (isCurrent) KeratonGold else SoganDark
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Fast Decade / Historic Milestone Presets
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            decadePresets.forEach { presetYear ->
                                val isSelected = presetYear == selectedYear
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isSelected) KeratonGold else Color.White,
                                    border = BorderStroke(1.dp, if (isSelected) KeratonGold else Color(0xFFD7CCC8)),
                                    modifier = Modifier
                                        .clickable { selectedYear = presetYear }
                                        .testTag("decade_chip_$presetYear")
                                ) {
                                    Text(
                                        text = "$presetYear",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) SoganDark else SoganPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Month Selection (12 Months Grid)
                Text(
                    text = when (language) {
                        AppLanguage.JAVANESE -> "PILIHAN WULAN (BULAN)"
                        AppLanguage.INDONESIAN -> "PILIHAN BULAN"
                        AppLanguage.ENGLISH -> "SELECT MONTH"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = SoganMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (row in 0 until 4) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            for (col in 0 until 3) {
                                val monthIndex = row * 3 + col
                                val monthNumber = monthIndex + 1
                                val isSelected = monthNumber == selectedMonth
                                val isThisMonth = selectedYear == today.year && monthNumber == today.monthValue

                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedMonth = monthNumber }
                                        .testTag("month_btn_$monthNumber"),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) SoganPrimary
                                    else if (isThisMonth) KeratonGoldContainer
                                    else Color(0xFFF9F6F0),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) SoganPrimary
                                        else if (isThisMonth) KeratonGold
                                        else Color(0xFFEFEBE9)
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = monthNames[monthIndex].take(3),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) KeratonGold else SoganDark
                                        )
                                        Text(
                                            text = monthNames[monthIndex],
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 9.5.sp,
                                            color = if (isSelected) Color.White.copy(alpha = 0.85f) else Color.Gray,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Live Javanese Almanac Calculation Preview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer.copy(alpha = 0.7f)),
                    border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = SoganPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Pratinjau Almanak Jawa Periode Iki:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "• Sasi Jawa: ${javanesePreview.first}",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganDark,
                            fontSize = 11.5.sp
                        )
                        Text(
                            text = "• Warsa Jawa: ${javanesePreview.second}",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganDark,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "• ${javanesePreview.third}",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganMedium,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Bottom Buttons: "Hari Ini", "Batal", and "Terapkan"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Quick "Hari Ini" Button
                    OutlinedButton(
                        onClick = {
                            selectedYear = today.year
                            selectedMonth = today.monthValue
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("picker_jump_today_btn"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SoganPrimary
                        ),
                        border = BorderStroke(1.dp, SoganPrimary.copy(alpha = 0.6f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Today,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (language) {
                                AppLanguage.JAVANESE -> "Dina Iki"
                                AppLanguage.INDONESIAN -> "Hari Ini"
                                AppLanguage.ENGLISH -> "Today"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Apply / Confirm Button
                    Button(
                        onClick = {
                            onConfirm(selectedYear, selectedMonth)
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("apply_month_year_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoganPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = when (language) {
                                AppLanguage.JAVANESE -> "Terapaken"
                                AppLanguage.INDONESIAN -> "Terapkan"
                                AppLanguage.ENGLISH -> "Apply"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
