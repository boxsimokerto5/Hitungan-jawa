package com.example.calendar

import com.example.localization.AppLanguage
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

data class WetonKelahiran(
    val gregorianDate: LocalDate,
    val javaneseDate: JavaneseDate,
    val wetonName: String,
    val dinaNameId: String,
    val dinaNameJv: String,
    val dinaNameEn: String,
    val pasaran: Pasaran,
    val neptuDina: Int,
    val neptuPasaran: Int,
    val neptuTotal: Int,
    val watakLakuning: String,
    val watakDeskripsiId: String,
    val watakDeskripsiJv: String,
    val watakDeskripsiEn: String,
    val karakterKelahiranId: String,
    val karakterKelahiranJv: String,
    val pancasuda: String,
    val pancasudaDeskripsiId: String,
    val pancasudaDeskripsiJv: String,
    val nagaDinaArah: String,
    val pepatahLuhurJv: String,
    val pepatahArtiId: String,
    val nextSelapananDate: LocalDate,
    val daysUntilNextSelapanan: Long,
    val totalSelapanLived: Long,
    val umurTahun: Int,
    val umurBulan: Int,
    val umurHari: Int,
    val totalHariHidup: Long
) {
    fun getWatakDescription(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> watakDeskripsiJv
            AppLanguage.INDONESIAN -> watakDeskripsiId
            AppLanguage.ENGLISH -> watakDeskripsiEn
        }
    }

    fun getKarakterDescription(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> karakterKelahiranJv
            AppLanguage.INDONESIAN -> karakterKelahiranId
            AppLanguage.ENGLISH -> karakterKelahiranId
        }
    }

    fun getPancasudaDescription(language: AppLanguage): String {
        return when (language) {
            AppLanguage.JAVANESE -> pancasudaDeskripsiJv
            AppLanguage.INDONESIAN -> pancasudaDeskripsiId
            AppLanguage.ENGLISH -> pancasudaDeskripsiId
        }
    }
}

data class SavedWetonProfile(
    val id: String,
    val name: String,
    val birthDate: LocalDate,
    val relationLabel: String = "Pribadi"
)

data class JodohPetunganResult(
    val weton1: WetonKelahiran,
    val weton2: WetonKelahiran,
    val totalNeptu: Int,
    val kategori: String, // Pegat, Ratu, Jodho, Topo, Tinari, Padu, Sujanan, Pesthi
    val maknaId: String,
    val maknaJv: String,
    val nasehatId: String,
    val sisaBagiTujuh: Int
)

object WetonKelahiranEngine {

    fun calculate(birthDate: LocalDate, today: LocalDate = LocalDate.now()): WetonKelahiran {
        val jvDate = JavaneseCalendarEngine.fromLocalDate(birthDate)
        val dina = jvDate.dayOfWeek
        val pasaran = jvDate.pasaran
        val neptuDina = jvDate.dayOfWeekNeptu
        val neptuPasaran = pasaran.neptu
        val neptuTotal = neptuDina + neptuPasaran

        val dinaId = getDinaNameId(dina)
        val dinaJv = getDinaNameJv(dina)
        val dinaEn = getDinaNameEn(dina)

        val wetonName = "$dinaId ${pasaran.idName}"

        // Watak Lakuning berdasarkan Neptu Total
        val (watakLakuning, watakId, watakJv, watakEn) = getLakuningInfo(neptuTotal)

        // Karakter Spesifik Weton (35 Variasi Primbon)
        val (karakterId, karakterJv) = getKarakterWeton(dina, pasaran)

        // Pancasuda (Neptu % 7 atau formula Primbon Jawa)
        val (pancasuda, pancasudaId, pancasudaJv) = getPancasudaInfo(neptuTotal)

        // Naga Dina / Arah Rejeki
        val nagaDina = getNagaDina(dina, pasaran)

        // Pepatah Luhur
        val (pepatahJv, pepatahId) = getPepatahLuhur(neptuTotal)

        // Selapanan calculation (35 hari siklus)
        val daysDiff = ChronoUnit.DAYS.between(birthDate, today)
        val (daysUntilNext, nextSelapananDate) = if (daysDiff >= 0) {
            val remainder = daysDiff % 35
            val daysWait = if (remainder == 0L) 0L else (35 - remainder)
            Pair(daysWait, today.plusDays(daysWait))
        } else {
            Pair(-daysDiff, birthDate)
        }

        val totalSelapan = if (daysDiff >= 0) daysDiff / 35 else 0L

        // Umur Masehi
        val period = if (daysDiff >= 0) Period.between(birthDate, today) else Period.ZERO
        val totalDays = if (daysDiff >= 0) daysDiff else 0L

        return WetonKelahiran(
            gregorianDate = birthDate,
            javaneseDate = jvDate,
            wetonName = wetonName,
            dinaNameId = dinaId,
            dinaNameJv = dinaJv,
            dinaNameEn = dinaEn,
            pasaran = pasaran,
            neptuDina = neptuDina,
            neptuPasaran = neptuPasaran,
            neptuTotal = neptuTotal,
            watakLakuning = watakLakuning,
            watakDeskripsiId = watakId,
            watakDeskripsiJv = watakJv,
            watakDeskripsiEn = watakEn,
            karakterKelahiranId = karakterId,
            karakterKelahiranJv = karakterJv,
            pancasuda = pancasuda,
            pancasudaDeskripsiId = pancasudaId,
            pancasudaDeskripsiJv = pancasudaJv,
            nagaDinaArah = nagaDina,
            pepatahLuhurJv = pepatahJv,
            pepatahArtiId = pepatahId,
            nextSelapananDate = nextSelapananDate,
            daysUntilNextSelapanan = daysUntilNext,
            totalSelapanLived = totalSelapan,
            umurTahun = period.years,
            umurBulan = period.months,
            umurHari = period.days,
            totalHariHidup = totalDays
        )
    }

