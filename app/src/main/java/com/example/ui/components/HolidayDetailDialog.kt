package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.calendar.JavaneseHoliday
import com.example.calendar.JavaneseHolidayCategory
import com.example.calendar.JavaneseHolidayInstance
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary

@Composable
fun HolidayDetailDialog(
    holiday: JavaneseHoliday,
    instance: JavaneseHolidayInstance? = null,
    language: AppLanguage,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(KeratonGoldContainer),
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
                            tint = SoganDark
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = holiday.getName(language),
                            style = MaterialTheme.typography.titleLarge,
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
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Category Chip
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
                            style = MaterialTheme.typography.labelSmall,
                            color = SoganDark,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    if (instance != null) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = KremJawa
                        ) {
                            Text(
                                text = "Wuku: ${instance.javaneseDate.wukuName}",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoganPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Traditional Greeting
                val greeting = holiday.getGreeting(language)
                if (greeting.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = KeratonGoldContainer),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Panyuwunan / Donga Rahayu:"
                                else if (language == AppLanguage.INDONESIAN) "Ucapan & Doa Tradisi:"
                                else "Traditional Greeting & Blessing:",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoganPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = greeting,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark
                            )
                        }
                    }
                }

                // Description & Meaning
                Column {
                    Text(
                        text = if (language == AppLanguage.JAVANESE) "Makna & Kawicaksanan Adat:"
                        else if (language == AppLanguage.INDONESIAN) "Makna Filosofis & Sejarah Tradisi:"
                        else "Cultural Meaning & Significance:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SoganDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = holiday.getDescription(language),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF455A64)
                    )
                }

                // Traditions / Customs / Ubarampe
                val traditions = holiday.getTraditions(language)
                if (traditions.isNotEmpty()) {
                    Column {
                        Text(
                            text = if (language == AppLanguage.JAVANESE) "Tata Cara, Laku & Ubarampe:"
                            else if (language == AppLanguage.INDONESIAN) "Tata Cara & Perlengkapan (Ubarampe):"
                            else "Traditions & Offerings (Ubarampe):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = traditions,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF455A64)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("close_holiday_dialog_button")
            ) {
                Text(StringResources.get("close", language), color = SoganPrimary)
            }
        }
    )
}
