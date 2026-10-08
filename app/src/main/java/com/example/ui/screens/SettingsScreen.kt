package com.example.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.notification.NotificationHelper
import com.example.ui.MainViewModel
import com.example.ui.components.JavaneseHeaderBanner
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    onNavigateToAboutUs: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val isHolidayNotif by viewModel.isHolidayNotifEnabled.collectAsStateWithLifecycle()
    val isActivityNotif by viewModel.isActivityNotifEnabled.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Izin notifikasi diaktifkan!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Izin notifikasi ditolak.", Toast.LENGTH_SHORT).show()
        }
    }

    val appPackageName = context.packageName

    val onShareApp = {
        val shareMessage = when (language) {
            AppLanguage.JAVANESE ->
                "Mangga sesarengan nguri-uri kabudayan Jawa! Sumangga dipun-undhuh saha dipun-instal aplikasi Hitungan JAWA - Almanak & Kalender Jawa jangkep kaliyan Weton, Neptu, Pawukon, Pranata Mangsa, lan Petungan Adat.\n\nKlik pranala punika kagem nginstal langsung saking Google Play Store:\nhttps://play.google.com/store/apps/details?id=$appPackageName"
            AppLanguage.INDONESIAN ->
                "Yuk lestarikan tradisi & budaya Jawa! Ayo unduh dan instal aplikasi Hitungan JAWA - Kalender & Almanak Jawa lengkap dengan Weton, Neptu, Pawukon, Pranata Mangsa, dan Perhitungan Adat Jawa.\n\nKlik link berikut untuk menginstal langsung dari Google Play Store:\nhttps://play.google.com/store/apps/details?id=$appPackageName"
            AppLanguage.ENGLISH ->
                "Discover and preserve Javanese cultural heritage! Download and install the Hitungan JAWA app - Complete Javanese Calendar, Weton, Neptu, Pawukon & Traditional Calculations.\n\nInstall directly from the Google Play Store:\nhttps://play.google.com/store/apps/details?id=$appPackageName"
        }
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Aplikasi Hitungan JAWA")
            putExtra(Intent.EXTRA_TEXT, shareMessage)
        }
        val chooserIntent = Intent.createChooser(sendIntent, StringResources.get("share_app", language))
        context.startActivity(chooserIntent)
    }

    val onRateApp = {
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            }
            context.startActivity(marketIntent)
        } catch (e: ActivityNotFoundException) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
            context.startActivity(webIntent)
        }
    }

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_semar_hitungan_jawa_1791452830769),
                        contentDescription = "Logo Semar Hitungan JAWA",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = StringResources.get("tab_settings", language),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Basa, Notifikasi & Info Pananggalan"
                            else if (language == AppLanguage.INDONESIAN) "Bahasa, Notifikasi & Info Kalender"
                            else "Language, Notifications & Calendar Info",
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
        }

        // SCROLLABLE CONTENT (HANYA BAGIAN INI YANG BERGERAK)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Language Card
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
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Language,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = StringResources.get("settings_language_title", language),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("settings_language_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AppLanguage.entries.forEach { appLang ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.setLanguage(appLang)
                                    }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = language == appLang,
                                    onClick = { viewModel.setLanguage(appLang) },
                                    colors = RadioButtonDefaults.colors(selectedColor = SoganPrimary),
                                    modifier = Modifier.testTag("lang_radio_${appLang.code}")
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = appLang.nativeName,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = if (language == appLang) FontWeight.Bold else FontWeight.Normal,
                                        color = if (language == appLang) SoganPrimary else Color(0xFF263238)
                                    )
                                    if (appLang.nativeName != appLang.displayName) {
                                        Text(
                                            text = appLang.displayName,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF78909C)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Notification Preferences Card
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
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.NotificationsActive,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = StringResources.get("settings_notifications_title", language),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !NotificationHelper.hasNotificationPermission(context)) {
                            Surface(
                                color = Color(0xFFFFEBEE),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Izin Notifikasi Diperlukan",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = BataMerah,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Izinkan notifikasi agar aplikasi dapat memberikan pengingat otomatis menjelang hari tradisi dan agenda.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = BataMerah
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = {
                                            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = BataMerah),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Izinkan Notifikasi")
                                    }
                                }
                            }
                        }

                        // Holiday Notif Switch
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = StringResources.get("settings_holiday_notif", language),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("settings_holiday_notif_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C)
                                )
                            }
                            Switch(
                                checked = isHolidayNotif,
                                onCheckedChange = { viewModel.setHolidayNotifEnabled(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = SoganPrimary, checkedTrackColor = KeratonGoldContainer),
                                modifier = Modifier.testTag("holiday_notif_switch")
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Activity Notif Switch
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = StringResources.get("settings_activity_notif", language),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("settings_activity_notif_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C)
                                )
                            }
                            Switch(
                                checked = isActivityNotif,
                                onCheckedChange = { viewModel.setActivityNotifEnabled(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = SoganPrimary, checkedTrackColor = KeratonGoldContainer),
                                modifier = Modifier.testTag("activity_notif_switch")
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Test Notification Button
                        Button(
                            onClick = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !NotificationHelper.hasNotificationPermission(context)) {
                                    permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    viewModel.sendTestNotification()
                                    Toast.makeText(context, StringResources.get("test_notification_success", language), Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("test_notification_button"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(StringResources.get("test_notification", language), color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. About Calendar Info Card
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
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = StringResources.get("calendar_info_title", language),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = StringResources.get("calendar_info_desc", language),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF546E7A),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // 4. About Us Card Row
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToAboutUs() }
                        .testTag("settings_about_us_row")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = StringResources.get("about_us", language),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("about_us_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // 5. Privacy Policy Card Row
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToPrivacyPolicy() }
                        .testTag("settings_privacy_policy_row")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Security,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = StringResources.get("privacy_policy", language),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("privacy_policy_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // 6. Share / Kirim Aplikasi Card Row
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShareApp() }
                        .testTag("settings_share_app_row")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Share,
                                    contentDescription = null,
                                    tint = SoganDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = StringResources.get("share_app", language),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("share_app_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // 7. Beri Rating Aplikasi Card Row
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRateApp() }
                        .testTag("settings_rate_app_row")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(KeratonGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFF57F17),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = StringResources.get("rate_app", language),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoganDark
                                )
                                Text(
                                    text = StringResources.get("rate_app_desc", language),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78909C),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // 8. App Version & Package Info Card
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = KremJawa,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_semar_hitungan_jawa_1791452830769),
                            contentDescription = "Logo Semar Hitungan JAWA",
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Hitungan JAWA • Versi 1.0",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                        Text(
                            text = "com.hitunganjawa.gecckocreator",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoganPrimary,
                            fontSize = 11.5.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        val updateManager = com.example.update.InAppUpdateManager.getInstance(context)
                        val isCheckingUpdate by updateManager.isCheckingUpdate.collectAsStateWithLifecycle()

                        Button(
                            onClick = {
                                val activity = context as? android.app.Activity
                                if (activity != null) {
                                    updateManager.checkManuallyFromSettings(
                                        activity = activity,
                                        onUpdateAvailable = {
                                            Toast.makeText(context, "Pembaruan resmi ditemukan di Google Play!", Toast.LENGTH_SHORT).show()
                                        },
                                        onAlreadyLatest = {
                                            Toast.makeText(context, "Aplikasi sudah menggunakan versi terbaru (v1.0).", Toast.LENGTH_LONG).show()
                                        },
                                        onErrorOrDevBuild = { _ ->
                                            updateManager.openPlayStorePage(context)
                                        }
                                    )
                                } else {
                                    updateManager.openPlayStorePage(context)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.SystemUpdate,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCheckingUpdate) "Memeriksa Google Play..." else "Periksa Pembaruan Play Store",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
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
