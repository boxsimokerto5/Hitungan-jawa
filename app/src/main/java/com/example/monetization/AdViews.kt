package com.example.monetization

import android.app.Activity
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import com.ironsource.mediationsdk.ISBannerSize
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.IronSourceBannerLayout

/**
 * Komponen Banner Ad sesuai kebijakan Google Play:
 * Diberi margin yang cukup agar tidak terjadi accidental click,
 * dan terintegrasi langsung dengan ironSource Banner Layout.
 */
@Composable
fun BannerAdView(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var bannerLayout by remember { mutableStateOf<IronSourceBannerLayout?>(null) }

    DisposableEffect(activity) {
        if (activity != null) {
            try {
                val layout = IronSource.createBanner(activity, ISBannerSize.BANNER)
                bannerLayout = layout
                if (layout != null) {
                    IronSource.loadBanner(layout)
                }
            } catch (_: Exception) {}
        }
        onDispose {
            bannerLayout?.let {
                try {
                    IronSource.destroyBanner(it)
                } catch (_: Exception) {}
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        val currentBanner = bannerLayout
        if (currentBanner != null) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("ironsource_banner_view"),
                factory = { ctx ->
                    FrameLayout(ctx).apply {
                        addView(currentBanner)
                    }
                }
            )
        } else {
            // Tampilan kontainer iklan standar mematuhi panduan periklanan
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("banner_ad_container"),
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFCFD8DC))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFECEFF1),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "IKLAN",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF546E7A),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                                fontSize = 10.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ruang Iklan Banner Sponsor",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF78909C),
                            fontSize = 11.sp
                        )
                    }
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFFB0BEC5),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Komponen Native Ad bernuansa Keraton:
 * Menyatu dengan tata letak daftar kartu, namun memiliki label "IKLAN / SPONSORED"
 * yang jelas dan tombol Call-To-Action terpisah agar 100% mematuhi Google Play Policy.
 */
@Composable
fun NativeAdCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("native_ad_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(KeratonGoldContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = SoganDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Rekomendasi Pilihan",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                        Text(
                            text = "Informasi & Layanan Terpercaya",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF78909C),
                            fontSize = 11.sp
                        )
                    }
                }

                // Wajib dari Google Play: Label Iklan
                Surface(
                    color = Color(0xFFFFF8E1),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, KeratonGold)
                ) {
                    Text(
                        text = "IKLAN",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF57F17),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Temukan berbagai kebutuhan budaya, busana adat Jawa, dan pernak-pernik tradisi nusantara terbaik melalui mitra resmi.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF37474F),
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { /* Interaksi iklan mitra */ },
                    colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Kunjungi Mitra",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * Dialog Rewarded Ad:
 * Pengguna sukarela memilih untuk menonton video singkat
 * demi membuka analisis primbon atau tips selapanan mendalam.
 */
@Composable
fun RewardedAdUnlockDialog(
    title: String,
    description: String,
    onWatchAd: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.OndemandVideo,
                    contentDescription = null,
                    tint = SoganPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
            }
        },
        text = {
            Column {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF37474F)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = KeratonGoldContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "⭐ Bebas biaya • Tonton video singkat 15-30 detik untuk langsung membuka.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SoganDark,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 11.5.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    onWatchAd()
                },
                colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Tonton Video Singkat", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Nanti Saja", color = Color(0xFF78909C))
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(14.dp)
    )
}
