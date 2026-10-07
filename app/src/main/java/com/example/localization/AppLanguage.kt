package com.example.localization

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String, val isRtl: Boolean) {
    JAVANESE("jv", "Basa Jawa", "Basa Jawa", false),
    INDONESIAN("id", "Bahasa Indonesia", "Bahasa Indonesia", false),
    ENGLISH("en", "English", "English", false);

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: JAVANESE
        }
    }
}
