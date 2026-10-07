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
    assertEquals("Kalender Jawa", appName)

    val list = com.example.calendar.JavaneseCalendarEngine.getUpcomingHolidays(java.time.LocalDate.of(2026, 10, 7), 10)
    println("UPCOMING_HOLIDAYS_COUNT: ${list.size}")
    for ((idx, item) in list.withIndex()) {
        println("ITEM $idx: ${item.holiday.nameId} on ${item.gregorianDate}")
    }
  }
}
