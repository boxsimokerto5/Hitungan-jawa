package com.example.localization

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("kalender_jawa_prefs", Context.MODE_PRIVATE)

    var language: AppLanguage
        get() {
            val code = prefs.getString(KEY_LANGUAGE, AppLanguage.JAVANESE.code) ?: AppLanguage.JAVANESE.code
            return AppLanguage.fromCode(code)
        }
        set(value) {
            prefs.edit().putString(KEY_LANGUAGE, value.code).apply()
        }

    var isHolidayNotifEnabled: Boolean
        get() = prefs.getBoolean(KEY_HOLIDAY_NOTIF, true)
        set(value) {
            prefs.edit().putBoolean(KEY_HOLIDAY_NOTIF, value).apply()
        }

    var isActivityNotifEnabled: Boolean
        get() = prefs.getBoolean(KEY_ACTIVITY_NOTIF, true)
        set(value) {
            prefs.edit().putBoolean(KEY_ACTIVITY_NOTIF, value).apply()
        }

    var userBirthDate: String?
        get() = prefs.getString(KEY_USER_BIRTH_DATE, null)
        set(value) {
            prefs.edit().putString(KEY_USER_BIRTH_DATE, value).apply()
        }

    companion object {
        private const val KEY_LANGUAGE = "key_app_language"
        private const val KEY_HOLIDAY_NOTIF = "key_holiday_notif"
        private const val KEY_ACTIVITY_NOTIF = "key_activity_notif"
        private const val KEY_USER_BIRTH_DATE = "key_user_birth_date"
    }
}
