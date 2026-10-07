package com.example.calendar

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// =========================================================================
// DATA CLASSES FOR HITUNGAN JAWA
// =========================================================================

data class MapatiResult(
    val hphtDate: LocalDate,
    val date120Hari: LocalDate,
    val javaneseDate120Hari: JavaneseDate,
    val rekomendasiHariBaik: List<Pair<LocalDate, JavaneseDate>>,
    val maknaTradisi: String,
    val uborampe: List<String>,
    val doaWejangan: String
)

data class MitoniResult(
    val hphtDate: LocalDate,
    val date210Hari: LocalDate,
    val javaneseDate210Hari: JavaneseDate,
    val wetonAyah: WetonKelahiran,
    val wetonIbu: WetonKelahiran,
    val rekomendasiMitoni: List<Pair<LocalDate, JavaneseDate>>,
    val tataCaraTradisi: List<String>,
    val uborampe: List<String>,
    val wejanganLeluhur: String
)

data class HplJawaResult(
    val hphtDate: LocalDate,
    val hplDate: LocalDate,
    val javaneseDateHpl: JavaneseDate,
    val wukuHpl: String,
    val pranataMangsaHpl: String,
    val neptuHpl: Int,
    val karakterPrediksi: String,
    val wejanganKelahiran: String
)

data class SepasarResult(
    val birthDate: LocalDate,
    val birthWeton: JavaneseDate,
    val sepasarDate: LocalDate,
    val sepasarWeton: JavaneseDate,
    val tradisiMakna: String,
    val uborampe: List<String>
)

data class SelapananResult(
    val birthDate: LocalDate,
    val birthWeton: JavaneseDate,
    val listSelapanan: List<Triple<Int, LocalDate, JavaneseDate>>, // Ke-1, ke-2, ke-3, dst
    val tradisiMakna: String,
    val uborampe: List<String>
)

data class TedhakSitenResult(
    val birthDate: LocalDate,
    val birthWeton: JavaneseDate,
    val tedhakSitenDate: LocalDate, // 245 hari (7 selapan)
    val tedhakSitenWeton: JavaneseDate,
    val tahapanProsesi: List<Pair<String, String>>,
    val uborampe: List<String>,
    val filosofiLuhur: String
)

data class MendhemAriAriResult(
    val genderIsBoy: Boolean,
    val birthDate: LocalDate,
    val birthWeton: JavaneseDate,
    val lokasiPenguburan: String,
    val uborampe: List<String>,
    val tataCara: List<String>,
    val wejanganLeluhur: String
)

data class WatakBayiResult(
    val birthDate: LocalDate,
    val birthWeton: JavaneseDate,
    val jamLahirKategori: String,
    val neptuTotal: Int,
    val watakNeptu: String,
    val watakWuku: String,
    val watakJamLahir: String,
    val potensiKarier: String,
    val arahanPendidikan: String
)

data class NamaJawaItem(
    val nama: String,
    val aksaraJawa: String,
    val arti: String,
    val jenisKelamin: String, // Laki-laki, Perempuan, Netral
    val nilaiNeptuHuruf: Int,
    val kategori: String
)

data class JodohDetailedResult(
    val wetonPria: WetonKelahiran,
    val wetonWanita: WetonKelahiran,
    val totalNeptu: Int,
    val hasilSiklus8: String,
    val maknaSiklus8: String,
    val nasehatSiklus8: String,
    val hasilSiklus7: String,
    val maknaSiklus7: String,
    val hasilSiklus4: String,
    val maknaSiklus4: String,
    val tingkatKeserasian: Int, // 1 - 100
    val solusiPenangkal: String
)

data class RekomendasiNikahItem(
    val date: LocalDate,
    val javaneseDate: JavaneseDate,
    val bintang: Int, // 3 - 5
    val statusHari: String, // Misal: "Sri (Rezeki Lancar)", "Gedhong (Kesejahteraan)", dll
    val keterangan: String
)

data class HariBaikNikahResult(
    val wetonPria: WetonKelahiran,
    val wetonWanita: WetonKelahiran,
    val bulanTarget: String,
    val daftarRekomendasi: List<RekomendasiNikahItem>,
    val pantanganHari: List<String>,
    val wejanganNikah: String
)

data class ArahRezekiResult(
    val wetonSuami: WetonKelahiran,
    val wetonIstri: WetonKelahiran,
    val gabunganNeptu: Int,
    val arahUtamaRezeki: String,
    val arahPintuRumah: String,
    val kotaWilayahHoki: String,
    val wejanganIkhtiar: String
)

data class SlametanKematianItem(
    val tahapan: String,
    val sebutanJawa: String,
    val date: LocalDate,
    val javaneseDate: JavaneseDate,
    val maknaSpiritual: String
)

data class SlametanKematianResult(
    val tanggalMeninggal: LocalDate,
    val waktuMeninggalSetelahMaghrib: Boolean,
    val geblagDate: LocalDate,
    val geblagWeton: JavaneseDate,
    val listTahapan: List<SlametanKematianItem>,
    val wejanganDoa: String
)

data class PindahRumahResult(
    val wetonKepalaKeluarga: WetonKelahiran,
    val arahTujuan: String,
    val tanggalPilihan: LocalDate,
    val wetonTanggalPilihan: JavaneseDate,
    val statusPancasuda: String,
    val kecocokanSkor: Int,
    val tataCaraBoyongan: List<String>,
    val uborampe: List<String>,
    val wejanganRumah: String
)

data class BukaUsahaResult(
    val wetonPemilik: WetonKelahiran,
    val bidangUsaha: String,
    val tanggalPilihan: LocalDate,
    val wetonTanggalPilihan: JavaneseDate,
    val statusPancaSiklus: String, // Sri, Rejeki, Gedhong, Loro, Pati
    val skorHoki: Int,
    val arahHokiTempatUsaha: String,
    val wejanganUsaha: String
)

data class NagaDinaResult(
    val date: LocalDate,
    val javaneseDate: JavaneseDate,
    val arahKepalaNaga: String, // Pantangan
    val arahPunggungNaga: String, // Selamat / Rezeki
    val wejanganPerjalanan: String,
    val amalanDoa: String
)

// =========================================================================
// HITUNGAN JAWA ENGINE LOGIC
// =========================================================================

object HitunganJawaEngine {

