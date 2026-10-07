package com.example.calendar

import com.example.localization.AppLanguage
import java.time.LocalDate

enum class Pasaran(val idName: String, val jvName: String, val enName: String, val neptu: Int, val aksara: String) {
    LEGI("Legi", "Legi (Manis)", "Legi", 5, "ꦭꦼꦒꦶ"),
    PAHING("Pahing", "Pahing (Pethakan)", "Pahing", 9, "ꦥꦲꦶꦁ"),
    PON("Pon", "Pon (Petakan)", "Pon", 7, "ꦥꦺꦴꦤ꧀"),
    WAGE("Wage", "Wage (Cemengan)", "Wage", 4, "ꦮꦒꦺ"),
    KLIWON("Kliwon", "Kliwon (Asih)", "Kliwon", 8, "ꦏ꧀ꦭꦶꦮꦺꦴꦤ꧀")
}

data class PranataMangsaInfo(
    val number: Int,
    val name: String,
    val candramengku: String,
    val dateRange: String,
    val descriptionJv: String,
    val descriptionId: String,
    val descriptionEn: String
)

data class JavaneseDate(
    val day: Int,
    val monthCode: Int, // 1 = Sura .. 12 = Besar
    val monthNameJv: String,
    val monthNameId: String,
    val monthNameEn: String,
    val monthAksara: String,
    val yearJavanese: Int, // e.g. 1960 AJ
    val yearNameWindu: String, // Alip, Ehe, Jimawal, Je, Dal, Be, Wawu, Jimakhir
    val winduName: String, // Kuntara, Sangara, Sancaya, Adi
    val kurupName: String, // Asapon
    val pasaran: Pasaran,
    val dayOfWeek: Int, // 1 = Minggu/Radite, 7 = Sabtu/Tumpak
    val dayOfWeekNeptu: Int,
    val neptuTotal: Int, // dina + pasaran
    val wetonName: String, // e.g. "Jumat Kliwon", "Senin Pon"
    val wukuName: String, // Sinta .. Watugunung
    val wukuNumber: Int, // 1..30
    val pranataMangsa: PranataMangsaInfo,
    val gregorianDate: LocalDate,
    val isWuntu: Boolean
) {
    fun getFormattedJavanese(language: AppLanguage): String {
        val monthName = when (language) {
            AppLanguage.JAVANESE -> monthNameJv
            AppLanguage.INDONESIAN -> monthNameId
            AppLanguage.ENGLISH -> monthNameEn
        }
        val dayString = if (language == AppLanguage.JAVANESE) {
            "${JavaneseCalendarEngine.toAksaraJawaNumber(day)} ($day)"
        } else {
            day.toString()
        }
        return "$dayString $monthName $yearJavanese $yearNameWindu (Windu $winduName)"
    }

    val javaneseDayAksara: String
        get() = JavaneseCalendarEngine.toAksaraJawaNumber(day)

    fun getDayOfWeekName(language: AppLanguage): String {
        return when (dayOfWeek) {
            1 -> when (language) {
                AppLanguage.JAVANESE -> "Minggu (Radite)"
                AppLanguage.INDONESIAN -> "Minggu"
                AppLanguage.ENGLISH -> "Sunday"
            }
            2 -> when (language) {
                AppLanguage.JAVANESE -> "Senen (Soma)"
                AppLanguage.INDONESIAN -> "Senin"
                AppLanguage.ENGLISH -> "Monday"
            }
            3 -> when (language) {
                AppLanguage.JAVANESE -> "Selasa (Anggara)"
                AppLanguage.INDONESIAN -> "Selasa"
                AppLanguage.ENGLISH -> "Tuesday"
            }
            4 -> when (language) {
                AppLanguage.JAVANESE -> "Rebo (Buda)"
                AppLanguage.INDONESIAN -> "Rabu"
                AppLanguage.ENGLISH -> "Wednesday"
            }
            5 -> when (language) {
                AppLanguage.JAVANESE -> "Kemis (Respati)"
                AppLanguage.INDONESIAN -> "Kamis"
                AppLanguage.ENGLISH -> "Thursday"
            }
            6 -> when (language) {
                AppLanguage.JAVANESE -> "Jemuwah (Sukra)"
                AppLanguage.INDONESIAN -> "Jumat"
                AppLanguage.ENGLISH -> "Friday"
            }
            7 -> when (language) {
                AppLanguage.JAVANESE -> "Setu (Tumpak)"
                AppLanguage.INDONESIAN -> "Sabtu"
                AppLanguage.ENGLISH -> "Saturday"
            }
            else -> ""
        }
    }

    fun getShortDinoPasaran(): String {
        val dinoShort = when (dayOfWeek) {
            1 -> "Minggu"
            2 -> "Senin"
            3 -> "Selasa"
            4 -> "Rabu"
            5 -> "Kamis"
            6 -> "Jumat"
            7 -> "Sabtu"
            else -> ""
        }
        return "$dinoShort ${pasaran.idName}"
    }
}