    private fun getDinaNameId(dina: Int): String = when (dina) {
        1 -> "Minggu"; 2 -> "Senin"; 3 -> "Selasa"; 4 -> "Rabu"; 5 -> "Kamis"; 6 -> "Jumat"; 7 -> "Sabtu"; else -> ""
    }

    private fun getDinaNameJv(dina: Int): String = when (dina) {
        1 -> "Minggu (Radite)"; 2 -> "Senen (Soma)"; 3 -> "Selasa (Anggara)"; 4 -> "Rebo (Buda)"
        5 -> "Kemis (Respati)"; 6 -> "Jemuwah (Sukra)"; 7 -> "Setu (Tumpak)"; else -> ""
    }

    private fun getDinaNameEn(dina: Int): String = when (dina) {
        1 -> "Sunday"; 2 -> "Monday"; 3 -> "Tuesday"; 4 -> "Wednesday"; 5 -> "Thursday"; 6 -> "Friday"; 7 -> "Saturday"; else -> ""
    }

    private fun getLakuningInfo(neptu: Int): Array<String> {
        return when (neptu) {
            7 -> arrayOf(
                "Lakuning Pandhita Kang Semedi",
                "Berwatak pendiam, tekun berpikir, batiniah kokoh, suka menyendiri untuk berkontemplasi, dan tidak menyukai keramaian yang berlebihan.",
                "Watakipun anteng, remen tetirakat lan nanting batin, mboten remen pasulayan, micanten namung ingkang wigatos kemawon.",
                "Quiet nature, deep contemplative spirit, steadfast inner strength, preferring meaningful solitude."
            )
            8 -> arrayOf(
                "Lakuning Geni",
                "Memiliki semangat membara, dinamis, pantang menyerah, pemberani, cepat terpancing emosi namun cepat reda dan berhati pemaaf.",
                "Kadi dene latu, gregedipun ageng, kendel ngadhepi reribet, cepet duka nanging enggal kendho lan gampil paring pangapura.",
                "Fiery nature, courageous, dynamic, passionate, quick to react but forgiving and warm-hearted."
            )
            9 -> arrayOf(
                "Lakuning Angin",
                "Lincah, mudah bergaul ke segala kalangan, pandai berbicara, berjiwa bebas, suka merantau, dan tidak suka terkekang aturan yang kaku.",
                "Kadi dene samirana, luwes anggene paseduluran, pinter micara, remen njajah desa milangkori, mboten remen dipunkrangkeng.",
                "Breeze nature, highly adaptable, eloquent speaker, free-spirited, friendly to all walks of life."
            )
            10 -> arrayOf(
                "Lakuning Pandhita Sakti",
                "Cerdas, haus ilmu pengetahuan, memiliki wibawa pemikir, daya nalar tajam, bijaksana memberi nasihat, dan dihormati kawan-kawannya.",
                "Wasis anggene ngudi ngelmu, landhep panggraitane, wicaksana paring wewarah, dados panutan para rowang.",
                "Sage nature, brilliant intellect, observant, natural counselor with quiet respect and wisdom."
            )
            11 -> arrayOf(
                "Lakuning Setan",
                "Pemberani luar biasa, teguh memegang prinsip, tidak mudah terpengaruh orang lain, pantang mundur jika meyakini kebenaran, giat mencari rezeki.",
                "Kendel sanget, mboten gampil dipunplayokaken dening tiyang sanes, tanggon pados rejeki, kenceng pamanggihipun.",
                "Tenacious and resolute spirit, exceptionally brave, unyielding in convictions, hardworking."
            )
            12 -> arrayOf(
                "Lakuning Kembang",
                "Mempesona, ramah tamah, cinta damai, pandai menyenangkan hati sesama, tutur katanya halus, dan disukai banyak teman.",
                "Kadi dene sekar arum, nyengsemaken manah, grapyak semanak, remen karukunan, micara alus nentremaken suasana.",
                "Blossom nature, charming, peacemaker, gracious communicator, bringing joy and harmony to gatherings."
            )
            13 -> arrayOf(
                "Lakuning Lintang",
                "Tenang dan bersahaja, bagai bintang malam yang bercahaya di kala gelap, tidak suka pamer atau menonjolkan diri namun kehadirannya dirindukan.",
                "Kadi dene kartika ing akasa, anteng prasaja, madhangi pepeteng, mboten remen gumunggung nanging dipunajeni.",
                "Starlight nature, modest and serene, shines quietly in difficult moments without self-promotion."
            )
            14 -> arrayOf(
                "Lakuning Rembulan",
                "Teduh menyejukkan hati, berbudi pekerti luhur, tutur katanya lembut menenteramkan, berjiwa pengayom, dan menjadi pelipur lara bagi sesama.",
                "Kadi dene purnamasiddhi, adhem ayem nyirep hawa panas, trapsila solah bawane, dados pengayomaning sesami.",
                "Moonlight nature, soothing, gentle-mannered, compassionate, natural protector and comforting presence."
            )
            15 -> arrayOf(
                "Lakuning Srengenge",
                "Berwibawa seperti matahari, memberi energi dan penerangan, berjiwa pemimpin sejati, dermawan, tegas, dan dapat diandalkan dalam keadaan kritis.",
                "Kadi dene surya, madhangi jagad, dados pimpinan kang misuwur, loma marang liyan, tanggap ing samukawis kahanan.",
                "Sunlight nature, charismatic leader, generous, radiant energy, reliable guardian and decision maker."
            )
            16 -> arrayOf(
                "Lakuning Bumi",
                "Sabar tanpa batas, tawakal, berhati lapang menampung suka dan duka sesama, tidak mudah mengeluh, kokoh bagai tanah tumpuan.",
                "Kadi dene bantala, jembar dhadhane, sabar ngadhepi pacoban, loma nampa sakehing lelakon tanpa sesambat.",
                "Earth nature, patient, immensely resilient, enduring difficulties with grace, grounded and reliable."
            )
            17 -> arrayOf(
                "Lakuning Gunung",
                "Bercita-cita luhur dan tinggi, teguh pendirian bagai gunung karang, wawasan luas, disegani kawan maupun lawan, memiliki martabat tinggi.",
                "Kadi dene arga kang agung, luhur gegayuhane, mboten miyar-miyur, jembar wawasane, kinurmatan dening sesami.",
                "Mountain nature, lofty aspirations, unshakeable convictions, broad perspective, naturally dignified."
            )
            18 -> arrayOf(
                "Lakuning Paripurna",
                "Memiliki wibawa kepemimpinan tertinggi, berkuasa atas dirinya, firasat tajam, rezeki luas, tegas dalam keadilan, dan berkharisma kuat.",
                "Watak sampurna kanthi panguwasa kang luhur, landhep panggraitane, jembar sandhang pangane, adil paramarta.",
                "Pinnacle nature, supreme authority and presence, acute intuition, generous fortune, firmly just."
            )
            else -> arrayOf(
                "Lakuning Jagad",
                "Berwawasan luas, fleksibel, mudah menempatkan diri di berbagai lingkungan adat dan sosial.",
                "Jembar pasrawungane, saged manjing ajur-ajer ing sakehing kahanan.",
                "Universal nature, adaptable, open-minded across varied situations."
            )
        }
    }

