package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlannerEvent
import com.example.localization.AppLanguage
import com.example.localization.StringResources
import com.example.ui.theme.BataMerah
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganLight
import com.example.ui.theme.SoganMedium
import com.example.ui.theme.SoganPrimary
import kotlinx.coroutines.launch

private data class CategoryOption(
    val key: String,
    val label: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditEventDialog(
    initialEvent: PlannerEvent? = null,
    dateLabel: String,
    language: AppLanguage,
    onDismiss: () -> Unit,
    onSave: (title: String, desc: String, time: String, category: String, hasReminder: Boolean) -> Unit
) {
    var title by remember { mutableStateOf(initialEvent?.title ?: "") }
    var description by remember { mutableStateOf(initialEvent?.description ?: "") }
    var time by remember { mutableStateOf(initialEvent?.time ?: "") }
    var category by remember { mutableStateOf(initialEvent?.category ?: "PERSONAL") }
    var hasReminder by remember { mutableStateOf(initialEvent?.hasReminder ?: true) }
    var titleError by remember { mutableStateOf(false) }

    // skipPartiallyExpanded = false ensures Google Maps-like behavior:
    // Opens at half-screen height initially, can be dragged up to expanded (3/4 or full) height
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf(
        CategoryOption("HOLIDAY", StringResources.get("cat_holiday", language), Icons.Default.Celebration),
        CategoryOption("RELIGIOUS", StringResources.get("cat_religious", language), Icons.Default.AutoAwesome),
        CategoryOption("FAMILY", StringResources.get("cat_family", language), Icons.Default.People),
        CategoryOption("WORK", StringResources.get("cat_work", language), Icons.Default.Work),
        CategoryOption("PERSONAL", StringResources.get("cat_personal", language), Icons.Default.Person)
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
                .imePadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Title + Date Badge + Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(SoganDark, SoganPrimary)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EventNote,
                            contentDescription = null,
                            tint = KeratonGold,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (initialEvent == null) {
                                StringResources.get("add_activity", language)
                            } else {
                                StringResources.get("edit_activity", language)
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SoganDark,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = KeratonGold,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = dateLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = SoganPrimary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = SoganMedium
                    )
                }
            }

            // 1. Title Input
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (it.isNotBlank()) titleError = false
                },
                label = { Text(StringResources.get("activity_title_hint", language)) },
                placeholder = {
                    Text(
                        if (language == AppLanguage.JAVANESE) "Tuladha: Selapanan Bayi, Nyekar, Slametan"
                        else if (language == AppLanguage.INDONESIAN) "Contoh: Selapanan Bayi, Nyekar, Syukuran"
                        else "e.g. Baby Selapanan, Family Gathering"
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Title,
                        contentDescription = null,
                        tint = if (titleError) BataMerah else KeratonGold
                    )
                },
                isError = titleError,
                supportingText = if (titleError) {
                    {
                        Text(
                            if (language == AppLanguage.JAVANESE) "Irah-irahan ora kena kothong"
                            else if (language == AppLanguage.INDONESIAN) "Judul kegiatan tidak boleh kosong"
                            else "Title cannot be empty",
                            color = BataMerah
                        )
                    }
                } else null,
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = SoganPrimary,
                    unfocusedBorderColor = SoganLight.copy(alpha = 0.4f),
                    focusedLabelColor = SoganPrimary,
                    cursorColor = SoganPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_title_input")
            )

            // 2. Time Input
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text(StringResources.get("time_hint", language)) },
                placeholder = { Text("09:00, 19:30") },
                leadingIcon = {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = KeratonGold
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = SoganPrimary,
                    unfocusedBorderColor = SoganLight.copy(alpha = 0.4f),
                    focusedLabelColor = SoganPrimary,
                    cursorColor = SoganPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_time_input")
            )

            // 3. Category Selection
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = StringResources.get("category", language),
                    style = MaterialTheme.typography.labelLarge,
                    color = SoganDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { cat ->
                        val isSelected = category == cat.key
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) SoganPrimary else Color.White,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) SoganDark else SoganLight.copy(alpha = 0.35f)
                            ),
                            shadowElevation = if (isSelected) 2.dp else 0.dp,
                            modifier = Modifier.clickable { category = cat.key }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isSelected) Icons.Default.Check else cat.icon,
                                    contentDescription = null,
                                    tint = if (isSelected) KeratonGold else SoganMedium,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = cat.label,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else SoganDark,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // 4. Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(StringResources.get("activity_desc_hint", language)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = KeratonGold
                    )
                },
                minLines = 2,
                maxLines = 4,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = SoganPrimary,
                    unfocusedBorderColor = SoganLight.copy(alpha = 0.4f),
                    focusedLabelColor = SoganPrimary,
                    cursorColor = SoganPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_desc_input")
            )

            // 5. Reminder Switch Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.25f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
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
                                Icons.Default.Notifications,
                                contentDescription = null,
                                tint = SoganPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = StringResources.get("enable_reminder", language),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SoganDark,
                                fontSize = 13.sp
                            )
                            Text(
                                text = if (language == AppLanguage.JAVANESE) "Pangeling sadurunge wektu acara"
                                else if (language == AppLanguage.INDONESIAN) "Pengingat otomatis sebelum acara"
                                else "Automatic alert before event",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoganLight,
                                fontSize = 11.sp
                            )
                        }
                    }
                    Switch(
                        checked = hasReminder,
                        onCheckedChange = { hasReminder = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = SoganPrimary,
                            uncheckedThumbColor = SoganLight,
                            uncheckedTrackColor = Color(0xFFE0E0E0)
                        ),
                        modifier = Modifier.testTag("reminder_switch")
                    )
                }
            }

            // 6. Action Buttons (Batal & Simpen)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, SoganLight.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SoganPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("cancel_event_button")
                ) {
                    Text(
                        text = StringResources.get("cancel", language),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Button(
                    onClick = {
                        if (title.isBlank()) {
                            titleError = true
                        } else {
                            coroutineScope.launch {
                                sheetState.hide()
                                onSave(title, description, time, category, hasReminder)
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoganPrimary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1.4f)
                        .height(48.dp)
                        .testTag("save_event_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = KeratonGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = StringResources.get("save", language),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
