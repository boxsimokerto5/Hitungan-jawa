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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
fun KecocokanJodohDetailedSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var priaBirthDate by remember { mutableStateOf(LocalDate.of(1996, 4, 18)) }
    var wanitaBirthDate by remember { mutableStateOf(LocalDate.of(1998, 9, 24)) }

    val result = remember(priaBirthDate, wanitaBirthDate) {
        HitunganJawaEngine.calculateKecocokanJodoh(priaBirthDate, wanitaBirthDate)
    }

    val shareText = remember(result) {
        """
        💍 HASIL HITUNGAN JODOH PRIMBON JAWA (PETHUKAN) 💍
        
        👨 Pria: ${HitunganJawaEngine.formatDateId(result.wetonPria.gregorianDate)}
        • Weton: ${result.wetonPria.wetonName} (Neptu: ${result.wetonPria.neptuTotal})
        
        👩 Wanita: ${HitunganJawaEngine.formatDateId(result.wetonWanita.gregorianDate)}
        • Weton: ${result.wetonWanita.wetonName} (Neptu: ${result.wetonWanita.neptuTotal})
        
        ⚡ Gabungan Neptu: ${result.totalNeptu}
        🎯 Hasil Siklus 8: "${result.hasilSiklus8}" (Skor Keserasian: ${result.tingkatKeserasian}%)
        📖 Makna: ${result.maknaSiklus8}
        
        🌿 Pancasuda Siklus 7: ${result.hasilSiklus7} (${result.maknaSiklus7})
        🌺 Siklus 4: ${result.hasilSiklus4} (${result.maknaSiklus4})
        
        💡 Nasehat Pernikahan:
        ${result.nasehatSiklus8}
        
        🕊️ Solusi & Ikhtiar:
        ${result.solusiPenangkal}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Hitung Kecocokan Jodoh (Pethukan)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Analisis neptu pasangan menurut Primbon Siklus 8, 7, dan 4", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Calon Suami (Pria)",
            date = priaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, priaBirthDate) { priaBirthDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Tanggal Lahir Calon Istri (Wanita)",
            date = wanitaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, wanitaBirthDate) { wanitaBirthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Hasil Petungan Siklus 8:", fontSize = 12.sp, color = SoganPrimary)
                        Text(
                            text = result.hasilSiklus8.uppercase(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(SoganDark, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Skor ${result.tingkatKeserasian}%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total Neptu Gabungan: ${result.wetonPria.neptuTotal} + ${result.wetonWanita.neptuTotal} = ${result.totalNeptu}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SoganDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.maknaSiklus8, fontSize = 13.sp, color = SoganDark, lineHeight = 19.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Analisis Aspek Primbon Lainnya:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(6.dp))
                Text("• Naungan Pancasuda (Siklus 7): ${result.hasilSiklus7}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
                Text(result.maknaSiklus7, fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(start = 10.dp, bottom = 4.dp))

                Text("• Karakter Siklus 4: ${result.hasilSiklus4}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
                Text(result.maknaSiklus4, fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(start = 10.dp, bottom = 4.dp))

                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(6.dp))

                Text("Wejangan & Ikhtiar:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.nasehatSiklus8, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.solusiPenangkal, fontSize = 12.sp, color = SoganPrimary, fontWeight = FontWeight.Medium, lineHeight = 17.sp)
            }
        }

        ShareAndCopyRow(
            title = "Hasil Hitungan Jodoh Jawa",
            contentSummary = shareText
        )
    }
}

@Composable
fun HariBaikNikahSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var priaBirthDate by remember { mutableStateOf(LocalDate.of(1996, 4, 18)) }
    var wanitaBirthDate by remember { mutableStateOf(LocalDate.of(1998, 9, 24)) }
    var targetMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }
    var targetYear by remember { mutableIntStateOf(LocalDate.now().year) }

    val result = remember(priaBirthDate, wanitaBirthDate, targetMonth, targetYear) {
        HitunganJawaEngine.calculateHariBaikNikah(priaBirthDate, wanitaBirthDate, Pair(targetYear, targetMonth))
    }

    val shareText = remember(result) {
        """
        💒 REKOMENDASI HARI BAIK NIKAH (IJAB & RESEPSI) 💒
        Almanak Primbon Tradisi Jawa
        
        👨 Calon Pria: ${result.wetonPria.wetonName} (Neptu ${result.wetonPria.neptuTotal})
        👩 Calon Wanita: ${result.wetonWanita.wetonName} (Neptu ${result.wetonWanita.neptuTotal})
        
        🌟 Rekomendasi Tanggal Terbaik (${result.bulanTarget}):
        ${result.daftarRekomendasi.joinToString("\n\n") { "• ${HitunganJawaEngine.formatDateId(it.date)} (${it.javaneseDate.wetonName})\n  Status: ${it.statusHari}\n  ${it.keterangan}" }}
        
        🚫 Hari yang Dihindari:
        ${result.pantanganHari.joinToString("\n") { "• $it" }}
        
        🕊️ Wejangan:
        ${result.wejanganNikah}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Cari Hari Baik Nikah", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Rekomendasi tanggal terbaik Ijab Kabul & Resepsi terhindar dari dina tali bangke", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Calon Suami",
            date = priaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, priaBirthDate) { priaBirthDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Tanggal Lahir Calon Istri",
            date = wanitaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, wanitaBirthDate) { wanitaBirthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Rekomendasi Tanggal Terbaik (${result.bulanTarget}):", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))

        result.daftarRekomendasi.forEach { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(HitunganJawaEngine.formatDateId(item.date), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                        Row {
                            repeat(item.bintang) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = KeratonGold, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                    Text("Weton: ${item.javaneseDate.wetonName} • ${item.statusHari}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.keterangan, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = KremJawa),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Pantangan yang Dihindari:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                result.pantanganHari.forEach { item ->
                    Text("• $item", fontSize = 12.sp, color = SoganDark, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        ShareAndCopyRow(
            title = "Rekomendasi Hari Baik Nikah",
            contentSummary = shareText
        )
    }
}

@Composable
fun ArahRezekiPasanganSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var priaBirthDate by remember { mutableStateOf(LocalDate.of(1996, 4, 18)) }
    var wanitaBirthDate by remember { mutableStateOf(LocalDate.of(1998, 9, 24)) }

    val result = remember(priaBirthDate, wanitaBirthDate) {
        HitunganJawaEngine.calculateArahRezekiSuamiIstri(priaBirthDate, wanitaBirthDate)
    }

    val shareText = remember(result) {
        """
        🧭 ARAH REZEKI SUAMI ISTRI (PRIMBON JAWA) 🧭
        
        👨 Suami: ${result.wetonSuami.wetonName} (Neptu ${result.wetonSuami.neptuTotal})
        👩 Istri: ${result.wetonIstri.wetonName} (Neptu ${result.wetonIstri.neptuTotal})
        ⚡ Total Gabungan: ${result.gabunganNeptu}
        
        🌟 Arah Utama Rezeki & Keberuntungan:
        ${result.arahUtamaRezeki}
        
        🏡 Arah Terbaik Pintu Depan Rumah / Tempat Usaha:
        ${result.arahPintuRumah}
        
        📍 Karakteristik Wilayah Hoki:
        ${result.kotaWilayahHoki}
        
        🕊️ Wejangan:
        ${result.wejanganIkhtiar}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Arah Rezeki Suami Istri", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Petunjuk mata angin keberuntungan tempat tinggal & usaha setelah menikah", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Suami",
            date = priaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, priaBirthDate) { priaBirthDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Tanggal Lahir Istri",
            date = wanitaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, wanitaBirthDate) { wanitaBirthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🌟 Arah Utama Keberuntungan Rezeki:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = result.arahUtamaRezeki,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Rekomendasi Hadap Pintu: ${result.arahPintuRumah}", fontSize = 13.sp, color = SoganDark)
                Text("• Wilayah/Daerah Hoki: ${result.kotaWilayahHoki}", fontSize = 13.sp, color = SoganDark)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Wejangan Ikhtiar:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.wejanganIkhtiar, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
            }
        }

        ShareAndCopyRow(
            title = "Arah Rezeki Suami Istri",
            contentSummary = shareText
        )
    }
}