    private fun getKarakterWeton(dina: Int, pasaran: Pasaran): Pair<String, String> {
        return when (dina) {
            1 -> when (pasaran) { // Minggu
                Pasaran.LEGI -> Pair(
                    "Tegas, dermawan, tenang menghadapi masalah, pandai menyembunyikan perasaan, dan setia pada sahabat sejati.",
                    "Santosa ing tekad, loma tetulung, saged nyimpen wewadi, setya tuhu marang mitra pitepangan."
                )
                Pasaran.PAHING -> Pair(
                    "Berjiwa mandiri, berkemauan keras, menyukai kerapian dan keindahan, tidak suka bergantung pada bantuan orang lain.",
                    "Mandhiri solah bawane, kenceng panyuwunane, remen marang kaendahan lan karukunan, mboten gumantung liyan."
                )
                Pasaran.PON -> Pair(
                    "Pemberani, pandai berbicara di muka umum, kreatif, memiliki intuisi bisnis yang baik, suka melindungi yang lemah.",
                    "Kendel sanget, wasis micara ing ngarsaning akathah, trampil pados pangupajiwa, remen ngayomi kang apes."
                )
                Pasaran.WAGE -> Pair(
                    "Cerdas, suka menuntut ilmu pengetahuan, teliti, hemat dan cermat mengelola keuangan, berbudi luhur.",
                    "Wasis nalaripun, tliti ing samukawis pakaryan, gemi setiti ngreksa rejeki, luhur bebudene."
                )
                Pasaran.KLIWON -> Pair(
                    "Pemaaf, pandai bergaul, berwibawa, bijaksana, memiliki rasa percaya diri yang kuat dan dihormati di lingkungannya.",
                    "Gampil paring pangaksama, luwes pasedulurane, kinurmatan dening tangga teparo, mantep ing tekad."
                )
            }
            2 -> when (pasaran) { // Senin
                Pasaran.LEGI -> Pair(
                    "Sopan santun, cinta damai, tutur kata ramah, suka bepergian dan mencari kawan baru, tidak mudah mendendam.",
                    "Grapyak semanak, remen karukunan, micara alus nengsemaken, seneng sesrawungan pados paseduluran anyar."
                )
                Pasaran.PAHING -> Pair(
                    "Jujur, pekerja keras, tahan banting, bersungguh-sungguh menggapai cita-cita, teguh pada komitmen yang diucapkan.",
                    "Jujur ing lathi lan solah bawa, mempeng makarya, tanggon ngadhepi pakewuh, netepi janji kang wus kawetu."
                )
                Pasaran.PON -> Pair(
                    "Cerdas, ramah, berhati lembut, suka menolong tanpa pamrih, cepat berempati pada penderitaan sesama.",
                    "Lantip ing panggraitan, alus budine, loma tetulung tanpa pamrih, gampil welas asih marang kang nandang sangsara."
                )
                Pasaran.WAGE -> Pair(
                    "Tenang, setia, berpendirian teguh, hemat dan terencana, tidak suka tergesa-gesa dalam mengambil keputusan penting.",
                    "Anteng, setya ing janji, kukuh pamanggihe, gemi ngatur panguripan, mateng anggene nimbang samukawis prakara."
                )
                Pasaran.KLIWON -> Pair(
                    "Disiplin, bertanggung jawab tinggi, menghargai waktu, memiliki kepedulian keluarga yang sangat besar.",
                    "Gusti paring bebuden tata titi, tanggel jawab ageng, ngajeni wekdal, tresna asih sanget marang kulawarga."
                )
            }
            3 -> when (pasaran) { // Selasa
                Pasaran.LEGI -> Pair(
                    "Ceria, mudah menghidupkan suasana, optimis, suka menolong sesama, tidak suka membesar-besarkan masalah sepele.",
                    "Padhang polatane, tansah bombong atine, entengan tetulung, mboten remen nggedhekake perkara cilik."
                )
                Pasaran.PAHING -> Pair(
                    "Pemberani, suka membela kebenaran, berjiwa ksatria, tegas dalam membedakan hal yang adil dan tidak adil.",
                    "Kendel mbelani bebener, watak ksatria, teges anggene nimbang leres lan lupute tumindak."
                )
                Pasaran.PON -> Pair(
                    "Berjiwa teduh, setia kawan, tidak suka pamer kemampuan, tekun dan ulet menyelesaikan tugas hingga tuntas.",
                    "Adhem ayem, setya marang sedulur, mboten remen umuk, tutug anggene nindakake jejibahan."
                )
                Pasaran.WAGE -> Pair(
                    "Sederhana, hemat, telaten, tekun berdoa, memiliki kesabaran batin yang sangat kuat di masa-masa sulit.",
                    "Prasaja ing urip, gemi nastiti, temen ndedonga marang Gusti, kuwat nglampahi prihatin."
                )
                Pasaran.KLIWON -> Pair(
                    "Anggara Kasih: Penuh kasih sayang, berbudi pekerti sakral, batiniah tajam, disenangi banyak kalangan, berjiwa pemaaf.",
                    "Weton Anggara Kasih: Kebak ing asih tresna, landhep batinipun, kinasihing sesami, jembar pangapurane."
                )
            }
            4 -> when (pasaran) { // Rabu
                Pasaran.LEGI -> Pair(
                    "Bijaksana, berpikiran terbuka, menjunjung tinggi keadilan, pandai bernegosiasi dan menengahi perselisihan.",
                    "Wicaksana, jembar wawasane, ngugemi kaadilan, wasis mrantasi pasulayan kanthi tentrem."
                )
                Pasaran.PAHING -> Pair(
                    "Bersemangat tinggi, mandiri, pandai memanfaatkan peluang, berhati pemurah bila orang lain meminta tolong.",
                    "Gegayuhanipun ageng, mandhiri, wasis pados margi rejeki, loma marang sapa wae kang sambat."
                )
                Pasaran.PON -> Pair(
                    "Tenang, berwibawa, bersikap adil, pandai menghibur orang yang sedang sedih, berhati penyabar.",
                    "Semu anteng nanging kinurmatan, adil paramarta, wasis nglipur manah kang susah, sabar ing pangucap."
                )
                Pasaran.WAGE -> Pair(
                    "Cerdas, analitis, menyukai hal-hal ilmiah dan spiritual, rajin belajar, suka berbagi wawasan kepada kawan.",
                    "Pinter lan landhep pikirane, remen ngudi kawruh lahir lan batin, seneng andum ngelmu marang liyan."
                )
                Pasaran.KLIWON -> Pair(
                    "Pemikir mendalam, setia pada janji, tekun beribadah, memiliki pendirian kokoh dan dihormati oleh keluarga besar.",
                    "Jero panggraitane, netepi sedaya ujar, temen ing donga, kukuh ing tekad lan diajeni kulawarga."
                )
            }
            5 -> when (pasaran) { // Kamis
                Pasaran.LEGI -> Pair(
                    "Bercita-cita mulia, dermawan, berwawasan jauh ke depan, suka mendidik dan membimbing orang lain ke arah kebaikan.",
                    "Luhur pangangen-angene, loma, mirsani masa ngajeng kanthi wasis, remen paring tuladha becik marang sesami."
                )
                Pasaran.PAHING -> Pair(
                    "Pemberani, berwibawa raja, tidak suka diintimidasi, mandiri dalam mencari nafkah, tangguh lahir dan batin.",
                    "Kendel kadi dene prajurit, prawira, mboten gampil gigrig, ulet nyambut gawe, santosa lahir batin."
                )
                Pasaran.PON -> Pair(
                    "Ramah tamah, pandai mengelola perkumpulan, disenangi banyak teman, memiliki kesabaran dan tutur kata santun.",
                    "Semanak grapyak, wasis ngempalaken baladewa, disenengi tangga rowang, sabar lan trapsila basane."
                )
                Pasaran.WAGE -> Pair(
                    "Ulet, pekerja keras, hemat, setia, dapat dipercaya memegang amanah besar dalam pekerjaan atau keluarga.",
                    "Mempeng makarya, gemi ngreksa arta, jujur ing amanah, saged dipunugemi ing samukawis gawe."
                )
                Pasaran.KLIWON -> Pair(
                    "Berjiwa guru/pendidik, sabar, disegani, memiliki kepribadian menyejukkan dan perkataannya didengarkan orang lain.",
                    "Watak pandhita, sabar momong sesami, kinabekten dening murid lan rowang, sabdane dados panutan."
                )
            }
            6 -> when (pasaran) { // Jumat
                Pasaran.LEGI -> Pair(
                    "Jujur, dapat dipercaya, cinta kebenaran, suka membantu orang susah, memiliki jiwa kepemimpinan yang adil.",
                    "Jujur tanpa seling surup, ngugemi bebener, entengan tetulung wong kesrakat, adil pimpinane."
                )
                Pasaran.PAHING -> Pair(
                    "Kreatif, menyukai seni dan estetika, berpendirian teguh, ramah, pandai menyenangkan hati pasangan dan keluarga.",
                    "Trampil seni lan kaendahan, kukuh ing karep, sumeh ngguyokake ati, tresna sanget marang jodho."
                )
                Pasaran.PON -> Pair(
                    "Berbudi pekerti halus, pemaaf, setia, tidak suka mengungkit kebaikan masa lalu, berjiwa mulia.",
                    "Alus budi pakertine, gampil paring pangapura, mboten remen ngundhat-undhat kabecikan, mulya atine."
                )
                Pasaran.WAGE -> Pair(
                    "Tenang, penuh perhitungan matang, hemat, cermat, pandai menjaga rahasia diri dan rahasia sesama.",
                    "Anteng sareh, mateng anggene ngetung samukawis, gemi, wasis nyimpen wewadine rowang."
                )
                Pasaran.KLIWON -> Pair(
                    "Weton Sakral: Berbudi luhur, welas asih, daya spiritual tinggi, berwibawa, menjadi pengayom bagi sanak saudara.",
                    "Weton Sakral: Luhur budine, kebak welas asih, landhep mata batine, pantes dados pangayomaning sedulur."
                )
            }
            7 -> when (pasaran) { // Sabtu
                Pasaran.LEGI -> Pair(
                    "Santai namun bertanggung jawab, disukai kawan-kawan, suka berbagi makanan, tidak suka ikut campur urusan orang lain.",
                    "Sareh nanging rampung gaweyane, disenengi kanca, seneng andum pangan, mboten remen urusane liyan."
                )
                Pasaran.PAHING -> Pair(
                    "Neptu 18 (Tertinggi): Berjiwa pemimpin besar, wibawa tak tertandingi, mandiri, rezeki luas, tegas dalam ketetapan.",
                    "Neptu 18 (Puncak): Watak nata/ratu, wibawa ageng, mandhiri, jembar sandhang pangane, kukuh ing sabda."
                )
                Pasaran.PON -> Pair(
                    "Pemberani, ulet, tahan banting, memiliki loyalitas tinggi kepada sahabat, pantang mundur sebelum berhasil.",
                    "Kendel, ulet makarya, setya marang rowang, mboten purun mundur sadurunge gegayuhane kasembadan."
                )
                Pasaran.WAGE -> Pair(
                    "Tegas, pendiam, tekun bekerja dalam hening, tidak suka berpura-pura, berhati setia dan jujur.",
                    "Tegas, mboten kakehan rembug nanging nyambut gawe nyata, mboten remen lamis, setya tuhu."
                )
                Pasaran.KLIWON -> Pair(
                    "Sabar, tawakal, berwibawa, bijaksana memecahkan masalah rumit, dihormati oleh keluarga dan masyarakat sekitar.",
                    "Sabar ngadhepi lelakon, wasis mrantasi perkara angel, kinurmatan ing bebrayan agung."
                )
            }
            else -> Pair("Berbudi pekerti luhur dan bersahaja.", "Luhur bebudene lan tansah ngugemi kautaman.")
        }
    }

