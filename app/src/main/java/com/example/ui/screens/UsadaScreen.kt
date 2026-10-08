package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.usada.UsadaCategory
import com.example.data.usada.UsadaDataRepository
import com.example.data.usada.UsadaHerbInfo
import com.example.data.usada.UsadaMethod
import com.example.data.usada.UsadaRecipe
import com.example.localization.AppLanguage
import com.example.monetization.AdManager
import com.example.monetization.BannerAdView
import com.example.monetization.NativeAdCard
import com.example.ui.MainViewModel
import com.example.ui.components.JavaneseHeaderBanner
import com.example.ui.components.UsadaDetailDialog
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KeratonGoldDark
import com.example.ui.theme.KeratonGoldLight
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary

@Composable
fun UsadaScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteUsadaIds.collectAsStateWithLifecycle()

    var activeTab by rememberSaveable { mutableIntStateOf(0) } // 0: Resep Ramuan, 1: Kamus Herbal
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedMethod by rememberSaveable { mutableStateOf<String?>(null) }
    var showOnlyFavorites by rememberSaveable { mutableStateOf(false) }

    var selectedRecipeForDetail by remember { mutableStateOf<UsadaRecipe?>(null) }

    // Filtered recipes
    val categoryFilter = selectedCategory?.let { catId ->
        UsadaCategory.values().find { it.id == catId }
    }
    val methodFilter = selectedMethod?.let { metId ->
        UsadaMethod.values().find { it.id == metId }
    }

    val filteredRecipes = remember(searchQuery, selectedCategory, selectedMethod, showOnlyFavorites, favoriteIds) {
        UsadaDataRepository.filterRecipes(
            query = searchQuery,
            category = categoryFilter,
            method = methodFilter,
            onlyFavorites = showOnlyFavorites,
            favoriteIds = favoriteIds
        )
    }

    // Detail dialog
    selectedRecipeForDetail?.let { recipe ->
        UsadaDetailDialog(
            recipe = recipe,
            isFavorite = favoriteIds.contains(recipe.id),
            onToggleFavorite = { viewModel.toggleFavoriteUsada(recipe.id) },
            onDismiss = { selectedRecipeForDetail = null }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KremJawa)
            .testTag("usada_screen")
    ) {
        // 1. SOGAN & KERATON GOLD TOP BANNER (RAMPING, ELEGAN & TIDAK TERLALU LEBAR KE BAWAH)
        JavaneseHeaderBanner(
            contentPaddingBottom = 8.dp
        ) {
            // Header Row: Icon, Title, and Compact Counter Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // App Logo Badge
                    Image(
                        painter = painterResource(id = R.drawable.ic_semar_hitungan_jawa_1791452830769),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = when (language) {
                                AppLanguage.JAVANESE -> "Usada & Jamu Jawi"
                                AppLanguage.ENGLISH -> "Javanese Traditional Usada"
                                else -> "Usada & Jamu Tradisional"
                            },
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = when (language) {
                                AppLanguage.JAVANESE -> "Primbon Usada • Tamba & Racikan"
                                AppLanguage.ENGLISH -> "Ancient Herbal Healing Wisdom"
                                else -> "Primbon Usada • Warisan Herbal"
                            },
                            color = KeratonGoldLight,
                            fontSize = 11.sp,
                            fontStyle = FontStyle.Italic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Compact Badge (No vertical stretching)
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, KeratonGold.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "${UsadaDataRepository.recipes.size} Ramuan",
                        color = KeratonGoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // 2. MAIN SUB-TABS (Resep Ramuan vs Kamus Herbal)
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            ScrollableTabRow(
                selectedTabIndex = activeTab,
                containerColor = Color.White,
                contentColor = SoganPrimary,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[activeTab]),
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
                            Icon(
                                imageVector = Icons.Default.LocalDrink,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (activeTab == 0) SoganPrimary else Color(0xFF8D6E63)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Daftar Ramuan & Obat",
                                fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Normal,
                                color = if (activeTab == 0) SoganPrimary else Color(0xFF8D6E63),
                                fontSize = 13.sp
                            )
                        }
                    },
                    modifier = Modifier.testTag("tab_usada_recipes")
                )

                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Yard,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (activeTab == 1) SoganPrimary else Color(0xFF8D6E63)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Kamus Rimpang & Herbal",
                                fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Normal,
                                color = if (activeTab == 1) SoganPrimary else Color(0xFF8D6E63),
                                fontSize = 13.sp
                            )
                        }
                    },
                    modifier = Modifier.testTag("tab_usada_glossary")
                )
            }
        }

        // 3. TAB CONTENT
        if (activeTab == 0) {
            // TAB 1: RESEP RAMUAN & OBAT SAKIT
            RecipesContent(
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                selectedCategory = selectedCategory,
                onSelectCategory = { selectedCategory = it },
                selectedMethod = selectedMethod,
                onSelectMethod = { selectedMethod = it },
                showOnlyFavorites = showOnlyFavorites,
                onToggleOnlyFavorites = { showOnlyFavorites = !showOnlyFavorites },
                filteredRecipes = filteredRecipes,
                favoriteIds = favoriteIds,
                onToggleFavorite = { viewModel.toggleFavoriteUsada(it) },
                onSelectRecipe = { selectedRecipeForDetail = it }
            )
        } else {
            // TAB 2: KAMUS RIMPANG & TANAMAN HERBAL JAWA
            HerbGlossaryContent()
        }

        // Banner Ad docked at bottom of UsadaScreen
        BannerAdView(adUnitId = AdManager.BANNER_2_ID, modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp))
    }
}

