package com.example.ui.screens.hitungan

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import com.example.calendar.NamaJawaItem
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate

@Composable
fun SepasarSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var birthDate by remember { mutableStateOf(LocalDate.now().minusDays(3)) }

    val result = remember(birthDate) {
        HitunganJawaEngine.calculateSepasar(birthDate)
    }

    val shareText = remember(result) {
        """
        👶 HITUNG SEPASAR (5 HARI KELAHIRAN) 👶
        Almanak Primbon Tradisi Jawa
        
        📅 Tanggal Lahir: ${HitunganJawaEngine.formatDateId(result.birthDate)}
        🌟 Weton Lahir: ${result.birthWeton.wetonName} (Neptu: ${result.birthWeton.neptuTotal})
        
        ✨ Peringatan Sepasar (Hari ke-5):
        • Tanggal: ${HitunganJawaEngine.formatDateId(result.sepasarDate)}
        • Weton Sepasar: ${result.sepasarWeton.wetonName}
        
        📜 Tradisi & Makna:
        ${result.tradisiMakna}
        
        🍚 Uborampe Bancakan Sepasaran:
        ${result.uborampe.joinToString("\n") { "• $it" }}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Hitung Sepasar (5 Hari)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Peringatan 5 hari kelahiran & tradisi puput puser", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Bayi",
            date = birthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, birthDate) { birthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✨ Tanggal Peringatan Sepasaran:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Text(
                    text = HitunganJawaEngine.formatDateId(result.sepasarDate),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Text("Weton: ${result.sepasarWeton.wetonName} • Wuku ${result.sepasarWeton.wukuName}", fontSize = 13.sp, color = SoganDark)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Tradisi Puput Puser & Brokohan:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.tradisiMakna, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Uborampe Bancakan:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                result.uborampe.forEach { item ->
                    Text("• $item", fontSize = 12.sp, color = SoganDark, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        ShareAndCopyRow(
            title = "Hasil Hitungan Sepasar Bayi",
            contentSummary = shareText
        )
    }
}

@Composable
fun SelapananSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var birthDate by remember { mutableStateOf(LocalDate.now().minusDays(20)) }

    val result = remember(birthDate) {
        HitunganJawaEngine.calculateSelapanan(birthDate)
    }

    val shareText = remember(result) {
        """
        👶 HITUNG SELAPANAN (35 HARI) 👶
        Almanak Primbon Tradisi Jawa
        
        📅 Tanggal Lahir: ${HitunganJawaEngine.formatDateId(result.birthDate)}
        🌟 Weton Lahir: ${result.birthWeton.wetonName}
        
        ✨ Jadwal Selapanan Bayi:
        ${result.listSelapanan.joinToString("\n") { "• Selapan ke-${it.first}: ${HitunganJawaEngine.formatDateId(it.second)} (${it.third.wetonName})" }}
        
        📜 Makna Tradisi:
        ${result.tradisiMakna}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Hitung Selapanan (35 Hari)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Siklus berulangnya weton kelahiran untuk potong rambut & aqiqah", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Bayi",
            date = birthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, birthDate) { birthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Jadwal Selapanan Berikutnya (Siklus 35 Hari):", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        result.listSelapanan.forEach { item ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = if (item.first == 1) KeratonGoldContainer else Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(28.dp)
                            .background(KeratonGold, CircleShape)
                    ) {
                        Text("${item.first}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Column {
                        Text(HitunganJawaEngine.formatDateId(item.second), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                        Text("Weton: ${item.third.wetonName} (Hari ke-${item.first * 35})", fontSize = 12.sp, color = Color.Gray)
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
                Text("Makna Potong Rambut & Aqiqah:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.tradisiMakna, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
            }
        }

        ShareAndCopyRow(
            title = "Jadwal Selapanan Bayi",
            contentSummary = shareText
        )
    }
}

