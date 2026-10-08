package com.example.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.usada.UsadaRecipe
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KeratonGoldDark
import com.example.ui.theme.KeratonGoldLight
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganMedium
import com.example.ui.theme.SoganPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsadaDetailDialog(
    recipe: UsadaRecipe,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val checkedIngredients = remember { mutableStateMapOf<Int, Boolean>() }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 46.dp, height = 5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(SoganMedium.copy(alpha = 0.35f))
                )
            }
        },
        containerColor = KremJawa,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .testTag("usada_detail_dialog")
        ) {
            // 1. DIALOG HEADER (Royal Sogan & Batik Theme)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF26140D),
                                SoganDark,
                                SoganPrimary
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Top bar inside header: Category badge, method, & actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Category Badge
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(recipe.category.accentColor.copy(alpha = 0.25f))
                                    .border(1.dp, recipe.category.accentColor.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = recipe.category.icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = recipe.category.titleId,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Actions: Share, Favorite, Close
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Share Button
                                IconButton(
                                    onClick = { shareRecipe(context, recipe) },
                                    modifier = Modifier
                                        .size(34.dp)
                                        .testTag("btn_share_recipe")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Bagikan Ramuan",
                                        tint = KeratonGoldLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                // Favorite Button
                                IconButton(
                                    onClick = onToggleFavorite,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .testTag("btn_fav_recipe_detail")
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Favorit",
                                        tint = if (isFavorite) KeratonGold else Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                // Close Button
                                IconButton(
                                    onClick = onDismiss,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .testTag("btn_close_usada_dialog")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Tutup",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Nama Sakit (Illness Name)
                        Text(
                            text = recipe.illnessName,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Traditional Javanese Remedy Name
                        Text(
                            text = "“${recipe.javaneseRemedyName}”",
                            color = KeratonGoldLight,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Badges Row: Method, Time, Difficulty
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Method Badge
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = recipe.method.icon,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD54F),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = recipe.method.shortName,
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Time Badge
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${recipe.estimatedMinutes} Menit",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Difficulty
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "Tingkat: ${recipe.difficulty}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }

                // Golden Accent Line below header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(KeratonGoldDark, KeratonGold, KeratonGoldLight, KeratonGoldDark)
                            )
                        )
                )

                // 2. DIALOG CONTENT BODY
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Ringkasan Manfaat
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBF7F0)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE8DCC4)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = SoganPrimary,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = recipe.summary,
                                color = Color(0xFF3E2723),
                                fontSize = 13.5.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // SECTION: BAHAN-BAHAN & TAKARAN (Checklist Interaktif)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Komposisi Bahan & Takaran",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = SoganDark
                        )
                        Text(
                            text = "${recipe.ingredients.size} Bahan",
                            fontSize = 12.sp,
                            color = Color(0xFF795548),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "Ketuk bahan untuk menandai yang sudah siap di dapur:",
                        fontSize = 11.5.sp,
                        color = Color(0xFF8D6E63),
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        recipe.ingredients.forEachIndexed { index, ingredient ->
                            val isChecked = checkedIngredients[index] == true
                            val bgColor by animateColorAsState(
                                targetValue = if (isChecked) Color(0xFFF1F8E9) else Color(0xFFFAFAFA),
                                label = "ingredient_bg"
                            )
                            val borderColor = if (isChecked) Color(0xFF81C784) else Color(0xFFE0E0E0)

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        checkedIngredients[index] = !isChecked
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = bgColor,
                                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Custom Checkbox circle
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isChecked) Color(0xFF2E7D32) else Color.White
                                            )
                                            .border(
                                                1.5.dp,
                                                if (isChecked) Color(0xFF2E7D32) else Color(0xFFBDBDBD),
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isChecked) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = ingredient.name,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.5.sp,
                                                color = if (isChecked) Color(0xFF1B5E20) else Color(0xFF212121)
                                            )
                                            Text(
                                                text = ingredient.amount,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SoganPrimary
                                            )
                                        }
                                        if (ingredient.preparationNote.isNotBlank()) {
                                            Text(
                                                text = ingredient.preparationNote,
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF757575)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // SECTION: CARA MEMBUAT (Langkah demi Langkah)
                    Text(
                        text = "Cara Meracik & Mengolah",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = SoganDark
                    )
                    Text(
                        text = "Langkah pengolahan ramuan tradisional:",
                        fontSize = 11.5.sp,
                        color = Color(0xFF8D6E63),
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        recipe.steps.forEachIndexed { stepIndex, stepText ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFF9F7F3))
                                    .border(1.dp, Color(0xFFEDE6DA), RoundedCornerShape(10.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                // Step number badge
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(SoganPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${stepIndex + 1}",
                                        color = KeratonGoldLight,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = stepText,
                                    fontSize = 13.sp,
                                    color = Color(0xFF3E2723),
                                    lineHeight = 18.5.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // SECTION: ATURAN PAKAI / CARA MINUM
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA5D6A7)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Aturan Pakai & Waktu Konsumsi",
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = recipe.usageRule,
                                fontSize = 12.5.sp,
                                color = Color(0xFF2E7D32),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    // SECTION: PANTANGAN & HAL YANG DIHINDARI (Jika ada)
                    recipe.contraindications?.let { warningText ->
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFCC80)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Color(0xFFE65100),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Pantangan & Anjuran Hidup Sehat",
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFBF360C)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = warningText,
                                    fontSize = 12.5.sp,
                                    color = Color(0xFFE65100),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    // SECTION: PETUAH PRIMBON USADA JAWA (Kearifan Serat Usada)
                    recipe.primbonWisdom?.let { wisdomText ->
                        Spacer(modifier = Modifier.height(14.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, KeratonGold),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoStories,
                                        contentDescription = null,
                                        tint = SoganDark,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Serat Primbon Usada Jawi",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SoganDark
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "“$wisdomText”",
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = Color(0xFF4E342E),
                                    lineHeight = 17.5.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ACTION BUTTON: BAGIKAN KE KELUARGA & TUTUP
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { shareRecipe(context, recipe) },
                            color = Color(0xFFEFEBE9),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    tint = SoganPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Bagikan Resep",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SoganPrimary
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(onClick = onDismiss),
                            color = SoganPrimary,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Tutup",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }
}

/**
 * Share Recipe text via Android System Intent
 */
private fun shareRecipe(context: Context, recipe: UsadaRecipe) {
    val shareBody = buildString {
        appendLine("🌿 USADA JAWA - RESEP RAMUAN TRADISIONAL")
        appendLine("Obat Keluhan: ${recipe.illnessName}")
        appendLine("Nama Ramuan: ${recipe.javaneseRemedyName}")
        appendLine("Kategori: ${recipe.category.titleId}")
        appendLine("Metode Olah: ${recipe.method.titleId}")
        appendLine("Waktu Olah: ${recipe.estimatedMinutes} Menit (${recipe.difficulty})")
        appendLine()
        appendLine("📌 KOMPOSISI BAHAN:")
        recipe.ingredients.forEach { ing ->
            appendLine("• ${ing.name} (${ing.amount})${if (ing.preparationNote.isNotBlank()) " - " + ing.preparationNote else ""}")
        }
        appendLine()
        appendLine("🥣 CARA MEMBUAT:")
        recipe.steps.forEachIndexed { i, step ->
            appendLine("${i + 1}. $step")
        }
        appendLine()
        appendLine("⏰ ATURAN PAKAI:")
        appendLine(recipe.usageRule)
        recipe.contraindications?.let {
            appendLine()
            appendLine("⚠️ PANTANGAN:")
            appendLine(it)
        }
        recipe.primbonWisdom?.let {
            appendLine()
            appendLine("📜 SERAT PRIMBON USADA:")
            appendLine("\"$it\"")
        }
        appendLine()
        appendLine("Dibagikan dari Aplikasi Hitungan JAWA (Almanak & Usada Tradisional Jawa)")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Resep Usada Jawa: ${recipe.illnessName}")
        putExtra(Intent.EXTRA_TEXT, shareBody)
    }
    context.startActivity(Intent.createChooser(intent, "Bagikan Resep Usada Jawa"))
}