    private fun getPancasudaInfo(neptu: Int): Array<String> {
        val sisa = neptu % 7
        return when (sisa) {
            1 -> arrayOf(
                "Wasesa Segara",
                "Berhati lapang bagai samudra, suka memaafkan kekhilafan orang lain, dan dianugerahi rezeki yang luas mengalir.",
                "Jembar dhadhane kadi segara, gampil paring pangapura, jembar rejekine lumintu tanpa kendhat."
            )
            2 -> arrayOf(
                "Tunggak Semi",
                "Rezekinya selalu tumbuh dan bersemi kembali. Meski sempat surut, akan lekas mendapatkan jalan kemudahan baru.",
                "Rejekine tansah trubus lan semi maneh, tansah pinaringan margi gampil dening Gusti Kang Murbeng Dumadi."
            )
            3 -> arrayOf(
                "Satria Wibawa",
                "Selalu memperoleh kemuliaan, kehormatan, dan wibawa di mata masyarakat sekitar berkat keluhuran budinya.",
                "Tansah pinaringan kamulyan, kawibawan, lan kajen keringan ing satengahing bebrayan amarga trapsilane."
            )
            4 -> arrayOf(
                "Sumur Sinaba",
                "Menjadi tempat bertanya dan rujukan ilmu pengetahuan bagi orang lain karena ketajaman nalar dan kebijaksanaannya.",
                "Dados sumbering kawruh lan panuwunan pirsa tumrap tiyang kathah, awit landhep panggraitane."
            )
            5 -> arrayOf(
                "Bumi Kapetak",
                "Ulet, pekerja keras tanpa kenal lelah, tahan uji menghadapi rintangan, dan mampu mencapai keberhasilan lewat ketekunan.",
                "Ulet mempeng makarya tanpa sambat, kuwat nglampahi prihatin, saged nggayuh kabegjan kanthi temen."
            )
            6 -> arrayOf(
                "Satria Wirang",
                "Dianugerahi kesabaran luar biasa untuk melewati ujian fitnah dan cobaan hidup, hingga akhirnya meraih kemuliaan sejati.",
                "Pinaringan sabar ingkang linangkung ngadhepi rubeda, pungkasane pinaringan pepadhang lan drajat luhur."
            )
            else -> arrayOf( // 0 -> 7
                "Lebu Katiyup Angin",
                "Dinamis, suka bepergian mencari pengalaman baru, berjiwa bebas, dan berpotensi sukses besar jika fokus pada satu tujuan.",
                "Dinamis, remen ngupadi kawruh ing paran, saged nggayuh kamulyan ageng menawi ajeg lan mantep ing satunggal gegayuhan."
            )
        }
    }

