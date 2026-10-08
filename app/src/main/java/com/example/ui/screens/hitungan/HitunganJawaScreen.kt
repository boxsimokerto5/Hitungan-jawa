package com.example.ui.screens.hitungan

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.R
import com.example.localization.AppLanguage
import com.example.ui.MainViewModel
import com.example.ui.components.JavaneseHeaderBanner
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganMedium
import com.example.ui.theme.SoganPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HitunganJawaScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(HitunganCategory.KANDUNGAN) }
    var activeModalType by remember { mutableStateOf<HitunganType?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val allCards = remember {
        listOf(
            // KANDUNGAN
            HitunganCardInfo(
                type = HitunganType.MAPATI_4_BULAN,
                category = HitunganCategory.KANDUNGAN,
                title = "Hitungan 4 Bulanan",
                subtitle = "Mapati / Ngeblang",
                description = "Penentu hari baik syukuran ditiupkannya ruh janin (120 hari).",
                tag = "120 Hari",
                icon = Icons.Default.PregnantWoman,
                accentColor = Color(0xFFD97706)
            ),
            HitunganCardInfo(
                type = HitunganType.MITONI_7_BULAN,
                category = HitunganCategory.KANDUNGAN,
                title = "Hitungan 7 Bulanan",
                subtitle = "Mitoni / Tingkeban",
                description = "Penentu tanggal & pasaran terbaik tradisi 7 bulanan & cengkir gading.",
                tag = "210 Hari",
                icon = Icons.Default.ChildCare,
                accentColor = Color(0xFFC59B27)
            ),
            HitunganCardInfo(
                type = HitunganType.HPL_JAWA,
                category = HitunganCategory.KANDUNGAN,
                title = "Estimasi Kelahiran",
                subtitle = "HPL Jawa & Karakter",
                description = "Prediksi tanggal lahir, Pranata Mangsa, Wuku, & neptu orang tua.",
                tag = "HPL Medis & Jawa",
                icon = Icons.Default.Favorite,
                accentColor = Color(0xFFE11D48)
            ),

            // KELAHIRAN
            HitunganCardInfo(
                type = HitunganType.SEPASAR_5_HARI,
                category = HitunganCategory.KELAHIRAN,
                title = "Hitung Sepasar",
                subtitle = "5 Hari Kelahiran",
                description = "Menghitung hari peringatan sepasar kelahiran & puput puser.",
                tag = "Puput Puser",
                icon = Icons.Default.CalendarToday,
                accentColor = Color(0xFF2E7D32)
            ),
            HitunganCardInfo(
                type = HitunganType.SELAPANAN_35_HARI,
                category = HitunganCategory.KELAHIRAN,
                title = "Hitung Selapanan",
                subtitle = "35 Hari Kelahiran",
                description = "Tepat hari weton berulang pertama kali untuk potong rambut & aqiqah.",
                tag = "35 Hari",
                icon = Icons.Default.Refresh,
                accentColor = Color(0xFF0284C7)
            ),
            HitunganCardInfo(
                type = HitunganType.TEDHAK_SITEN,
                category = HitunganCategory.KELAHIRAN,
                title = "Hitung Tedhak Siten",
                subtitle = "7 Selapan (Turun Tanah)",
                description = "Menghitung hari baik tradisi bayi pertama kali turun tanah (usia ±8 bulan).",
                tag = "7 Selapan",
                icon = Icons.Default.Landscape,
                accentColor = Color(0xFF7C3AED)
            ),
            HitunganCardInfo(
                type = HitunganType.MENDHEM_ARI_ARI,
                category = HitunganCategory.KELAHIRAN,
                title = "Tata Cara Mendhem Ari-Ari",
                subtitle = "Panduan & Uborampe",
                description = "Panduan lokasi & tata cara mengubur ari-ari sesuai jenis kelamin.",
                tag = "Adat Sakral",
                icon = Icons.Default.Eco,
                accentColor = Color(0xFF059669)
            ),
            HitunganCardInfo(
                type = HitunganType.WATAK_KARAKTER_BAYI,
                category = HitunganCategory.KELAHIRAN,
                title = "Watak & Karakter Bayi",
                subtitle = "Astrologi Primbon",
                description = "Penjabaran karakter bawaan berdasarkan Wuku, Neptu, & jam lahir.",
                tag = "Primbon Watak",
                icon = Icons.Default.Psychology,
                accentColor = Color(0xFFEA580C)
            ),
            HitunganCardInfo(
                type = HitunganType.REKOMENDASI_NAMA_JAWA,
                category = HitunganCategory.KELAHIRAN,
                title = "Rekomendasi Nama Jawa",
                subtitle = "Makna & Aksara Jawa",
                description = "Koleksi nama anak berbahasa Jawa beserta neptu huruf & arti luhur.",
                tag = "Nama Adiluhung",
                icon = Icons.Default.MenuBook,
                accentColor = Color(0xFF9333EA)
            ),

            // PERJODOHAN
            HitunganCardInfo(
                type = HitunganType.KECOCOKAN_JODOH,
                category = HitunganCategory.PERJODOHAN,
                title = "Hitung Kecocokan Jodoh",
                subtitle = "Pethukan Salaki Rabi",
                description = "Kalkulator kecocokan neptu pasangan (Pegat, Ratu, Jodoh, Topo, Tinari, dll).",
                tag = "Siklus 8 & 7",
                icon = Icons.Default.Favorite,
                accentColor = Color(0xFFBE123C)
            ),
            HitunganCardInfo(
                type = HitunganType.HARI_BAIK_NIKAH,
                category = HitunganCategory.PERJODOHAN,
                title = "Cari Hari Baik Nikah",
                subtitle = "Ijab Kabul & Resepsi",
                description = "Rekomendasi tanggal terbaik terhindar dari Dina Tali Bangke & Ngebleng.",
                tag = "Hari Mulia",
                icon = Icons.Default.Celebration,
                accentColor = Color(0xFFD97706)
            ),
            HitunganCardInfo(
                type = HitunganType.ARAH_REZEKI_PASANGAN,
                category = HitunganCategory.PERJODOHAN,
                title = "Arah Rezeki Suami Istri",
                subtitle = "Keberuntungan Rumah & Usaha",
                description = "Petunjuk arah keberuntungan dan tempat tinggal yang baik setelah menikah.",
                tag = "Mata Angin",
                icon = Icons.Default.Explore,
                accentColor = Color(0xFF0D9488)
            ),

            // KEMATIAN & HAJAT
            HitunganCardInfo(
                type = HitunganType.SELAMETAN_KEMATIAN,
                category = HitunganCategory.KEMATIAN_HAJAT,
                title = "Slametan Kematian",
                subtitle = "Geblag s/d 1000 Hari",
                description = "Kalkulator penentu tanggal peringatan: Geblag, 3, 7, 40, 100, 1 Thn, 2 Thn, 1000 Hari.",
                tag = "8 Peringatan",
                icon = Icons.Default.Nightlight,
                accentColor = Color(0xFF475569)
            ),
            HitunganCardInfo(
                type = HitunganType.PINDAH_RUMAH,
                category = HitunganCategory.KEMATIAN_HAJAT,
                title = "Pindah Rumah & Boyongan",
                subtitle = "Hari Baik & Arah Hadap",
                description = "Menghitung hari baik pindah rumah dan arah keberuntungan rumah baru.",
                tag = "Boyongan",
                icon = Icons.Default.Home,
                accentColor = Color(0xFF16A34A)
            ),
            HitunganCardInfo(
                type = HitunganType.BUKA_USAHA,
                category = HitunganCategory.KEMATIAN_HAJAT,
                title = "Buka Usaha / Bisnis",
                subtitle = "Grand Opening & Hoki",
                description = "Menentukan tanggal hoki untuk memulai usaha baru dan arah hadap toko.",
                tag = "Kejayaan Usaha",
                icon = Icons.Default.Storefront,
                accentColor = Color(0xFFCA8A04)
            ),
            HitunganCardInfo(
                type = HitunganType.NAGA_DINA,
                category = HitunganCategory.KEMATIAN_HAJAT,
                title = "Bepergian Jauh (Naga Dina)",
                subtitle = "Arah Aman Perjalanan",
                description = "Petunjuk arah dan hari yang aman untuk perjalanan jauh terhindar dari marabahaya.",
                tag = "Keselamatan",
                icon = Icons.Default.CompassCalibration,
                accentColor = Color(0xFF4338CA)
            )
        )
    }

    val filteredCards = remember(selectedCategory) {
        allCards.filter { it.category == selectedCategory }
    }

    BackHandler(enabled = activeModalType != null) {
        activeModalType = null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KremJawa)
    ) {
        // Top Header (FIXED / TIDAK IKUT SCROLL NAIK-TURUN)
        JavaneseHeaderBanner(
            contentPaddingBottom = 12.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Hitungan JAWA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "ꦥꦺꦠꦸꦔꦤ꧀ꦗꦮ • Pedoman Primbon Adiluhung",
                        fontSize = 11.5.sp,
                        color = KeratonGoldContainer
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_hitungan_jawa_logo_1791383285970),
                    contentDescription = "Logo Hitungan Jawa",
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        // Category Tab Row
        ScrollableTabRow(
            selectedTabIndex = HitunganCategory.values().indexOf(selectedCategory),
            containerColor = Color.White,
            contentColor = SoganDark,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                val tabIndex = HitunganCategory.values().indexOf(selectedCategory)
                if (tabIndex in tabPositions.indices) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = KeratonGold,
                        height = 3.dp
                    )
                }
            },
            divider = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hitungan_category_tab_row")
        ) {
            HitunganCategory.values().forEach { cat ->
                val isSelected = selectedCategory == cat
                Tab(
                    selected = isSelected,
                    onClick = { selectedCategory = cat },
                    text = {
                        Text(
                            text = cat.title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SoganDark else Color.Gray,
                            maxLines = 1
                        )
                    },
                    modifier = Modifier.testTag("tab_${cat.name.lowercase()}")
                )
            }
        }

        // Subtitle banner for category
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3EDE2))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedCategory.subtitle,
                    fontSize = 12.sp,
                    color = SoganMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = selectedCategory.aksara,
                    fontSize = 13.sp,
                    color = KeratonGold,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // 2-Column Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("hitungan_cards_grid")
        ) {
            items(filteredCards, key = { it.type.name }) { card ->
                HitunganGridCard(
                    card = card,
                    onClick = { activeModalType = card.type }
                )
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Modal Bottom Sheet when card is clicked
    if (activeModalType != null) {
        ModalBottomSheet(
            onDismissRequest = { activeModalType = null },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            when (activeModalType) {
                HitunganType.MAPATI_4_BULAN -> MapatiSheet(onDismiss = { activeModalType = null })
                HitunganType.MITONI_7_BULAN -> MitoniSheet(onDismiss = { activeModalType = null })
                HitunganType.HPL_JAWA -> HplJawaSheet(onDismiss = { activeModalType = null })
                HitunganType.SEPASAR_5_HARI -> SepasarSheet(onDismiss = { activeModalType = null })
                HitunganType.SELAPANAN_35_HARI -> SelapananSheet(onDismiss = { activeModalType = null })
                HitunganType.TEDHAK_SITEN -> TedhakSitenSheet(onDismiss = { activeModalType = null })
                HitunganType.MENDHEM_ARI_ARI -> MendhemAriAriSheet(onDismiss = { activeModalType = null })
                HitunganType.WATAK_KARAKTER_BAYI -> WatakBayiSheet(onDismiss = { activeModalType = null })
                HitunganType.REKOMENDASI_NAMA_JAWA -> NamaJawaSheet(onDismiss = { activeModalType = null })
                HitunganType.KECOCOKAN_JODOH -> KecocokanJodohDetailedSheet(onDismiss = { activeModalType = null })
                HitunganType.HARI_BAIK_NIKAH -> HariBaikNikahSheet(onDismiss = { activeModalType = null })
                HitunganType.ARAH_REZEKI_PASANGAN -> ArahRezekiPasanganSheet(onDismiss = { activeModalType = null })
                HitunganType.SELAMETAN_KEMATIAN -> SlametanKematianSheet(onDismiss = { activeModalType = null })
                HitunganType.PINDAH_RUMAH -> PindahRumahSheet(onDismiss = { activeModalType = null })
                HitunganType.BUKA_USAHA -> BukaUsahaSheet(onDismiss = { activeModalType = null })
                HitunganType.NAGA_DINA -> NagaDinaSheet(onDismiss = { activeModalType = null })
                null -> {}
            }
        }
    }
}

@Composable
fun HitunganGridCard(
    card: HitunganCardInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("card_${card.type.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(38.dp)
                        .background(card.accentColor.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = card.icon,
                        contentDescription = null,
                        tint = card.accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .background(KeratonGoldContainer, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = card.tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoganDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = card.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SoganDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = card.subtitle,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = KeratonGold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = card.description,
                fontSize = 11.sp,
                color = Color.DarkGray,
                lineHeight = 15.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
