package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary

@Composable
fun PrivacyPolicyScreen(
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                            .testTag("privacy_back_button")
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
                            text = StringResources.get("privacy_policy", language),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Komitmen Keamanan & Perlindungan Privasi",
                            style = MaterialTheme.typography.bodySmall,
                            color = KeratonGold,
                            fontSize = 11.sp
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Section 1: Non-Collection Guarantee
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
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Jaminan Ora Ana Pangumpulan Data"
                                else if (language == AppLanguage.INDONESIAN) "Jaminan Bebas Pengumpulan Data"
                                else "Zero Data Collection Commitment",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Aplikasi Kalender Jawa punika murni nglayani kabutuhan penanggalan panjenengan tanpa ngempalaken, tanpa ngrekam, lan tanpa nyimpen data pribadi marang server njawi. Aplikasi saged dipunginaaken sacara mandiri tanpa perlu akun utawi internet."
                            else if (language == AppLanguage.INDONESIAN)
                                "Kami berkomitmen penuh untuk melindungi privasi Anda. Aplikasi 'Kalender Jawa' TIDAK mengumpulkan, melacak, mentransmisikan, atau menjual data pribadi Anda (nama, lokasi, kontak, dsb.) ke server eksternal mana pun. Aplikasi ini dapat digunakan sepenuhnya secara offline tanpa akun."
                            else
                                "We are completely committed to protecting your privacy. The 'Javanese Calendar' app does NOT collect, track, transmit, or sell your personal identifiable information (PII) to any external servers. The app operates standalone without requiring registration or internet.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section 2: Local Storage
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
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Storage,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Panyimpenan Lokal ing Piranti"
                                else if (language == AppLanguage.INDONESIAN) "Penyimpanan Data Lokal di Perangkat"
                                else "Strict On-Device Data Storage",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Sedaya agenda kagiyatan, pèngetan weton, lan jadwal slametan kasimpen kanthi rapi wonten ing database lokal piranti (Room SQLite Sandbox). Ora ana pihak katelu kang bisa ngakses."
                            else if (language == AppLanguage.INDONESIAN)
                                "Seluruh catatan, judul kegiatan, dan jadwal yang Anda masukkan disimpan secara eksklusif di dalam memori internal perangkat Anda menggunakan basis data lokal Android (Room SQLite Sandbox)."
                            else
                                "All appointments, notes, and activity schedules entered by the user are stored solely within the device's local application sandbox (Room SQLite).",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section 3: Device Permissions
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
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.NotificationsActive,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Izin Piranti Handphone"
                                else if (language == AppLanguage.INDONESIAN) "Penjelasan Izin Perangkat"
                                else "Explanation of Device Permissions",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val permissions = if (language == AppLanguage.JAVANESE) listOf(
                            "POST_NOTIFICATIONS: Digunakake mung kanggo ngirim pangeling dinten tradisi Jawa lan jadwal kagiyatan. Ora tau ngirim pariwara utawa spam.",
                            "VIBRATE: Menehi getaran alus nalika wektu pangeling teka.",
                            "Izin iki bisa diatur utawa dipateni kapan wae liwat menu Setelan piranti."
                        ) else if (language == AppLanguage.INDONESIAN) listOf(
                            "POST_NOTIFICATIONS: Digunakan semata-mata untuk membunyikan pengingat jadwal hari tradisi Jawa dan agenda kegiatan yang Anda tentukan sendiri.",
                            "VIBRATE: Digunakan untuk memberikan getaran saat pengingat waktu kegiatan tiba.",
                            "Izin ini sepenuhnya berada dalam kendali Anda dan dapat dimatikan kapan saja melalui menu Pengaturan."
                        ) else listOf(
                            "POST_NOTIFICATIONS: Exclusively used to deliver traditional alarms and user-scheduled reminders. Never used for marketing.",
                            "VIBRATE: Provides gentle haptic feedback during alerts.",
                            "You may grant or revoke these permissions at any time through system settings."
                        )

                        permissions.forEach { perm ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
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
                                    text = perm,
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

        // Section 4: Data Retention & User Rights
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
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFEBEE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = BataMerah,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Hak Mbusak & Kontrol Data"
                                else if (language == AppLanguage.INDONESIAN) "Hak Hapus & Kontrol Pengguna"
                                else "Data Deletion & User Rights",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Panjenengan nggadhahi kontrol kebak. Saben kagiyatan saged dipunowahi utawi kabusek kapan kemawon. Menawi aplikasi kabusek saking piranti, sedaya data lokal badhe ical saknalika."
                            else if (language == AppLanguage.INDONESIAN)
                                "Anda memegang kendali penuh atas data Anda. Setiap kegiatan dapat diedit atau dihapus kapan saja. Menghapus aplikasi dari perangkat atau melakukan 'Hapus Data' pada Pengaturan Android akan melenyapkan seluruh data secara permanen seketika."
                            else
                                "You retain 100% control over your data. You can delete or edit any event at any time. Uninstalling the application instantly and permanently removes all stored data.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section 5: Children's Privacy
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
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Security,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Privasi Bocah & Kepatuhan Kawicaksanan"
                                else if (language == AppLanguage.INDONESIAN) "Privasi Anak & Kepatuhan Kebijakan"
                                else "Children's Privacy & Compliance",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.JAVANESE)
                                "Aplikasi punika aman dipunginaaken dening sedaya yuswa, boten ngemot bab ingkang boten trep, lan netepi sedaya pranatan Google Play Developer Program."
                            else if (language == AppLanguage.INDONESIAN)
                                "Aplikasi ini aman digunakan oleh semua kelompok usia. Kami mematuhi seluruh panduan Google Play Developer Program dan COPPA, serta tidak menyajikan konten yang membahayakan anak-anak."
                            else
                                "The app is family-friendly, suitable for all ages, and complies with Google Play Developer Program policies and COPPA requirements.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF455A64),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section 6: Contact & Date
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
                            text = if (language == AppLanguage.JAVANESE) "Pungkasan Kaanyaraken: Oktober 2026"
                            else if (language == AppLanguage.INDONESIAN) "Terakhir Diperbarui: Oktober 2026"
                            else "Last Updated: October 2026",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Email Pangembang: support@almanakjawa.local",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganPrimary
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