    private fun getNagaDina(dina: Int, pasaran: Pasaran): String {
        return when (dina) {
            1 -> "Timur (Wetan) & Utara (Lor)"
            2 -> "Selatan (Kidul) & Timur (Wetan)"
            3 -> "Barat (Kulon) & Selatan (Kidul)"
            4 -> "Utara (Lor) & Barat (Kulon)"
            5 -> "Timur (Wetan) & Selatan (Kidul)"
            6 -> "Barat (Kulon) & Utara (Lor)"
            7 -> "Selatan (Kidul) & Timur (Wetan)"
            else -> "Timur (Wetan)"
        }
    }

    private fun getPepatahLuhur(neptu: Int): Pair<String, String> {
        val list = listOf(
            Pair("Sura Dira Jayaningrat Lebur Dening Pangastuti", "Segala keangkaramurkaan dan kekerasan akan runtuh oleh kelembutan hati serta kasih sayang."),
            Pair("Urip Iku Urup", "Hidup itu hendaknya menyala dan memberi manfaat serta penerangan bagi sesama di sekeliling kita."),
            Pair("Memayu Hayuning Bawana", "Memperindah ketenteraman alam semesta dan menjaga keharmonisan lahir batin sesama ciptaan Tuhan."),
            Pair("Ngluruk Tanpa Bala, Menang Tanpa Ngasorake", "Berjuang dengan kesatria tanpa mengandalkan kekuatan orang banyak, dan menang tanpa mempermalukan lawan."),
            Pair("Aja Kumaki, Aja Kumalungkung, Aja Dumeh", "Jangan sombong, jangan tinggi hati, dan jangan mentang-mentang berkuasa atau berkecukupan."),
            Pair("Alang-alang Dudu Aling-aling", "Segala rintangan dan kesulitan bukanlah penghalang untuk mencapai kemuliaan sejati.")
        )
        val index = Math.floorMod(neptu, list.size)
        return list[index]
    }