/**
 * Recipes Tab Content
 */
@Composable
private fun RecipesContent(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCategory: String?,
    onSelectCategory: (String?) -> Unit,
    selectedMethod: String?,
    onSelectMethod: (String?) -> Unit,
    showOnlyFavorites: Boolean,
    onToggleOnlyFavorites: () -> Unit,
    filteredRecipes: List<UsadaRecipe>,
    favoriteIds: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onSelectRecipe: (UsadaRecipe) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("usada_recipes_list"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Search Bar & Filter Controls Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Search Input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = {
                        Text(
                            "Cari keluhan sakit, jahe, batuk, maag...",
                            fontSize = 13.sp,
                            color = Color(0xFF9E9E9E)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Hapus",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SoganPrimary,
                        unfocusedBorderColor = Color(0xFFD7CCC8),
                        focusedContainerColor = Color(0xFFFAFAFA),
                        unfocusedContainerColor = Color(0xFFFAFAFA)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("input_search_usada")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips Scrollable Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // All Category Chip
                    FilterChip(
                        selected = selectedCategory == null && !showOnlyFavorites,
                        onClick = {
                            onSelectCategory(null)
                        },
                        label = {
                            Text(
                                "Semua Kategori (${UsadaDataRepository.recipes.size})",
                                fontSize = 11.5.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SoganPrimary,
                            selectedLabelColor = Color.White
                        )
                    )

                    // Favorites Only Chip
                    FilterChip(
                        selected = showOnlyFavorites,
                        onClick = onToggleOnlyFavorites,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        label = {
                            Text(
                                "Favorit Saya (${favoriteIds.size})",
                                fontSize = 11.5.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KeratonGold,
                            selectedLabelColor = Color(0xFF26140D)
                        )
                    )

                    // Specific Category Chips
                    UsadaCategory.values().forEach { category ->
                        val count = UsadaDataRepository.recipes.count { it.category == category }
                        FilterChip(
                            selected = selectedCategory == category.id && !showOnlyFavorites,
                            onClick = {
                                onSelectCategory(if (selectedCategory == category.id) null else category.id)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = category.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(13.dp)
                                )
                            },
                            label = {
                                Text(
                                    "${category.titleId} ($count)",
                                    fontSize = 11.5.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SoganPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Method filter row (Godokan, Peras/Loloh, Parem/Balur, Tapel/Pilis, Kumur)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Metode:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8D6E63)
                    )

                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { onSelectMethod(null) },
                        color = if (selectedMethod == null) Color(0xFFEFEBE9) else Color.Transparent,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (selectedMethod == null) SoganPrimary else Color(0xFFE0E0E0)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Semua",
                            fontSize = 10.5.sp,
                            fontWeight = if (selectedMethod == null) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedMethod == null) SoganPrimary else Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    UsadaMethod.values().forEach { method ->
                        val isSelected = selectedMethod == method.id
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    onSelectMethod(if (isSelected) null else method.id)
                                },
                            color = if (isSelected) Color(0xFFEFEBE9) else Color.Transparent,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) SoganPrimary else Color(0xFFE0E0E0)
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = method.shortName,
                                fontSize = 10.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) SoganPrimary else Color.Gray,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }

        // Recommended / Featured Card (Wedang Jahe Sereh or Jamu Beras Kencur)
        if (searchQuery.isEmpty() && selectedCategory == null && !showOnlyFavorites) {
            item {
                FeaturedRecommendationBanner(
                    onSelect = onSelectRecipe
                )
            }
        }

        // Section Title & Result Count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showOnlyFavorites) "Ramuan Favorit Saya" else "Daftar Ramuan Tradisional",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = SoganDark
                )
                Text(
                    text = "${filteredRecipes.size} Ramuan Ditemukan",
                    fontSize = 11.5.sp,
                    color = Color(0xFF8D6E63)
                )
            }
        }

        // Empty State
        if (filteredRecipes.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFFBCAAA4),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (showOnlyFavorites) "Belum ada ramuan yang ditandai favorit." else "Tidak ada ramuan yang sesuai pencarian.",
                            color = Color(0xFF795548),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (showOnlyFavorites) "Ketuk ikon penanda buku pada ramuan untuk menyimpannya di sini." else "Coba cari dengan kata kunci lain atau pilih 'Semua Kategori'.",
                            color = Color(0xFF9E9E9E),
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            // Recipe Cards
            itemsIndexed(filteredRecipes, key = { _, it -> it.id }) { index, recipe ->
                if (index == 3) {
                    NativeAdCard()
                    Spacer(modifier = Modifier.height(4.dp))
                }
                RecipeItemCard(
                    recipe = recipe,
                    isFavorite = favoriteIds.contains(recipe.id),
                    onToggleFavorite = { onToggleFavorite(recipe.id) },
                    onClick = { onSelectRecipe(recipe) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

/**
 * Featured Recommendation Banner
 */
@Composable
private fun FeaturedRecommendationBanner(
    onSelect: (UsadaRecipe) -> Unit
) {
    val featuredRecipe = UsadaDataRepository.recipes.firstOrNull { it.id == "usada_masuk_angin" }
        ?: UsadaDataRepository.recipes.first()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSelect(featuredRecipe) }
            .testTag("card_featured_usada"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF3E2723),
                            Color(0xFF5D4037)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = KeratonGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Rekomendasi Primbon Hari Ini",
                            color = KeratonGoldLight,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "Paling Populer",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = featuredRecipe.illnessName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "“${featuredRecipe.javaneseRemedyName}”",
                    color = KeratonGoldLight,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = featuredRecipe.summary,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "• ${featuredRecipe.ingredients.size} Bahan • ${featuredRecipe.estimatedMinutes} Menit",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = KeratonGold,
                        modifier = Modifier.clickable { onSelect(featuredRecipe) }
                    ) {
                        Text(
                            text = "Lihat Resep & Cara Olah ➔",
                            color = Color(0xFF26140D),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Individual Recipe Item Card
 */
@Composable
private fun RecipeItemCard(
    recipe: UsadaRecipe,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("recipe_item_${recipe.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEBE9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Category tag + Method tag + Bookmark action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category badge
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = recipe.category.accentColor.copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = recipe.category.icon,
                                contentDescription = null,
                                tint = recipe.category.accentColor,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = recipe.category.titleId,
                                color = recipe.category.accentColor,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Method badge
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFF5F5F5)
                    ) {
                        Text(
                            text = recipe.method.shortName,
                            color = Color(0xFF616161),
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                // Bookmark icon button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("btn_fav_${recipe.id}")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Favorit",
                        tint = if (isFavorite) KeratonGoldDark else Color(0xFFBDBDBD),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Nama Sakit (Illness Name)
            Text(
                text = recipe.illnessName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF26140D)
            )

            // Nama Ramuan Tradisional
            Text(
                text = "“${recipe.javaneseRemedyName}”",
                fontSize = 13.5.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = SoganPrimary,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Summary description
            Text(
                text = recipe.summary,
                fontSize = 12.5.sp,
                color = Color(0xFF5D4037),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = Color(0xFFF5F5F5))

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom metadata row: Bahan count, Waktu, & Tap hint
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${recipe.ingredients.size} Bahan Alami",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF795548)
                    )
                    Text(
                        text = "•",
                        fontSize = 11.sp,
                        color = Color(0xFFBDBDBD)
                    )
                    Text(
                        text = "${recipe.estimatedMinutes} Menit (${recipe.difficulty})",
                        fontSize = 11.5.sp,
                        color = Color(0xFF795548)
                    )
                }

                Text(
                    text = "Lihat Resep ➔",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganPrimary
                )
            }
        }
    }
}

