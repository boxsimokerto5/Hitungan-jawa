package com.example.localization

object StringResources {

    fun get(key: String, language: AppLanguage): String {
        return strings[key]?.get(language) ?: strings[key]?.get(AppLanguage.INDONESIAN) ?: key
    }

    private val strings: Map<String, Map<AppLanguage, String>> = mapOf(
        "app_title" to mapOf(
            AppLanguage.JAVANESE to "Almanak & Kalender Jawa",
            AppLanguage.INDONESIAN to "Kalender Jawa & Weton",
            AppLanguage.ENGLISH to "Javanese Calendar & Weton"
        ),
        "tab_calendar" to mapOf(
            AppLanguage.JAVANESE to "Kalender",
            AppLanguage.INDONESIAN to "Kalender",
            AppLanguage.ENGLISH to "Calendar"
        ),
        "tab_weton" to mapOf(
            AppLanguage.JAVANESE to "Weton",
            AppLanguage.INDONESIAN to "Weton",
            AppLanguage.ENGLISH to "Weton"
        ),
        "tab_hitungan_jawa" to mapOf(
            AppLanguage.JAVANESE to "Hitungan Jawa",
            AppLanguage.INDONESIAN to "Hitungan Jawa",
            AppLanguage.ENGLISH to "Hitungan Jawa"
        ),
        "tab_holidays" to mapOf(
            AppLanguage.JAVANESE to "Tradisi & Pèngetan",
            AppLanguage.INDONESIAN to "Hari Besar & Tradisi",
            AppLanguage.ENGLISH to "Traditions & Events"
        ),
        "tab_planner" to mapOf(
            AppLanguage.JAVANESE to "Rancangan",
            AppLanguage.INDONESIAN to "Rencana",
            AppLanguage.ENGLISH to "Planner"
        ),
        "tab_settings" to mapOf(
            AppLanguage.JAVANESE to "Setelan",
            AppLanguage.INDONESIAN to "Pengaturan",
            AppLanguage.ENGLISH to "Settings"
        ),
        "today" to mapOf(
            AppLanguage.JAVANESE to "Dina Iki",
            AppLanguage.INDONESIAN to "Hari Ini",
            AppLanguage.ENGLISH to "Today"
        ),
        "selected_date" to mapOf(
            AppLanguage.JAVANESE to "Tanggal Pinilih",
            AppLanguage.INDONESIAN to "Tanggal Terpilih",
            AppLanguage.ENGLISH to "Selected Date"
        ),
        "javanese_date" to mapOf(
            AppLanguage.JAVANESE to "Tanggal Jawa",
            AppLanguage.INDONESIAN to "Tanggal Jawa",
            AppLanguage.ENGLISH to "Javanese Date"
        ),
        "gregorian_date" to mapOf(
            AppLanguage.JAVANESE to "Tanggal Masèhi",
            AppLanguage.INDONESIAN to "Tanggal Masehi",
            AppLanguage.ENGLISH to "Gregorian Date"
        ),
        "javanese_holidays" to mapOf(
            AppLanguage.JAVANESE to "Pèngetan & Tradisi Adat Jawa",
            AppLanguage.INDONESIAN to "Hari Besar & Tradisi Adat Jawa",
            AppLanguage.ENGLISH to "Javanese Traditions & Observances"
        ),
        "upcoming_holidays" to mapOf(
            AppLanguage.JAVANESE to "Pèngetan Sabanjuré",
            AppLanguage.INDONESIAN to "Tradisi & Hari Besar Mendatang",
            AppLanguage.ENGLISH to "Upcoming Traditions & Events"
        ),
        "no_holidays_today" to mapOf(
            AppLanguage.JAVANESE to "Ora ana pèngetan adat mirunggan ing dina iki",
            AppLanguage.INDONESIAN to "Tidak ada peringatan tradisi khusus pada tanggal ini",
            AppLanguage.ENGLISH to "No special tradition on this date"
        ),
        "activities_title" to mapOf(
            AppLanguage.JAVANESE to "Rancangan Kagiyatan",
            AppLanguage.INDONESIAN to "Rencana Kegiatan",
            AppLanguage.ENGLISH to "Activities & Plans"
        ),
        "no_activities" to mapOf(
            AppLanguage.JAVANESE to "Durung ana rancangan kagiyatan ing tanggal iki",
            AppLanguage.INDONESIAN to "Belum ada rencana kegiatan untuk tanggal ini",
            AppLanguage.ENGLISH to "No activities planned for this date"
        ),
        "add_activity" to mapOf(
            AppLanguage.JAVANESE to "Tambah Kagiyatan",
            AppLanguage.INDONESIAN to "Tambah Rencana",
            AppLanguage.ENGLISH to "Add Activity"
        ),
        "edit_activity" to mapOf(
            AppLanguage.JAVANESE to "Owahi Kagiyatan",
            AppLanguage.INDONESIAN to "Edit Kegiatan",
            AppLanguage.ENGLISH to "Edit Activity"
        ),
        "activity_title_hint" to mapOf(
            AppLanguage.JAVANESE to "Irah-irahan (tuladha: Selapanan Bayi, Nyekar, Slametan)",
            AppLanguage.INDONESIAN to "Judul Kegiatan (contoh: Selapanan Weton, Nyekar, Slametan)",
            AppLanguage.ENGLISH to "Activity title (e.g. Weton Observance, Family Gathering)"
        ),
        "activity_desc_hint" to mapOf(
            AppLanguage.JAVANESE to "Katrangan / Cathetan Tambahan (ora wajib)",
            AppLanguage.INDONESIAN to "Catatan / Keterangan tambahan (opsional)",
            AppLanguage.ENGLISH to "Notes or extra details (optional)"
        ),
        "time_hint" to mapOf(
            AppLanguage.JAVANESE to "Wektu (tuladha: 19:30 utawa kosongake)",
            AppLanguage.INDONESIAN to "Waktu (contoh: 19:30 atau kosongkan)",
            AppLanguage.ENGLISH to "Time (e.g. 19:30 or leave blank)"
        ),
        "category" to mapOf(
            AppLanguage.JAVANESE to "Kategori",
            AppLanguage.INDONESIAN to "Kategori",
            AppLanguage.ENGLISH to "Category"
        ),
        "cat_holiday" to mapOf(
            AppLanguage.JAVANESE to "Tradisi & Adat",
            AppLanguage.INDONESIAN to "Tradisi & Adat",
            AppLanguage.ENGLISH to "Tradition & Ritual"
        ),
        "cat_religious" to mapOf(
            AppLanguage.JAVANESE to "Ibadah & Donga",
            AppLanguage.INDONESIAN to "Ibadah & Doa",
            AppLanguage.ENGLISH to "Prayer & Worship"
        ),
        "cat_family" to mapOf(
            AppLanguage.JAVANESE to "Kulawarga & Hajatan",
            AppLanguage.INDONESIAN to "Keluarga & Acara",
            AppLanguage.ENGLISH to "Family & Social"
        ),
        "cat_work" to mapOf(
            AppLanguage.JAVANESE to "Pakaryan",
            AppLanguage.INDONESIAN to "Pekerjaan",
            AppLanguage.ENGLISH to "Work"
        ),
        "cat_personal" to mapOf(
            AppLanguage.JAVANESE to "Pribadi",
            AppLanguage.INDONESIAN to "Pribadi",
            AppLanguage.ENGLISH to "Personal"
        ),
        "enable_reminder" to mapOf(
            AppLanguage.JAVANESE to "Uripake Notifikasi Pangeling",
            AppLanguage.INDONESIAN to "Nyalakan Pengingat Notifikasi",
            AppLanguage.ENGLISH to "Enable Notification Reminder"
        ),
        "save" to mapOf(
            AppLanguage.JAVANESE to "Simpen",
            AppLanguage.INDONESIAN to "Simpan",
            AppLanguage.ENGLISH to "Save"
        ),
        "cancel" to mapOf(
            AppLanguage.JAVANESE to "Batal",
            AppLanguage.INDONESIAN to "Batal",
            AppLanguage.ENGLISH to "Cancel"
        ),
        "delete" to mapOf(
            AppLanguage.JAVANESE to "Busek",
            AppLanguage.INDONESIAN to "Hapus",
            AppLanguage.ENGLISH to "Delete"
        ),
        "filter_all" to mapOf(
            AppLanguage.JAVANESE to "Kabeh",
            AppLanguage.INDONESIAN to "Semua",
            AppLanguage.ENGLISH to "All"
        ),
        "filter_keraton" to mapOf(
            AppLanguage.JAVANESE to "Keraton & Grebeg",
            AppLanguage.INDONESIAN to "Tradisi Keraton",
            AppLanguage.ENGLISH to "Royal Traditions"
        ),
        "filter_islam_jawa" to mapOf(
            AppLanguage.JAVANESE to "Islam-Jawa",
            AppLanguage.INDONESIAN to "Hari Besar Islam-Jawa",
            AppLanguage.ENGLISH to "Islamic-Javanese"
        ),
        "filter_sakral" to mapOf(
            AppLanguage.JAVANESE to "Wengi Sakral (Kliwon)",
            AppLanguage.INDONESIAN to "Malam Sakral (Kliwon)",
            AppLanguage.ENGLISH to "Sacred Nights"
        ),
        "search_holidays_hint" to mapOf(
            AppLanguage.JAVANESE to "Golek jeneng tradisi / dinten pèngetan...",
            AppLanguage.INDONESIAN to "Cari nama hari besar / tradisi...",
            AppLanguage.ENGLISH to "Search traditions or events..."
        ),
        "settings_language_title" to mapOf(
            AppLanguage.JAVANESE to "Basa Tampilan",
            AppLanguage.INDONESIAN to "Pilihan Bahasa Tampilan",
            AppLanguage.ENGLISH to "Display Language"
        ),
        "settings_language_desc" to mapOf(
            AppLanguage.JAVANESE to "Ndhukung Basa Jawa, Bahasa Indonesia, lan English.",
            AppLanguage.INDONESIAN to "Mendukung Basa Jawa, Bahasa Indonesia, dan English.",
            AppLanguage.ENGLISH to "Supports Javanese, Indonesian, and English."
        ),
        "settings_notifications_title" to mapOf(
            AppLanguage.JAVANESE to "Pangeling & Notifikasi Otomatis",
            AppLanguage.INDONESIAN to "Pengingat & Notifikasi Otomatis",
            AppLanguage.ENGLISH to "Automatic Notification Reminders"
        ),
        "settings_holiday_notif" to mapOf(
            AppLanguage.JAVANESE to "Pangeling Tradisi & Wengi Sakral",
            AppLanguage.INDONESIAN to "Pengingat Hari Besar & Tradisi Jawa",
            AppLanguage.ENGLISH to "Javanese Tradition Reminders"
        ),
        "settings_holiday_notif_desc" to mapOf(
            AppLanguage.JAVANESE to "Tampa pawarta nalika ngancik dinten sakral (Jumat Kliwon, 1 Sura, Grebeg).",
            AppLanguage.INDONESIAN to "Terima pemberitahuan saat menjelang hari tradisi penting (Jumat Kliwon, 1 Sura, Grebeg).",
            AppLanguage.ENGLISH to "Receive notifications ahead of important Javanese traditions."
        ),
        "settings_activity_notif" to mapOf(
            AppLanguage.JAVANESE to "Pangeling Kagiyatan Terjadwal",
            AppLanguage.INDONESIAN to "Pengingat Kegiatan Terjadwal",
            AppLanguage.ENGLISH to "Scheduled Activity Reminders"
        ),
        "settings_activity_notif_desc" to mapOf(
            AppLanguage.JAVANESE to "Tandha swara notifikasi nalika wektu kagiyatan teka.",
            AppLanguage.INDONESIAN to "Bunyikan notifikasi saat waktu kegiatan tiba.",
            AppLanguage.ENGLISH to "Trigger notifications when activity time arrives."
        ),
        "test_notification" to mapOf(
            AppLanguage.JAVANESE to "Kirim Tes Notifikasi Saiki",
            AppLanguage.INDONESIAN to "Kirim Tes Notifikasi Sekarang",
            AppLanguage.ENGLISH to "Send Test Notification Now"
        ),
        "test_notification_success" to mapOf(
            AppLanguage.JAVANESE to "Notifikasi pangeling kasil dikirim marang piranti!",
            AppLanguage.INDONESIAN to "Notifikasi pengingat berhasil dikirim ke perangkat!",
            AppLanguage.ENGLISH to "Test notification sent successfully to device!"
        ),
        "calendar_info_title" to mapOf(
            AppLanguage.JAVANESE to "Babagan Penanggalan Jawa Sultan Agungan",
            AppLanguage.INDONESIAN to "Tentang Penanggalan Jawa Sultan Agungan",
            AppLanguage.ENGLISH to "About Javanese Sultan Agungan Calendar"
        ),
        "calendar_info_desc" to mapOf(
            AppLanguage.JAVANESE to "Penanggalan Jawa yaiku pananggalan lunisolar peninggalan Sultan Agung Hanyokrokusumo Mataram (1555 AJ / 1633 M) kang nyawijikake Taun Saka lan Taun Hijriyah kanthi siklus Windu (8 taun: Alip, Ehe, Jimawal, Je, Dal, Be, Wawu, Jimakhir), Pasaran 5 dina (Pancawara), Pawukon 30 wuku, lan Pranata Mangsa 12 mangsa.",
            AppLanguage.INDONESIAN to "Penanggalan Jawa adalah sistem penanggalan perpaduan Saka dan Hijriyah yang diresmikan Sultan Agung Mataram (1555 AJ / 1633 M). Memiliki keunikan siklus Windu 8 tahun, Pasaran 5 hari (Pancawara), 30 Wuku, Neptu, dan 12 Pranata Mangsa peredaran matahari.",
            AppLanguage.ENGLISH to "The Javanese Calendar is a unique lunisolar calendar established by Sultan Agung of Mataram in 1633 CE (1555 AJ). It harmonizes the Saka solar calendar with the Islamic lunar system, featuring the 8-year Windu cycle, 5-day Pasaran, 30 Wuku, Neptu values, and 12 solar agricultural seasons (Pranata Mangsa)."
        ),
        "all_plans" to mapOf(
            AppLanguage.JAVANESE to "Sedaya Rancangan Kagiyatan",
            AppLanguage.INDONESIAN to "Semua Agenda Rencana",
            AppLanguage.ENGLISH to "All Planned Agendas"
        ),
        "in_days" to mapOf(
            AppLanguage.JAVANESE to "ing %d dina maneh",
            AppLanguage.INDONESIAN to "dalam %d hari",
            AppLanguage.ENGLISH to "in %d days"
        ),
        "days_ago" to mapOf(
            AppLanguage.JAVANESE to "%d dina kepungkur",
            AppLanguage.INDONESIAN to "%d hari lalu",
            AppLanguage.ENGLISH to "%d days ago"
        ),
        "aksara_jawa_label" to mapOf(
            AppLanguage.JAVANESE to "Aksara Jawa",
            AppLanguage.INDONESIAN to "Aksara Jawa",
            AppLanguage.ENGLISH to "Javanese Script"
        ),
        "view_details" to mapOf(
            AppLanguage.JAVANESE to "Priksa Rincian",
            AppLanguage.INDONESIAN to "Lihat Rincian",
            AppLanguage.ENGLISH to "View Details"
        ),
        "close" to mapOf(
            AppLanguage.JAVANESE to "Tutup",
            AppLanguage.INDONESIAN to "Tutup",
            AppLanguage.ENGLISH to "Close"
        ),
        "about_us" to mapOf(
            AppLanguage.JAVANESE to "Babagan Aplikasi",
            AppLanguage.INDONESIAN to "Tentang Kami",
            AppLanguage.ENGLISH to "About Us"
        ),
        "about_us_desc" to mapOf(
            AppLanguage.JAVANESE to "Katrangan aplikasi, rumus penanggalan Jawa, lan fitur",
            AppLanguage.INDONESIAN to "Informasi aplikasi, sejarah kalender Jawa, dan pengembang",
            AppLanguage.ENGLISH to "Application info, Javanese calendar history & features"
        ),
        "privacy_policy" to mapOf(
            AppLanguage.JAVANESE to "Kawicaksanan Privasi",
            AppLanguage.INDONESIAN to "Kebijakan Privasi",
            AppLanguage.ENGLISH to "Privacy Policy"
        ),
        "privacy_policy_desc" to mapOf(
            AppLanguage.JAVANESE to "Komitmen keamanan, data kasimpen ing memori piranti piyambak",
            AppLanguage.INDONESIAN to "Komitmen keamanan, privasi data lokal, dan izin perangkat",
            AppLanguage.ENGLISH to "Privacy commitment, local data storage & device permissions"
        ),
        "back" to mapOf(
            AppLanguage.JAVANESE to "Mbalik",
            AppLanguage.INDONESIAN to "Kembali",
            AppLanguage.ENGLISH to "Back"
        ),
        "app_version" to mapOf(
            AppLanguage.JAVANESE to "Versi Aplikasi",
            AppLanguage.INDONESIAN to "Versi Aplikasi",
            AppLanguage.ENGLISH to "App Version"
        ),
        "pranata_mangsa_title" to mapOf(
            AppLanguage.JAVANESE to "Pranata Mangsa",
            AppLanguage.INDONESIAN to "Pranata Mangsa (Musim)",
            AppLanguage.ENGLISH to "Pranata Mangsa Season"
        ),
        "weton_neptu_title" to mapOf(
            AppLanguage.JAVANESE to "Weton & Neptu",
            AppLanguage.INDONESIAN to "Weton & Nilai Neptu",
            AppLanguage.ENGLISH to "Weton & Neptu Value"
        ),
        "wuku_title" to mapOf(
            AppLanguage.JAVANESE to "Wuku",
            AppLanguage.INDONESIAN to "Wuku (Pawukon)",
            AppLanguage.ENGLISH to "Wuku (Pawukon Cycle)"
        ),
        "calculator_seda_title" to mapOf(
            AppLanguage.JAVANESE to "Petungan Pengetan Dinten Seda",
            AppLanguage.INDONESIAN to "Kalkulator Peringatan Hari Wafat (Slametan)",
            AppLanguage.ENGLISH to "Memorial Days Calculator (Slametan)"
        ),
        "calculator_seda_desc" to mapOf(
            AppLanguage.JAVANESE to "Ngetung dinten pengetan seda: Geblak, 3 dina, 7 dina, 40 dina, 100 dina, Mendhak 1 & 2, lan Nyewu (1000 dina).",
            AppLanguage.INDONESIAN to "Hitung hari peringatan wafat: Geblak, 3 hari, 7 hari, 40 hari, 100 hari, Pendhak 1 & 2, serta Nyewu (1000 hari).",
            AppLanguage.ENGLISH to "Calculate traditional Javanese remembrance dates: 3 days, 7 days, 40 days, 100 days, 1st & 2nd anniversary, and 1000 days."
        ),
        "tab_weton" to mapOf(
            AppLanguage.JAVANESE to "Weton Lahir",
            AppLanguage.INDONESIAN to "Weton Lahir",
            AppLanguage.ENGLISH to "Birth Weton"
        ),
        "weton_calculator_title" to mapOf(
            AppLanguage.JAVANESE to "Kalkulator Weton Kelahiran",
            AppLanguage.INDONESIAN to "Kalkulator Weton Kelahiran",
            AppLanguage.ENGLISH to "Javanese Birth Weton Calculator"
        ),
        "weton_calculator_desc" to mapOf(
            AppLanguage.JAVANESE to "Petungan jangkep: Dina, Pasaran, Neptu, Watak Lakuning, Wuku, lan Pèngetan Selapanan 35 dina.",
            AppLanguage.INDONESIAN to "Kalkulasi lengkap: Hari, Pasaran, Neptu, Watak Lahir, Wuku, dan Peringatan Selapanan 35 hari.",
            AppLanguage.ENGLISH to "Complete calculation: Day, Pasaran, Neptu, Birth Traits, Wuku, and 35-day Selapanan cycle."
        ),
        "select_birth_date" to mapOf(
            AppLanguage.JAVANESE to "Pilih Tanggal Lair Masèhi",
            AppLanguage.INDONESIAN to "Pilih Tanggal Lahir Masehi",
            AppLanguage.ENGLISH to "Select Gregorian Birth Date"
        ),
        "weton_result_title" to mapOf(
            AppLanguage.JAVANESE to "Weton & Hari Kelahiran Jawa",
            AppLanguage.INDONESIAN to "Weton & Hari Kelahiran Jawa",
            AppLanguage.ENGLISH to "Javanese Birth Day & Weton"
        ),
        "watak_lakuning_title" to mapOf(
            AppLanguage.JAVANESE to "Watak Bawaan (Lakuning)",
            AppLanguage.INDONESIAN to "Watak Bawaan (Lakuning)",
            AppLanguage.ENGLISH to "Inherent Nature (Lakuning)"
        ),
        "karakter_primbon_title" to mapOf(
            AppLanguage.JAVANESE to "Karakter Weton (Primbon)",
            AppLanguage.INDONESIAN to "Karakter Weton (Primbon)",
            AppLanguage.ENGLISH to "Weton Character (Primbon)"
        ),
        "pancasuda_title" to mapOf(
            AppLanguage.JAVANESE to "Naungan Pancasuda",
            AppLanguage.INDONESIAN to "Naungan Pancasuda",
            AppLanguage.ENGLISH to "Pancasuda Classification"
        ),
        "selapanan_title" to mapOf(
            AppLanguage.JAVANESE to "Siklus Wetonan / Selapanan",
            AppLanguage.INDONESIAN to "Siklus Wetonan / Selapanan",
            AppLanguage.ENGLISH to "Wetonan / Selapanan Cycle"
        ),
        "next_wetonan" to mapOf(
            AppLanguage.JAVANESE to "Wetonan Sabanjuré",
            AppLanguage.INDONESIAN to "Wetonan Berikutnya",
            AppLanguage.ENGLISH to "Next Wetonan Celebration"
        ),
        "total_selapan_lived" to mapOf(
            AppLanguage.JAVANESE to "Gunggungipun Selapan Kang Wus Dilampahi",
            AppLanguage.INDONESIAN to "Total Selapan yang Telah Dilalui",
            AppLanguage.ENGLISH to "Total 35-Day Selapan Cycles Lived"
        ),
        "save_as_my_weton" to mapOf(
            AppLanguage.JAVANESE to "Simpen Minangka Weton Kula",
            AppLanguage.INDONESIAN to "Simpan Sebagai Weton Saya",
            AppLanguage.ENGLISH to "Save as My Birth Weton"
        ),
        "my_weton_badge" to mapOf(
            AppLanguage.JAVANESE to "Weton Kula",
            AppLanguage.INDONESIAN to "Weton Saya",
            AppLanguage.ENGLISH to "My Weton"
        ),
        "jodoh_calculator_title" to mapOf(
            AppLanguage.JAVANESE to "Petungan Jodho Neptu",
            AppLanguage.INDONESIAN to "Kalkulator Kecocokan Jodoh",
            AppLanguage.ENGLISH to "Neptu Compatibility Calculator"
        ),
        "check_weton_quick" to mapOf(
            AppLanguage.JAVANESE to "Cek Weton Kelahiran",
            AppLanguage.INDONESIAN to "Cek Weton Kelahiran",
            AppLanguage.ENGLISH to "Inspect Birth Weton"
        )
    )
}