    // Perhitungan Kecocokan Jodoh menurut Neptu Weton Primbon Jawa (Sisa Bagi 7 / 8)
    fun hitungKecocokanJodoh(weton1: WetonKelahiran, weton2: WetonKelahiran): JodohPetunganResult {
        val total = weton1.neptuTotal + weton2.neptuTotal
        // Siklus Petungan 8 Jodoh: 1. Pegat, 2. Ratu, 3. Jodho, 4. Topo, 5. Tinari, 6. Padu, 7. Sujanan, 8. Pesthi
        val sisa8 = (total % 8).let { if (it == 0) 8 else it }

        val (kategori, maknaId, maknaJv, nasehatId) = when (sisa8) {
            1 -> arrayOf(
                "Pegat",
                "Perlu saling sabar dan menguatkan komunikasi karena berpotensi menghadapi ujian perbedaan pendapat atau masalah ekonomi.",
                "Kudu tansah sabar lan ngudi karukunan, amarga saged pinanggihi pacoban pasulayan utawi babagan arta.",
                "Dianjurkan rutin berdiskusi terbuka, saling menghargai privasi, dan banyak memohon keselamatan bersama."
            )
            2 -> arrayOf(
                "Ratu",
                "Pasangan yang harmonis dan disegani! Kehidupan rumah tangga dipenuhi wibawa, dihormati oleh keluarga besar dan tetangga.",
                "Jodho ingkang kinurmatan! Bale somah tansah ayem tentrem, diajeni dening tangga teparo lan para sesepuh.",
                "Pertahankan kerendahan hati dan teruslah menjadi teladan kebaikan di tengah masyarakat."
            )
            3 -> arrayOf(
                "Jodho",
                "Sangat cocok dan serasi (Jodho Sejati)! Masing-masing dapat saling melengkapi kekurangan, rukun hingga hari tua.",
                "Jodho sanyatane! Saged jumbuh saling ngisi kakirangan, rukun ngantos kaki-kaki lan nini-nini.",
                "Rawat terus rasa kasih sayang dan rasa syukur atas keserasian yang dianugerahkan Tuhan."
            )
            4 -> arrayOf(
                "Topo",
                "Di awal pernikahan mungkin melalui ujian keprihatinan atau perjuangan keras, namun pada akhirnya akan memetik kemuliaan dan sukses besar.",
                "Ing wiwitaning bale somah nglampahi laku prihatin, nanging pungkasane pinaringan kabegjan lan kamulyan ageng.",
                "Tetaplah kompak dan saling menguatkan mental saat merintis usaha atau membina rumah tangga."
            )
            5 -> arrayOf(
                "Tinari",
                "Limpahan rezeki dan kemudahan sandang pangan! Selalu menemukan solusi rezeki dan dinaungi kebahagiaan.",
                "Jembar sandhang pangane! Gampil pados rejeki lan pinaringan bingah ing sadina-dina.",
                "Gunakan kelapangan rezeki untuk berderma dan membantu sesama agar berkah semakin melimpah."
            )
            6 -> arrayOf(
                "Padu",
                "Sering mengalami perdebatan kecil atau adu argumen, namun cinta dan komitmen keduanya tetap kuat bila mampu mengendalikan ego.",
                "Asring padudon babagan perkara alit, nanging saged lestari menawi sami ngendhaleni hawa nepsu.",
                "Utamakan kepala dingin, hindari berbicara saat emosi memuncak, dan saling memaafkan sebelum tidur."
            )
            7 -> arrayOf(
                "Sujanan",
                "Rentan godaan rasa cemburu atau pihak ketiga. Kunci keselamatan rumah tangga adalah transparansi dan kejujuran.",
                "Saged pinanggihi pacoban sujana (meri/cemburu). Kuncinipun inggih menika jujur lan blaka marang pasangan.",
                "Tumbuhkan rasa saling percaya penuh dan jangan menyimpan rahasia penting dari pasangan."
            )
            else -> arrayOf( // 8
                "Pesthi",
                "Rukun, tenteram, adem ayem, dan damai sejahtera sampai akhir hayat tanpa rintangan berarti yang merusak pernikahan.",
                "Ayem tentrem adhem, mboten wonten rubeda ageng ingkang saged misahaken katresnanipun ngantos puputing yuswa.",
                "Pertahankan suasana rumah yang hangat, saling mendoakan, dan jaga kebersamaan keluarga."
            )
        }

        return JodohPetunganResult(
            weton1 = weton1,
            weton2 = weton2,
            totalNeptu = total,
            kategori = kategori,
            maknaId = maknaId,
            maknaJv = maknaJv,
            nasehatId = nasehatId,
            sisaBagiTujuh = sisa8
        )
    }
}
