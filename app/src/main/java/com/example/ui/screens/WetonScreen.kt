package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.ui.components.JavaneseDatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.calendar.JodohPetunganResult
import com.example.calendar.WetonKelahiran
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.MainViewModel
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganLight
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WetonScreen(
    viewModel: MainViewModel,
    onNavigateToCalendar: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val birthDate by viewModel.birthDateForWeton.collectAsStateWithLifecycle()
    val wetonResult by viewModel.wetonKelahiranResult.collectAsStateWithLifecycle()
    val savedUserBirthDate by viewModel.savedUserBirthDate.collectAsStateWithLifecycle()
    val partnerBirthDate by viewModel.partnerBirthDate.collectAsStateWithLifecycle()
    val partnerWetonResult by viewModel.partnerWetonResult.collectAsStateWithLifecycle()

    var activeTab by remember { mutableIntStateOf(0) } // 0 = Weton Pribadi, 1 = Petungan Jodoh

    val isCurrentDateSaved = savedUserBirthDate == birthDate

    var showDatePickerDialog by remember { mutableStateOf(false) }
    var pickerInitialDate by remember { mutableStateOf(birthDate) }
    var pickerTitle by remember { mutableStateOf("Pilih Tanggal Lahir") }
    var onDateConfirmed by remember { mutableStateOf<(LocalDate) -> Unit>({}) }

    fun openDatePicker(initial: LocalDate, titleText: String, onSelect: (LocalDate) -> Unit) {
        pickerInitialDate = initial
        pickerTitle = titleText
        onDateConfirmed = onSelect
        showDatePickerDialog = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KremJawa)
    ) {
        // 1. TOP HEADER BANNER (FIXED)
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
                                text = StringResources.get("weton_calculator_title", language),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Dina • Pasaran • Neptu • Watak Primbon",
                                style = MaterialTheme.typography.bodySmall,
                                color = KeratonGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
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

                Text(
                    text = StringResources.get("weton_calculator_desc", language),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 11.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 2. SUB-TABS: Weton Kelahiran vs Petungan Jodoh (STICKY)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = Color.White,
                contentColor = SoganPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab]),
                        color = SoganPrimary,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Weton Lair" else "Weton Kelahiran",
                                fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    },
                    selectedContentColor = SoganPrimary,
                    unselectedContentColor = Color(0xFF8D6E63)
                )

                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Petungan Jodho" else "Kecocokan Jodoh",
                                fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    },
                    selectedContentColor = Color(0xFFC2185B),
                    unselectedContentColor = Color(0xFF8D6E63)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // SCROLLABLE CONTENT (HANYA BAGIAN INI YANG BERGERAK)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(top = 4.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        if (activeTab == 0) {
            // ==================== TAB 0: WETON KELAHIRAN PRIBADI ====================

            // 3. DATE SELECTOR CARD (INTERACTIVE GREGORIAN DATE PICKER)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .testTag("birth_date_picker_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = StringResources.get("select_birth_date", language),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )

                            Button(
                                onClick = {
                                    openDatePicker(
                                        birthDate,
                                        if (language == AppLanguage.JAVANESE) "Pilih Tanggal Weton Lair" else "Pilih Tanggal Lahir"
                                    ) { newDate ->
                                        viewModel.setBirthDateForWeton(newDate)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.testTag("btn_pick_birth_date")
                            ) {
                                Icon(Icons.Default.EditCalendar, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Pilih Tanggal" else "Pilih Tanggal",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Selected Gregorian Date Display
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    openDatePicker(
                                        birthDate,
                                        if (language == AppLanguage.JAVANESE) "Pilih Tanggal Weton Lair" else "Pilih Tanggal Lahir"
                                    ) { newDate ->
                                        viewModel.setBirthDateForWeton(newDate)
                                    }
                                },
                            color = KremJawa
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = SoganPrimary)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = birthDate.format(
                                                DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id"))
                                            ),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SoganDark
                                        )
                                        Text(
                                            text = "Tahun ${birthDate.year} • Bulan ${birthDate.monthValue} • Tanggal ${birthDate.dayOfMonth}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF8D6E63),
                                            fontSize = 11.sp
                                        )
                                    }
                                }

                                if (isCurrentDateSaved) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = KeratonGoldContainer
                                    ) {
                                        Text(
                                            text = StringResources.get("my_weton_badge", language),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SoganDark,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Quick Presets Row (Hari Ini, 17 Ags 1945, 1 Jan 2000, Weton Saya)
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Pilihan Cepet:" else "Pilihan Cepat:",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF8D6E63)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            savedUserBirthDate?.let { saved ->
                                item {
                                    FilterChip(
                                        selected = birthDate == saved,
                                        onClick = { viewModel.setBirthDateForWeton(saved) },
                                        label = { Text("⭐ " + StringResources.get("my_weton_badge", language)) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = KeratonGoldContainer,
                                            selectedLabelColor = SoganDark
                                        )
                                    )
                                }
                            }
                            item {
                                FilterChip(
                                    selected = birthDate == LocalDate.of(1945, 8, 17),
                                    onClick = { viewModel.setBirthDateForWeton(LocalDate.of(1945, 8, 17)) },
                                    label = { Text("17 Ags 1945 (RI)") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = birthDate == LocalDate.of(1990, 1, 1),
                                    onClick = { viewModel.setBirthDateForWeton(LocalDate.of(1990, 1, 1)) },
                                    label = { Text("1 Jan 1990") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = birthDate == LocalDate.of(2000, 1, 1),
                                    onClick = { viewModel.setBirthDateForWeton(LocalDate.of(2000, 1, 1)) },
                                    label = { Text("1 Jan 2000") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = birthDate == LocalDate.now(),
                                    onClick = { viewModel.setBirthDateForWeton(LocalDate.now()) },
                                    label = { Text(StringResources.get("today", language)) }
                                )
                            }
                        }
                    }
                }
            }

            // 4. HERO WETON RESULT CARD (GRAND JAVANESE DESIGN)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .testTag("weton_hero_result_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Header Badge
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = KeratonGoldContainer,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SoganPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = StringResources.get("weton_result_title", language),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                            }
                        }

                        // Big Prominent Weton Name: e.g. "Jumat Kliwon"
                        Text(
                            text = wetonResult.wetonName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = SoganDark,
                            textAlign = TextAlign.Center
                        )

                        // Aksara Jawa representation
                        Text(
                            text = "${wetonResult.dinaNameJv.substringBefore(" ")} ${wetonResult.pasaran.aksara}",
                            style = MaterialTheme.typography.titleMedium,
                            color = SoganPrimary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Neptu Breakdown Chips Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Dina Chip
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = KremJawa,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = "${wetonResult.dinaNameId}: ${wetonResult.neptuDina}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                )
                            }

                            Text(text = "+", fontWeight = FontWeight.Bold, color = SoganPrimary)

                            // Pasaran Chip
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = KremJawa,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = "${wetonResult.pasaran.idName}: ${wetonResult.neptuPasaran}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                )
                            }

                            Text(text = "=", fontWeight = FontWeight.Bold, color = SoganPrimary)

                            // Total Neptu Badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SoganPrimary,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = "Neptu ${wetonResult.neptuTotal}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Javanese Sultan Agungan Details Strip
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFFFBEB),
                            border = androidx.compose.foundation.BorderStroke(1.dp, KeratonGold.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Kalender Jawa:", style = MaterialTheme.typography.labelSmall, color = SoganLight)
                                        Text(
                                            text = "${wetonResult.javaneseDate.day} ${wetonResult.javaneseDate.monthNameJv} ${wetonResult.javaneseDate.yearJavanese} AJ",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = SoganDark
                                        )
                                        Text(
                                            text = "Tahun ${wetonResult.javaneseDate.yearNameWindu} • Windu ${wetonResult.javaneseDate.winduName}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF6D4C41),
                                            fontSize = 11.sp
                                        )
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Pawukon & Musim:", style = MaterialTheme.typography.labelSmall, color = SoganLight)
                                        Text(
                                            text = "Wuku ${wetonResult.javaneseDate.wukuName} (${wetonResult.javaneseDate.wukuNumber})",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = SoganDark
                                        )
                                        Text(
                                            text = "Mangsa ${wetonResult.javaneseDate.pranataMangsa.name}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF6D4C41),
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action: Bookmark / Save as My Weton
                        OutlinedButton(
                            onClick = {
                                if (isCurrentDateSaved) {
                                    viewModel.clearSavedUserBirthDate()
                                    Toast.makeText(context, "Weton pribadi dihapus dari simpanan", Toast.LENGTH_SHORT).show()
                                } else {
                                    viewModel.saveUserBirthDate(birthDate)
                                    Toast.makeText(context, StringResources.get("saved_as_my_weton_success", language), Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (isCurrentDateSaved) BataMerah else SoganPrimary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_save_my_weton")
                        ) {
                            Icon(
                                if (isCurrentDateSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCurrentDateSaved) {
                                    if (language == AppLanguage.JAVANESE) "Wus Kasimpen Minangka Weton Kula" else "Sudah Disimpan Sebagai Weton Saya"
                                } else {
                                    StringResources.get("save_as_my_weton", language)
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp
                            )
                        }
                    }
                }
            }

            // 5. WATAK LAHIR (LAKUNING & PRIMBON KHUSUS)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .testTag("watak_primbon_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = SoganPrimary, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = StringResources.get("watak_lakuning_title", language),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        // Lakuning Title Pill
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KeratonGoldContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    when (wetonResult.neptuTotal) {
                                        14 -> Icons.Default.Nightlight
                                        15 -> Icons.Default.LightMode
                                        else -> Icons.Default.Star
                                    },
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = wetonResult.watakLakuning,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SoganDark
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Lakuning Trait Narrative
                        Text(
                            text = wetonResult.getWatakDescription(language),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4E342E),
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Specific Weton Character
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = SoganPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${StringResources.get("karakter_primbon_title", language)} (${wetonResult.wetonName}):",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KremJawa,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = wetonResult.getKarakterDescription(language),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF5D4037),
                                modifier = Modifier.padding(10.dp),
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }

            // 6. NAUNGAN PANCASUDA & FALSAFAH LUHUR
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = KeratonGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = StringResources.get("pancasuda_title", language) + ": " + wetonResult.pancasuda,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = wetonResult.getPancasudaDescription(language),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF5D4037),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Naga Dina & Arah Rejeki
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CompassCalibration, contentDescription = null, tint = SoganPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Naga Dina / Arah Rejeki:" else "Arah Rezeki & Keselamatan:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF8D6E63)
                                )
                            }
                            Text(
                                text = wetonResult.nagaDinaArah,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Falsafah Luhur Jawa
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFFF8E1),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Pitutur Luhur: \"${wetonResult.pepatahLuhurJv}\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = "Artinya: ${wetonResult.pepatahArtiId}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = Color(0xFF6D4C41),
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }

            // 7. SIKLUS WETONAN / SELAPANAN (35 HARI) & UMUR LENGKAP
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .testTag("selapanan_cycle_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.HourglassTop, contentDescription = null, tint = SoganPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = StringResources.get("selapanan_title", language),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Next Wetonan Info
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = KremJawa,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = StringResources.get("next_wetonan", language),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SoganLight
                                    )
                                    Text(
                                        text = wetonResult.nextSelapananDate.format(
                                            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id"))
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SoganDark
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (wetonResult.daysUntilNextSelapanan == 0L) BataMerah else SoganPrimary
                                ) {
                                    Text(
                                        text = if (wetonResult.daysUntilNextSelapanan == 0L) {
                                            if (language == AppLanguage.JAVANESE) "Dina Iki!" else "Hari Ini!"
                                        } else {
                                            "${wetonResult.daysUntilNextSelapanan} " + if (language == AppLanguage.JAVANESE) "dina maneh" else "hari lagi"
                                        },
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Usia & Total Selapan Statistics
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = KremJawa
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "Total Selapan Dijalani:", style = MaterialTheme.typography.labelSmall, color = Color(0xFF8D6E63))
                                    Text(
                                        text = "${wetonResult.totalSelapanLived} Selapan",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SoganDark
                                    )
                                    Text(text = "(Siklus 35 hari berulang)", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp, color = SoganLight)
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = KremJawa
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "Usia Masehi:", style = MaterialTheme.typography.labelSmall, color = Color(0xFF8D6E63))
                                    Text(
                                        text = "${wetonResult.umurTahun} Thn ${wetonResult.umurBulan} Bln",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SoganDark
                                    )
                                    Text(text = "(${wetonResult.totalHariHidup} hari hidup)", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp, color = SoganLight)
                                }
                            }
                        }
                    }
                }
            }

        } else {
            // ==================== TAB 1: PETUNGAN JODOH PASANGAN ====================
            item {
                PetunganJodohSection(
                    viewModel = viewModel,
                    language = language,
                    weton1 = wetonResult,
                    partnerBirthDate = partnerBirthDate,
                    partnerWeton = partnerWetonResult,
                    onSelectPartnerDate = {
                        openDatePicker(
                            partnerBirthDate ?: LocalDate.of(1996, 5, 20),
                            if (language == AppLanguage.JAVANESE) "Pilih Tanggal Lair Pasangan" else "Pilih Tanggal Lahir Pasangan"
                        ) { newDate ->
                            viewModel.setPartnerBirthDate(newDate)
                        }
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
    }

    if (showDatePickerDialog) {
        JavaneseDatePickerDialog(
            initialDate = pickerInitialDate,
            title = pickerTitle,
            language = language,
            onDismissRequest = { showDatePickerDialog = false },
            onDateSelected = { selected ->
                onDateConfirmed(selected)
                showDatePickerDialog = false
            }
        )
    }
}

@Composable
fun PetunganJodohSection(
    viewModel: MainViewModel,
    language: AppLanguage,
    weton1: WetonKelahiran,
    partnerBirthDate: LocalDate?,
    partnerWeton: WetonKelahiran?,
    onSelectPartnerDate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .testTag("petungan_jodoh_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFE91E63), modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = StringResources.get("jodoh_calculator_title", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SoganDark
                    )
                    Text(
                        text = "Petungan Neptu Pasangan (Siklus 8 Primbon Jawa)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF8D6E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Weton 1 (Anda)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = KremJawa,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Weton Anda:", style = MaterialTheme.typography.labelSmall, color = SoganLight)
                        Text(text = weton1.wetonName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SoganDark)
                        Text(text = weton1.gregorianDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")), style = MaterialTheme.typography.bodySmall, color = Color(0xFF6D4C41))
                    }
                    Surface(shape = RoundedCornerShape(8.dp), color = SoganPrimary) {
                        Text(
                            text = "Neptu ${weton1.neptuTotal}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Weton 2 (Pasangan)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (partnerWeton != null) KremJawa else Color(0xFFFCE4EC),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectPartnerDate() }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Weton Pasangan:", style = MaterialTheme.typography.labelSmall, color = SoganLight)
                        if (partnerWeton != null) {
                            Text(text = partnerWeton.wetonName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SoganDark)
                            Text(text = partnerWeton.gregorianDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")), style = MaterialTheme.typography.bodySmall, color = Color(0xFF6D4C41))
                        } else {
                            Text(text = "Ketuk untuk memilih tanggal lahir pasangan", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = Color(0xFFC2185B))
                        }
                    }

                    Button(
                        onClick = onSelectPartnerDate,
                        colors = ButtonDefaults.buttonColors(containerColor = if (partnerWeton != null) SoganPrimary else Color(0xFFE91E63)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = if (partnerWeton != null) "Ubah" else "Pilih Tanggal", fontSize = 11.sp)
                    }
                }
            }

            // Hasil Petungan Jodoh jika kedua Weton ada
            if (partnerWeton != null) {
                val petunganResult = remember(weton1, partnerWeton) {
                    viewModel.hitungKecocokanJodoh(weton1, partnerWeton)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF8E1),
                    border = androidx.compose.foundation.BorderStroke(1.dp, KeratonGold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total Neptu: ${weton1.neptuTotal} + ${partnerWeton.neptuTotal} = ${petunganResult.totalNeptu}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = when (petunganResult.kategori) {
                                "Ratu", "Jodho", "Tinari", "Pesthi" -> Color(0xFF2E7D32)
                                else -> Color(0xFFD97706)
                            }
                        ) {
                            Text(
                                text = "Hasil: ${petunganResult.kategori}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE) petunganResult.maknaJv else petunganResult.maknaId,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SoganDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "💡 Nasehat Bijak:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganPrimary
                                )
                                Text(
                                    text = petunganResult.nasehatId,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF5D4037),
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