/**
 * Tab 2: Herb Glossary Content (Kamus Rimpang & Tanaman Obat Usada Jawa)
 */
@Composable
private fun HerbGlossaryContent() {
    var herbSearch by rememberSaveable { mutableStateOf("") }
    val filteredHerbs = remember(herbSearch) {
        if (herbSearch.isBlank()) {
            UsadaDataRepository.herbsGlossary
        } else {
            val q = herbSearch.trim().lowercase()
            UsadaDataRepository.herbsGlossary.filter {
                it.indonesianName.lowercase().contains(q) ||
                    it.javaneseName.lowercase().contains(q) ||
                    it.latinName.lowercase().contains(q) ||
                    it.primaryBenefit.lowercase().contains(q) ||
                    it.description.lowercase().contains(q)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("usada_herbs_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Search & Intro
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = herbSearch,
                    onValueChange = { herbSearch = it },
                    placeholder = {
                        Text(
                            "Cari tanaman: Temulawak, Sirih, Jahe, Kencur...",
                            fontSize = 13.sp,
                            color = Color(0xFF9E9E9E)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (herbSearch.isNotEmpty()) {
                            IconButton(onClick = { herbSearch = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Hapus",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SoganPrimary,
                        unfocusedBorderColor = Color(0xFFD7CCC8),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Intro Banner
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EBE6)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD7CCC8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = null,
                            tint = SoganPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Mengenal khasiat 15 tanaman obat, rimpang empon-empon, dan dedaunan warisan leluhur Jawa.",
                            fontSize = 12.sp,
                            color = SoganDark,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Daftar Tanaman Obat (${filteredHerbs.size})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoganDark
                )
            }
        }

        // Herb Items
        items(filteredHerbs, key = { it.indonesianName }) { herb ->
            HerbItemCard(herb = herb)
        }
    }
}

/**
 * Individual Herb Glossary Card
 */
@Composable
private fun HerbItemCard(herb: UsadaHerbInfo) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .animateContentSize()
            .testTag("herb_item_${herb.javaneseName}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEBE9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = herb.indonesianName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF26140D)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = herb.partUsed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Jawa: ${herb.javaneseName} • (${herb.latinName})",
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF8D6E63),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Tutup" else "Buka",
                        tint = SoganPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Khasiat Utama Badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFFF8E1),
                border = androidx.compose.foundation.BorderStroke(1.dp, KeratonGoldContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Khasiat: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp,
                        color = SoganDark
                    )
                    Text(
                        text = herb.primaryBenefit,
                        fontSize = 11.5.sp,
                        color = Color(0xFF4E342E),
                        maxLines = if (expanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFF5F5F5))
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = herb.description,
                    fontSize = 12.5.sp,
                    color = Color(0xFF424242),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Pasangan Racikan: ",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoganPrimary
                    )
                    Text(
                        text = herb.commonPairs,
                        fontSize = 11.5.sp,
                        color = Color(0xFF5D4037)
                    )
                }
            }
        }
    }
}
