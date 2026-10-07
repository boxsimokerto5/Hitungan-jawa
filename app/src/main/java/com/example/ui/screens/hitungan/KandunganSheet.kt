package com.example.ui.screens.hitungan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.HitunganJawaEngine
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate

@Composable
fun MapatiSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var hphtDate by remember { mutableStateOf(LocalDate.now().minusDays(100)) }

    val result = remember(hphtDate) {
        HitunganJawaEngine.calculateMapati(hphtDate)
    }

    val shareText = remember(result) {
        """
        🌸 HASIL HITUNGAN 4 BULANAN (MAPATI / NGUPATI) 🌸
        Almanak Primbon Tradisi Jawa
        
        📅 HPHT: ${HitunganJawaEngine.formatDateId(result.hphtDate)}
        ✨ Tepat 120 Hari (Ditiupkannya Ruh): 
           ${HitunganJawaEngine.formatDateId(result.date120Hari)}
           Weton: ${result.javaneseDate120Hari.wetonName} (Neptu: ${result.javaneseDate120Hari.neptuTotal})
           Wuku: ${result.javaneseDate120Hari.wukuName}
        
        🌟 Rekomendasi Hari Baik Syukuran:
        ${result.rekomendasiHariBaik.joinToString("\n") { "• ${HitunganJawaEngine.formatDateId(it.first)} (${it.second.wetonName})" }}
        
        📜 Uborampe Adat:
        ${result.uborampe.joinToString("\n") { "• $it" }}
        
        🕊️ Wejangan:
        ${result.doaWejangan}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .background(KeratonGoldContainer, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.PregnantWoman, contentDescription = null, tint = SoganDark)
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text("Hitungan 4 Bulanan (Mapati)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Text("Penentu hari baik syukuran ditiupkannya ruh", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Pilih Hari Pertama Haid Terakhir (HPHT):", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        DateSelectorButton(
            label = "Tanggal HPHT Calon Ibu",
            date = hphtDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, hphtDate) { hphtDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✨ Tanggal Tepat 120 Hari (Genap 4 Bulan)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Text(
                    text = HitunganJawaEngine.formatDateId(result.date120Hari),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Text(
                    text = "Weton: ${result.javaneseDate120Hari.wetonName} • Wuku ${result.javaneseDate120Hari.wukuName} • Neptu ${result.javaneseDate120Hari.neptuTotal}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = SoganDark
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Rekomendasi Hari Baik Syukuran Adat:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        result.rekomendasiHariBaik.forEach { item ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = KeratonGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.size(8.dp))
                    Column {
                        Text(HitunganJawaEngine.formatDateId(item.first), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                        Text("${item.second.wetonName} • Wuku ${item.second.wukuName}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = KremJawa),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Makna Tradisi & Uborampe:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.maknaTradisi, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                result.uborampe.forEach { item ->
                    Text("• $item", fontSize = 12.sp, color = SoganDark, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        ShareAndCopyRow(
            title = "Hasil Hitungan 4 Bulanan Jawa",
            contentSummary = shareText
        )
    }
}

@Composable
fun MitoniSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var hphtDate by remember { mutableStateOf(LocalDate.now().minusDays(180)) }
    var ayahBirthDate by remember { mutableStateOf(LocalDate.of(1995, 5, 12)) }
    var ibuBirthDate by remember { mutableStateOf(LocalDate.of(1997, 8, 20)) }

    val result = remember(hphtDate, ayahBirthDate, ibuBirthDate) {
        HitunganJawaEngine.calculateMitoni(hphtDate, ayahBirthDate, ibuBirthDate)
    }

    val shareText = remember(result) {
        """
        🌺 HASIL HITUNGAN 7 BULANAN (MITONI / TINGKEBAN) 🌺
        Almanak Primbon Tradisi Jawa
        
        📅 HPHT: ${HitunganJawaEngine.formatDateId(result.hphtDate)}
        👨 Weton Ayah: ${result.wetonAyah.wetonName} (Neptu ${result.wetonAyah.neptuTotal})
        👩 Weton Ibu: ${result.wetonIbu.wetonName} (Neptu ${result.wetonIbu.neptuTotal})
        
        ✨ Genap 7 Bulan (210 Hari): 
           ${HitunganJawaEngine.formatDateId(result.date210Hari)} (${result.javaneseDate210Hari.wetonName})
        
        🌟 Rekomendasi Tanggal Mitoni Terbaik:
        ${result.rekomendasiMitoni.joinToString("\n") { "• ${HitunganJawaEngine.formatDateId(it.first)} (${it.second.wetonName} - Tgl ${it.second.day} Bulan Jawa)" }}
        
        📜 Uborampe Utama:
        ${result.uborampe.joinToString("\n") { "• $it" }}
        
        🕊️ Wejangan:
        ${result.wejanganLeluhur}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFFFECB3), RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.ChildCare, contentDescription = null, tint = SoganDark)
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text("Hitungan 7 Bulanan (Mitoni)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Text("Kalkulator tradisi Tingkeban & pasaran terbaik", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal HPHT Calon Ibu",
            date = hphtDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, hphtDate) { hphtDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Tanggal Lahir Calon Ayah",
            date = ayahBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, ayahBirthDate) { ayahBirthDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Tanggal Lahir Calon Ibu",
            date = ibuBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, ibuBirthDate) { ibuBirthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✨ Genap Usia 7 Bulan (210 Hari)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Text(
                    text = HitunganJawaEngine.formatDateId(result.date210Hari),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Text(
                    text = "Weton: ${result.javaneseDate210Hari.wetonName} • Wuku ${result.javaneseDate210Hari.wukuName}",
                    fontSize = 13.sp,
                    color = SoganDark
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Rekomendasi Tanggal Upacara Mitoni:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        result.rekomendasiMitoni.forEach { item ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = KeratonGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.size(8.dp))
                    Column {
                        Text(HitunganJawaEngine.formatDateId(item.first), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                        Text("${item.second.wetonName} • Tanggal ${item.second.day} ${item.second.monthNameId}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = KremJawa),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Tata Cara Pokok Mitoni / Tingkeban:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(6.dp))
                result.tataCaraTradisi.forEach { step ->
                    Text(step, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp, modifier = Modifier.padding(vertical = 3.dp))
                }
            }
        }

        ShareAndCopyRow(
            title = "Hasil Hitungan Mitoni Jawa",
            contentSummary = shareText
        )
    }
}

@Composable
fun HplJawaSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var hphtDate by remember { mutableStateOf(LocalDate.now().minusDays(150)) }

    val result = remember(hphtDate) {
        HitunganJawaEngine.calculateHplJawa(hphtDate)
    }

    val shareText = remember(result) {
        """
        👶 ESTIMASI KELAHIRAN (HPL JAWA) 👶
        Almanak Primbon Tradisi Jawa
        
        📅 HPHT: ${HitunganJawaEngine.formatDateId(result.hphtDate)}
        🎉 Prediksi Tanggal Lahir (HPL Naegele): 
           ${HitunganJawaEngine.formatDateId(result.hplDate)}
           
        🔮 Prediksi Karakter Astrologi Jawa:
        • Weton: ${result.javaneseDateHpl.wetonName}
        • Wuku: ${result.wukuHpl}
        • Pranata Mangsa: ${result.pranataMangsaHpl}
        • Neptu Lahir: ${result.neptuHpl}
        
        📖 Ramalan Karakter:
        ${result.karakterPrediksi}
        
        🕊️ Wejangan:
        ${result.wejanganKelahiran}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .background(KeratonGoldContainer, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = SoganDark)
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text("Estimasi Kelahiran (HPL Jawa)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Text("Prediksi Pranata Mangsa, Wuku, & Neptu Bayi", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal HPHT Calon Ibu",
            date = hphtDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, hphtDate) { hphtDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🎉 Prediksi Hari Perkiraan Lahir (HPL):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Text(
                    text = HitunganJawaEngine.formatDateId(result.hplDate),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = KeratonGold.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Prediksi Weton: ${result.javaneseDateHpl.wetonName} (Neptu: ${result.neptuHpl})", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
                Text("• Wuku Naungan: ${result.wukuHpl}", fontSize = 13.sp, color = SoganDark)
                Text("• Pranata Mangsa: ${result.pranataMangsaHpl}", fontSize = 13.sp, color = SoganDark)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Ramalan Karakter Bawaan:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.karakterPrediksi, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text("Wejangan Leluhur:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.wejanganKelahiran, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
            }
        }

        ShareAndCopyRow(
            title = "Hasil HPL Jawa",
            contentSummary = shareText
        )
    }
}
