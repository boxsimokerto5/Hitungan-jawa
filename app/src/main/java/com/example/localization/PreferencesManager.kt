package com.example.localization

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("luach_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LANGUAGE = "app_language"
        private const val KEY_HOLIDAY_NOTIF = "holiday_notifications_enabled"
        private const val KEY_ACTIVITY_NOTIF = "activity_notifications_enabled"
        private const val KEY_USER_BIRTH_DATE = "user_birth_date"
        private const val KEY_SAVED_WETONS = "saved_family_wetons"
    }

    var userBirthDate: String?
        get() = prefs.getString(KEY_USER_BIRTH_DATE, null)
        set(value) = prefs.edit().putString(KEY_USER_BIRTH_DATE, value).apply()

    var savedWetonsRaw: String
        get() = prefs.getString(KEY_SAVED_WETONS, "") ?: ""
        set(value) = prefs.edit().putString(KEY_SAVED_WETONS, value).apply()

    var language: AppLanguage
        get() {
            val code = prefs.getString(KEY_LANGUAGE, AppLanguage.INDONESIAN.code) ?: AppLanguage.INDONESIAN.code
            return AppLanguage.fromCode(code)
        }
        set(value) {
            prefs.edit().putString(KEY_LANGUAGE, value.code).apply()
        }

    var isHolidayNotifEnabled: Boolean
        get() = prefs.getBoolean(KEY_HOLIDAY_NOTIF, true)
        set(value) = prefs.edit().putBoolean(KEY_HOLIDAY_NOTIF, value).apply()

    var isActivityNotifEnabled: Boolean
        get() = prefs.getBoolean(KEY_ACTIVITY_NOTIF, true)
        set(value) = prefs.edit().putBoolean(KEY_ACTIVITY_NOTIF, value).apply()
}
