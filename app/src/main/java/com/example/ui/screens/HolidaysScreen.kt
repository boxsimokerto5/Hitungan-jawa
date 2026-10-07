package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.calendar.JavaneseHoliday
import com.example.calendar.JavaneseHolidayCategory
import com.example.calendar.JavaneseHolidayInstance
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.JavaneseHolidayFilterTab
import com.example.ui.MainViewModel
import com.example.ui.components.HolidayDetailDialog
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun HolidaysScreen(
    viewModel: MainViewModel,
    onNavigateToDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val upcomingHolidays by viewModel.upcomingHolidays.collectAsStateWithLifecycle()
    val currentFilter by viewModel.holidayFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    var holidayToShowDetail by remember { mutableStateOf<JavaneseHoliday?>(null) }
    var holidayInstanceDetail by remember { mutableStateOf<JavaneseHolidayInstance?>(null) }

    val today = LocalDate.now()

    val filteredList = remember(upcomingHolidays, currentFilter, searchQuery, language) {
        upcomingHolidays.filter { instance ->
            val h = instance.holiday
            val matchesFilter = when (currentFilter) {
                JavaneseHolidayFilterTab.ALL -> true
                JavaneseHolidayFilterTab.KERATON -> h.category == JavaneseHolidayCategory.TRADISI_KERATON
                JavaneseHolidayFilterTab.ISLAM_JAWA -> h.category == JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA
                JavaneseHolidayFilterTab.SAKRAL -> h.category == JavaneseHolidayCategory.MALAM_SAKRAL
            }
            val matchesSearch = if (searchQuery.isBlank()) true else {
                h.nameEn.contains(searchQuery, ignoreCase = true) ||
                h.nameId.contains(searchQuery, ignoreCase = true) ||
                h.nameJv.contains(searchQuery, ignoreCase = true) ||
                h.getDescription(language).contains(searchQuery, ignoreCase = true)
            }
            matchesFilter && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(KremJawa),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Top Sogan & Keraton Gold Header
        item {
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
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 12.dp)
            ) {
                Column {
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
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = StringResources.get("tab_holidays", language),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Grebeg, Sekaten, Suran & Dinten Sakral"
                                    else if (language == AppLanguage.INDONESIAN) "Grebeg, Sekaten, Suran & Malam Sakral"
                                    else "Royal Traditions, Festivals & Sacred Vigils",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = KeratonGold,
                                    fontSize = 11.sp
                                )
                            }
                        }

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

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = {
                            Text(
                                StringResources.get("search_holidays_hint", language),
                                color = Color(0xFF8D6E63),
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = SoganPrimary)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Hapus pencarian",
                                        tint = SoganPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = KeratonGold,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("holiday_search_input"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Filter chips bar
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = currentFilter == JavaneseHolidayFilterTab.ALL,
                        onClick = { viewModel.setHolidayFilter(JavaneseHolidayFilterTab.ALL) },
                        label = { Text(StringResources.get("filter_all", language), fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SoganPrimary,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_all_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == JavaneseHolidayFilterTab.KERATON,
                        onClick = { viewModel.setHolidayFilter(JavaneseHolidayFilterTab.KERATON) },
                        label = { Text(StringResources.get("filter_keraton", language), fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFD97706),
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_keraton_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == JavaneseHolidayFilterTab.ISLAM_JAWA,
                        onClick = { viewModel.setHolidayFilter(JavaneseHolidayFilterTab.ISLAM_JAWA) },
                        label = { Text(StringResources.get("filter_islam_jawa", language), fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BataMerah,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_islam_jawa_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == JavaneseHolidayFilterTab.SAKRAL,
                        onClick = { viewModel.setHolidayFilter(JavaneseHolidayFilterTab.SAKRAL) },
                        label = { Text(StringResources.get("filter_sakral", language), fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF7B1FA2),
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_sakral_chip")
                    )
                }
            }
        }

        // Summary count and status
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == AppLanguage.JAVANESE) "Dhaftar Dina Luhur (${filteredList.size})"
                    else if (language == AppLanguage.INDONESIAN) "Daftar Hari Besar & Sakral (${filteredList.size})"
                    else "Holidays & Traditions (${filteredList.size})",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Text(
                    text = if (language == AppLanguage.JAVANESE) "Tutul kanggo rincian"
                    else if (language == AppLanguage.INDONESIAN) "Ketuk untuk rincian & makna"
                    else "Tap for details",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = Color(0xFF8D6E63)
                )
            }
        }

        // Empty state when search or filter returns 0 results
        if (filteredList.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(KeratonGoldContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.EventAvailable,
                                contentDescription = null,
                                tint = SoganPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Ora ana dinten ageng ingkang cocog"
                            else if (language == AppLanguage.INDONESIAN) "Tidak ada hari besar yang cocok dengan pencarian"
                            else "No holidays found matching your query",
                            fontWeight = FontWeight.Bold,
                            color = SoganDark,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Coba atur tembung padosan utawi gantos filter kategori."
                            else if (language == AppLanguage.INDONESIAN) "Coba ubah kata kunci atau ganti filter kategori di atas."
                            else "Try clearing your search query or selecting another filter.",
                            color = Color(0xFF8D6E63),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // List of holidays & traditions
        items(filteredList) { instance ->
            val holiday = instance.holiday
            val daysUntil = ChronoUnit.DAYS.between(today, instance.gregorianDate)

            val countdownBadgeText = when {
                daysUntil == 0L -> StringResources.get("today", language)
                daysUntil == 1L -> if (language == AppLanguage.JAVANESE) "Sesuk" else if (language == AppLanguage.INDONESIAN) "Besok" else "Tomorrow"
                daysUntil > 1L -> String.format(StringResources.get("in_days", language), daysUntil)
                else -> String.format(StringResources.get("days_ago", language), -daysUntil)
            }

            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            holidayToShowDetail = holiday
                            holidayInstanceDetail = instance
                        }
                        .testTag("holiday_card_${holiday.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = holiday.getName(language),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = when (holiday.category) {
                                        JavaneseHolidayCategory.TRADISI_KERATON -> Color(0xFFD97706)
                                        JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA -> BataMerah
                                        JavaneseHolidayCategory.MALAM_SAKRAL -> Color(0xFF7B1FA2)
                                        else -> SoganPrimary
                                    }
                                )
                                Text(
                                    text = "${instance.javaneseDate.wetonName} (Neptu ${instance.javaneseDate.neptuTotal}) • Wuku ${instance.javaneseDate.wukuName}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoganPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (daysUntil <= 1L) KeratonGoldContainer else Color(0xFFF5EBE1)
                            ) {
                                Text(
                                    text = countdownBadgeText,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (daysUntil <= 1L) Color(0xFFB45309) else SoganDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${instance.javaneseDate.day} ${instance.javaneseDate.monthNameJv} ${instance.javaneseDate.yearJavanese} AJ (${instance.javaneseDate.yearNameWindu})",
                                style = MaterialTheme.typography.bodySmall,
                                color = SoganPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = instance.gregorianDate.format(
                                    DateTimeFormatter.ofPattern("d MMMM yyyy", when (language) {
                                        AppLanguage.JAVANESE -> Locale("id")
                                        AppLanguage.INDONESIAN -> Locale("id")
                                        AppLanguage.ENGLISH -> Locale.ENGLISH
                                    })
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF78909C)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = holiday.getDescription(language),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFF1EAE0)
                            ) {
                                Text(
                                    text = holiday.getCategoryName(language),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoganDark,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onNavigateToDate(instance.gregorianDate)
                                    }
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = SoganPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = StringResources.get("tab_calendar", language),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = SoganPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
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
