package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.calendar.JavaneseHoliday
import com.example.calendar.JavaneseHolidayCategory
import com.example.calendar.JavaneseHolidayInstance
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KeratonGoldLight
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganLight
import com.example.ui.theme.SoganMedium
import com.example.ui.theme.SoganPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HolidayDetailDialog(
    holiday: JavaneseHoliday,
    instance: JavaneseHolidayInstance? = null,
    language: AppLanguage,
    onDismiss: () -> Unit
) {
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .testTag("holiday_detail_sheet"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Category Icon + Title + Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        KeratonGoldLight.copy(alpha = 0.4f),
                                        KeratonGoldContainer
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (holiday.category) {
                                JavaneseHolidayCategory.TRADISI_KERATON -> Icons.Default.Celebration
                                JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA -> Icons.Default.Star
                                JavaneseHolidayCategory.MALAM_SAKRAL -> Icons.Default.AutoAwesome
                                else -> Icons.Default.Info
                            },
                            contentDescription = null,
                            tint = SoganDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = holiday.getName(language),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark
                        )
                        if (instance != null) {
                            Text(
                                text = "${instance.javaneseDate.wetonName} • ${instance.javaneseDate.day} ${instance.javaneseDate.monthNameJv} ${instance.javaneseDate.yearJavanese} AJ",
                                style = MaterialTheme.typography.titleSmall,
                                color = SoganPrimary
                            )
                        }
                    }
                }

                // Close Button
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SoganMedium.copy(alpha = 0.12f))
                        .testTag("close_holiday_dialog_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = StringResources.get("close", language),
                        tint = SoganDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Category Chips Row (Category, Wuku, etc.)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = KeratonGoldContainer
                ) {
                    Text(
                        text = holiday.getCategoryName(language),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = SoganDark,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }

                if (instance != null) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SoganLight.copy(alpha = 0.25f)
                    ) {
                        Text(
                            text = "Wuku: ${instance.javaneseDate.wukuName}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            color = SoganPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            // Traditional Greeting Card (if available)
            val greeting = holiday.getGreeting(language)
            if (greeting.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = KeratonGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Panyuwunan / Donga Rahayu:"
                                else if (language == AppLanguage.INDONESIAN) "Ucapan & Doa Tradisi:"
                                else "Traditional Greeting & Blessing:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SoganPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = greeting,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = SoganDark
                        )
                    }
                }
            }

            // Description & Meaning Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (language == AppLanguage.JAVANESE) "Makna & Kawicaksanan Adat:"
                        else if (language == AppLanguage.INDONESIAN) "Makna Filosofis & Sejarah Tradisi:"
                        else "Cultural Meaning & Significance:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SoganDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = holiday.getDescription(language),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF3E2723),
                        lineHeight = 21.sp
                    )
                }
            }

            // Traditions / Customs / Ubarampe Card
            val traditions = holiday.getTraditions(language)
            if (traditions.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Tata Cara, Laku & Ubarampe:"
                            else if (language == AppLanguage.INDONESIAN) "Tata Cara & Perlengkapan (Ubarampe):"
                            else "Traditions & Offerings (Ubarampe):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = traditions,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF3E2723),
                            lineHeight = 21.sp
                        )
                    }
                }
            }

            // Bottom Close Action Button
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 16.dp)
            ) {
                Text(
                    text = StringResources.get("close", language),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