    private val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))

    fun formatDateId(date: LocalDate): String {
        return try {
            date.format(dateFormatter)
        } catch (_: Exception) {
            date.toString()
        }
    }

    // -------------------------------------------------------------
    // 1. KATEGORI KANDUNGAN
    // -------------------------------------------------------------

    fun calculateMapati(hphtDate: LocalDate): MapatiResult {
        // Usia 4 bulan = 120 hari
        val date120 = hphtDate.plusDays(120)
        val jv120 = JavaneseCalendarEngine.fromLocalDate(date120)

        // Cari 3 rekomendasi hari baik di sekitar 120 hari (antara hari ke-115 s/d 125)
        val rekomendasi = mutableListOf<Pair<LocalDate, JavaneseDate>>()
        for (i in -5..7) {
            val d = date120.plusDays(i.toLong())
            val jv = JavaneseCalendarEngine.fromLocalDate(d)
            // Hari baik: hindari neptu kecil sekali atau pasaran yang berbenturan
            val isGoodDina = jv.dayOfWeek in listOf(2, 4, 5, 6) // Senin, Rabu, Kamis, Jumat
            val isGoodPasaran = jv.pasaran in listOf(Pasaran.LEGI, Pasaran.PON, Pasaran.KLIWON)
            if (isGoodDina && isGoodPasaran) {
                rekomendasi.add(Pair(d, jv))
                if (rekomendasi.size >= 3) break
            }
        }
        if (rekomendasi.isEmpty()) {
            rekomendasi.add(Pair(date120, jv120))
        }

        val uborampe = listOf(
            "Kupat Luwar (simbol doa agar bayi lahir dengan lancar tanpa hambatan)",
            "Tumpeng Megana / Punar (nasi kuning berhias sayuran)",
            "Ketan Salak & Jenang Abang Putih (simbol asal-usul manusia)",
            "Janganan (sayur mayur 7 macam)",
            "Rujak Degan (kelapa muda muda manis penyejuk hati)"
        )

        return MapatiResult(
            hphtDate = hphtDate,
            date120Hari = date120,
            javaneseDate120Hari = jv120,
            rekomendasiHariBaik = rekomendasi,
            maknaTradisi = "Mapati (Ngupati) dilaksanakan tepat saat janin berusia 4 bulan kehamilan (120 hari). Pada momentum sakral inilah Malaikat meniupkan ruh dan menetapkan empat perkara: rezeki, ajal, jodoh, dan suratan amalnya. Tradisi ini dirayakan dengan doa dan sedekah kupat luwar.",
            uborampe = uborampe,
            doaWejangan = "Mugi jabang bayi pinaringan kasarasen, panjang yuswa, dipungampilaken sedaya rejekinipun dening Gusti Kang Murbeng Dumadi, saha dados putra/putri ingkang bekti marang tiyang sepuh kaliyan agami."
        )
    }

    fun calculateMitoni(hphtDate: LocalDate, ayahBirthDate: LocalDate, ibuBirthDate: LocalDate): MitoniResult {
        // Usia 7 bulan = 210 hari (30 minggu)
        val date210 = hphtDate.plusDays(210)
        val jv210 = JavaneseCalendarEngine.fromLocalDate(date210)
        val wetonAyah = WetonKelahiranEngine.calculate(ayahBirthDate)
        val wetonIbu = WetonKelahiranEngine.calculate(ibuBirthDate)

        // Rekomendasi Mitoni: Menurut adat, dilaksanakan pada hari Selasa Wage atau Sabtu Kliwon,
        // atau hari dengan neptu baik sebelum purnama di rentang 200 - 225 hari
        val rekomendasi = mutableListOf<Pair<LocalDate, JavaneseDate>>()
        for (i in -10..15) {
            val d = date210.plusDays(i.toLong())
            val jv = JavaneseCalendarEngine.fromLocalDate(d)
            // Prioritas Selasa Wage / Sabtu Kliwon atau hari ganjil bulan Jawa sebelum tanggal 16
            val isSelasaWage = (jv.dayOfWeek == 3 && jv.pasaran == Pasaran.WAGE)
            val isSabtuKliwon = (jv.dayOfWeek == 7 && jv.pasaran == Pasaran.KLIWON)
            val isJumatKliwon = (jv.dayOfWeek == 6 && jv.pasaran == Pasaran.KLIWON)
            val isKamisLegi = (jv.dayOfWeek == 5 && jv.pasaran == Pasaran.LEGI)
            val isGanjilJawa = jv.day in listOf(7, 9, 11, 13, 15)

            if ((isSelasaWage || isSabtuKliwon || isJumatKliwon || isKamisLegi) && isGanjilJawa) {
                rekomendasi.add(Pair(d, jv))
                if (rekomendasi.size >= 3) break
            }
        }
        if (rekomendasi.isEmpty()) {
            for (i in -7..14) {
                val d = date210.plusDays(i.toLong())
                val jv = JavaneseCalendarEngine.fromLocalDate(d)
                if (jv.day in listOf(7, 9, 11, 13, 15)) {
                    rekomendasi.add(Pair(d, jv))
                    if (rekomendasi.size >= 3) break
                }
            }
        }
        if (rekomendasi.isEmpty()) {
            rekomendasi.add(Pair(date210, jv210))
        }

        val tataCara = listOf(
            "1. Siraman 7 Sumber Air: Memandikan calon ibu dengan air dari 7 sumber mata air suci yang ditaburi bunga setaman.",
            "2. Upacara Ganti Busana (Kain Jarik 7 Motif): Calon ibu berganti kain sebanyak 7 kali hingga para hadirin menyatakan 'Pantes!' pada corak terakhir (Sido Mukti, Sido Asih, Truntum).",
            "3. Memasukkan Telur & Tropong: Calon ayah meluncurkan telur ayam kampung ke dalam kain calon ibu sebagai simbol doa kelancaran bersalin.",
            "4. Membelah Cengkir Gading: Calon ayah membelah kelapa gading bergambar Raden Kamajaya & Dewi Ratih dengan sekali tebas golok.",
            "5. Jualan Rujak & Dawet: Calon ibu dan ayah berjualan rujak manis menggunakan kreweng (kepingan genteng tanah liat) sebagai lambang doa kelancaran rezeki anak."
        )

        val uborampe = listOf(
            "Cengkir Gading (Kelapa muda kuning berlukis Kamajaya & Ratih)",
            "Bunga Setaman (Mawar, Melati, Kenanga, Kantil)",
            "7 Motif Jarik Tradisional (Sido Luhur, Sido Asih, Sido Mukti, Semen Rama, dll)",
            "Air Suci dari 7 Sumber Mata Air",
            "Kreweng (keping pecahan genteng tanah liat) & Centhong",
            "Rujak Manis 7 Rasa & Dawet Ayu"
        )

        return MitoniResult(
            hphtDate = hphtDate,
            date210Hari = date210,
            javaneseDate210Hari = jv210,
            wetonAyah = wetonAyah,
            wetonIbu = wetonIbu,
            rekomendasiMitoni = rekomendasi,
            tataCaraTradisi = tataCara,
            uborampe = uborampe,
            wejanganLeluhur = "Mitoni (Tingkeban) mujudake donga pangajab supaya jabang bayi lair slamet lan sampurna tanpa rubeda, tansah ayu utawa bagus rupane kadi Kamajaya lan Ratih, sarta luhur bebudene."
        )
    }

    fun calculateHplJawa(hphtDate: LocalDate): HplJawaResult {
        // Rumus Naegele: HPHT + 280 hari (40 minggu)
        val hplDate = hphtDate.plusDays(280)
        val jvHpl = JavaneseCalendarEngine.fromLocalDate(hplDate)
        val wuku = jvHpl.wukuName
        val pranata = jvHpl.pranataMangsa.name
        val neptu = jvHpl.neptuTotal

        val karakterPrediksi = when {
            neptu >= 15 -> "Diprediksi memiliki watak berjiwa pemimpin, visioner, berwibawa besar, dan disegani di lingkungannya."
            neptu in 12..14 -> "Diprediksi memiliki karakter tenang, tekun, cerdas dalam menyelesaikan masalah, dan pembawa rezeki bagi keluarga."
            neptu in 9..11 -> "Diprediksi memiliki sifat penyayang, setia kawan, kreatif, dan pandai beradaptasi dalam pergaulan."
            else -> "Diprediksi bersahaja, tulus hati, sederhana, dan memiliki intuisi spiritual yang tajam."
        }

        return HplJawaResult(
            hphtDate = hphtDate,
            hplDate = hplDate,
            javaneseDateHpl = jvHpl,
            wukuHpl = wuku,
            pranataMangsaHpl = pranata,
            neptuHpl = neptu,
            karakterPrediksi = karakterPrediksi,
            wejanganKelahiran = "Mugi proses babaran lumaku kanthi rancag, rahayu widada ingkang ibu saha jabang bayi, pinaringan wilujeng nir ing sambikala."
        )
    }

    // -------------------------------------------------------------
    // 2. KATEGORI KELAHIRAN & ANAK
    // -------------------------------------------------------------

    fun calculateSepasar(birthDate: LocalDate): SepasarResult {
        val birthWeton = JavaneseCalendarEngine.fromLocalDate(birthDate)
        // Hari ke-5 (sepasar): birthDate + 4 hari
        val sepasarDate = birthDate.plusDays(4)
        val sepasarWeton = JavaneseCalendarEngine.fromLocalDate(sepasarDate)

        val uborampe = listOf(
            "Sego Golong (nasi bulat berlambang kebulatan tekad & persatuan batin)",
            "Jenang Abang Putih (simbol penghormatan asal-usul ayah dan ibu)",
            "Gudangan / Kuluban (aneka sayur rebus berbumbu kelapa urap)",
            "Telur Rebus (lambang wiji dadi / bibit kehidupan yang utuh)",
            "Gereh Pethek & Peyek Kacang"
        )

        return SepasarResult(
            birthDate = birthDate,
            birthWeton = birthWeton,
            sepasarDate = sepasarDate,
            sepasarWeton = sepasarWeton,
            tradisiMakna = "Sepasaran (5 Hari Kelahiran) memperingati lengkapnya satu siklus Pasaran Jawa (Pancawara: Legi, Pahing, Pon, Wage, Kliwon) sejak bayi lahir. Momen ini umumnya bertepatan dengan puputan tali pusar bayi. Tradisi diisi dengan kenduri bancakan sego golong memohon keselamatan dan berkah usia.",
            uborampe = uborampe
        )
    }

    fun calculateSelapanan(birthDate: LocalDate): SelapananResult {
        val birthWeton = JavaneseCalendarEngine.fromLocalDate(birthDate)
        // 35 hari adalah siklus selapan
        val list = mutableListOf<Triple<Int, LocalDate, JavaneseDate>>()
        for (i in 1..6) {
            val d = birthDate.plusDays((i * 35).toLong())
            val jv = JavaneseCalendarEngine.fromLocalDate(d)
            list.add(Triple(i, d, jv))
        }

        val uborampe = listOf(
            "Bancakan Nasi Tumpeng & Gudangan Pedas Manis",
            "Jenang Sengkolo (Jenang Merah & Putih)",
            "Gunting & Mangkuk Air Kembang Setaman (untuk potong rambut bayi pertama kali)",
            "Bancakan Wetonan (dibagikan ke anak-anak kecil tetangga)"
        )

        return SelapananResult(
            birthDate = birthDate,
            birthWeton = birthWeton,
            listSelapanan = list,
            tradisiMakna = "Selapanan (35 Hari Kelahiran) adalah pertemuan kembali antara hari Saptawara (7 hari Masehi) dan Pasaran Jawa (5 hari) tepat di titik weton pertama bayi berulang. Diadakan upacara cukur rambut pertama (paras) dan potong kuku perdana, pembersihan hadas, serta doa syukur agar anak tumbuh suci dan berbudi luhur.",
            uborampe = uborampe
        )
    }

    fun calculateTedhakSiten(birthDate: LocalDate): TedhakSitenResult {
        val birthWeton = JavaneseCalendarEngine.fromLocalDate(birthDate)
        // 7 Selapan = 7 * 35 = 245 hari
        val tedhakSitenDate = birthDate.plusDays(245)
        val tedhakSitenWeton = JavaneseCalendarEngine.fromLocalDate(tedhakSitenDate)

        val tahapan = listOf(
            Pair("1. Menginjak Jadah 7 Warna", "Bayi dibimbing menginjak 7 jadah ketan berwarna (hitam, ungu, biru, hijau, merah, kuning, putih) melambangkan kemampuan menghadapi segala rona dan cobaan hidup."),
            Pair("2. Menaiki Tangga Tebu Arjuna", "Menaiki anak tangga dari tebu wulung/arjuna melambangkan 'anteping kalbu' (tekad mantap) meniti tangga kesuksesan hidup."),
            Pair("3. Memasuki Kurungan Ayam", "Bayi dimasukkan ke dalam kurungan bambu berhias untuk memilih benda kesukaannya (buku, uang, cermin, pulpen, cangkul kecil) sebagai isyarat minat dan bakat masa depannya."),
            Pair("4. Siraman Air Bunga Setaman", "Memandikan bayi dengan air suci beraroma wangi kembang setaman agar senantiasa harum nama baik dan perilakunya."),
            Pair("5. Udik-Udik (Sebar Beras Kuning & Koin)", "Keluarga menebarkan beras kuning dan uang koin kepada anak-anak kecil sebagai wujud latihan kedermawanan dan sedekah.")
        )

        val uborampe = listOf(
            "Jadah 7 Warna (Ketan 7 warna)",
            "Tangga Tebu Arjuna (Tebu Wulung)",
            "Kurungan Ayam yang dihias janur kuning dan bunga",
            "Benda-benda simbolis (Buku, Pena, Uang, Stetoskop mainan, Al-Quran, dll)",
            "Air Suci Kembang Setaman & Gayung Batok Kelapa",
            "Udik-udik (Beras Kuning, Uang Logam, Bunga Melati)"
        )

        return TedhakSitenResult(
            birthDate = birthDate,
            birthWeton = birthWeton,
            tedhakSitenDate = tedhakSitenDate,
            tedhakSitenWeton = tedhakSitenWeton,
            tahapanProsesi = tahapan,
            uborampe = uborampe,
            filosofiLuhur = "Tedhak Siten asale saka tembung 'tedhak' (turun/ngidak) lan 'siten' (siti/lemah). Upacara iki minangka pralambang rasa sukur marang Gusti dene si jabang bayi wus wiwit saged ngambah bantala (lemah bumi) kanthi sukma lan raga kang sentosa."
        )
    }

    fun getMendhemAriAriGuide(genderIsBoy: Boolean, birthDate: LocalDate): MendhemAriAriResult {
        val jv = JavaneseCalendarEngine.fromLocalDate(birthDate)
        val lokasi = if (genderIsBoy) {
            "Sebelah KANAN pintu utama rumah (dilihat dari arah dalam rumah menghadap ke luar halaman). Melambangkan laki-laki sebagai pilar pelindung dan tangan kanan keluarga."
        } else {
            "Sebelah KIRI pintu utama rumah (dilihat dari arah dalam rumah menghadap ke luar halaman). Melambangkan perempuan sebagai penjaga ketenteraman, kehangatan, dan kelembutan rumah tangga."
        }

        val uborampe = listOf(
            "Kendhi tanah liat kecil berpenutup (wadah ari-ari suci)",
            "Alas daun talas muda / daun pisang raja",
            "Garam krosok & asam jawa (untuk membersihkan dan mengawetkan secara alami)",
            "Jarum jahit dan benang lawe putih (simbol kecermatan dan kebersihan hati)",
            "Kunyit segar (antiseptik alami dan simbol cahaya)",
            "Kertas bertuliskan Aksara Jawa (Ha-Na-Ca-Ra-Ka) / Doa Arab Pegon",
            "Kembang boreh & wewangian melati",
            "Lampu sentir / penerang (lampu minyak atau lampu listrik kecil) selama 35 hari"
        )

        val tataCara = listOf(
            "1. Pencucian: Ari-ari dicuci bersih menggunakan air mengalir dan garam krosok hingga tuntas.",
            "2. Pembungkusan: Diletakkan ke dalam kendhi tanah liat yang dialasi daun talas, lalu dimasukkan jarum, benang lawe, kunyit, tulisan doa, dan taburan kembang boreh.",
            "3. Penutupan: Tutup kendhi rapat-rapat dan dibungkus kain mori putih bersih.",
            "4. Penggalian: Buat lubang tanah sedalam sekitar 50-70 cm di lokasi yang ditentukan (kanan/kiri pintu utama).",
            "5. Penguburan: Calon ayah (ayah kandung) menguburkan kendhi dengan tenang seraya melafalkan doa keselamatan.",
            "6. Pemberian Perlindungan: Tutup atasnya dengan kurungan bambu atau batu pipih agar terhindar dari gangguan binatang.",
            "7. Lampu Penerang (Sentir): Dinyalakan lampu kecil di atas makam ari-ari setiap malam selama selapanan (35 hari) sebagai lambang doa agar jalan hidup anak senantiasa terang benderang."
        )

        return MendhemAriAriResult(
            genderIsBoy = genderIsBoy,
            birthDate = birthDate,
            birthWeton = jv,
            lokasiPenguburan = lokasi,
            uborampe = uborampe,
            tataCara = tataCara,
            wejanganLeluhur = "Ari-ari dipunanggep minangka 'Kakang Kawah Adhi Ari-Ari' (sedulur papat lima pancer) ingkang ngancani jabang bayi wiwit ing guwa garba. Mula dipunrumat kanthi sae minangka wujud kurmat marang peparinging Gusti."
        )
    }

    fun calculateWatakBayi(birthDate: LocalDate, jamLahirKategori: String): WatakBayiResult {
        val jv = JavaneseCalendarEngine.fromLocalDate(birthDate)
        val weton = WetonKelahiranEngine.calculate(birthDate)
        val neptu = jv.neptuTotal

        val watakJam = when (jamLahirKategori) {
            "Pagi (06.00 - 11.00)" -> "Lahir saat fajar terbit hingga pagi hari: Berjiwa segar, penuh inisiatif, optimis tinggi, pembawa aura cerah, dan cepat menyerap ilmu pengetahuan baru."
            "Siang (11.00 - 15.00)" -> "Lahir saat matahari di puncak: Memiliki keberanian luar biasa, berjiwa kompetitif, pantang menyerah, lugas berbicara, dan berbakat menjadi pemimpin tegas."
            "Sore (15.00 - 18.00)" -> "Lahir saat surya bergeser teduh: Berhati penyabar, diplomatis, pendamai, memiliki kepekaan rasa dan estetika tinggi, serta disukai oleh banyak kalangan."
            else -> "Lahir saat malam hari (18.00 - 06.00): Memiliki ketajaman intuisi spiritual, pemikir mendalam (filosofis), tenang menghadapi krisis, mandiri, dan setia pada komitmen hidup."
        }

        val potensiKarier = when {
            neptu in listOf(10, 14, 18) -> "Sangat cocok memimpin perusahaan, perwira, birokrat pemerintah, hakim, atau tokoh masyarakat berwibawa."
            neptu in listOf(8, 12, 16) -> "Cocok dalam bidang wirausaha mandiri, arsitektur, teknologi, perdagangan besar, atau konsultan profesional."
            neptu in listOf(7, 11, 15) -> "Bakat cemerlang di bidang seni kreatif, diplomasi, hukum, diplomasi budaya, atau akademisi ilmuwan."
            else -> "Unggul di bidang komunikasi, pelayanan publik, medis/kesehatan, pendidikan, dan sastra."
        }

        return WatakBayiResult(
            birthDate = birthDate,
            birthWeton = jv,
            jamLahirKategori = jamLahirKategori,
            neptuTotal = neptu,
            watakNeptu = "${weton.watakLakuning}: ${weton.watakDeskripsiId}",
            watakWuku = "Bernaung di bawah Wuku ${jv.wukuName}: Memiliki daya tahan mental yang ulet dan berkah kecukupan bila dididik dengan keteladanan moral.",
            watakJamLahir = watakJam,
            potensiKarier = potensiKarier,
            arahanPendidikan = "Didiklah dengan kelembutan tutur kata serta teladan laku nyata. Kuatkan spiritualitas dan kejujuran sedari dini agar energi neptunya menjadi berkah agung."
        )
    }

    fun recommendNamaJawa(gender: String, category: String, query: String): List<NamaJawaItem> {
        val masterList = listOf(
            NamaJawaItem("Danendra", "ꦢꦤꦺꦤ꧀ꦢꦿ", "Raja yang kaya raya dan dermawan", "Laki-laki", 8, "Kejayaan & Rezeki"),
            NamaJawaItem("Raditya", "ꦫꦢꦶꦠꦾ", "Matahari yang menerangi alam semesta", "Laki-laki", 7, "Cerdas & Bijak"),
            NamaJawaItem("Bimantara", "ꦧꦶꦩꦤ꧀ꦠꦫ", "Jiwa yang gagah perkasa dan berwawasan luas", "Laki-laki", 9, "Keteguhan"),
            NamaJawaItem("Pradana", "ꦥꦿꦢꦤ", "Paling utama, dermawan, dan berbudi luhur", "Laki-laki", 6, "Budi Luhur"),
            NamaJawaItem("Aryaguna", "ꦄꦂꦪꦒꦸꦤ", "Bangsawan yang penuh kebajikan", "Laki-laki", 8, "Budi Luhur"),
            NamaJawaItem("Mahardika", "ꦩꦲꦂꦢꦶꦏ", "Berilmu tinggi, bijaksana, dan berbudi mulia", "Laki-laki", 10, "Cerdas & Bijak"),
            NamaJawaItem("Adiwilaga", "ꦲꦢꦶꦮꦶꦭꦒ", "Ksatria unggul yang tangguh di medan laga", "Laki-laki", 7, "Keteguhan"),
            NamaJawaItem("Haryoseno", "ꦲꦂꦪꦱꦺꦤ", "Prajurit utama pembela kebenaran", "Laki-laki", 8, "Keteguhan"),
            NamaJawaItem("Baskara", "ꦧꦱ꧀ꦏꦫ", "Cahaya penerang yang memberi kehangatan", "Laki-laki", 6, "Ketenteraman"),
            NamaJawaItem("Cakrawala", "ꦕꦏꦿꦮꦭ", "Bintang penunjuk arah, berpandangan luas", "Laki-laki", 8, "Cerdas & Bijak"),

            NamaJawaItem("Laksita", "ꦭꦏ꧀ꦱꦶꦠ", "Anak yang cekatan, tangkas, dan bertabur kemuliaan", "Perempuan", 7, "Cerdas & Bijak"),
            NamaJawaItem("Pramudita", "ꦥꦿꦩꦸꦢꦶꦠ", "Orang yang cerdas pandai dan berbudi luhur", "Perempuan", 8, "Cerdas & Bijak"),
            NamaJawaItem("Nareswari", "ꦤꦫꦺꦯ꧀ꦮꦫꦶ", "Permaisuri yang agung, anggun, dan bijaksana", "Perempuan", 9, "Kejayaan & Rezeki"),
            NamaJawaItem("Ayuningtyas", "ꦲꦪꦸꦤꦶꦁꦠꦾꦱ꧀", "Wanita berparas jelita dan berhati suci murni", "Perempuan", 7, "Budi Luhur"),
            NamaJawaItem("Sekar Ayu", "ꦱꦼꦏꦂꦲꦪꦸ", "Bunga yang harum semerbak menebar kedamaian", "Perempuan", 6, "Ketenteraman"),
            NamaJawaItem("Widyaningrum", "ꦮꦶꦢꦾꦤꦶꦁꦫꦸꦩ꧀", "Wanita berilmu pengetahuan tinggi dan berkharisma", "Perempuan", 9, "Cerdas & Bijak"),
            NamaJawaItem("Kirana", "ꦏꦶꦫꦤ", "Sinar cahaya terang yang indah mempesona", "Perempuan", 5, "Ketenteraman"),
            NamaJawaItem("Mustika", "ꦩꦸꦱ꧀ꦠꦶꦏ", "Permata mulia yang sangat berharga", "Perempuan", 7, "Kejayaan & Rezeki"),
            NamaJawaItem("Endah", "ꦲꦺꦤ꧀ꦢꦃ", "Kelemahlembutan budi pekerti yang indah memikat", "Perempuan", 4, "Ketenteraman"),
            NamaJawaItem("Ratnaningsih", "ꦫꦠꦤꦶꦁꦱꦶꦃ", "Permata cinta kasih yang tulus tiada tara", "Perempuan", 8, "Budi Luhur"),

            NamaJawaItem("Arunika", "ꦲꦫꦸꦤꦶꦏ", "Sinar mentari pagi penyejuk jiwa", "Netral", 6, "Ketenteraman"),
            NamaJawaItem("Nirwasita", "ꦤꦶꦂꦮꦱꦶꦠ", "Kearifan luhur yang mendatangkan kedamaian", "Netral", 8, "Cerdas & Bijak"),
            NamaJawaItem("Wicaksana", "ꦮꦶꦕꦏ꧀ꦱꦤ", "Pribadi yang adil, bijaksana, dan santun", "Netral", 8, "Budi Luhur"),
            NamaJawaItem("Kusuma", "ꦏꦸꦱꦸꦩ", "Pribadi yang luhur harum laksana bunga teratai", "Netral", 6, "Budi Luhur"),
            NamaJawaItem("Pinandita", "ꦥꦶꦤꦤ꧀ꦢꦶꦠ", "Pribadi yang teguh dalam kesalehan dan ilmu", "Netral", 9, "Cerdas & Bijak"),
            NamaJawaItem("Rahardja", "ꦫꦲꦂꦗ", "Keselamatan, kemakmuran, dan kesejahteraan hidup", "Netral", 7, "Kejayaan & Rezeki")
        )

        return masterList.filter { item ->
            val matchGender = gender == "Semua" || item.jenisKelamin == gender || item.jenisKelamin == "Netral"
            val matchCategory = category == "Semua" || item.kategori == category
            val matchQuery = query.isBlank() || item.nama.contains(query, ignoreCase = true) || item.arti.contains(query, ignoreCase = true)
            matchGender && matchCategory && matchQuery
        }
    }

    // -------------------------------------------------------------
    // 3. KATEGORI PERJODOHAN & PERNIKAHAN
    // -------------------------------------------------------------

    fun calculateKecocokanJodoh(priaBirthDate: LocalDate, wanitaBirthDate: LocalDate): JodohDetailedResult {
        val wetonPria = WetonKelahiranEngine.calculate(priaBirthDate)
        val wetonWanita = WetonKelahiranEngine.calculate(wanitaBirthDate)
        val total = wetonPria.neptuTotal + wetonWanita.neptuTotal

        // Siklus 8
        val sisa8 = (total % 8).let { if (it == 0) 8 else it }
        val (hasil8, makna8, nasehat8, skorDasar) = when (sisa8) {
            1 -> listOf(
                "Pegat",
                "Berpotensi menghadapi ujian perbedaan sudut pandang atau ekonomi di awal pernikahan.",
                "Kunci sukses: Perbanyak komunikasi terbuka, saling memaafkan, dan kuatkan manajemen finansial keluarga.",
                "70"
            )
            2 -> listOf(
                "Ratu",
                "Pasangan harmonis berwibawa tinggi, disegani sanak saudara, serta disayangi tetangga.",
                "Kunci sukses: Pertahankan kerendahan hati dan teruslah menjadi teladan kebaikan bersama sesama.",
                "95"
            )
            3 -> listOf(
                "Jodoh",
                "Jodoh Sejati! Saling melengkapi kelebihan dan kekurangan, adem ayem rukun hingga hari tua.",
                "Kunci sukses: Rawat terus rasa cinta, syukur, dan kebiasaan berbincang mesra setiap malam.",
                "98"
            )
            4 -> listOf(
                "Topo",
                "Di awal merintis mengalami laku prihatin, namun di masa depan memetik kejayaan agung.",
                "Kunci sukses: Tetap kompak dan saling menguatkan saat berjuang merintis fondasi rumah tangga.",
                "85"
            )
            5 -> listOf(
                "Tinari",
                "Murah rezeki dan gampang sandang pangan! Selalu menemukan pintu kemudahan rezeki.",
                "Kunci sukses: Gemar berderma dan bersedekah agar berkah kemakmuran semakin melimpah ruah.",
                "92"
            )
            6 -> listOf(
                "Padu",
                "Sering mengalami perdebatan kecil, namun cinta dan komitmen pernikahan tetap utuh kokoh.",
                "Kunci sukses: Kendalikan ego, hindari menaikkan nada bicara saat emosi, dan selesaikan masalah hari itu juga.",
                "75"
            )
            7 -> listOf(
                "Sujanan",
                "Rentan ujian rasa cemburu atau godaan pihak luar. Fondasi utamanya adalah saling terbuka.",
                "Kunci sukses: Terapkan transparansi tanpa rahasia, jaga komitmen pandangan mata dan hati.",
                "72"
            )
            else -> listOf(
                "Pesthi",
                "Tentrem, damai sejahtera, rukun sentosa tanpa rintangan besar yang mengusik rumah tangga.",
                "Kunci sukses: Ciptakan kehangatan rumah, rajin beribadah bersama, dan nikmati berkah kebersamaan.",
                "96"
            )
        }

        // Siklus 7 (Pancasuda Jodoh)
        val sisa7 = (total % 7).let { if (it == 0) 7 else it }
        val (hasil7, makna7) = when (sisa7) {
            1 -> Pair("Wasesa Segara", "Berjiwa pemaaf, berhati luas seluas samudra, dan rezeki mengalir tiada putus.")
            2 -> Pair("Tunggak Semi", "Rezekinya selalu bertunas kembali, sempat surut lekas berganti limpahan baru.")
            3 -> Pair("Satria Wibawa", "Mendapatkan kemuliaan, kehormatan, dan derajat tinggi di mata masyarakat.")
            4 -> Pair("Sumur Sinaba", "Menjadi tempat rujukan ilmu dan sumber nasihat bijak bagi sanak famili.")
            5 -> Pair("Bumi Kapetak", "Tahan uji banting, pekerja keras luar biasa, berhasil melalui ketekunan.")
            6 -> Pair("Satria Wirang", "Melewati ujian kesabaran dan fitnah, berujung pada derajat kemuliaan sejati.")
            else -> Pair("Lebu Katiyup Angin", "Dinamis, sering berpindah, sukses gemilang bila memiliki tujuan hidup yang mantap.")
        }

        // Siklus 4
        val sisa4 = (total % 4).let { if (it == 0) 4 else it }
        val (hasil4, makna4) = when (sisa4) {
            1 -> Pair("Gondho", "Disukai banyak orang, wangi nama baik keluarganya, murah pergaulan.")
            2 -> Pair("Kedhana", "Dikaruniai kemakmuran materi dan keturunan yang cerdas berbakti.")
            3 -> Pair("Kusuma", "Menjadi teladan kebaikan laksana bunga mekar yang mengharumkan semesta.")
            else -> Pair("Raras", "Tentram batinnya, tenang jiwanya, terhindar dari marabahaya besar.")
        }

        val solusi = if (sisa8 in listOf(1, 6, 7)) {
            "Ikhtiar Penangkal (Ruwatan & Sedekah): Adakan syukuran jenang abang putih, bersedekah kepada anak yatim piatu, memilih hari ijab kabul di hari yang memiliki neptu paling kuat (misal Jumat Kliwon / Sabtu Pon), serta menyelaraskan niat lillahita'ala."
        } else {
            "Keselarasan neptu pasangan ini tergolong sangat harmonis. Cukup diimbangi dengan doa selamat bersama keluarga besar dan menjaga komitmen saling memuliakan pasangan."
        }

        return JodohDetailedResult(
            wetonPria = wetonPria,
            wetonWanita = wetonWanita,
            totalNeptu = total,
            hasilSiklus8 = hasil8,
            maknaSiklus8 = makna8,
            nasehatSiklus8 = nasehat8,
            hasilSiklus7 = hasil7,
            maknaSiklus7 = makna7,
            hasilSiklus4 = hasil4,
            maknaSiklus4 = makna4,
            tingkatKeserasian = skorDasar.toIntOrNull() ?: 80,
            solusiPenangkal = solusi
        )
    }

    fun calculateHariBaikNikah(
        priaBirthDate: LocalDate,
        wanitaBirthDate: LocalDate,
        targetYearMonth: Pair<Int, Int> // Year, Month
    ): HariBaikNikahResult {
        val wetonPria = WetonKelahiranEngine.calculate(priaBirthDate)
        val wetonWanita = WetonKelahiranEngine.calculate(wanitaBirthDate)

        val year = targetYearMonth.first
        val month = targetYearMonth.second
        val firstDay = LocalDate.of(year, month, 1)
        val daysInMonth = firstDay.lengthOfMonth()

        val listRekomendasi = mutableListOf<RekomendasiNikahItem>()

        for (day in 1..daysInMonth) {
            val d = LocalDate.of(year, month, day)
            val jv = JavaneseCalendarEngine.fromLocalDate(d)

            // Filter hari pantangan (Dina Tali Bangke, Sampar Wangke, hari naas)
            // Misalnya hindari hari dengan neptu 6 (Selasa Wage) atau hari bentrok
            val neptuHari = jv.neptuTotal
            val gabunganDenganPengantin = (neptuHari + wetonPria.neptuTotal + wetonWanita.neptuTotal) % 5

            val isGoodDina = jv.dayOfWeek in listOf(1, 4, 5, 6, 7) // Ahad, Rabu, Kamis, Jumat, Sabtu
            val isGoodPasaran = jv.pasaran in listOf(Pasaran.LEGI, Pasaran.PAHING, Pasaran.PON, Pasaran.KLIWON)

            if (isGoodDina && isGoodPasaran) {
                val (status, bintang, ket) = when (gabunganDenganPengantin) {
                    1 -> Triple("Sri (Keberkahan & Kemuliaan)", 5, "Sangat mustajab untuk Ijab Kabul & Resepsi! Melambangkan sandang pangan melimpah dan aura pengantin memesona.")
                    2 -> Triple("Rejeki (Kesejahteraan Finansial)", 5, "Membuka pintu-pintu rezeki berlipat ganda, kehidupan rumah tangga berkecukupan dan sejahtera.")
                    3 -> Triple("Gedhong (Kemapanan Rumah Tangga)", 4, "Membawa kemantapan memiliki tempat tinggal mandiri dan investasi masa depan yang kokoh.")
                    else -> Triple("Ayem Tentrem (Ketenteraman Hati)", 4, "Menyejukkan suasana batin kedua keluarga besar, rukun sentosa.")
                }
                listRekomendasi.add(
                    RekomendasiNikahItem(
                        date = d,
                        javaneseDate = jv,
                        bintang = bintang,
                        statusHari = status,
                        keterangan = ket
                    )
                )
            }
        }

        val pantangan = listOf(
            "Dina Tali Bangke & Sampar Wangke (pantangan utama pesta hajatan)",
            "Dina Sangar & Dhendhan Kukuda (hari rawan perselisihan energi)",
            "Dina Geblag (Hari wafatnya orang tua / kakek nenek mempelai)",
            "Bulan Sura (sebagian tradisi memprioritaskan bulan berkah seperti Ruwah, Sawal, Dzulkaidah, atau Besar)"
        )

        val wejangan = "Pernikahan ingkang prayogi inggih menika ingkang dipunwiwiti kanthi donga sukur, milih dinten ingkang luhur neptunipun, sarta dipunrestoni dening tiyang sepuh kekalih supados pinaringan berkah sakinah mawaddah warahmah."

        return HariBaikNikahResult(
            wetonPria = wetonPria,
            wetonWanita = wetonWanita,
            bulanTarget = "${firstDay.month.name} $year",
            daftarRekomendasi = listRekomendasi.take(6),
            pantanganHari = pantangan,
            wejanganNikah = wejangan
        )
    }

    fun calculateArahRezekiSuamiIstri(priaBirthDate: LocalDate, wanitaBirthDate: LocalDate): ArahRezekiResult {
        val wetonPria = WetonKelahiranEngine.calculate(priaBirthDate)
        val wetonWanita = WetonKelahiranEngine.calculate(wanitaBirthDate)
        val gabungan = wetonPria.neptuTotal + wetonWanita.neptuTotal

        val sisa4 = gabungan % 4
        val (arahRezeki, arahPintu, kotaHoki) = when (sisa4) {
            1 -> Triple(
                "TIMUR (Wetan) & UTARA (Lor)",
                "Menghadap ke TIMUR atau UTARA",
                "Kota / wilayah di sebelah Timur atau Utara dari kampung halaman leluhur"
            )
            2 -> Triple(
                "SELATAN (Kidul) & TIMUR (Wetan)",
                "Menghadap ke SELATAN atau TIMUR",
                "Kota / daerah berkembang yang menghampar di sisi Selatan atau Timur"
            )
            3 -> Triple(
                "BARAT (Kulon) & SELATAN (Kidul)",
                "Menghadap ke BARAT atau SELATAN",
                "Pusat perdagangan kota atau wilayah di sisi Barat atau Selatan"
            )
            else -> Triple(
                "UTARA (Lor) & BARAT (Kulon)",
                "Menghadap ke UTARA atau BARAT",
                "Kawasan niaga dan pusat pertumbuhan di sisi Utara atau Barat"
            )
        }

        return ArahRezekiResult(
            wetonSuami = wetonPria,
            wetonIstri = wetonWanita,
            gabunganNeptu = gabungan,
            arahUtamaRezeki = arahRezeki,
            arahPintuRumah = arahPintu,
            kotaWilayahHoki = kotaHoki,
            wejanganIkhtiar = "Arah rejeki mujudake pituduh lairiyah saking petungan Primbon Jawa. Ingkang baku, tansah mempeng makarya kanthi jujur, guyub rukun kekalih bojo, lan mboten kendhat nyuwun berkahing Gusti Kang Maha Suci."
        )
    }

    // -------------------------------------------------------------
    // 4. KATEGORI KEMATIAN & HAJAT BESAR
    // -------------------------------------------------------------

    fun calculateSlametanKematian(tanggalMeninggal: LocalDate, waktuMeninggalSetelahMaghrib: Boolean): SlametanKematianResult {
        // Dalam kalender Jawa, pergantian hari terjadi saat Maghrib (surup).
        val effectiveGeblag = if (waktuMeninggalSetelahMaghrib) {
            tanggalMeninggal.plusDays(1)
        } else {
            tanggalMeninggal
        }

        val jvGeblag = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag)

        // Rumus Slametan Kematian Tradisi Jawa:
        // 1. Geblag: Hari H (0 hari)
        // 2. Nelung Dina (3 Hari): Hari ke-3 (+2 hari dari geblag)
        // 3. Mitung Dina (7 Hari): Hari ke-7 (+6 hari dari geblag)
        // 4. Matang Puluh Dina (40 Hari): Hari ke-40 (+39 hari dari geblag)
        // 5. Nyatus Dina (100 Hari): Hari ke-100 (+99 hari dari geblag)
        // 6. Mendhak Pisan (1 Tahun Jawa): 354 hari (+353 hari dari geblag)
        // 7. Mendhak Pindo (2 Tahun Jawa): 708 hari (+707 hari dari geblag)
        // 8. Nyewu (1000 Hari): Hari ke-1000 (+999 hari dari geblag)

        val tahapanList = listOf(
            SlametanKematianItem(
                tahapan = "Geblag (Hari H Wafat)",
                sebutanJawa = "Dina Geblag",
                date = effectiveGeblag,
                javaneseDate = jvGeblag,
                maknaSpiritual = "Peringatan hari berpulangnya almarhum/almarhumah ke Rahmatullah. Dilaksanakan takziah, pembacaan surat Yasin, tahlil, dan doa fidyah keselamatan arwah."
            ),
            SlametanKematianItem(
                tahapan = "Nelung Dina (3 Hari)",
                sebutanJawa = "Pèngetan Nelung Dina",
                date = effectiveGeblag.plusDays(2),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(2)),
                maknaSpiritual = "Peringatan hari ke-3. Simbol doa penyempurnaan ruh yang mulai meninggalkan alam dunia fana menuju alam barzakh."
            ),
            SlametanKematianItem(
                tahapan = "Mitung Dina (7 Hari)",
                sebutanJawa = "Pèngetan Mitung Dina",
                date = effectiveGeblag.plusDays(6),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(6)),
                maknaSpiritual = "Peringatan hari ke-7. Berakhirnya masa berkabung utama, diisi tahlilan akbar dan sedekah makanan kepada sanak tetangga."
            ),
            SlametanKematianItem(
                tahapan = "Matang Puluh Dina (40 Hari)",
                sebutanJawa = "Pèngetan Matang Puluh Dina",
                date = effectiveGeblag.plusDays(39),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(39)),
                maknaSpiritual = "Peringatan hari ke-40. Menurut kearifan Jawa, jasad dalam makam telah lebur menyatu dengan bumi, ruh bersiap menempati maqom di alam kubur."
            ),
            SlametanKematianItem(
                tahapan = "Nyatus Dina (100 Hari)",
                sebutanJawa = "Pèngetan Nyatus Dina",
                date = effectiveGeblag.plusDays(99),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(99)),
                maknaSpiritual = "Peringatan hari ke-100. Simbol hancurnya tulang belulang lahiriah, keluarga memanjatkan doa ampunan atas segala dosa almarhum/almarhumah."
            ),
            SlametanKematianItem(
                tahapan = "Mendhak Pisan (1 Tahun)",
                sebutanJawa = "Pèngetan Mendhak Sepisan (1 Tahun Jawa)",
                date = effectiveGeblag.plusDays(353),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(353)),
                maknaSpiritual = "Peringatan haul pertama (1 tahun penanggalan Jawa = 354 hari). Upacara ziarah kubur (nyekar) dan kirim doa kubur."
            ),
            SlametanKematianItem(
                tahapan = "Mendhak Pindo (2 Tahun)",
                sebutanJawa = "Pèngetan Mendhak Pindo (2 Tahun Jawa)",
                date = effectiveGeblag.plusDays(707),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(707)),
                maknaSpiritual = "Peringatan haul kedua (2 tahun penanggalan Jawa = 708 hari). Doa agar amal jariyah almarhum senantiasa menerangi alam kubur."
            ),
            SlametanKematianItem(
                tahapan = "Nyewu (1000 Hari)",
                sebutanJawa = "Pèngetan Nyewu Dina (Sampurnaning Pati)",
                date = effectiveGeblag.plusDays(999),
                javaneseDate = JavaneseCalendarEngine.fromLocalDate(effectiveGeblag.plusDays(999)),
                maknaSpiritual = "Peringatan 1000 hari sebagai puncak penyempurnaan kematian (Sampurnaning Pati). Dilakukan peletakan kijing/batu nisan permanen dan sedekah besar keluarga."
            )
        )

        return SlametanKematianResult(
            tanggalMeninggal = tanggalMeninggal,
            waktuMeninggalSetelahMaghrib = waktuMeninggalSetelahMaghrib,
            geblagDate = effectiveGeblag,
            geblagWeton = jvGeblag,
            listTahapan = tahapanList,
            wejanganDoa = "Slametan pèngetan tiyang seda mujudake bektine kulawarga ingkang taksih gesang kanthi kirim donga tahlil, sedekah, lan nyuwunaken pangapunten sedaya lepatipun almarhum/almarhumah marang Gusti Kang Maha Asih."
        )
    }

    fun calculatePindahRumah(kepalaKeluargaBirthDate: LocalDate, arahTujuan: String, targetDate: LocalDate): PindahRumahResult {
        val wetonKepala = WetonKelahiranEngine.calculate(kepalaKeluargaBirthDate)
        val jvTarget = JavaneseCalendarEngine.fromLocalDate(targetDate)

        // Perhitungan Siklus 4 Boyongan: Guru, Ratu, Rogoh, Sempati
        val totalNeptu = wetonKepala.neptuTotal + jvTarget.neptuTotal
        val sisa4 = (totalNeptu % 4).let { if (it == 0) 4 else it }

        val (status, skor, wejangan) = when (sisa4) {
            1 -> Triple(
                "Guru (Sangat Baik & Dihormati)",
                95,
                "Rumah baru akan menjadi pusat ketenteraman, dihormati sanak saudara, serta membawa ilmu kebajikan bagi keluarga."
            )
            2 -> Triple(
                "Ratu (Wibawa & Keberkahan)",
                92,
                "Rumah tangga dipenuhi kewibawaan agung, rezeki lancar berkecukupan, dan disegani tetangga sekitar."
            )
            3 -> Triple(
                "Rogoh (Sederhana & Waspada)",
                75,
                "Perlu meningkatkan ketelitian penjagaan rumah, kuatkan sedekah keselamatan dan silaturahmi tetangga."
            )
            else -> Triple(
                "Sempati (Dinamis & Mandiri)",
                85,
                "Penghuni rumah akan aktif bepergian mencari kemajuan karier, membawa kemandirian hidup yang pesat."
            )
        }

        val tataCara = listOf(
            "1. Memboyong Beras & Air Pertama: Saat pertama kali melangkah masuk rumah baru, bawa kendhi air suci dan tempat beras yang terisi penuh sebagai doa kemakmuran.",
            "2. Menyalakan Lampu Utama: Nyalakan seluruh penerang rumah selama malam pertama boyongan.",
            "3. Membawa Bantal & Tikar: Masukkan perlengkapan tidur sebagai simbol ketenangan batin.",
            "4. Selamatan Jenang Abang Putih: Bagikan jenang merah putih dan tumpeng robjong kepada tetangga baru sebagai bentuk kulonuwun (izin bermasyarakat)."
        )

        val uborampe = listOf(
            "Kendhi berisi air sumur / air bersih (simbol sumber kehidupan)",
            "Cething beras penuh beserta bumbu dapur lengkap",
            "Lampu sentir / penerang",
            "Bunga setaman & sapu lidi baru",
            "Tumpeng slametan & jenang abang putih"
        )

        return PindahRumahResult(
            wetonKepalaKeluarga = wetonKepala,
            arahTujuan = arahTujuan,
            tanggalPilihan = targetDate,
            wetonTanggalPilihan = jvTarget,
            statusPancasuda = status,
            kecocokanSkor = skor,
            tataCaraBoyongan = tataCara,
            uborampe = uborampe,
            wejanganRumah = wejangan
        )
    }

    fun calculateBukaUsaha(pemilikBirthDate: LocalDate, bidangUsaha: String, targetDate: LocalDate): BukaUsahaResult {
        val wetonPemilik = WetonKelahiranEngine.calculate(pemilikBirthDate)
        val jvTarget = JavaneseCalendarEngine.fromLocalDate(targetDate)

        // Siklus 5 Usaha: Sri (1), Rejeki (2), Gedhong (3), Loro (4), Pati (5)
        val totalNeptu = wetonPemilik.neptuTotal + jvTarget.neptuTotal
        val sisa5 = (totalNeptu % 5).let { if (it == 0) 5 else it }

        val (status, skor, wejangan) = when (sisa5) {
            1 -> Triple(
                "Sri (Kemakmuran & Daya Pikat Pelanggan)",
                98,
                "Tanggal luar biasa! Usaha baru dinaungi daya tarik memesona, pelanggan berdatangan, dan rezeki melimpah berkah."
            )
            2 -> Triple(
                "Rejeki (Laba Melimpah & Arus Kas Lancar)",
                95,
                "Sangat menguntungkan! Aliran modal dan perputaran keuntungan bergerak cepat dan membawa kemudahan ekspansi."
            )
            3 -> Triple(
                "Gedhong (Kekayaan Bertahan & Aset Kuat)",
                90,
                "Bagus untuk jangka panjang! Usaha kokoh bertahan, mampu membeli aset ruko/tempat sendiri dan dipercaya mitra."
            )
            4 -> Triple(
                "Loro (Perlu Ketekunan Ekstra)",
                70,
                "Disarankan menambah promosi dan riset pasar mendalam agar tidak menghadapi kelelahan operasional di awal."
            )
            else -> Triple(
                "Pati (Sebaiknya Pilih Hari Lain)",
                60,
                "Dianjurkan menggeser tanggal pembukaan 1-2 hari ke depan mencari hari yang jatuh pada status Sri atau Rejeki."
            )
        }

        val arahHoki = when (wetonPemilik.pasaran) {
            Pasaran.LEGI -> "Arah TIMUR atau UTARA membawa magnet pembeli terbesar."
            Pasaran.PAHING -> "Arah SELATAN atau TIMUR membawa kelancaran transaksi niaga."
            Pasaran.PON -> "Arah BARAT atau SELATAN melipatgandakan peluang laba usaha."
            Pasaran.WAGE -> "Arah UTARA atau BARAT menghadirkan ketenangan dan ketahanan modal."
            Pasaran.KLIWON -> "Segala penjuru angin terbuka, terutama menghadap TIMUR LAUT."
        }

        return BukaUsahaResult(
            wetonPemilik = wetonPemilik,
            bidangUsaha = bidangUsaha,
            tanggalPilihan = targetDate,
            wetonTanggalPilihan = jvTarget,
            statusPancaSiklus = status,
            skorHoki = skor,
            arahHokiTempatUsaha = arahHoki,
            wejanganUsaha = wejangan
        )
    }

    fun calculateNagaDina(targetDate: LocalDate): NagaDinaResult {
        val jv = JavaneseCalendarEngine.fromLocalDate(targetDate)
        val dayOfWeek = jv.dayOfWeek // 1 = Minggu .. 7 = Sabtu

        // Petungan Naga Dina Klasik:
        // Menentukan letak kepala naga (mulut = arah bahaya/pantangan)
        // dan punggung/ekor naga (arah keselamatan/rezeki)
        val (arahKepala, arahPunggung, wejangan) = when (dayOfWeek) {
            1 -> Triple(
                "TIMUR (Wetan)",
                "BARAT (Kulon) & UTARA (Lor)",
                "Pada hari Minggu (Radite), kepala Naga Dina berada di Timur. Hindari bepergian lurus menuju arah Timur. Bila hendak berniaga atau melamar pekerjaan, tujulah arah Barat atau Utara."
            )
            2 -> Triple(
                "SELATAN (Kidul)",
                "UTARA (Lor) & TIMUR (Wetan)",
                "Pada hari Senin (Soma), kepala Naga Dina berada di Selatan. Hindari memulai perjalanan menuju arah Selatan. Keselamatan dan kelancaran berada di arah Utara dan Timur."
            )
            3 -> Triple(
                "BARAT (Kulon)",
                "TIMUR (Wetan) & SELATAN (Kidul)",
                "Pada hari Selasa (Anggara), kepala Naga Dina berada di Barat. Hindari menuju Barat. Arah terbaik dan aman adalah Timur serta Selatan."
            )
            4 -> Triple(
                "UTARA (Lor)",
                "SELATAN (Kidul) & BARAT (Kulon)",
                "Pada hari Rabu (Buda), kepala Naga Dina berada di Utara. Jangan bepergian langsung ke arah Utara. Pilihlah rute menuju Selatan atau Barat."
            )
            5 -> Triple(
                "TIMUR LAUT (Wetan Ngalor)",
                "BARAT DAYA (Kulon Ngidul) & TENGGARA",
                "Pada hari Kamis (Respati), kepala Naga Dina bersemayam di Timur Laut. Arah selamat dan pembawa rezeki berada di Barat Daya dan Tenggara."
            )
            6 -> Triple(
                "BARAT DAYA (Kulon Ngidul)",
                "TIMUR LAUT (Wetan Ngalor) & UTARA",
                "Pada hari Jumat (Sukra), kepala Naga Dina berada di Barat Daya. Hindari arah Barat Daya. Bergeraklah ke Timur Laut atau Utara."
            )
            else -> Triple(
                "TENGGARA (Wetan Ngidul)",
                "BARAT LAUT (Kulon Ngalor) & TIMUR",
                "Pada hari Sabtu (Tumpak), kepala Naga Dina berada di Tenggara. Hindari arah Tenggara. Keselamatan prima berada di arah Barat Laut dan Timur."
            )
        }

        val amalan = "Sadurunge jangkah bidhal, wacaa donga slamet (Bismillah / Doa Safar) kaping 3, mantepake batin lillahi ta'ala, lan mboten sumengka ing ati supados tansah tinebihna saking sambikala."

        return NagaDinaResult(
            date = targetDate,
            javaneseDate = jv,
            arahKepalaNaga = arahKepala,
            arahPunggungNaga = arahPunggung,
            wejanganPerjalanan = wejangan,
            amalanDoa = amalan
        )
    }
}
