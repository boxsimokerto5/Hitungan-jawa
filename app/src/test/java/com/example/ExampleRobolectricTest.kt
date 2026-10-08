package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Hitungan JAWA", appName)

    val list = com.example.calendar.JavaneseCalendarEngine.getUpcomingHolidays(java.time.LocalDate.of(2026, 10, 7), 10)
    println("UPCOMING_HOLIDAYS_COUNT: ${list.size}")
    for ((idx, item) in list.withIndex()) {
        println("ITEM $idx: ${item.holiday.nameId} on ${item.gregorianDate}")
    }
  }

  @Test
  fun `verify javanese date picker calculation`() {
    val date = java.time.LocalDate.of(2026, 10, 7)
    val jvDate = com.example.calendar.JavaneseCalendarEngine.fromLocalDate(date)
    assertEquals("Rabu Pahing", jvDate.wetonName)
    assertEquals(16, jvDate.neptuTotal)
    assertEquals("Wayang", jvDate.wukuName)
    assertEquals(27, jvDate.wukuNumber)
    assertEquals(1960, jvDate.yearJavanese)
  }

  @Test
  fun `verify app package name and share strings`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    assertEquals("com.hitunganjawa.gecckocreator", context.packageName)

    val shareTitle = com.example.localization.StringResources.get("share_app", com.example.localization.AppLanguage.INDONESIAN)
    val rateTitle = com.example.localization.StringResources.get("rate_app", com.example.localization.AppLanguage.INDONESIAN)
    assertEquals("Bagikan / Kirim Aplikasi", shareTitle)
    assertEquals("Beri Rating & Ulasan", rateTitle)
  }

  @Test
  fun `verify ironSource configuration keys`() {
    assertEquals("28923b09d", com.example.monetization.AdManager.APP_KEY)
    assertEquals("u69dlx056j56xqeo", com.example.monetization.AdManager.BANNER_1_ID)
    assertEquals("89bmz131ymy183nt", com.example.monetization.AdManager.BANNER_2_ID)
    assertEquals("9q3ur98wulcj7q2s", com.example.monetization.AdManager.INTERSTITIAL_ID)
    assertEquals("tmf1a39uruhmkzjb", com.example.monetization.AdManager.NATIVE_ID)
    assertEquals("2wm05o1m4l5n6s8n", com.example.monetization.AdManager.REWARDED_ID)
    assertEquals(16, com.example.monetization.AdManager.INTERSTITIAL_CLICK_THRESHOLD)
    assertEquals(90_000L, com.example.monetization.AdManager.INTERSTITIAL_COOLDOWN_MILLIS)
  }

  @Test
  fun `verify meta and yandex adapters present on classpath`() {
    val metaAdapterClass = Class.forName("com.ironsource.adapters.facebook.FacebookAdapter")
    val yandexAdapterClass = Class.forName("com.ironsource.adapters.yandex.YandexAdapter")
    org.junit.Assert.assertNotNull(metaAdapterClass)
    org.junit.Assert.assertNotNull(yandexAdapterClass)
  }
}
