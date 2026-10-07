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
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate

@Composable
fun SlametanKematianSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var dateWafat by remember { mutableStateOf(LocalDate.now().minusDays(10)) }
    var afterMaghrib by remember { mutableStateOf(false) }

    val result = remember(dateWafat, afterMaghrib) {
        HitunganJawaEngine.calculateSlametanKematian(dateWafat, afterMaghrib)
    }

    val shareText = remember(result) {
        """
        🕯️ KALKULATOR SLAMETAN KEMATIAN TRADISI JAWA 🕯️
        
        📅 Tanggal Wafat: ${HitunganJawaEngine.formatDateId(result.tanggalMeninggal)} ${if (result.waktuMeninggalSetelahMaghrib) "(Ba'da Maghrib)" else "(Sebelum Maghrib)"}
        🪦 Dina Geblag: ${HitunganJawaEngine.formatDateId(result.geblagDate)} (${result.geblagWeton.wetonName})
        
        ✨ 8 Rangkaian Slametan Peringatan Kematian:
        ${result.listTahapan.joinToString("\n\n") { "• ${it.tahapan}\n  Tanggal: ${HitunganJawaEngine.formatDateId(it.date)} (${it.javaneseDate.wetonName})\n  ${it.maknaSpiritual}" }}
        
        🕊️ Wejangan Doa:
        ${result.wejanganDoa}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Hitung Selamatan Kematian (Slametan)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Kalkulator 8 peringatan wafat: Geblag s/d Nyewu (1000 Hari)", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Meninggal Dunia",
            date = dateWafat,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, dateWafat) { dateWafat = it }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Wafat Setelah Maghrib (Surup)?", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
                    Text("Dalam kalender Jawa hari berganti saat Maghrib", fontSize = 11.sp, color = Color.Gray)
                }
                Switch(
                    checked = afterMaghrib,
                    onCheckedChange = { afterMaghrib = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = SoganDark, checkedTrackColor = KeratonGoldContainer)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("🪦 Hari Geblag Terhitung:", fontSize = 12.sp, color = SoganPrimary, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "${HitunganJawaEngine.formatDateId(result.geblagDate)} (${result.geblagWeton.wetonName})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("8 Tahapan Peringatan Kematian:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))

        result.listTahapan.forEach { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(item.tahapan, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                    Text(
                        "${HitunganJawaEngine.formatDateId(item.date)} • ${item.javaneseDate.wetonName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KeratonGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.maknaSpiritual, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp)
                }
            }
        }

        ShareAndCopyRow(
            title = "Jadwal Slametan Kematian",
            contentSummary = shareText
        )
    }
}

@Composable
fun PindahRumahSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var kepalaBirthDate by remember { mutableStateOf(LocalDate.of(1988, 10, 15)) }
    var targetDate by remember { mutableStateOf(LocalDate.now().plusDays(14)) }
    var arahTujuan by remember { mutableStateOf("TIMUR") }

    val arahList = listOf("UTARA", "TIMUR", "SELATAN", "BARAT")

    val result = remember(kepalaBirthDate, targetDate, arahTujuan) {
        HitunganJawaEngine.calculatePindahRumah(kepalaBirthDate, arahTujuan, targetDate)
    }

    val shareText = remember(result) {
        """
        🏡 HITUNGAN PINDAH RUMAH & BOYONGAN JAWA 🏡
        
        👨 Kepala Keluarga: ${result.wetonKepalaKeluarga.wetonName} (Neptu ${result.wetonKepalaKeluarga.neptuTotal})
        📅 Tanggal Boyongan: ${HitunganJawaEngine.formatDateId(result.tanggalPilihan)} (${result.wetonTanggalPilihan.wetonName})
        🧭 Arah Rumah Baru: ${result.arahTujuan}
        
        🌟 Status Primbon: ${result.statusPancasuda} (Skor: ${result.kecocokanSkor}%)
        📖 Makna: ${result.wejanganRumah}
        
        🧺 Uborampe yang Dibawa Pertama Kali:
        ${result.uborampe.joinToString("\n") { "• $it" }}
        
        📜 Tata Cara Boyongan:
        ${result.tataCaraBoyongan.joinToString("\n") { "• $it" }}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Pindah Rumah & Boyongan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Menghitung hari baik pindah rumah & arah keberuntungan tempat tinggal", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Kepala Keluarga",
            date = kepalaBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, kepalaBirthDate) { kepalaBirthDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Target Tanggal Rencana Pindah",
            date = targetDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, targetDate) { targetDate = it }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Arah Mata Angin Rumah Baru:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            arahList.forEach { arah ->
                FilterChip(
                    selected = arahTujuan == arah,
                    onClick = { arahTujuan = arah },
                    label = { Text(arah, fontSize = 11.sp) },
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
                Text("🌟 Status Hari Pilihan: ${result.statusPancasuda}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.wejanganRumah, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Tata Cara Adat Boyongan:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                result.tataCaraBoyongan.forEach { step ->
                    Text(step, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        ShareAndCopyRow(
            title = "Hasil Hitungan Pindah Rumah",
            contentSummary = shareText
        )
    }
}

@Composable
fun BukaUsahaSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var pemilikBirthDate by remember { mutableStateOf(LocalDate.of(1992, 7, 11)) }
    var targetDate by remember { mutableStateOf(LocalDate.now().plusDays(7)) }
    var bidangUsaha by remember { mutableStateOf("Perdagangan & Toko") }

    val bidangList = listOf("Perdagangan & Toko", "Kuliner & Makanan", "Jasa & Kreatif", "Pertanian & Ternak")

    val result = remember(pemilikBirthDate, targetDate, bidangUsaha) {
        HitunganJawaEngine.calculateBukaUsaha(pemilikBirthDate, bidangUsaha, targetDate)
    }

    val shareText = remember(result) {
        """
        🏪 HITUNG HARI BAIK BUKA USAHA / BISNIS 🏪
        Almanak Primbon Tradisi Jawa
        
        👤 Pemilik: ${result.wetonPemilik.wetonName} (Neptu ${result.wetonPemilik.neptuTotal})
        💼 Bidang: ${result.bidangUsaha}
        📅 Tanggal Opening: ${HitunganJawaEngine.formatDateId(result.tanggalPilihan)} (${result.wetonTanggalPilihan.wetonName})
        
        ✨ Status Primbon: ${result.statusPancaSiklus}
        🎯 Skor Kelancaran: ${result.skorHoki}%
        🧭 Arah Hadap Toko / Usaha:
        ${result.arahHokiTempatUsaha}
        
        🕊️ Wejangan:
        ${result.wejanganUsaha}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Buka Usaha / Bisnis", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Menentukan tanggal hoki untuk grand opening dan arah hadap toko", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Lahir Pemilik Usaha",
            date = pemilikBirthDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, pemilikBirthDate) { pemilikBirthDate = it }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSelectorButton(
            label = "Target Tanggal Grand Opening",
            date = targetDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, targetDate) { targetDate = it }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Pilih Kategori Bidang Usaha:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SoganDark)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            bidangList.take(2).forEach { b ->
                FilterChip(
                    selected = bidangUsaha == b,
                    onClick = { bidangUsaha = b },
                    label = { Text(b, fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            bidangList.drop(2).forEach { b ->
                FilterChip(
                    selected = bidangUsaha == b,
                    onClick = { bidangUsaha = b },
                    label = { Text(b, fontSize = 11.sp) },
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
                Text("🌟 Status Hari: ${result.statusPancaSiklus}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.wejanganUsaha, fontSize = 12.sp, color = SoganDark, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = KeratonGold.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))
                Text("🧭 Arah Hoki Toko:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Text(result.arahHokiTempatUsaha, fontSize = 12.sp, color = SoganDark)
            }
        }

        ShareAndCopyRow(
            title = "Hari Baik Buka Usaha",
            contentSummary = shareText
        )
    }
}

@Composable
fun NagaDinaSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var travelDate by remember { mutableStateOf(LocalDate.now()) }

    val result = remember(travelDate) {
        HitunganJawaEngine.calculateNagaDina(travelDate)
    }

    val shareText = remember(result) {
        """
        🐉 PETUNGAN BEPERGIAN JAUH (NAGA DINA) 🐉
        Almanak Primbon Tradisi Jawa
        
        📅 Tanggal Perjalanan: ${HitunganJawaEngine.formatDateId(result.date)}
        🌟 Weton: ${result.javaneseDate.wetonName}
        
        🚫 Arah Pantangan (Mulut Naga Dina):
        ${result.arahKepalaNaga} (Hindari bepergian lurus ke arah ini)
        
        ✅ Arah Selamat & Membawa Rezeki (Punggung Naga):
        ${result.arahPunggungNaga}
        
        📖 Petuah Primbon:
        ${result.wejanganPerjalanan}
        
        🤲 Amalan Doa:
        ${result.amalanDoa}
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Bepergian Jauh (Naga Dina)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SoganDark)
        Text("Petunjuk arah aman dan hari selamat untuk perjalanan jauh agar terhindar dari marabahaya", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        DateSelectorButton(
            label = "Tanggal Rencana Bepergian",
            date = travelDate,
            onDateClick = {
                HitunganShareUtil.showDatePicker(context, travelDate) { travelDate = it }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🚫 ARAH PANTANGAN (KEPALA NAGA DINA):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BataMerah)
                Text(
                    text = result.arahKepalaNaga,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = BataMerah
                )
                Text("Dilarang bepergian lurus menghadap kepala naga karena rentan aral rintangan.", fontSize = 12.sp, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✅ ARAH AMAN & BERKAH (PUNGGUNG NAGA):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SoganPrimary)
                Text(
                    text = result.arahPunggungNaga,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
                Text("Arah pembawa keselamatan, kemudahan urusan, dan kelancaran rezeki.", fontSize = 12.sp, color = SoganDark)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Petuah Perjalanan Leluhur:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.wejanganPerjalanan, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Amalan Doa Selamat:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoganDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(result.amalanDoa, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 17.sp)
            }
        }

        ShareAndCopyRow(
            title = "Petunjuk Naga Dina",
            contentSummary = shareText
        )
    }
}
