package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.components.JavaneseHeaderBanner
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary

@Composable
fun AboutUsScreen(
    language: AppLanguage,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                        .testTag("about_back_button")
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = StringResources.get("back", language),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = StringResources.get("about_us", language),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Almanak & Kalender Jawa Lengkap",
                        style = MaterialTheme.typography.bodySmall,
                        color = KeratonGold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // SCROLLABLE CONTENT (HANYA BAGIAN INI YANG BERGERAK)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Section 1: Overview
            item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_hitungan_jawa_logo_1791383285970),
                                contentDescription = "App Logo",
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (language == AppLanguage.JAVANESE) "Kawruh Pananggalan Jawa"
                                    else if (language == AppLanguage.INDONESIAN) "Filosofi & Sejarah Kalender Jawa"
                                    else "History of the Javanese Calendar",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = "Sultan Agungan Mataram 1555 AJ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoganPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Pananggalan Jawa dipunresmekaken dening Sultan Agung Hanyokrokusumo saking Karaton Mataram Islam ing dinten Jemuwah Legi, 1 Sura 1555 AJ (8 Juli 1633 Masehi). Pananggalan punika nyawijikaken sistem solar (Saka) lan lunar (Hijriyah) dados satunggal tatanan agung ingkang ngemot pitung dina (Saptawara), limang dina pasaran (Pancawara), telung puluh wuku (Pawukon), lan rolas mangsa (Pranata Mangsa)."
                            else if (language == AppLanguage.INDONESIAN)
                                "Kalender Jawa diresmikan oleh Sultan Agung Hanyokrokusumo dari Kesultanan Mataram Islam pada hari Jumat Legi, 1 Sura 1555 AJ (8 Juli 1633 M). Sistem ini memadukan kalender Saka (matahari) dan Hijriyah (bulan) secara harmonis, mencakup siklus 7 hari (Saptawara), 5 hari Pasaran (Pancawara), 30 Wuku (Pawukon), siklus 8 tahun Windu, dan 12 Pranata Mangsa musim pertanian."
                            else
                                "The Javanese Calendar was decreed by Sultan Agung of the Islamic Mataram Sultanate on Friday Legi, 1 Sura 1555 AJ (8 July 1633 CE). It masterfully harmonized the Saka solar calendar and the Islamic lunar calendar into an enduring cosmological framework comprising the 7-day week, 5-day Pasaran, 30 Wuku cycle, 8-year Windu cycle, and 12 Pranata Mangsa solar seasons.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF455A64),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // Section 2: Core Components
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Pérangan Utama Pananggalan Jawa"
                                else if (language == AppLanguage.INDONESIAN) "Fitur & Properti Lengkap Kalender Jawa"
                                else "Core Properties & Features",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val features = if (language == AppLanguage.JAVANESE) listOf(
                            "Pancawara (Pasaran): Legi (5), Pahing (9), Pon (7), Wage (4), lan Kliwon (8).",
                            "Weton & Neptu: Pethungan dinten lan pasaran kagem pèngetan, weton, lan pasrawungan.",
                            "Siklus Windu (8 Taun): Alip, Ehe, Jimawal, Je, Dal, Be, Wawu, lan Jimakhir.",
                            "Siklus Pawukon (30 Wuku): Sinta dumugi Watugunung (siklus 210 dina).",
                            "Pranata Mangsa: 12 mangsa tetanen wiwit Mangsa Kasa dumugi Saddha.",
                            "Kalkulator Dinten Seda: Pethungan geblak, 3, 7, 40, 100, mendhak 1 & 2, lan nyewu (1000 dina)."
                        ) else if (language == AppLanguage.INDONESIAN) listOf(
                            "Siklus Pasaran (Pancawara): Legi (5), Pahing (9), Pon (7), Wage (4), Kliwon (8).",
                            "Weton & Neptu: Kombinasi hari masehi dan pasaran lengkap dengan nilai bobot neptu.",
                            "Siklus Windu (8 Tahun): Alip, Ehe, Jimawal, Je, Dal, Be, Wawu, dan Jimakhir.",
                            "Pawukon (30 Wuku): Siklus 210 hari dari Wuku Sinta hingga Watugunung.",
                            "Pranata Mangsa: 12 musim tradisional pertanian Jawa lengkap dengan candramengku.",
                            "Kalkulator Peringatan Kematian: Menghitung geblak, 3 hari, 7 hari, 40 hari, 100 hari, pendhak, dan 1000 hari."
                        ) else listOf(
                            "5-day Pasaran cycle: Legi (5), Pahing (9), Pon (7), Wage (4), Kliwon (8).",
                            "Weton & Neptu: Combination of weekday and market day with traditional numerical weight.",
                            "8-year Windu cycle: Alip, Ehe, Jimawal, Je, Dal, Be, Wawu, and Jimakhir.",
                            "30 Wuku (Pawukon): 210-day astrological cycle from Sinta to Watugunung.",
                            "12 Pranata Mangsa: Traditional agricultural seasons with natural poetic descriptors.",
                            "Remembrance Calculator: Accurately computes 3, 7, 40, 100, 1st & 2nd anniversary, and 1000-day memorials."
                        )

                        features.forEach { itemText ->
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SoganPrimary,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = itemText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF455A64),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Multilingual
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Language,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Multi-Basa & Aksara Jawa"
                                else if (language == AppLanguage.INDONESIAN) "Multi-Bahasa & Aksara Jawa Asli"
                                else "Multilingual & Authentic Javanese Script",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Aplikasi punika nyawisaken pilihan Basa Jawa (Krama/Ngoko Luhur), Bahasa Indonesia, lan English. Dilengkapi tulisan wilangan Aksara Jawa (꧐ ꧑ ꧒ ꧓ ꧔ ꧕ ꧖ ꧗ ꧘ ꧙) kagem nglestarekaken kabudayan adiluhung."
                            else if (language == AppLanguage.INDONESIAN)
                                "Aplikasi menyediakan pilihan Basa Jawa, Bahasa Indonesia, dan English. Dilengkapi dengan angka dan penulisan Aksara Jawa asli (꧐ ꧑ ꧒ ꧓ ꧔ ꧕ ꧖ ꧗ ꧘ ꧙) untuk melestarikan kebudayaan luhur Nusantara."
                            else
                                "The app offers full trilingual support across Javanese, Indonesian, and English, elegantly paired with authentic Javanese script numerals and symbols to preserve this cultural heritage.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section 4: Privacy & Offline Architecture
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Privasi & Panyimpenan Lokal"
                                else if (language == AppLanguage.INDONESIAN) "Privasi & Penyimpanan Lokal"
                                else "Privacy & Standalone Offline Storage",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Sedaya cathetan, jadwal hajatan, lan pèngetan weton kasimpen kanthi aman wonten ing memori piranti panjenengan piyambak ngginakaken Room Database tanpa perlu internet utawi server njawi."
                            else if (language == AppLanguage.INDONESIAN)
                                "Seluruh catatan agenda, jadwal hajatan, dan pengingat weton disimpan secara mandiri dan privat di dalam memori internal perangkat Anda (Room Database) tanpa memerlukan koneksi server eksternal."
                            else
                                "All appointments, weton events, and plans remain strictly stored on your device's local database without requiring external cloud accounts or internet connectivity.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section 5: App Version
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hitungan JAWA (Almanak & Weton)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                        Text(
                            text = "${StringResources.get("app_version", language)} 1.0",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Package: com.hitunganjawa.gecckocreator",
                            style = MaterialTheme.typography.labelSmall,
                            color = SoganDark,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Penanggalan Sultan Agungan Mataram 1555 AJ • Kurup Asapon",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF6D4C41)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
    }
}
