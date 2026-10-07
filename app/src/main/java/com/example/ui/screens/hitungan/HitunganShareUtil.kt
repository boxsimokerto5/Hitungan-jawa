package com.example.ui.screens.hitungan

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.JavaneseCalendarEngine
import com.example.localization.AppLanguage
import com.example.ui.components.JavaneseDatePickerDialog
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import java.time.LocalDate

object HitunganShareUtil {

    fun shareText(context: Context, title: String, text: String) {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, title)
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Bagikan Hasil Hitungan Jawa")
            context.startActivity(shareIntent)
        } catch (_: Exception) {
            Toast.makeText(context, "Tidak dapat membuka aplikasi pembagi.", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Hasil perhitungan berhasil disalin!", Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
            Toast.makeText(context, "Gagal menyalin ke clipboard.", Toast.LENGTH_SHORT).show()
        }
    }

    fun showDatePicker(
        context: Context,
        initialDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit
    ) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth
        ).show()
    }
}

@Composable
fun JavaneseDateSelector(
    label: String,
    date: LocalDate,
    language: AppLanguage = AppLanguage.INDONESIAN,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showPicker by remember { mutableStateOf(false) }

    DateSelectorButton(
        label = label,
        date = date,
        onDateClick = { showPicker = true },
        modifier = modifier
    )

    if (showPicker) {
        JavaneseDatePickerDialog(
            initialDate = date,
            title = label,
            language = language,
            onDismissRequest = { showPicker = false },
            onDateSelected = { selected ->
                onDateSelected(selected)
                showPicker = false
            }
        )
    }
}

@Composable
fun DateSelectorButton(
    label: String,
    date: LocalDate,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val jvDate = remember(date) { JavaneseCalendarEngine.fromLocalDate(date) }

    Card(
        onClick = onDateClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("date_selector_button")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .background(KeratonGoldContainer, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = SoganDark,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${date.dayOfMonth} ${date.month.name} ${date.year}",
                    fontSize = 14.sp,
                    color = SoganDark,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${jvDate.wetonName} • Neptu ${jvDate.neptuTotal} • ${jvDate.day} ${jvDate.monthNameId} ${jvDate.yearJavanese} AJ",
                    fontSize = 11.5.sp,
                    color = KeratonGold,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = KremJawa,
                border = BorderStroke(1.dp, KeratonGold.copy(alpha = 0.5f))
            ) {
                Text(
                    text = "Pilih",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SoganPrimary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ShareAndCopyRow(
    title: String,
    contentSummary: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        OutlinedButton(
            onClick = {
                HitunganShareUtil.copyToClipboard(context, title, contentSummary)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Salin Teks", fontSize = 13.sp)
        }

        Button(
            onClick = {
                HitunganShareUtil.shareText(context, title, contentSummary)
            },
            colors = ButtonDefaults.buttonColors(containerColor = SoganPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Bagikan", fontSize = 13.sp)
        }
    }
}
