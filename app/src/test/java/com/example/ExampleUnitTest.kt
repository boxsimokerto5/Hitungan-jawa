package com.example

import com.example.calendar.JavaneseCalendarEngine
import com.example.calendar.Pasaran
import com.example.calendar.WetonKelahiranEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class ExampleUnitTest {

    @Test
    fun aksaraJawaNumber_convertsDigitsProperly() {
        assertEquals("꧑", JavaneseCalendarEngine.toAksaraJawaNumber(1))
        assertEquals("꧑꧐", JavaneseCalendarEngine.toAksaraJawaNumber(10))
        assertEquals("꧒꧕", JavaneseCalendarEngine.toAksaraJawaNumber(25))
        assertEquals("꧑꧙꧖꧐", JavaneseCalendarEngine.toAksaraJawaNumber(1960))
    }

    @Test
    fun pasaran_anchorCalculatesAccurately() {
        // 17 Agustus 1945 was Jumat Legi
        val aug17 = LocalDate.of(1945, 8, 17)
        assertEquals(Pasaran.LEGI, JavaneseCalendarEngine.getPasaran(aug17))

        // Next days: Pahing, Pon, Wage, Kliwon
        assertEquals(Pasaran.PAHING, JavaneseCalendarEngine.getPasaran(aug17.plusDays(1)))
        assertEquals(Pasaran.PON, JavaneseCalendarEngine.getPasaran(aug17.plusDays(2)))
        assertEquals(Pasaran.WAGE, JavaneseCalendarEngine.getPasaran(aug17.plusDays(3)))
        assertEquals(Pasaran.KLIWON, JavaneseCalendarEngine.getPasaran(aug17.plusDays(4)))
        assertEquals(Pasaran.LEGI, JavaneseCalendarEngine.getPasaran(aug17.plusDays(5)))
    }

    @Test
    fun neptu_calculatesWetonAccurately() {
        val aug17 = LocalDate.of(1945, 8, 17) // Jumat Legi: Jumat=6, Legi=5 -> 11
        val jvDate = JavaneseCalendarEngine.fromLocalDate(aug17)
        assertEquals(6, jvDate.dayOfWeekNeptu)
        assertEquals(5, jvDate.pasaran.neptu)
        assertEquals(11, jvDate.neptuTotal)
        assertEquals("Jumat Legi", jvDate.wetonName)
    }

    @Test
    fun wetonKelahiran_calculatesAccuratelyForGregorianDates() {
        // 17 Agustus 1945 -> Jumat Legi (6 + 5 = 11)
        val birth1945 = LocalDate.of(1945, 8, 17)
        val weton1945 = WetonKelahiranEngine.calculate(birth1945, today = LocalDate.of(2026, 10, 7))
        assertEquals("Jumat Legi", weton1945.wetonName)
        assertEquals(Pasaran.LEGI, weton1945.pasaran)
        assertEquals(6, weton1945.neptuDina)
        assertEquals(5, weton1945.neptuPasaran)
        assertEquals(11, weton1945.neptuTotal)
        assertTrue(weton1945.watakLakuning.contains("Setan") || weton1945.watakLakuning.isNotEmpty())
        assertTrue(weton1945.karakterKelahiranId.isNotEmpty())
        assertTrue(weton1945.pancasuda.isNotEmpty())
        assertTrue(weton1945.totalSelapanLived > 0)

        // 1 Januari 1970 (Epoch) was Thursday Wage (Kamis Wage: 8 + 4 = 12)
        val birth1970 = LocalDate.of(1970, 1, 1)
        val weton1970 = WetonKelahiranEngine.calculate(birth1970, today = LocalDate.of(2026, 10, 7))
        assertEquals("Kamis Wage", weton1970.wetonName)
        assertEquals(Pasaran.WAGE, weton1970.pasaran)
        assertEquals(8, weton1970.neptuDina)
        assertEquals(4, weton1970.neptuPasaran)
        assertEquals(12, weton1970.neptuTotal)
        assertEquals("Lakuning Kembang", weton1970.watakLakuning)

        // 1 Januari 2000 was Saturday Legi (Sabtu Legi: 9 + 5 = 14 - Lakuning Rembulan)
        val birth2000 = LocalDate.of(2000, 1, 1)
        val weton2000 = WetonKelahiranEngine.calculate(birth2000, today = LocalDate.of(2026, 10, 7))
        assertEquals("Sabtu Legi", weton2000.wetonName)
        assertEquals(Pasaran.LEGI, weton2000.pasaran)
        assertEquals(9, weton2000.neptuDina)
        assertEquals(5, weton2000.neptuPasaran)
        assertEquals(14, weton2000.neptuTotal)
        assertEquals("Lakuning Rembulan", weton2000.watakLakuning)

        // 22 Januari 2000 was Saturday Pahing (Sabtu Pahing: 9 + 9 = 18 - Neptu Puncak)
        val birth2000Pahing = LocalDate.of(2000, 1, 22)
        val weton2000Pahing = WetonKelahiranEngine.calculate(birth2000Pahing, today = LocalDate.of(2026, 10, 7))
        assertEquals("Sabtu Pahing", weton2000Pahing.wetonName)
        assertEquals(Pasaran.PAHING, weton2000Pahing.pasaran)
        assertEquals(9, weton2000Pahing.neptuDina)
        assertEquals(9, weton2000Pahing.neptuPasaran)
        assertEquals(18, weton2000Pahing.neptuTotal)
        assertEquals("Lakuning Paripurna", weton2000Pahing.watakLakuning)
    }

    @Test
    fun wetonKelahiran_selapananCyclesRepeatEvery35Days() {
        val birth = LocalDate.of(2026, 1, 1) // Kamis Pahing
        val wetonBirth = WetonKelahiranEngine.calculate(birth, today = birth)
        assertEquals(0L, wetonBirth.daysUntilNextSelapanan)

        // Exactly 35 days later: Weton must be identical!
        val plus35Days = birth.plusDays(35)
        val wetonNext = WetonKelahiranEngine.calculate(plus35Days, today = plus35Days)
        assertEquals(wetonBirth.wetonName, wetonNext.wetonName)
        assertEquals(wetonBirth.neptuTotal, wetonNext.neptuTotal)
    }

    @Test
    fun wetonKelahiran_petunganJodohWorksCorrectly() {
        val weton1 = WetonKelahiranEngine.calculate(LocalDate.of(1995, 8, 17))
        val weton2 = WetonKelahiranEngine.calculate(LocalDate.of(1996, 5, 20))
        val result = WetonKelahiranEngine.hitungKecocokanJodoh(weton1, weton2)
        assertNotNull(result.kategori)
        assertTrue(result.totalNeptu > 0)
        assertTrue(result.maknaId.isNotEmpty())
    }

    @Test
    fun wuku_calculatesPawukonAccurately() {
        // 28 Februari 2024 was Wuku Galungan (index 10, wuku #11)
        val feb28 = LocalDate.of(2024, 2, 28)
        val (wukuName, wukuNum) = JavaneseCalendarEngine.getWuku(feb28)
        assertEquals("Galungan", wukuName)
        assertEquals(11, wukuNum)
    }

    @Test
    fun pranataMangsa_identifiesSeasonCorrectly() {
        // 17 Agustus is Mangsa Karo (3 Agt - 25 Agt)
        val aug17 = LocalDate.of(2026, 8, 17)
        val mangsa = JavaneseCalendarEngine.getPranataMangsa(aug17)
        assertTrue(mangsa.name.startsWith("Karo"))
    }

    @Test
    fun holidayCatalog_containsCoreJavaneseTraditions() {
        val catalog = JavaneseCalendarEngine.allHolidaysCatalog
        val ids = catalog.map { it.id }
        assertTrue(ids.contains("satu_sura"))
        assertTrue(ids.contains("grebeg_mulud"))
        assertTrue(ids.contains("sekaten_miyos_gongso"))
        assertTrue(ids.contains("rebo_wekasan"))
        assertTrue(ids.contains("grebeg_sawal"))
        assertTrue(ids.contains("bakda_kupat"))
        assertTrue(ids.contains("grebeg_besar"))
    }

    @Test
    fun kalkulatorSeda_calculatesMilestones() {
        val start = LocalDate.of(2026, 1, 1)
        val milestones = JavaneseCalendarEngine.calculatePengetanSeda(start)
        assertEquals(8, milestones.size)
        // 3 days
        assertEquals(LocalDate.of(2026, 1, 3), milestones[1].targetDate)
        // 7 days
        assertEquals(LocalDate.of(2026, 1, 7), milestones[2].targetDate)
    }
}
