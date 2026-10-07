package com.example.calendar

import java.time.LocalDate
import java.time.Month
import java.time.temporal.ChronoUnit

object JavaneseCalendarEngine {

    // 1. AKSARA JAWA NUMERAL CONVERSION
    fun toAksaraJawaNumber(number: Int): String {
        if (number < 0) return number.toString()
        val digits = arrayOf("꧐", "꧑", "꧒", "꧓", "꧔", "꧕", "꧖", "꧗", "꧘", "꧙")
        val str = number.toString()
        val sb = StringBuilder()
        for (c in str) {
            val d = c - '0'
            if (d in 0..9) {
                sb.append(digits[d])
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    // 2. PASARAN (PANCAWARA)
    // Anchor: 17 Agustus 1945 is Jumat Legi (Legi = index 0)
    private val anchorDate = LocalDate.of(1945, 8, 17)

    fun getPasaran(date: LocalDate): Pasaran {
        val daysDiff = ChronoUnit.DAYS.between(anchorDate, date)
        val pasaranIndex = Math.floorMod(daysDiff, 5).toInt()
        return when (pasaranIndex) {
            0 -> Pasaran.LEGI
            1 -> Pasaran.PAHING
            2 -> Pasaran.PON
            3 -> Pasaran.WAGE
            4 -> Pasaran.KLIWON
            else -> Pasaran.LEGI
        }
    }

    // 3. DINA (SAPTAWARA - 7 HARI)
    // 1 = Minggu (Radite), 2 = Senin (Soma), ..., 7 = Sabtu (Tumpak)
    fun getDayOfWeek(date: LocalDate): Int {
        return when (date.dayOfWeek.value) {
            7 -> 1 // Minggu
            1 -> 2 // Senin
            2 -> 3 // Selasa
            3 -> 4 // Rabu
            4 -> 5 // Kamis
            5 -> 6 // Jumat
            6 -> 7 // Sabtu
            else -> 1
        }
    }

    fun getDayOfWeekNeptu(dayOfWeek: Int): Int {
        return when (dayOfWeek) {
            1 -> 5 // Minggu (Radite)
            2 -> 4 // Senin (Soma)
            3 -> 3 // Selasa (Anggara)
            4 -> 7 // Rabu (Buda)
            5 -> 8 // Kamis (Respati)
            6 -> 6 // Jumat (Sukra)
            7 -> 9 // Sabtu (Tumpak)
            else -> 0
        }
    }

    // 4. WUKU (PAWUKON - 30 WUKU, 210 HARI SIKLUS)
    // Anchor: 17 Desember 2023 was Sunday, Day 0 of Wuku Sinta (index 0)
    private val wukuAnchor = LocalDate.of(2023, 12, 17)

    val wukuNames = listOf(
        "Sinta", "Landep", "Wukir", "Kurantil", "Tolu",
        "Gumbreg", "Warigalit", "Warigagung", "Julungwangi", "Sungsang",
        "Galungan", "Kuningan", "Langkir", "Mandasiya", "Julungpujut",
        "Pahang", "Kuruwelut", "Marakeh", "Tambir", "Medangkungan",
        "Maktal", "Wuye", "Manahil", "Prangbakat", "Bala",
        "Wugu", "Wayang", "Kulawu", "Dukut", "Watugunung"
    )

    fun getWuku(date: LocalDate): Pair<String, Int> {
        val daysDiff = ChronoUnit.DAYS.between(wukuAnchor, date)
        val wukuIndex = Math.floorMod(daysDiff / 7, 30).toInt()
        val wukuNum = wukuIndex + 1
        return Pair(wukuNames[wukuIndex], wukuNum)
    }

    // 5. PRANATA MANGSA (12 MUSIM JAWA)
    fun getPranataMangsa(date: LocalDate): PranataMangsaInfo {
        val m = date.monthValue
        val d = date.dayOfMonth

        return when {
            // Kasa: 23 Juni - 2 Agustus (41 hari)
            (m == 6 && d >= 23) || (m == 7) || (m == 8 && d <= 2) -> PranataMangsaInfo(
                number = 1,
                name = "Kasa (Kartika)",
                candramengku = "Sesotya murca ing embanan",
                dateRange = "23 Juni - 2 Agustus",
                descriptionJv = "Gegodhongan padha rontok, wiwit mangsa ketiga (kemarau), para tani wiwit ngolah palawija.",
                descriptionId = "Daun-daun berguguran, permulaan musim kemarau, petani mulai menanam palawija.",
                descriptionEn = "Leaves start falling, beginning of the dry season, planting secondary crops."
            )
            // Karo: 3 Agustus - 25 Agustus (23 hari)
            (m == 8 && d in 3..25) -> PranataMangsaInfo(
                number = 2,
                name = "Karo (Puso)",
                candramengku = "Bantala rengka",
                dateRange = "3 Agustus - 25 Agustus",
                descriptionJv = "Lemah padha nela/rengka, hawa panas garing, wit randhu lan pelem wiwit kembangan.",
                descriptionId = "Tanah mulai retak dan pecah, hawa panas kering, randu dan mangga mulai berbunga.",
                descriptionEn = "Ground dries and cracks, hot dry weather, mango trees begin flowering."
            )
            // Katelu: 26 Agustus - 18 September (24 hari)
            (m == 8 && d >= 26) || (m == 9 && d <= 18) -> PranataMangsaInfo(
                number = 3,
                name = "Katelu (Manggasri)",
                candramengku = "Suta manut ing bapa",
                dateRange = "26 Agustus - 18 September",
                descriptionJv = "Palawija wiwit awoh, pring wiwit trubus, uwi lan gadhung mrambat ing lanjaran.",
                descriptionId = "Palawija mulai berbuah, rebung bambu mulai bertunas, tanaman ubi merambat.",
                descriptionEn = "Crops bear fruit, bamboo shoots sprout, vines follow their poles."
            )
            // Kapat: 19 September - 13 Oktober (25 hari)
            (m == 9 && d >= 19) || (m == 10 && d <= 13) -> PranataMangsaInfo(
                number = 4,
                name = "Kapat (Sitra)",
                candramengku = "Waspa kumembeng jroning kalbu",
                dateRange = "19 September - 13 Oktober",
                descriptionJv = "Sumbering banyu wiwit asat, sumur asat, tetanduran nunggu tibaning labuh.",
                descriptionId = "Mata air mengering, sumur surut, tanaman menantikan musim penghujan datang.",
                descriptionEn = "Springs run dry, deep longing for rain, waiting for the wet season transition."
            )
            // Kalima: 14 Oktober - 9 November (27 hari)
            (m == 10 && d >= 14) || (m == 11 && d <= 9) -> PranataMangsaInfo(
                number = 5,
                name = "Kalima (Manggala)",
                candramengku = "Pancuran mas sumawur ing jagad",
                dateRange = "14 Oktober - 9 November",
                descriptionJv = "Udan wiwit tiba arang-arang (labuh), manuk-manuk ngendhog, para tani wiwit nyiapake winih.",
                descriptionId = "Mulai turun hujan rintik (musim labuh), burung bersarang dan bertelur, petani menyemai bibit.",
                descriptionEn = "First rain showers arrive, birds lay eggs, farmers prepare rice seedbeds."
            )
            // Kanem: 10 November - 22 Desember (43 hari)
            (m == 11 && d >= 10) || (m == 12 && d <= 22) -> PranataMangsaInfo(
                number = 6,
                name = "Kanem (Naya)",
                candramengku = "Rasa mulya kasucian",
                dateRange = "10 November - 22 Desember",
                descriptionJv = "Udan wiwit kerep, woh-wohan padha tuwa (rambutan, durian), para tani wiwit nggaru lan mbajak sawah.",
                descriptionId = "Hujan mulai sering turun, buah-buahan seperti durian dan rambutan mulai masak, bajak sawah dimulai.",
                descriptionEn = "Frequent rains, tropical fruits ripen, plowing and tilling the paddy fields."
            )
            // Kapitu: 23 Desember - 3 Februari (43 hari)
            (m == 12 && d >= 23) || (m == 1) || (m == 2 && d <= 3) -> PranataMangsaInfo(
                number = 7,
                name = "Kapitu (Palguna)",
                candramengku = "Wisa kentas ing maruta",
                dateRange = "23 Desember - 3 Februari",
                descriptionJv = "Puncaking mangsa rendheng (hujan lebat), banjir, angin gedhe, para tani wiwit nandur pari.",
                descriptionId = "Puncak musim hujan, angin kencang dan potensi banjir, masa tandur padi di sawah.",
                descriptionEn = "Peak rainy season, strong winds and high waters, transplanting rice seedlings."
            )
            // Kawolu: 4 Februari - 29 Februari / 1 Maret (26-27 hari)
            (m == 2 && d >= 4) || (m == 3 && d == 1 && !date.isLeapYear) -> PranataMangsaInfo(
                number = 8,
                name = "Kawolu (Wisaka)",
                candramengku = "Anjrah jroning kayun",
                dateRange = "4 Februari - 29 Februari / 1 Maret",
                descriptionJv = "Pari padha ijo royo-royo, uir-uir muni ing wit-witan, kucing wiwit gandheng.",
                descriptionId = "Padi menghijau di sawah, serangga tonggeret mulai berbunyi, musim kawin satwa.",
                descriptionEn = "Paddy fields grow lush green, cicadas sing in trees, mating season for animals."
            )
            // Kasanga: 2 Maret - 25 Maret (25 hari)
            (m == 3 && d in 2..25) -> PranataMangsaInfo(
                number = 9,
                name = "Kasanga (Jita)",
                candramengku = "Wedaring wacana mulya",
                dateRange = "2 Maret - 25 Maret",
                descriptionJv = "Pari wiwit lemu mentes ngisi, manuk-manuk mencok mangan pari, jangkrik ngenthir.",
                descriptionId = "Padi mulai menguning dan bunting, burung berkicau dan hinggap di bulir padi.",
                descriptionEn = "Rice grains begin filling and ripening, birds feast on stalks."
            )
            // Kadasa: 26 Maret - 18 April (24 hari)
            (m == 3 && d >= 26) || (m == 4 && d <= 18) -> PranataMangsaInfo(
                number = 10,
                name = "Kadasa (Srawana)",
                candramengku = "Gedhong mineb jroning kalbu",
                dateRange = "26 Maret - 18 April",
                descriptionJv = "Mangsa panen raya pari, pari disimpen ing lumbung, hawa wiwit suda udane (mareng).",
                descriptionId = "Musim panen raya, padi disimpan di lumbung, peralihan menuju musim kemarau (mareng).",
                descriptionEn = "Grand rice harvest, granaries are filled, transitional season towards dry weather."
            )
            // Desta: 19 April - 11 Mei (23 hari)
            (m == 4 && d >= 19) || (m == 5 && d <= 11) -> PranataMangsaInfo(
                number = 11,
                name = "Desta (Padrawana)",
                candramengku = "Sotya sininar wedhi",
                dateRange = "19 April - 11 Mei",
                descriptionJv = "Manuk-manuk ngloloh anakke, hawa wiwit sumuk lan adhem ing wayah wengi.",
                descriptionId = "Burung menyuapi anaknya, udara mulai terasa dingin pada malam hari.",
                descriptionEn = "Mother birds feed their chicks, cool crisp nights appear."
            )
            // Saddha: 12 Mei - 22 Juni (41 hari)
            else -> PranataMangsaInfo(
                number = 12,
                name = "Saddha (Asuji)",
                candramengku = "Tirta sah saking sasana",
                dateRange = "12 Mei - 22 Juni",
                descriptionJv = "Mangsa bediding (adhem njekut ing wayah esuk/wengi), panen palawija, banyu wiwit asat.",
                descriptionId = "Musim bediding (suhu dingin menyengat di pagi dan malam hari), panen palawija.",
                descriptionEn = "Bediding cold snap season with crisp morning chill, dry season settling in."
            )
        }
    }

    // 6. TAHUN JAWA SULTAN AGUNGAN & 12 WULAN
    // Mataram Javanese Calendar Engine with 8-year Windu cycle
    val javaneseMonths = listOf(
        Triple("Sura", "Sura", "Sura"),
        Triple("Sapar", "Sapar", "Sapar"),
        Triple("Mulud", "Mulud (Rabiulawal)", "Mulud"),
        Triple("Bakda Mulud", "Bakda Mulud (Rabiulakhir)", "Bakda Mulud"),
        Triple("Jumadilawal", "Jumadilawal", "Jumadilawal"),
        Triple("Jumadilakir", "Jumadilakhir", "Jumadilakhir"),
        Triple("Rejeb", "Rejeb (Rajab)", "Rejeb"),
        Triple("Ruwah", "Ruwah (Sya'ban)", "Ruwah"),
        Triple("Pasa", "Pasa (Ramadhan)", "Pasa"),
        Triple("Sawal", "Sawal (Syawal)", "Sawal"),
        Triple("Sela", "Sela (Dzulkaidah)", "Sela"),
        Triple("Besar", "Besar (Dzulhijjah)", "Besar")
    )

    val javaneseMonthAksara = listOf(
        "ꦱꦸꦫ", "ꦱꦥꦂ", "ꦩꦸꦭꦸꦢ꧀", "ꦧꦏ꧀ꦢꦩꦸꦭꦸꦢ꧀",
        "ꦗꦸꦩꦢꦶꦭꦮꦭ꧀", "ꦗꦸꦩꦢꦶꦭꦏꦶꦂ", "ꦉꦗꦼꦧ꧀", "ꦫꦸꦮꦃ",
        "ꦥꦱ", "ꦱꦮꦭ꧀", "ꦱꦼꦭ", "ꦧꦼꦱꦂ"
    )

    // Years in Windu cycle (8 years)
    val winduYearNames = listOf(
        "Alip", "Ehe", "Jimawal", "Je", "Dal", "Be", "Wawu", "Jimakhir"
    )

    // In Javanese calendar: Ehe (index 1), Dal (index 4), and Jimakhir (index 7) are Tahun Wuntu (Leap, 355 days)
    val isLeapWinduYear = listOf(
        false, true, false, false, true, false, false, true
    )

    val winduNames = listOf(
        "Kuntara", "Sangara", "Sancaya", "Adi"
    )

    // Standard year definition from known Anchor:
    // 1 Sura 1957 AJ (Jimawal) = 19 Juli 2023
    private val sura1957Anchor = LocalDate.of(2023, 7, 19)
    private const val anchorJavaneseYear = 1957
    private const val anchorWinduYearIndex = 2 // Jimawal is 0-based index 2 (Alip=0, Ehe=1, Jimawal=2)

    private fun getYearLength(yearIndexInWindu: Int): Int {
        return if (isLeapWinduYear[yearIndexInWindu]) 355 else 354
    }

    private fun getMonthLength(monthCode: Int, isWuntu: Boolean): Int {
        return when (monthCode) {
            1 -> 30  // Sura
            2 -> 29  // Sapar
            3 -> 30  // Mulud
            4 -> 29  // Bakda Mulud
            5 -> 30  // Jumadilawal
            6 -> 29  // Jumadilakir
            7 -> 30  // Rejeb
            8 -> 29  // Ruwah
            9 -> 30  // Pasa
            10 -> 29 // Sawal
            11 -> 30 // Sela
            12 -> if (isWuntu) 30 else 29 // Besar
            else -> 30
        }
    }

    fun fromLocalDate(date: LocalDate): JavaneseDate {
        val pasaran = getPasaran(date)
        val dayOfWeek = getDayOfWeek(date)
        val dayOfWeekNeptu = getDayOfWeekNeptu(dayOfWeek)
        val pasaranNeptu = pasaran.neptu
        val neptuTotal = dayOfWeekNeptu + pasaranNeptu
        val wetonName = "${getDinaNameId(dayOfWeek)} ${pasaran.idName}"
        val (wukuName, wukuNum) = getWuku(date)
        val pranata = getPranataMangsa(date)

        // Find Javanese Year and 1 Sura of that year
        var curYear = anchorJavaneseYear
        var curWinduIndex = anchorWinduYearIndex
        var curSuraDate = sura1957Anchor

        if (date.isBefore(curSuraDate)) {
            while (date.isBefore(curSuraDate)) {
                curYear -= 1
                curWinduIndex = Math.floorMod(curWinduIndex - 1, 8)
                val len = getYearLength(curWinduIndex)
                curSuraDate = curSuraDate.minusDays(len.toLong())
            }
        } else {
            while (true) {
                val len = getYearLength(curWinduIndex)
                val nextSuraDate = curSuraDate.plusDays(len.toLong())
                if (date.isBefore(nextSuraDate)) {
                    break
                }
                curSuraDate = nextSuraDate
                curYear += 1
                curWinduIndex = (curWinduIndex + 1) % 8
            }
        }

        val isWuntu = isLeapWinduYear[curWinduIndex]
        val winduCycleIndex = Math.floorMod((curYear - 1555) / 8, 4)
        val winduName = winduNames[winduCycleIndex]
        val yearNameWindu = winduYearNames[curWinduIndex]

        // Now determine month and day from curSuraDate
        var remainingDays = ChronoUnit.DAYS.between(curSuraDate, date).toInt()
        var curMonth = 1
        for (m in 1..12) {
            val mLen = getMonthLength(m, isWuntu)
            if (remainingDays < mLen) {
                curMonth = m
                break
            }
            remainingDays -= mLen
        }
        val jvDay = remainingDays + 1

        val monthTriple = javaneseMonths[curMonth - 1]
        val monthAksara = javaneseMonthAksara[curMonth - 1]

        return JavaneseDate(
            day = jvDay,
            monthCode = curMonth,
            monthNameJv = monthTriple.first,
            monthNameId = monthTriple.second,
            monthNameEn = monthTriple.third,
            monthAksara = monthAksara,
            yearJavanese = curYear,
            yearNameWindu = yearNameWindu,
            winduName = winduName,
            kurupName = "Asapon",
            pasaran = pasaran,
            dayOfWeek = dayOfWeek,
            dayOfWeekNeptu = dayOfWeekNeptu,
            neptuTotal = neptuTotal,
            wetonName = wetonName,
            wukuName = wukuName,
            wukuNumber = wukuNum,
            pranataMangsa = pranata,
            gregorianDate = date,
            isWuntu = isWuntu
        )
    }

    private fun getDinaNameId(dina: Int): String {
        return when (dina) {
            1 -> "Minggu"
            2 -> "Senin"
            3 -> "Selasa"
            4 -> "Rabu"
            5 -> "Kamis"
            6 -> "Jumat"
            7 -> "Sabtu"
            else -> ""
        }
    }

    // 7. KATALOG HARI BESAR & PERINGATAN ADAT JAWA
    val allHolidaysCatalog: List<JavaneseHoliday> = listOf(
        JavaneseHoliday(
            id = "satu_sura",
            nameJv = "Malam 1 Sura / Warsa Enggal Jawa",
            nameId = "1 Sura (Tahun Baru Jawa Sultan Agungan)",
            nameEn = "1 Sura (Javanese New Year)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 1,
            dayStart = 1,
            dayEnd = 1,
            descriptionJv = "Pengetan Warsa Enggal Jawa Sultan Agungan 1555 AJ. Katindakaken Kirab Pusaka Karaton Kasunanan Surakarta lan Mubeng Beteng Karaton Ngayogyakarta Hadiningrat kanthi lampah tapa bisu.",
            descriptionId = "Tahun Baru Kalender Jawa Sultan Agungan. Dirayakan dengan tradisi Kirab Pusaka di Keraton Surakarta dan ritual Mubeng Beteng (jalan sunyi mengelilingi benteng) di Keraton Yogyakarta.",
            descriptionEn = "Javanese New Year (Anno Javanico). Commemorated with sacred heirloom processions (Kirab Pusaka) and silent circumambulation of royal palace walls (Mubeng Beteng).",
            greetingJv = "Sugeng Warsa Enggal Jawa!",
            greetingId = "Selamat Tahun Baru Jawa 1 Sura!",
            greetingEn = "Happy Javanese New Year 1 Sura!",
            traditionsJv = "Kirab Kebo Bule Kyai Slamet, Mubeng Beteng tapa bisu, jamasan pusaka, sesuci diri ing patirtaan.",
            traditionsId = "Kirab Kerbau Kyai Slamet, laku bisu keliling benteng keraton, pembersihan pusaka keramat (jamasan), tirakatan doa keselamatan.",
            traditionsEn = "Sacred heirloom procession, silent palace walk, weapon bathing ritual, night vigil.",
            isWorkProhibited = false
        ),
        JavaneseHoliday(
            id = "sepuluh_sura",
            nameJv = "Suran / Jenang Sura",
            nameId = "10 Sura (Tradisi Jenang Sura)",
            nameEn = "10 Sura (Ashura Javanese Porridge)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 1,
            dayStart = 10,
            dayEnd = 10,
            descriptionJv = "Pengetan Dinten Asyura kanthi tradisi andum Jenang Sura pitung rupa minangka wujud syukur marang Gusti Kang Maha Kawasa.",
            descriptionId = "Peringatan hari Asyura dalam tradisi Jawa yang diwarnai dengan pembuatan dan pembagian Bubur/Jenang Sura tujuh rupa sebagai ungkapan rasa syukur.",
            descriptionEn = "Commemoration of Ashura in Javanese tradition, marked by cooking and sharing ceremonial seven-ingredient savory porridge (Jenang Sura).",
            greetingJv = "Sugeng Dinten Suran!",
            greetingId = "Selamat Hari Suran!",
            greetingEn = "Blessed Day of Suran!",
            traditionsJv = "Mbagekake Jenang Sura, donga wilujeng, santunan anak yatim.",
            traditionsId = "Membuat bubur Sura, kenduri keselamatan warga, santunan anak yatim.",
            traditionsEn = "Distributing Jenang Sura porridge, community prayer feasts."
        ),
        JavaneseHoliday(
            id = "rebo_wekasan",
            nameJv = "Rebo Wekasan (Pungkasan Sapar)",
            nameId = "Rebo Wekasan (Rabu Terakhir Sapar)",
            nameEn = "Rebo Wekasan (Final Wednesday of Sapar)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 2,
            dayStart = 25, // Handled dynamically in getHolidaysForDate
            dayEnd = 29,
            descriptionJv = "Dina Rebo pungkasan ing wulan Sapar. Kapercaya minangka dina tumurune donga tolak bala, dipunpengeti kanthi salat hajat, donga wilujeng, lan dhahar kue apem.",
            descriptionId = "Rabu terakhir di bulan Sapar. Diperingati secara turun-temurun dengan doa tolak bala, kenduri, salat hajat berjemaah, dan sajian kue apem.",
            descriptionEn = "The last Wednesday of the month of Sapar. Observed with special prayers seeking protection from misfortune, communal supplication, and apem cakes.",
            greetingJv = "Rahayu slamet widada ing dinten Rebo Wekasan",
            greetingId = "Selamat memperingati Rebo Wekasan, semoga terhindar dari marabahaya",
            greetingEn = "May safety and peace accompany Rebo Wekasan",
            traditionsJv = "Salat sunnah tolak bala, ngunjuk toya wafaq, mbagi apem lan lemper.",
            traditionsId = "Salat tolak bala berjemaah, minum air doa wafaq, selamatan kue apem.",
            traditionsEn = "Supplication prayers, community feast with traditional apem rice cakes."
        ),
        JavaneseHoliday(
            id = "sekaten_miyos_gongso",
            nameJv = "Sekaten (Miyosipun Gamelan Sekati)",
            nameId = "1 Mulud (Awal Perayaan Sekaten Keraton)",
            nameEn = "1 Mulud (Opening of Royal Sekaten)",
            category = JavaneseHolidayCategory.TRADISI_KERATON,
            monthCode = 3,
            dayStart = 1,
            dayEnd = 1,
            descriptionJv = "Wiwitan upacara Sekaten ing Karaton Ngayogyakarta lan Surakarta. Gamelan Sekati (Kyai Guntur Madu & Kyai Naga Wilaga) dipunboyong miyos dhateng Masjid Gedhe Kauman.",
            descriptionId = "Pembukaan festival perayaan tradisi Sekaten. Dua gamelan keraton pusaka (Kyai Guntur Madu dan Kyai Naga Wilaga) diarak menuju Pagongan Masjid Gedhe Kauman untuk dibunyikan selama sepekan penuh.",
            descriptionEn = "Commencement of the week-long Royal Sekaten festival. The sacred heirloom gamelan orchestras are carried in royal procession to the Grand Mosque.",
            greetingJv = "Sugeng ngriyayakaken Upacara Sekaten!",
            greetingId = "Selamat menyambut Perayaan Sekaten!",
            greetingEn = "Happy Sekaten Royal Celebration!",
            traditionsJv = "Miyos Gongso, ungeling Gamelan Sekati seminggu muput, dhahar ndhog abang lan kinang.",
            traditionsId = "Kirab gamelan pusaka, penabuhan gamelan Sekaten, mengunyah kinang (sirih), membeli telur merah berhias.",
            traditionsEn = "Sacred gamelan chiming, chewing betel leaf, red festive eggs."
        ),
        JavaneseHoliday(
            id = "grebeg_mulud",
            nameJv = "Grebeg Mulud / Sekaten Ageng",
            nameId = "12 Mulud (Grebeg Mulud Maulid Nabi)",
            nameEn = "12 Mulud (Grebeg Mulud Royal Festival)",
            category = JavaneseHolidayCategory.TRADISI_KERATON,
            monthCode = 3,
            dayStart = 12,
            dayEnd = 12,
            descriptionJv = "Puncakipun pengetan Miyosipun Kanjeng Nabi Muhammad SAW ing Karaton. Sultan ngedalaken Gunungan Kakung lan Gunungan Putri minangka wujud sedhekah raja marang kawula.",
            descriptionId = "Puncak perayaan Maulid Nabi di Keraton. Raja mengeluarkan iring-iringan Gunungan (hasil bumi) yang diarak dari bangsal Pagelaran ke Masjid Gedhe untuk diperebutkan oleh masyarakat (ngalap berkah).",
            descriptionEn = "Peak royal festival celebrating the birth of Prophet Muhammad. Enormous conical mounds of palace harvest offerings (Gunungan) are paraded and distributed to citizens.",
            greetingJv = "Sugeng Grebeg Mulud!",
            greetingId = "Selamat Hari Raya Grebeg Mulud!",
            greetingEn = "Blessed Grebeg Mulud Royal Feast!",
            traditionsJv = "Iring-iringan prajurit Keraton, Gunungan Kakung & Putri, Kondur Gongso, ngalap berkah.",
            traditionsId = "Pawai barisan prajurit keraton bergaya tradisional, perebutan isi Gunungan berkah, kepulangan gamelan ke keraton.",
            traditionsEn = "Parade of royal palace guards, Gunungan harvest mountain procession, seeking royal blessings."
        ),
        JavaneseHoliday(
            id = "rajaban",
            nameJv = "Rajaban (27 Rejeb)",
            nameId = "27 Rejeb (Peringatan Isra Mi'raj & Rajaban)",
            nameEn = "27 Rejeb (Rajaban & Isra Mi'raj)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 7,
            dayStart = 27,
            dayEnd = 27,
            descriptionJv = "Pengetan Isra Mi'raj Kanjeng Nabi Muhammad SAW kanthi slametan Rajaban, maos kitab Bzanji, lan pasugatan tumpeng.",
            descriptionId = "Peringatan Isra Mi'raj Nabi Muhammad SAW dalam tradisi Jawa. Diselenggarakan selamatan Rajaban di masjid, pembacaan puji-pujian Barzanji, dan makan tumpeng bersama.",
            descriptionEn = "Javanese Isra Mi'raj observance with Rajaban communal prayer gatherings and fragrant rice cones.",
            greetingJv = "Sugeng Pengetan Rajaban 27 Rejeb!",
            greetingId = "Selamat Memperingati Hari Rajaban!",
            greetingEn = "Blessed Rajaban Observance!",
            traditionsJv = "Slametan Rajaban, maosan serat Barzanji, kenduri ing langgar.",
            traditionsId = "Selamatan tumpeng, pembacaan Maulid Diba'i/Barzanji, silaturahmi warga.",
            traditionsEn = "Recitation of praise poetry, sharing consecrated festive food."
        ),
        JavaneseHoliday(
            id = "ruwahan",
            nameJv = "Ruwahan (Nisfu Ruwah / Sya'ban)",
            nameId = "15 Ruwah (Ruwahan & Doa Arwah Leluhur)",
            nameEn = "15 Ruwah (Ruwahan Ancestral Memorial)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 8,
            dayStart = 15,
            dayEnd = 15,
            descriptionJv = "Tradisi luhur ngintun donga marang para leluhur sadurunge ngancik wulan suci Pasa. Warga sami nyekar menyang pasarean lan andum kue apem, kolak, lan ketan.",
            descriptionId = "Tradisi penghormatan dan pengiriman doa bagi arwah para orang tua dan leluhur menjelang bulan puasa. Diisi dengan nyekar (ziarah makam) dan selamatan kue apem, ketan, serta kolak.",
            descriptionEn = "Sacred tradition of remembering and praying for departed ancestors before fasting month, visiting family graves with flowers and sharing symbolic sweet foods.",
            greetingJv = "Sugeng Ruwahan, mugi para leluhur pikantuk papan ingkang mulya",
            greetingId = "Selamat menjalankan tradisi Ruwahan",
            greetingEn = "Blessed Ruwahan Ancestral Commemoration",
            traditionsJv = "Nyekar ing pasarean, andum apem (nyuwun ngapura), kolak (nyedhak Gusti), lan ketan (raketing paseduluran).",
            traditionsId = "Ziarah kubur tabur bunga, selamatan apem (simbol permohonan ampun), ketan (rekatnya tali persaudaraan), kolak.",
            traditionsEn = "Grave pilgrimage, distributing sweet apem cakes, prayer meetings."
        ),
        JavaneseHoliday(
            id = "megengan_pasa",
            nameJv = "Megengan (Purwaning Wulan Pasa)",
            nameId = "1 Pasa (Awal Puasa & Megengan)",
            nameEn = "1 Pasa (Beginning of Fasting & Megengan)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 9,
            dayStart = 1,
            dayEnd = 1,
            descriptionJv = "Dinten sepisanan wulan Pasa (Siyem). Kawiwitan kanthi tradisi Megengan (ngempet hawa nepsu) lan padusan sesuci raga ing patirtaan.",
            descriptionId = "Hari pertama bulan puasa Ramadhan dalam kalender Jawa. Dirayakan dengan tradisi Megengan (menahan hawa nafsu) serta upacara Padusan (membersihkan diri lahir dan batin).",
            descriptionEn = "First day of the holy fasting month. Observed with Megengan restraint and Padusan spiritual spring purification bath.",
            greetingJv = "Sugeng nindakaken Ibadah Siyem Wulan Pasa!",
            greetingId = "Selamat menunaikan Ibadah Puasa Bulan Pasa!",
            greetingEn = "Blessed Fasting Month of Pasa!",
            traditionsJv = "Padusan ing patirtaan sumber banyu, kenduri Megengan, shalat tarawih.",
            traditionsId = "Padusan di sumber air alami, selamatan kue apem megengan, tarawih berjemaah.",
            traditionsEn = "Ritual spring bathing, communal evening meals."
        ),
        JavaneseHoliday(
            id = "maleman_selikuran",
            nameJv = "Maleman Selikuran (21 Pasa)",
            nameId = "21 Pasa (Maleman Selikuran Keraton)",
            nameEn = "21 Pasa (Maleman Selikuran Royal Vigil)",
            category = JavaneseHolidayCategory.TRADISI_KERATON,
            monthCode = 9,
            dayStart = 21,
            dayEnd = 21,
            descriptionJv = "Upacara Keraton mengeti malem selikur wulan Pasa (Lailatul Qadar). Para abdi dalem ngirab lampu ting (lentera) lan tumpeng sewu tumuju Sriwedari utawa Masjid Kauman.",
            descriptionId = "Peringatan malam ke-21 Ramadhan (Lailatul Qadar) oleh Keraton. Abdi dalem mengarak ribuan lampu ting (lentera hias) dan seribu nasi tumpeng mini dari keraton.",
            descriptionEn = "Royal night vigil on the 21st evening of fasting, marked by hundreds of palace lantern carriers and ceremonial cone-shaped rice offerings.",
            greetingJv = "Sugeng Maleman Selikuran, mugi pinaringan pepadhang",
            greetingId = "Selamat memperingati Maleman Selikuran",
            greetingEn = "Blessed Maleman Selikuran Night of Light",
            traditionsJv = "Kirab lampu ting, pembagian tumpeng sewu berkah, tirakatan wengi.",
            traditionsId = "Pawai ribuan lentera ting, pembagian nasi berkat tumpeng sewu, iktikaf semalam suntuk.",
            traditionsEn = "Parade of traditional candle lanterns, night prayers."
        ),
        JavaneseHoliday(
            id = "grebeg_sawal",
            nameJv = "Grebeg Sawal / Riyaya Bakda",
            nameId = "1 Sawal (Grebeg Sawal & Hari Raya Idul Fitri)",
            nameEn = "1 Sawal (Grebeg Sawal & Eid al-Fitr)",
            category = JavaneseHolidayCategory.TRADISI_KERATON,
            monthCode = 10,
            dayStart = 1,
            dayEnd = 1,
            descriptionJv = "Riyaya Bakda Sawal. Karaton ngedalaken Gunungan Sawal minangka tandha sukur paripurnaning ibadah siyem, kasarengan sungkeman marang tiyang sepuh.",
            descriptionId = "Hari Raya Idul Fitri dalam adat Jawa. Keraton menggelar upacara Grebeg Sawal dengan kirab Gunungan hasil bumi, dirangkaikan dengan tradisi sungkeman saling memaafkan sanak keluarga.",
            descriptionEn = "Javanese Eid Festival. Royal procession of giant Gunungan harvest mountains, followed by deep filial forgiveness ceremonies (Sungkeman).",
            greetingJv = "Sugeng Riyadi 1 Sawal, nyuwun gunging samodra pangaksami",
            greetingId = "Selamat Hari Raya Idul Fitri 1 Syawal, mohon maaf lahir dan batin",
            greetingEn = "Happy Eid al-Fitr 1 Sawal, forgiveness and peace!",
            traditionsJv = "Grebeg Gunungan Sawal, sungkeman marang sesepuh, ubarampe kupat lepet.",
            traditionsId = "Kirab Gunungan Keraton, tradisi sungkeman memohon maaf, hidangan kupat dan lepet.",
            traditionsEn = "Royal gunungan parade, kneeling ancestral forgiveness (Sungkeman)."
        ),
        JavaneseHoliday(
            id = "bakda_kupat",
            nameJv = "Bakda Kupat / Kupatan (8 Sawal)",
            nameId = "8 Sawal (Riyaya Kupat / Lebaran Ketupat)",
            nameEn = "8 Sawal (Bakda Kupat / Rice Cake Festival)",
            category = JavaneseHolidayCategory.PERINGATAN_ISLAM_JAWA,
            monthCode = 10,
            dayStart = 8,
            dayEnd = 8,
            descriptionJv = "Riyaya Bakda Kupat ing dinten kaping wolu wulan Sawal sabubare poso syawal. Warga sami dhahar kupat lan lepet minangka pralambang 'ngaku lepat' (nyuwun pangapura).",
            descriptionId = "Perayaan Lebaran Ketupat yang digelar sepekan setelah Idul Fitri. Kupat melambangkan 'ngaku lepat' (mengakui kesalahan) dan lepet melambangkan 'silep kang rapet' (menutup kekhilafan).",
            descriptionEn = "Traditional 8th of Sawal Ketupat Feast following the six-day optional fast. Braided rice cakes symbolize acknowledging mistakes and strengthening fraternal bonds.",
            greetingJv = "Sugeng Riyadi Bakda Kupat, mugi luluh sedaya kalepatan",
            greetingId = "Selamat merayakan Hari Raya Kupatan!",
            greetingEn = "Blessed Bakda Kupat Celebration!",
            traditionsJv = "Dhahar kupat sayur lan lepet ketan, andum dhaharan marang tangga teparo.",
            traditionsId = "Makan ketupat dengan opor/sayur lodeh, membagikan ketupat ke tetangga.",
            traditionsEn = "Enjoying diamond-shaped ketupat and sharing with neighbors."
        ),
        JavaneseHoliday(
            id = "grebeg_besar",
            nameJv = "Grebeg Besar (10 Besar)",
            nameId = "10 Besar (Grebeg Besar & Idul Adha)",
            nameEn = "10 Besar (Grebeg Besar Festival)",
            category = JavaneseHolidayCategory.TRADISI_KERATON,
            monthCode = 12,
            dayStart = 10,
            dayEnd = 10,
            descriptionJv = "Pengetan Riyaya Kurban ing Karaton. Sultan ngedalaken Gunungan Besar minangka wujud sedhekah dalem tumrap kawula, sinartan ibadah kurban.",
            descriptionId = "Peringatan Idul Adha adat keraton. Iring-iringan prajurit mengarak Gunungan Besar berisi aneka penganan dan hasil bumi ke Masjid Agung, diiringi penyembelihan hewan kurban keraton.",
            descriptionEn = "Royal palace Idul Adha celebration, showcasing royal guard formations and ceremonial gift mountains alongside livestock sacrifice.",
            greetingJv = "Sugeng Grebeg Besar 10 Dulkijah!",
            greetingId = "Selamat Hari Raya Grebeg Besar!",
            greetingEn = "Happy Grebeg Besar Festival!",
            traditionsJv = "Kirab Gunungan Besar, penyembelihan kurban keraton, donga wilujeng.",
            traditionsId = "Arak-arakan Gunungan Besar, ibadah kurban keraton, kenduri selamatan.",
            traditionsEn = "Gunungan procession, royal sacrificial sharing."
        )
    )

    fun getHolidaysForDate(date: LocalDate): List<JavaneseHolidayInstance> {
        val jvDate = fromLocalDate(date)
        val list = mutableListOf<JavaneseHolidayInstance>()

        // 1. Check Malam Jumat Kliwon (Thursday night entering Friday Kliwon, or Friday Kliwon day)
        if (jvDate.dayOfWeek == 6 && jvDate.pasaran == Pasaran.KLIWON) {
            val jumatKliwon = JavaneseHoliday(
                id = "jumat_kliwon_${date}",
                nameJv = "Dinten Jemuwah Kliwon",
                nameId = "Jumat Kliwon (Malam Sakral Tirakatan)",
                nameEn = "Friday Kliwon (Sacred Vigil)",
                category = JavaneseHolidayCategory.MALAM_SAKRAL,
                monthCode = jvDate.monthCode,
                dayStart = jvDate.day,
                dayEnd = jvDate.day,
                descriptionJv = "Dinten ageng ing tradisi Jawa kanthi neptu 14 (Jemuwah 6 + Kliwon 8). Dipunpitados minangka wekdal mustajab kagem tirakatan, ziarah leluhur, lan panyuwunan rohani.",
                descriptionId = "Hari istimewa dalam kosmologi Jawa dengan neptu 14 (Jumat 6 + Kliwon 8). Hari yang sarat nilai spiritual untuk berziarah, tirakat, dan mendekatkan diri kepada Sang Khalik.",
                descriptionEn = "High spiritual significance in Javanese cosmology with Neptu 14. Highly auspicious day for remembrance of ancestors and meditation vigils.",
                greetingJv = "Rahayu ing dinten Jemuwah Kliwon",
                greetingId = "Rahayu di hari Jumat Kliwon",
                greetingEn = "Peace on Friday Kliwon",
                traditionsJv = "Ziarah kubur leluhur, tawasulan, reresik pusaka, ngobong dupa/kemenyan.",
                traditionsId = "Ziarah ke makam orang tua/leluhur, doa tawasul, tirakatan malam hari.",
                traditionsEn = "Grave visits, contemplative prayer, incense offering."
            )
            list.add(JavaneseHolidayInstance(jumatKliwon, jvDate, date))
        }

        // 2. Check Malam Selasa Kliwon (Anggara Kasih)
        if (jvDate.dayOfWeek == 3 && jvDate.pasaran == Pasaran.KLIWON) {
            val anggaraKasih = JavaneseHoliday(
                id = "selasa_kliwon_${date}",
                nameJv = "Selasa Kliwon (Anggara Kasih)",
                nameId = "Selasa Kliwon (Anggara Kasih)",
                nameEn = "Tuesday Kliwon (Anggara Kasih)",
                category = JavaneseHolidayCategory.MALAM_SAKRAL,
                monthCode = jvDate.monthCode,
                dayStart = jvDate.day,
                dayEnd = jvDate.day,
                descriptionJv = "Weton Anggara Kasih (Selasa 3 + Kliwon 8 = Neptu 11). Dinten kebak asih tresna, prayogi kagem sesaji kembang setaman, ruwatan, lan ningkataken rasa syukur.",
                descriptionId = "Weton sakral Anggara Kasih (Selasa Kliwon, neptu 11). Dipercaya sebagai hari limpahan kasih sayang semesta, sangat baik untuk penyucian batin dan memohon keselamatan.",
                descriptionEn = "Sacred Anggara Kasih day representing divine love and inner peace, traditionally dedicated to purification and spiritual balance.",
                greetingJv = "Rahayu widada ing dinten Anggara Kasih",
                greetingId = "Selamat hari Anggara Kasih",
                greetingEn = "Peace on Anggara Kasih",
                traditionsJv = "Sesaji kembang setaman, siraman, semedi, donga asih.",
                traditionsId = "Penyiraman air kembang, meditasi hening, ziarah makam sesepuh.",
                traditionsEn = "Floral water purification, quiet meditation."
            )
            list.add(JavaneseHolidayInstance(anggaraKasih, jvDate, date))
        }

        // 3. Check Purwaning Wulan (Awal Bulan Jawa - Tanggal 1)
        if (jvDate.day == 1) {
            val awalBulan = JavaneseHoliday(
                id = "awal_wulan_${jvDate.monthCode}",
                nameJv = "Tanggal 1 Wulan ${jvDate.monthNameJv}",
                nameId = "1 ${jvDate.monthNameId} (Awal Bulan Jawa)",
                nameEn = "1st of ${jvDate.monthNameEn} (New Javanese Month)",
                category = JavaneseHolidayCategory.BULAN_BARU_JAWA,
                monthCode = jvDate.monthCode,
                dayStart = 1,
                dayEnd = 1,
                descriptionJv = "Dinten sepisanan mlebet wulan ${jvDate.monthNameJv} ${jvDate.yearJavanese} AJ Taun ${jvDate.yearNameWindu}. Wektu prayoga kagem nyuwun berkah lan pepadhang.",
                descriptionId = "Hari pertama memasuki bulan ${jvDate.monthNameId} tahun ${jvDate.yearJavanese} Jawa (${jvDate.yearNameWindu}). Awal yang baik untuk mengawali rencana dan memanjatkan doa berkah.",
                descriptionEn = "The first day of Javanese month ${jvDate.monthNameEn} ${jvDate.yearJavanese} AJ, marked by auspicious beginnings.",
                greetingJv = "Sugeng lumebeting Wulan ${jvDate.monthNameJv}!",
                greetingId = "Selamat memasuki bulan ${jvDate.monthNameId}!",
                greetingEn = "Blessed New Month of ${jvDate.monthNameEn}!",
                traditionsJv = "Slametan wiwitan wulan, panyuwunan wilujeng.",
                traditionsId = "Doa awal bulan, refleksi diri.",
                traditionsEn = "New month reflection and prayers."
            )
            list.add(JavaneseHolidayInstance(awalBulan, jvDate, date))
        }

        // 4. Catalog matches
        for (h in allHolidaysCatalog) {
            if (h.id == "rebo_wekasan") {
                // Special check for Rebo Wekasan: Wednesday in month of Sapar whose day is within the last 7 days of Sapar
                // Sapar has 29 days, so last Wednesday is between 23 and 29 Sapar
                if (jvDate.monthCode == 2 && jvDate.dayOfWeek == 4 && jvDate.day in 23..29) {
                    list.add(JavaneseHolidayInstance(h, jvDate, date))
                }
            } else {
                if (jvDate.monthCode == h.monthCode && jvDate.day in h.dayStart..h.dayEnd) {
                    val dayNum = jvDate.day - h.dayStart + 1
                    val totalDays = h.dayEnd - h.dayStart + 1
                    list.add(JavaneseHolidayInstance(h, jvDate, date, dayNumberInHoliday = dayNum, totalDays = totalDays))
                }
            }
        }

        return list
    }

    fun getUpcomingHolidays(fromDate: LocalDate, count: Int = 15): List<JavaneseHolidayInstance> {
        val results = mutableListOf<JavaneseHolidayInstance>()
        var curDate = fromDate
        var daysChecked = 0
        val seenHolidayKeys = mutableSetOf<String>()

        while (results.size < count && daysChecked < 370) {
            val holidays = getHolidaysForDate(curDate)
            for (h in holidays) {
                val key = "${h.holiday.id}_${h.javaneseDate.yearJavanese}_${h.javaneseDate.monthCode}"
                if (!seenHolidayKeys.contains(key) || h.holiday.category == JavaneseHolidayCategory.MALAM_SAKRAL) {
                    if (h.holiday.category != JavaneseHolidayCategory.MALAM_SAKRAL) {
                        seenHolidayKeys.add(key)
                    }
                    results.add(h)
                    if (results.size >= count) break
                }
            }
            curDate = curDate.plusDays(1)
            daysChecked++
        }
        return results
    }

    // 8. KALKULATOR PENGETAN DINA SEDA / SLAMETAN JAWA
    data class PengetanSeda(
        val labelJv: String,
        val labelId: String,
        val targetDate: LocalDate,
        val javaneseDate: JavaneseDate,
        val daysCount: Int
    )

    fun calculatePengetanSeda(startDate: LocalDate): List<PengetanSeda> {
        val list = mutableListOf<PengetanSeda>()
        val milestones = listOf(
            Triple("Geblak (Dina Seda)", "Hari H Wafat", 0),
            Triple("Nelung Dina (3 Dina)", "3 Hari", 2),
            Triple("Mitung Dina (7 Dina)", "7 Hari", 6),
            Triple("Matang Puluh (40 Dina)", "40 Hari", 39),
            Triple("Nyatus (100 Dina)", "100 Hari", 99),
            Triple("Mendhak I (1 Taun)", "Pendhak 1 (1 Tahun)", 353),
            Triple("Mendhak II (2 Taun)", "Pendhak 2 (2 Tahun)", 707),
            Triple("Nyewu (1000 Dina)", "1000 Hari (Nyewu)", 999)
        )
        for ((jv, id, daysAdd) in milestones) {
            val target = startDate.plusDays(daysAdd.toLong())
            val jvDate = fromLocalDate(target)
            list.add(PengetanSeda(jv, id, target, jvDate, daysAdd + 1))
        }
        return list
    }
}
