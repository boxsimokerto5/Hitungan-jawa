package com.example.calendar

import com.example.localization.AppLanguage
import java.time.LocalDate

enum class JavaneseHolidayCategory {
    TRADISI_KERATON, // Grebeg, Sekaten, Kirab Pusaka
    PERINGATAN_ISLAM_JAWA, // 1 Sura, Rebo Wekasan, Ruwahan, Kupatan
    MALAM_SAKRAL, // Malam Jumat Kliwon, Malam Selasa Kliwon (Anggara Kasih)
    BULAN_BARU_JAWA, // Tanggal 1 saben Wulan Jawa
    PRANATA_MANGSA // Pergantian Mangsa Tradisional
}

data class JavaneseHoliday(
    val id: String,
    val nameJv: String,
    val nameId: String,
    val nameEn: String,
    val category: JavaneseHolidayCategory,
    val monthCode: Int, // 1 = Sura .. 12 = Besar (0 if based on weton or gregorian)
    val dayStart: Int,
    val dayEnd: Int = dayStart,
    val descriptionJv: String,
    val descriptionId: String,
    val descriptionEn: String,
    val greetingJv: String = "",
    val greetingId: String = "",
    val greetingEn: String = "",
    val traditionsJv: String = "",
    val traditionsId: String = "",
    val traditionsEn: String = "",
    val isWorkProhibited: Boolean = false
) {
    fun getName(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> nameJv
            AppLanguage.INDONESIAN -> nameId
            AppLanguage.ENGLISH -> nameEn
        }
    }

    fun getDescription(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> descriptionJv
            AppLanguage.INDONESIAN -> descriptionId
            AppLanguage.ENGLISH -> descriptionEn
        }
    }

    fun getGreeting(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> greetingJv
            AppLanguage.INDONESIAN -> greetingId
            AppLanguage.ENGLISH -> greetingEn
        }
    }

    fun getTraditions(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> traditionsJv
            AppLanguage.INDONESIAN -> traditionsId
            AppLanguage.ENGLISH -> traditionsEn
        }
    }

    fun getCategoryName(language: AppLanguage): String {
        return when (category) {
            JavaneseHolidayCategory.TRADISI_KERATON -> when (language) {
                AppLanguage.JAVANESE -> "Tradisi Keraton & Upacara Adat"
                AppLanguage.INDONESIAN -> "Tradisi Keraton & Adat"
                AppLanguage.ENGLISH -> "Royal Tradition & Ceremony"
            }
            JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA -> when (language) {
                AppLanguage.JAVANESE -> "Pengetan Islam-Jawa"
                AppLanguage.INDONESIAN -> "Hari Besar Islam-Jawa"
                AppLanguage.ENGLISH -> "Javanese Islamic Observance"
            }
            JavaneseHolidayCategory.MALAM_SAKRAL -> when (language) {
                AppLanguage.JAVANESE -> "Wengi Sakral & Tirakatan"
                AppLanguage.INDONESIAN -> "Malam Sakral & Tirakat"
                AppLanguage.ENGLISH -> "Sacred Night & Vigil"
            }
            JavaneseHolidayCategory.BULAN_BARU_JAWA -> when (language) {
                AppLanguage.JAVANESE -> "Purwaning Wulan Jawa"
                AppLanguage.INDONESIAN -> "Awal Bulan Jawa"
                AppLanguage.ENGLISH -> "New Javanese Month"
            }
            JavaneseHolidayCategory.PRANATA_MANGSA -> when (language) {
                AppLanguage.JAVANESE -> "Ganti Pranata Mangsa"
                AppLanguage.INDONESIAN -> "Pergantian Musim (Mangsa)"
                AppLanguage.ENGLISH -> "Pranata Mangsa Season"
            }
        }
    }
}

data class JavaneseHolidayInstance(
    val holiday: JavaneseHoliday,
    val javaneseDate: JavaneseDate,
    val gregorianDate: LocalDate,
    val dayNumberInHoliday: Int = 1,
    val totalDays: Int = 1
)