@Composable
fun TedhakSitenSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var birthDate by remember { mutableStateOf(LocalDate.now().minusDays(180)) }

    val result = remember(birthDate) {
        HitunganJawaEngine.calculateTedhakSiten(birthDate)
    }

    val shareText = remember(result) {
        """
        🌾 UPACARA TEDHAK SITEN (7 SELAPAN / TURUN TANAH) 🌾
        Almanak Primbon Tradisi Jawa
        
        📅 Tanggal Lahir: ${HitunganJawaEngine.formatDateId(result.birthDate)}
        🌟 Weton Lahir: ${result.birthWeton.wetonName}
        
        ✨ Tanggal Tepat 7 Selapan (245 Hari):
           ${HitunganJawaEngine.formatDateId(result.tedhakSitenDate)}
           Weton: ${result.tedhakSitenWeton.wetonName}
        
        📜 Tahapan Prosesi:
        ${result.tahapanProsesi.joinToString("\n\n") { "${it.first}\n${it.second}" }}
        
        🕊️ Filosofi:
        ${result.filosofiLuhur}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Hitung Tedhak Siten (7 Selapan)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Tradisi sakral bayi pertama kali turun tanah (usia ± 8 bulan)", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Bayi",
            date = birthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, birthDate) { birthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✨ Tanggal Tepat 7 Selapan (Usia 245 Hari):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Text(
                    text = HitunganJawaEngine.formatDateId(result.tedhakSitenDate),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Text("Weton: ${result.tedhakSitenWeton.wetonName} • Wuku ${result.tedhakSitenWeton.wukuName}", fontSize = 13.sp, color = SoganDark)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("5 Tahapan Prosesi Tedhak Siten:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        result.tahapanProsesi.forEach { (tahap, desc) ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(tahap, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(desc, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp)
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
                Text("Filosofi Luhur Tedhak Siten:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.filosofiLuhur, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
            }
        }

        ShareAndCopyRow(
            title = "Hasil Hitungan Tedhak Siten",
            contentSummary = shareText
        )
    }
}

@Composable
fun MendhemAriAriSheet(
    onDismiss: () -> Unit
) {
    var isBoy by remember { mutableStateOf(true) }
    var birthDate by remember { mutableStateOf(LocalDate.now()) }

    val result = remember(isBoy, birthDate) {
        HitunganJawaEngine.getMendhemAriAriGuide(isBoy, birthDate)
    }

    val shareText = remember(result) {
        """
        🪴 TATA CARA MENDHEM ARI-ARI 🪴
        Almanak Primbon Tradisi Jawa
        
        👶 Jenis Kelamin: ${if (result.genderIsBoy) "Laki-laki" else "Perempuan"}
        📍 Lokasi Penguburan:
        ${result.lokasiPenguburan}
        
        🧺 Uborampe yang Diperlukan:
        ${result.uborampe.joinToString("\n") { "• $it" }}
        
        📜 Tata Cara Lengkap:
        ${result.tataCara.joinToString("\n") { "• $it" }}
        
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
        Text("Tata Cara Mendhem Ari-Ari", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Panduan lokasi & uborampe mengubur ari-ari sang jabang bayi", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = isBoy,
                onClick = { isBoy = true },
                label = { Text("Laki-laki (Jaler)") },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = KeratonGoldContainer, selectedLabelColor = SoganDark),
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = !isBoy,
                onClick = { isBoy = false },
                label = { Text("Perempuan (Estri)") },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = KeratonGoldContainer, selectedLabelColor = SoganDark),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📍 Posisi & Lokasi Penguburan yang Tepat:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SoganPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = result.lokasiPenguburan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = SoganDark,
                    lineHeight = 19.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Uborampe / Perlengkapan:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(6.dp))
                result.uborampe.forEach { item ->
                    Text("• $item", fontSize = 12.sp, color = SoganDark, modifier = Modifier.padding(vertical = 2.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("Tata Cara Pelaksanaan:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(6.dp))
                result.tataCara.forEach { step ->
                    Text(step, fontSize = 12.sp, color = SoganDark, lineHeight = 17.sp, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        ShareAndCopyRow(
            title = "Panduan Mendhem Ari-Ari",
            contentSummary = shareText
        )
    }
}

@Composable
fun WatakBayiSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var birthDate by remember { mutableStateOf(LocalDate.now()) }
    var jamLahir by remember { mutableStateOf("Pagi (06.00 - 11.00)") }

    val jamOptions = listOf(
        "Pagi (06.00 - 11.00)",
        "Siang (11.00 - 15.00)",
        "Sore (15.00 - 18.00)",
        "Malam (18.00 - 06.00)"
    )

    val result = remember(birthDate, jamLahir) {
        HitunganJawaEngine.calculateWatakBayi(birthDate, jamLahir)
    }

    val shareText = remember(result) {
        """
        🔮 WATAK & KARAKTER BAYI MENURUT PRIMBON 🔮
        
        📅 Tanggal Lahir: ${HitunganJawaEngine.formatDateId(result.birthDate)}
        🌟 Weton: ${result.birthWeton.wetonName} (Neptu: ${result.neptuTotal})
        ⏰ Waktu Lahir: ${result.jamLahirKategori}
        
        ✨ Karakter Bawaan Neptu:
        ${result.watakNeptu}
        
        🌿 Pengaruh Wuku:
        ${result.watakWuku}
        
        ☀️ Pengaruh Jam Kelahiran:
        ${result.watakJamLahir}
        
        💼 Potensi Bakat & Masa Depan:
        ${result.potensiKarier}
        
        📖 Nasehat Pola Asuh:
        ${result.arahanPendidikan}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Watak & Karakter Bayi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Penjabaran watak menurut Neptu, Wuku, & Jam lahir", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Bayi",
            date = birthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, birthDate) { birthDate = it }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Pilih Waktu Lahir:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
        Spacer(modifier = Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            jamOptions.take(2).forEach { option ->
                FilterChip(
                    selected = jamLahir == option,
                    onClick = { jamLahir = option },
                    label = { Text(option.substringBefore(" "), fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            jamOptions.drop(2).forEach { option ->
                FilterChip(
                    selected = jamLahir == option,
                    onClick = { jamLahir = option },
                    label = { Text(option.substringBefore(" "), fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🌟 Weton: ${result.birthWeton.wetonName} (Neptu ${result.neptuTotal})", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.watakNeptu, fontSize = 13.sp, color = SoganDark, lineHeight = 19.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Pengaruh Waktu Kelahiran:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.watakJamLahir, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Potensi Karier & Bakat:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.potensiKarier, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
            }
        }

        ShareAndCopyRow(
            title = "Karakter Weton Bayi",
            contentSummary = shareText
        )
    }
}

@Composable
fun NamaJawaSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedGender by remember { mutableStateOf("Semua") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var searchQuery by remember { mutableStateOf("") }

    val names = remember(selectedGender, selectedCategory, searchQuery) {
        HitunganJawaEngine.recommendNamaJawa(selectedGender, selectedCategory, searchQuery)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Rekomendasi Nama Jawa", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Nama bermakna adiluhung dilengkapi aksara Jawa & neptu huruf", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari nama atau arti...", fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = KeratonGold) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Semua", "Laki-laki", "Perempuan").forEach { g ->
                FilterChip(
                    selected = selectedGender == g,
                    onClick = { selectedGender = g },
                    label = { Text(g, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Ditemukan ${names.size} nama Jawa pilihan:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))

        names.forEach { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(item.nama, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(item.aksaraJawa, fontSize = 16.sp, color = KeratonGold, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(item.arti, fontSize = 12.sp, color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Kategori: ${item.kategori} • Neptu Huruf: ${item.nilaiNeptuHuruf}", fontSize = 11.sp, color = Color.Gray)
                    }
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Salin Nama",
                        tint = KeratonGold,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                HitunganShareUtil.copyToClipboard(context, item.nama, "${item.nama} (${item.aksaraJawa}): ${item.arti}")
                            }
                    )
                }
            }
        }
    }
}
