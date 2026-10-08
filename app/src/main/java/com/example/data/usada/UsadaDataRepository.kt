package com.example.data.usada

object UsadaDataRepository {

    val categories: List<UsadaCategory> = UsadaCategory.values().toList()

    val methods: List<UsadaMethod> = UsadaMethod.values().toList()

    val recipes: List<UsadaRecipe> = listOf(
        UsadaRecipe(
            id = "usada_batuk_kering",
            illnessName = "Batuk Kering & Tenggorokan Gatal",
            javaneseRemedyName = "Jamu Cengkeh Madu Jahe",
            category = UsadaCategory.PERNAPASAN,
            method = UsadaMethod.GODOKAN,
            summary = "Meredakan rasa gatal di tenggorokan, mengencerkan lendir kering, dan menghangatkan dada.",
            ingredients = listOf(
                UsadaIngredient("Jahe Merah / Emprit", "2 ruas jari (~30 gram)", "dicuci bersih lalu dimemarkan/geprek"),
                UsadaIngredient("Kuntum Cengkeh Kering", "5 butir", "utuh tanpa dihancurkan"),
                UsadaIngredient("Kayu Manis", "1 jari (sekitar 4 cm)", "dicuci bersih"),
                UsadaIngredient("Jeruk Nipis", "1/2 butir", "diperas airnya"),
                UsadaIngredient("Madu Hutan Alami", "2 sendok makan", "dicampurkan saat air sudah hangat kuku"),
                UsadaIngredient("Air Bersih", "400 ml", "air matang untuk merebus")
            ),
            steps = listOf(
                "Masukkan jahe memar, cengkeh, dan kayu manis ke dalam panci bersama 400 ml air.",
                "Rebus dengan api sedang hingga air mendidih dan aroma rempah harum semerbak, biarkan menyusut hingga tersisa sekitar 250 ml (sekitar 10-12 menit).",
                "Angkat rebusan dan saring airnya ke dalam cangkir tembikar atau gelas kaca.",
                "Tunggu hingga suhu suam-suam kuku, lalu tambahkan perasan jeruk nipis dan madu alami. Aduk perlahan searah jarum jam.",
                "Minum seteguk demi seteguk selagi masih hangat."
            ),
            usageRule = "Minum 2 kali sehari, yaitu pagi hari sesudah sarapan dan malam hari menjelang tidur.",
            contraindications = "Hindari minuman es/dingin, gorengan berminyak pekat, kacang tanah goreng, dan asap rokok selama masa pemulihan.",
            primbonWisdom = "Kaserat ing Serat Primbon Usada: Angeting cengkeh lan jahe ngicalaken lendir garing sarta nentremaken gorokan ingkang gerah.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_masuk_angin",
            illnessName = "Masuk Angin, Kembung & Meriang",
            javaneseRemedyName = "Wedang Jahe Sereh Gula Jawa",
            category = UsadaCategory.PERNAPASAN,
            method = UsadaMethod.GODOKAN,
            summary = "Mengusir hawa dingin, melancarkan keringat alami, meredakan mual, dan menghangatkan lambung.",
            ingredients = listOf(
                UsadaIngredient("Jahe Merah", "3 ruas jari", "dibakar sebentar di atas api lalu digeprek"),
                UsadaIngredient("Batang Sereh Wangi", "2 batang", "dimemarkan bagian pangkal putihnya"),
                UsadaIngredient("Daun Pandan Wangi", "1 lembar", "disimpulkan rapi"),
                UsadaIngredient("Biji Cengkeh", "3 butir", "utuh"),
                UsadaIngredient("Gula Jawa / Aren Asli", "2 sendok makan (sesuai selera)", "disisir halus"),
                UsadaIngredient("Air Bersih", "500 ml", "untuk rebusan")
            ),
            steps = listOf(
                "Bakar rimpang jahe sebentar di atas api kecil agar minyak atsirinya bangkit, lalu kupas kulit tipisnya dan memarkan.",
                "Rebus air bersama jahe memar, sereh geprek, cengkeh, dan simpul pandan hingga mendidih selama 10-15 menit.",
                "Masukkan sisiran gula aren asli, aduk perlahan hingga gula larut sempurna.",
                "Saring air wedang ke dalam cangkir dan nikmati hangat-hangat sembari menghirup uap aromatiknya."
            ),
            usageRule = "Minum 1-2 kali sehari saat tubuh terasa meriang, dingin, atau sehabis kehujanan.",
            contraindications = "Hindari mandi malam dengan air dingin dan jangan begadang di tempat berangin kencang.",
            primbonWisdom = "Jahe lan sereh nggugah geni urip ing lebet padharan, nundhung angin adhem supados raga enggal pulih seger.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_batuk_berdahak",
            illnessName = "Batuk Berdahak & Sesak Lendir",
            javaneseRemedyName = "Loloh Kencur Jahe Madu",
            category = UsadaCategory.PERNAPASAN,
            method = UsadaMethod.PERAS_SEDOH,
            summary = "Mengencerkan dahak kental di saluran napas, melegakan rongga dada, dan mempermudah pengeluaran lendir.",
            ingredients = listOf(
                UsadaIngredient("Kencur Segar", "3 ruas jari", "kupas dan parut halus"),
                UsadaIngredient("Jahe Emprit", "1 ruas jari", "kupas dan parut"),
                UsadaIngredient("Jeruk Nipis", "1 sendok teh perasan", "perasan segar"),
                UsadaIngredient("Madu Murni", "1 sendok makan", "sebagai pelarut lembut"),
                UsadaIngredient("Garam Dapur", "seujung sendok teh", "untuk membantu mencairkan lendir"),
                UsadaIngredient("Air Hangat Matang", "100 ml", "air hangat bersih")
            ),
            steps = listOf(
                "Campur parutan kencur dan jahe emprit dengan 100 ml air hangat matang.",
                "Remas-remas sejenak menggunakan sarung tangan bersih, lalu peras dan saring airnya menggunakan kain kasa bersih.",
                "Tambahkan seujung sendok garam, perasan jeruk nipis, dan madu murni ke dalam air perasan.",
                "Aduk rata lalu minum perlahan-lahan seteguk demi seteguk."
            ),
            usageRule = "Minum 2 kali sehari secara rutin selama 3-5 hari hingga dahak bersih.",
            contraindications = "Jangan minum air es dan kurangi konsumsi makanan bersantan kental.",
            primbonWisdom = "Kencur lan uyah sakedhik nduwe daya ngencerake riak kang rumaket ing pamulangan.",
            estimatedMinutes = 12,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_sakit_maag",
            illnessName = "Sakit Maag, Asam Lambung & Nyeri Ulu Hati",
            javaneseRemedyName = "Kunir Madu & Temulawak Padharan",
            category = UsadaCategory.PENCERNAAN,
            method = UsadaMethod.PERAS_SEDOH,
            summary = "Melapisi dan menyejukkan dinding lambung, meredakan produksi asam berlebih, serta mengobati luka lambung.",
            ingredients = listOf(
                UsadaIngredient("Kunyit Kuning Tua (Kunir)", "2 ruas jari (~25 gram)", "kupas dan cuci bersih air matang"),
                UsadaIngredient("Temulawak Segar", "1 ruas jari", "kupas dan cuci bersih"),
                UsadaIngredient("Madu Hutan Murni", "1 sendok makan", "madu asli berkualitas"),
                UsadaIngredient("Garam Dapur", "seujung sendok teh", "penyeimbang rasa & ion"),
                UsadaIngredient("Air Hangat Matang", "150 ml", "air matang suam kuku")
            ),
            steps = listOf(
                "Parut halus kunyit dan temulawak yang telah dicuci bersih dengan air matang.",
                "Seduh parutan rimpang dengan 150 ml air hangat suam kuku, diamkan sekitar 3 menit.",
                "Peras dan saring sarinya menggunakan kain saringan bersih ke dalam gelas.",
                "Campurkan madu murni dan sedikit garam dapur, aduk searah jarum jam secara khidmat.",
                "Minum dalam keadaan tenang selagi hangat."
            ),
            usageRule = "Minum setiap pagi saat perut masih kosong (30 menit sebelum sarapan) dan malam hari sebelum tidur.",
            contraindications = "Hindari makanan bercitarasa sangat pedas, kecut/asam cuka, kopi hitam pekat, ketan, dan minuman berkarbonasi.",
            primbonWisdom = "Serat Usada: Kunir mujudake tamba lambung warisan luhur kang njaga lan nambani tatu dinding weteng kanthi adhem santosa.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_perut_kembung_mulas",
            illnessName = "Perut Kembung, Begah & Mulas",
            javaneseRemedyName = "Godokan Daun Salam & Ketumbar",
            category = UsadaCategory.PENCERNAAN,
            method = UsadaMethod.GODOKAN,
            summary = "Mengeluarkan gas yang terperangkap di usus, meredakan kejang usus (mulas), dan melancarkan buang angin.",
            ingredients = listOf(
                UsadaIngredient("Daun Salam Segar", "7 lembar", "dicuci bersih"),
                UsadaIngredient("Biji Ketumbar", "1 sendok teh", "disangrai sebentar"),
                UsadaIngredient("Adas Manis", "1/2 sendok teh", "dibersihkan"),
                UsadaIngredient("Kayu Manis", "1 ruas jari kecil", "dicuci bersih"),
                UsadaIngredient("Gula Batu Alami", "secukupnya", "pemanis lembut"),
                UsadaIngredient("Air Bersih", "500 ml", "untuk merebus")
            ),
            steps = listOf(
                "Masukkan daun salam, ketumbar sangrai, adas manis, dan kayu manis ke dalam panci berisi 500 ml air.",
                "Rebus dengan api kecil hingga air tersisa kira-kira 300 ml (sekitar 12 menit).",
                "Tambahkan sedikit gula batu, aduk hingga larut.",
                "Saring air godokan dan minum selagi hangat suam kuku."
            ),
            usageRule = "Minum 1 gelas sesudah makan atau saat perut terasa begah dan kembung.",
            contraindications = "Kurangi makan terburu-buru dan hindari sayuran pemicu gas seperti kol mentah atau nangka muda.",
            primbonWisdom = "Uraping adas manis lan ketumbar ndadosaken angin ing usus medal kanthi gampil tanpa lara.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_diare_murus",
            illnessName = "Diare, Mencret & Perut Melilit",
            javaneseRemedyName = "Loloh Pucuk Daun Jambu Klutuk",
            category = UsadaCategory.PENCERNAAN,
            method = UsadaMethod.PERAS_SEDOH,
            summary = "Zat tanin alami mengencangkan dinding mukosa usus, menghentikan buang air cair, dan membunuh bakteri usus.",
            ingredients = listOf(
                UsadaIngredient("Pucuk Daun Jambu Biji", "5 lembar pucuk muda", "dicuci bersih air matang"),
                UsadaIngredient("Kunyit Segar", "1 ruas jari", "dikupas dan dicuci bersih"),
                UsadaIngredient("Garam Dapur", "seujung sendok teh", "mengikat cairan tubuh"),
                UsadaIngredient("Air Hangat Matang", "150 ml", "air matang")
            ),
            steps = listOf(
                "Tumbuk halus pucuk daun jambu klutuk muda dan kunyit menggunakan cobek bersih.",
                "Beri seujung sendok teh garam dapur.",
                "Seduh tumbukan dengan 150 ml air hangat matang, aduk lalu peras sarinya dengan saringan bersih.",
                "Minum air perasan tersebut secara perlahan."
            ),
            usageRule = "Minum 2 kali sehari setelah buang air besar hingga frekuensi diare berkurang normal.",
            contraindications = "Jangan makan makanan berminyak pekat, sambal pedas, atau susu murni selama perut masih murus.",
            primbonWisdom = "Pucuk godhong jambu klutuk ngerem murusing weteng kanthi rikat lan nyegah kelangan toya raga.",
            estimatedMinutes = 10,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_pegal_linu",
            illnessName = "Pegal Linu, Badan Loyo & Capek Sehabis Kerja",
            javaneseRemedyName = "Jamu Beras Kencur & Jahe Wangi",
            category = UsadaCategory.STAMINA_SENDI,
            method = UsadaMethod.GODOKAN,
            summary = "Menghilangkan rasa lelah kronis, merelaksasi otot kaku, melancarkan peredaran darah, dan mengembalikan energi.",
            ingredients = listOf(
                UsadaIngredient("Kencur Segar Pilihan", "100 gram", "dicuci bersih dan dikupas"),
                UsadaIngredient("Beras Putih Bersih", "50 gram", "direndam 3 jam lalu disangrai kekuningan"),
                UsadaIngredient("Jahe Emprit", "2 ruas jari", "dikupas dan dimemarkan"),
                UsadaIngredient("Asam Jawa Kawak", "1 sendok makan", "dibersihkan dari biji"),
                UsadaIngredient("Gula Kelapa / Aren", "75 gram", "disisir halus"),
                UsadaIngredient("Daun Pandan", "2 lembar", "disimpulkan"),
                UsadaIngredient("Air Bersih", "800 ml", "untuk kuah rebusan")
            ),
            steps = listOf(
                "Sangrai beras yang sudah direndam dan ditiriskan sampai harum kekuningan, lalu tumbuk atau blender halus menjadi bubuk.",
                "Rebus 800 ml air bersama gula aren, asam jawa, daun pandan, dan jahe memar hingga mendidih dan gula larut sempurna. Matikan api dan biarkan suam kuku.",
                "Parut atau blender kencur segar hingga lumat.",
                "Campurkan kencur halus dan tepung beras ke dalam wadah, lalu tuangkan air rebusan gula asam yang sudah suam kuku.",
                "Remas-remas racikan sebentar, lalu saring bersih menggunakan kain kasa halus.",
                "Sajikan dalam cangkir tradisional, nikmati segarnya khasiat beras kencur."
            ),
            usageRule = "Minum 1 gelas setiap sore atau malam hari setelah seharian beraktivitas fisik.",
            contraindications = "Imbangi dengan istirahat tidur yang cukup dan jangan memaksakan begadang.",
            primbonWisdom = "Beras kencur ngicalake rasa lungkrah lan sayah, nuwuhake kasantosan raga sarta nambahi nafsu nedha.",
            estimatedMinutes = 25,
            difficulty = "Sedang",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_asam_urat_pinggang",
            illnessName = "Nyeri Pinggang, Boyok & Asam Urat",
            javaneseRemedyName = "Godokan Kumis Kucing & Sambiloto",
            category = UsadaCategory.STAMINA_SENDI,
            method = UsadaMethod.GODOKAN,
            summary = "Membersihkan kristal purin dalam darah, melancarkan pembuangan air seni, dan meredakan radang pinggang.",
            ingredients = listOf(
                UsadaIngredient("Daun Kumis Kucing", "15 lembar segar", "dicuci bersih"),
                UsadaIngredient("Daun Sambiloto", "7 lembar", "dicuci bersih"),
                UsadaIngredient("Temulawak Tua", "2 ruas jari", "dikupas dan diiris tipis-tipis"),
                UsadaIngredient("Akar Alang-Alang", "15 gram", "dicuci bersih"),
                UsadaIngredient("Air Bersih", "600 ml", "untuk merebus")
            ),
            steps = listOf(
                "Masukkan seluruh bahan ke dalam panci gerabah tanah liat (atau panci stainless steel anti-asam).",
                "Tuangkan 600 ml air bersih dan rebus dengan api sedang.",
                "Biarkan mendidih hingga air menyusut menjadi sekitar 300 ml (separuh dari volume awal).",
                "Angkat dan saring air godokan.",
                "Biarkan suam-suam kuku sebelum diminum. Rasa sedikit pahit menandakan zat aktif sambiloto yang mujarab."
            ),
            usageRule = "Minum 1 cangkir sehari secara teratur selama 5-7 hari berturut-turut.",
            contraindications = "Batasi konsumsi jeroan hewani, emping melinjo, ekstrak ragi, dan makanan laut berkadar purin tinggi.",
            primbonWisdom = "Kumis kucing mancurake nguyuh lan ngresiki piranti ginjal, ngilangi racun lan linu ing sendhi.",
            estimatedMinutes = 20,
            difficulty = "Sedang",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_rematik_dingin",
            illnessName = "Rematik Dingin & Otot Kaku Kaki",
            javaneseRemedyName = "Parem Jahe Bengle & Beras Tumbuk",
            category = UsadaCategory.STAMINA_SENDI,
            method = UsadaMethod.PAREM_BALUR,
            summary = "Obat balur luar tradisional untuk memberikan kehangatan mendalam pada persendian dan melancarkan otot kaku.",
            ingredients = listOf(
                UsadaIngredient("Beras Putih", "2 sendok makan", "direndam 1 jam"),
                UsadaIngredient("Jahe Merah", "2 ruas jari", "dicuci bersih"),
                UsadaIngredient("Rimpang Bengle", "1 ruas jari", "dicuci bersih"),
                UsadaIngredient("Cengkeh Kering", "3 butir", "dihaluskan"),
                UsadaIngredient("Minyak Kelapa / Gandapura", "1 sendok teh", "sebagai pelarut pelicin")
            ),
            steps = listOf(
                "Tumbuk beras yang telah direndam bersama jahe merah, rimpang bengle, dan cengkeh hingga menjadi adonan liat berbutir halus.",
                "Tambahkan minyak kelapa asli atau minyak gandapura, aduk rata hingga bertekstur seperti lulur kental.",
                "Balurkan pasta parem ini secara merata pada lutut, betis, pinggang, atau pergelangan kaki yang kaku dan ngilu.",
                "Biarkan menempel hingga mengering dan rasa hangat meresap ke dalam sendi (sekitar 30-45 menit), lalu bersihkan dengan air hangat."
            ),
            usageRule = "Balurkan 1-2 kali sehari pada sore atau malam hari menjelang tidur.",
            contraindications = "Hanya untuk pemakaian luar pada kulit utuh. Jangan oleskan pada luka terbuka atau lecet.",
            primbonWisdom = "Parem bengle lan jahe ngasorake linu adhem ing balung, marakake sikil entheng kagem lumampah malih.",
            estimatedMinutes = 20,
            difficulty = "Sedang",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_darah_tinggi",
            illnessName = "Darah Tinggi (Hipertensi)",
            javaneseRemedyName = "Loloh Belimbing Wuluh & Timun",
            category = UsadaCategory.DARAH_ORGAN,
            method = UsadaMethod.PERAS_SEDOH,
            summary = "Kaya kalium alami yang membantu menurunkan ketegangan dinding pembuluh darah dan menstabilkan tensi.",
            ingredients = listOf(
                UsadaIngredient("Mentimun Segar", "1 buah ukuran sedang", "dicuci bersih"),
                UsadaIngredient("Belimbing Wuluh Muda", "3 buah", "dicuci bersih"),
                UsadaIngredient("Madu Murni", "1 sendok makan", "penyeimbang keasaman alami"),
                UsadaIngredient("Air Matang", "100 ml", "air dingin matang")
            ),
            steps = listOf(
                "Parut mentimun bersama belimbing wuluh hingga halus.",
                "Campur parutan dengan 100 ml air matang, lalu peras dan saring air sarinya.",
                "Tambahkan madu murni agar rasa seimbang dan segar di tenggorokan.",
                "Aduk rata dan minum segar di pagi hari."
            ),
            usageRule = "Minum 1 kali sehari di pagi hari secara rutin sembari tetap memantau tensi secara berkala.",
            contraindications = "Kurangi makanan asin tinggi natrium/garam, daging kambing olahan bumbu pekat, dan hindari stres pikiran.",
            primbonWisdom = "Tirta timun lan belimbing wuluh adhem sipate, ngedhukake hawa panas getih ing badan kanthi tentrem.",
            estimatedMinutes = 10,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_kolesterol",
            illnessName = "Kolesterol & Lemak Darah Berlebih",
            javaneseRemedyName = "Wedang Godok Daun Salam & Sereh",
            category = UsadaCategory.DARAH_ORGAN,
            method = UsadaMethod.GODOKAN,
            summary = "Senyawa flavonoid dan tanin dalam daun salam terbukti empiris menurunkan kadar trigliserida dan kolesterol jahat.",
            ingredients = listOf(
                UsadaIngredient("Daun Salam Segar/Kering", "10 lembar", "dicuci bersih"),
                UsadaIngredient("Batang Sereh", "2 batang", "dimemarkan"),
                UsadaIngredient("Jahe Emprit", "1 ruas jari", "dimemarkan"),
                UsadaIngredient("Air Bersih", "700 ml", "untuk merebus")
            ),
            steps = listOf(
                "Masukkan daun salam, sereh geprek, dan jahe ke dalam panci berisi 700 ml air.",
                "Rebus dengan api sedang hingga mendidih dan air tersisa sekitar 350 ml (separuh takaran awal).",
                "Angkat dan saring ke dalam gelas.",
                "Minum selagi hangat tanpa gula, atau tambahkan sedikit madu jika menghendaki rasa manis lembut."
            ),
            usageRule = "Minum 1 gelas setiap malam hari sesudah makan malam.",
            contraindications = "Batasi konsumsi gorengan dengan minyak jelantah, santan kental yang dipanaskan berulang, dan jeroan.",
            primbonWisdom = "Godhong salam mbedhal lemak getih kang kenthel, ndadosaken ilining getih lancar ora krasa abot ing gulu.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_anyang_anyangen",
            illnessName = "Anyang-Anyangen & Sakit Saat Buang Air Kecil",
            javaneseRemedyName = "Godokan Oyod Alang-Alang & Kumis Kucing",
            category = UsadaCategory.DARAH_ORGAN,
            method = UsadaMethod.GODOKAN,
            summary = "Mendinginkan kandung kemih, membunuh bakteri saluran kencing secara alami, dan melancarkan buang air seni.",
            ingredients = listOf(
                UsadaIngredient("Akar Alang-Alang Bersih", "30 gram", "dicuci bersih"),
                UsadaIngredient("Rambut Jagung Muda", "1 genggam kecil", "dicuci bersih"),
                UsadaIngredient("Daun Kumis Kucing", "10 lembar", "dicuci bersih"),
                UsadaIngredient("Gula Batu Alami", "1 bongkah kecil", "pemanis alami"),
                UsadaIngredient("Air Bersih", "750 ml", "untuk merebus")
            ),
            steps = listOf(
                "Rebus akar alang-alang, rambut jagung, dan daun kumis kucing dalam 750 ml air hingga mendidih.",
                "Biarkan menyusut hingga tersisa kira-kira 400 ml.",
                "Masukkan gula batu dan aduk sampai larut.",
                "Saring air rebusan dan minum hangat."
            ),
            usageRule = "Minum 2 kali sehari masing-masing 1 gelas hingga buang air kecil terasa tuntas dan lancar.",
            contraindications = "Jangan membiasakan menahan buang air kecil dan perbanyak minum air putih minimal 2 liter sehari.",
            primbonWisdom = "Oyod alang-alang adhem nglebur benter ing puser, nglancarake saluran nguyuh tanpa rasa perih sumeleh.",
            estimatedMinutes = 20,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_insomnia_susah_tidur",
            illnessName = "Susah Tidur (Insomnia) & Pikiran Gelisah",
            javaneseRemedyName = "Wedang Pala Madu & Kayu Manis",
            category = UsadaCategory.KEPALA_TIDUR,
            method = UsadaMethod.GODOKAN,
            summary = "Minyak atsiri myristicin dalam buah pala memberikan efek sedatif alami yang menenangkan saraf otak dan mengundang kantuk alami.",
            ingredients = listOf(
                UsadaIngredient("Biji Pala Kering", "1/2 butir", "dimemarkan halus"),
                UsadaIngredient("Batang Kayu Manis", "1 batang kecil (3-4 cm)", "dicuci bersih"),
                UsadaIngredient("Jahe Emprit", "1 ruas jari", "dimemarkan"),
                UsadaIngredient("Madu Hutan Asli", "1 sendok makan", "dimasukkan saat suam kuku"),
                UsadaIngredient("Air Bersih", "350 ml", "untuk merebus")
            ),
            steps = listOf(
                "Rebus pala memar, kayu manis, dan jahe dalam 350 ml air selama 10 menit dengan api kecil.",
                "Tuang ke dalam cangkir melalui saringan teh.",
                "Biarkan suam kuku sejenak, lalu tambahkan madu alami dan aduk merata.",
                "Minum santai sembari mengatur napas pelan sekitar 30 menit sebelum menuju peraduan tidur."
            ),
            usageRule = "Minum 1 cangkir setiap malam sebelum waktu tidur.",
            contraindications = "Hindari minum kopi atau teh pekat setelah lewat tengah hari, serta jauhkan pandangan dari layar gawai saat di ranjang.",
            primbonWisdom = "Aroma lan sari buah pala ngasorake pikiran kang ruwet, nuntun sukma marang sare ingkang tentrem kepenak.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_migrain_pusing",
            illnessName = "Sakit Kepala Sebelah (Migrain) & Pusing Cumleng",
            javaneseRemedyName = "Pilis Kencur Bengle & Daun Sirih",
            category = UsadaCategory.KEPALA_TIDUR,
            method = UsadaMethod.TAPEL_PILIS,
            summary = "Pengobatan pilis tradisi Keraton: menyejukkan saraf pelipis dan dahi, menurunkan ketegangan pembuluh darah kepala.",
            ingredients = listOf(
                UsadaIngredient("Kencur Segar", "1 ruas jari", "dikupas dan dicuci bersih"),
                UsadaIngredient("Rimpang Bengle", "1/2 ruas jari", "dicuci bersih"),
                UsadaIngredient("Daun Sirih", "2 lembar", "dicuci bersih"),
                UsadaIngredient("Air Mawar / Air Matang", "beberapa tetes", "untuk mengikat adonan")
            ),
            steps = listOf(
                "Haluskan kencur, bengle, dan daun sirih menggunakan cobek batu hingga lumat liat.",
                "Beri beberapa tetes air mawar atau air matang hingga konsistensinya menjadi pasta lembut yang mudah menempel.",
                "Tempelkan adonan pilis ini secara melintang pada kening dan kedua pelipis kanan-kiri.",
                "Pejamkan mata dan berbaringlah di ruangan yang redup dan tenang selama 20-30 menit hingga pasta pilis mengering, lalu seka bersih dengan handuk basah."
            ),
            usageRule = "Gunakan saat serangan migrain atau pusing melanda.",
            contraindications = "Hindari terik matahari langsung dan suasana bising saat proses pemulihan.",
            primbonWisdom = "Pilis kencur nentremake sarap ing pelipis, ngilangake pusing cumleng ing sirah kanthi sumeleh.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_gatal_alergi_kulit",
            illnessName = "Gatal Kulit, Alergi Biduran & Biang Keringat",
            javaneseRemedyName = "Baluran Kunyit Sirih & Asam Kawak",
            category = UsadaCategory.KULIT_LUAR,
            method = UsadaMethod.PAREM_BALUR,
            summary = "Khasiat kurkumin kunyit dan antiseptik sirih meredakan histamin gatal, membasmi jamur kulit, dan menyejukkan ruam kemerahan.",
            ingredients = listOf(
                UsadaIngredient("Kunyit Segar Basah", "2 ruas jari", "dikupas dan diparut halus"),
                UsadaIngredient("Daun Sirih Hijau", "5 lembar", "dicuci bersih dan ditumbuk halus"),
                UsadaIngredient("Asam Jawa Tua (Asam Kawak)", "1 sendok teh", "dibersihkan dari biji"),
                UsadaIngredient("Minyak Kelapa Murni (VCO)", "1 sendok makan", "sebagai pelumas lembut alami")
            ),
            steps = listOf(
                "Campurkan parutan kunyit, tumbukan daun sirih, dan asam kawak dalam satu mangkuk kecil.",
                "Tambahkan minyak kelapa murni, lalu aduk hingga membentuk pasta kental yang lembut.",
                "Oleskan secara merata pada area kulit yang gatal, biduran, atau ruam merah.",
                "Biarkan selama 20-30 menit hingga mengering, lalu bilas bersih dengan air suam kuku."
            ),
            usageRule = "Oleskan 2 kali sehari pagi dan sore hari sesudah mandi.",
            contraindications = "Hindari menggaruk dengan kuku tajam agar tidak menimbulkan lecet atau infeksi sekunder.",
            primbonWisdom = "Godhong suruh lan kunir minangka sarana tamba suci alami kang mateni lelara gatel ing kulit kanti resik.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_sariawan_bau_mulut",
            illnessName = "Sariawan Rongga Mulut, Bibir Pecah & Bau Mulut",
            javaneseRemedyName = "Tirta Kumur Rebusan Sirih & Cengkeh",
            category = UsadaCategory.KULIT_LUAR,
            method = UsadaMethod.TETES_KUMUR,
            summary = "Kandungan kavikol dan eugenol membunuh bakteri anaerob di mulut, mempercepat regenerasi sel bibir yang luka.",
            ingredients = listOf(
                UsadaIngredient("Daun Sirih Hijau Tua", "5 lembar", "dicuci bersih dan diremas sedikit"),
                UsadaIngredient("Kuntum Cengkeh", "3 butir", "utuh"),
                UsadaIngredient("Garam Dapur Halus", "1/2 sendok teh", "antiseptik alami"),
                UsadaIngredient("Air Bersih", "400 ml", "untuk merebus")
            ),
            steps = listOf(
                "Rebus daun sirih, cengkeh, dan garam dalam 400 ml air selama 10 menit hingga air berwarna kecokelatan aromatik.",
                "Angkat dan saring ke dalam gelas.",
                "Biarkan dingin hingga suhu suam kuku.",
                "Ambil sesendok makan lalu gunakan untuk berkumur-kumur di dalam rongga mulut selama 1-2 menit dengan menjangkau sariawan, lalu buang (jangan ditelan)."
            ),
            usageRule = "Gunakan untuk berkumur 3 kali sehari setelah makan dan menjelang tidur.",
            contraindications = "Hindari mengonsumsi makanan yang bersuhu terlalu panas atau berminyak membakar bibir.",
            primbonWisdom = "Suruh lan cengkeh mbrastha kuman ing cangkem, nambani sariawan lan ndadosaken ambegan arum sumebar.",
            estimatedMinutes = 12,
            difficulty = "Mudah",
            isRecommended = false
        ),

        UsadaRecipe(
            id = "usada_demam_panas_anak",
            illnessName = "Demam & Meriang Panas Anak",
            javaneseRemedyName = "Tapel Bawang Merah & Minyak Telon",
            category = UsadaCategory.IBU_ANAK,
            method = UsadaMethod.TAPEL_PILIS,
            summary = "Kandungan allisin dan minyak asiri bawang merah melebarkan pori-pori kulit secara lembut sehingga panas tubuh cepat terlepas.",
            ingredients = listOf(
                UsadaIngredient("Bawang Merah Segar", "3 butir", "dikupas dan dicuci bersih"),
                UsadaIngredient("Minyak Telon / Minyak Kelapa", "1 sendok makan", "minyak lembut"),
                UsadaIngredient("Asam Jawa Sedikit", "seujung sendok teh", "pelengkap pendingin")
            ),
            steps = listOf(
                "Geprek atau parut kasar bawang merah (hindari membuat terlalu halus agar uap pedih tidak menguap berlebih ke mata anak).",
                "Campurkan parutan bawang merah dengan 1 sendok makan minyak telon dan sedikit asam jawa.",
                "Aduk rata menggunakan telapak tangan.",
                "Balurkan secara lembut pada ubun-ubun, dada, punggung, perut, serta kedua telapak kaki anak sembari diusap kasih sayang."
            ),
            usageRule = "Balurkan sehabis anak diseka air hangat atau menjelang anak beristirahat tidur malam.",
            contraindications = "Jangan menyelimuti anak dengan selimut tebal berlapis saat badan sedang panas tinggi; berikan pakaian berbahan katun tipis.",
            primbonWisdom = "Brambang lan lenga klentik ngasorake bentering raga bocah cilik kanthi cara alami warisan simbah.",
            estimatedMinutes = 8,
            difficulty = "Mudah",
            isRecommended = true
        ),

        UsadaRecipe(
            id = "usada_pelancar_asi",
            illnessName = "Pelancar ASI & Pemulihan Stamina Ibu Menyusui",
            javaneseRemedyName = "Sayur Bening Katuk & Temulawak Segar",
            category = UsadaCategory.IBU_ANAK,
            method = UsadaMethod.GODOKAN,
            summary = "Senyawa laktagogum alami daun katuk merangsang hormon prolaktin dan oksitosin untuk melimpahkan air susu ibu secara alami.",
            ingredients = listOf(
                UsadaIngredient("Daun Katuk Segar", "1 mangkok daun muda", "dicuci bersih"),
                UsadaIngredient("Temulawak Parut", "1 ruas jari", "dikupas dan diparut"),
                UsadaIngredient("Kencur Segar", "1 ruas jari", "dimemarkan"),
                UsadaIngredient("Daun Salam", "2 lembar", "dicuci bersih"),
                UsadaIngredient("Bawang Merah", "2 butir", "diiris tipis"),
                UsadaIngredient("Gula Aren & Garam", "secukupnya", "penyedap rasa alami"),
                UsadaIngredient("Air Bersih", "600 ml", "kuah bening")
            ),
            steps = listOf(
                "Didihkan 600 ml air bersama irisan bawang merah, kencur memar, temulawak parut, dan daun salam.",
                "Setelah mendidih harum, masukkan daun katuk segar.",
                "Beri garam dan sedikit gula aren sesuai selera, masak sebentar saja hingga daun katuk matang layu kehijauan (sekitar 3-5 menit).",
                "Angkat dan sajikan hangat."
            ),
            usageRule = "Konsumsi 1-2 mangkok setiap hari beserta kuah dan sayurnya saat waktu makan.",
            contraindications = "Hindari stres mental dan cukupi konsumsi air putih minimal 2.5 hingga 3 liter setiap hari.",
            primbonWisdom = "Godhong katuk lan temulawak nuntun metuning toya susu ibu kanthi banter lan resik seger marang jabang bayi.",
            estimatedMinutes = 15,
            difficulty = "Mudah",
            isRecommended = false
        )
    )

    // Glosarium Tanaman Obat Tradisional Jawa (Kamus Herbal Usada)
    val herbsGlossary: List<UsadaHerbInfo> = listOf(
        UsadaHerbInfo(
            indonesianName = "Jahe Merah & Emprit",
            javaneseName = "Jahe Abang / Emprit",
            latinName = "Zingiber officinale",
            partUsed = "Rimpang",
            primaryBenefit = "Menghangatkan tubuh, mengusir masuk angin, meredakan batuk & mual",
            description = "Rimpang jahe memiliki rasa pedas hangat berkat senyawa gingerol dan shogaol. Merupakan rimpang utama dalam tradisi wedangan dan jamu godokan Jawa untuk membangkitkan panas tubuh alami.",
            commonPairs = "Sereh, Gula Jawa, Cengkeh, Madu"
        ),
        UsadaHerbInfo(
            indonesianName = "Kunyit / Kunir",
            javaneseName = "Kunir",
            latinName = "Curcuma longa",
            partUsed = "Rimpang",
            primaryBenefit = "Mengobati radang lambung (maag), pelindung hati, anti-bakteri kulit",
            description = "Mengandung kurkuminoid berwarna kuning emas yang berkhasiat kuat sebagai antiinflamasi dan antioksidan. Dalam Primbon Usada, kunir disebut sebagai penyejuk dinding lambung dan darah.",
            commonPairs = "Asam Jawa, Temulawak, Madu, Sirih"
        ),
        UsadaHerbInfo(
            indonesianName = "Temulawak",
            javaneseName = "Temulawak",
            latinName = "Curcuma xanthorrhiza",
            partUsed = "Rimpang",
            primaryBenefit = "Menambah nafsu makan, memperbaiki fungsi hati/liver, melancarkan empedu",
            description = "Rimpang asli Nusantara berukuran besar dengan aroma wangi khas. Sangat ampuh memulihkan stamina raga yang letih dan memicu sekresi enzim pencernaan.",
            commonPairs = "Kunyit, Gula Aren, Asam Jawa"
        ),
        UsadaHerbInfo(
            indonesianName = "Kencur",
            javaneseName = "Kencur",
            latinName = "Kaempferia galanga",
            partUsed = "Rimpang",
            primaryBenefit = "Meredakan batuk berdahak, pegal linu, menghangatkan pita suara",
            description = "Rimpang kecil beraroma wangi semerbak menyejukkan. Banyak dipakai dalam racikan beras kencur untuk melancarkan peredaran darah serta pilis untuk pusing kepala.",
            commonPairs = "Beras, Madu, Jeruk Nipis, Jahe"
        ),
        UsadaHerbInfo(
            indonesianName = "Daun Sirih",
            javaneseName = "Godhong Suruh",
            latinName = "Piper betle",
            partUsed = "Daun",
            primaryBenefit = "Antiseptik alami, obat sariawan, pembersih luka, obat gatal",
            description = "Daun berbentuk hati yang disakralkan dalam tradisi Jawa. Mengandung kavikol yang memiliki daya antiseptik 5 kali lebih kuat dari fenol biasa untuk membasmi kuman dan jamur.",
            commonPairs = "Cengkeh, Gambir, Asam Jawa, Kunyit"
        ),
        UsadaHerbInfo(
            indonesianName = "Kayu Manis",
            javaneseName = "Kayu Manis",
            latinName = "Cinnamomum burmannii",
            partUsed = "Kulit Batang",
            primaryBenefit = "Mengontrol gula darah, memberi aroma sedap, menghangatkan lambung",
            description = "Kulit batang kering aromatik yang memberikan rasa manis alami dan aroma mewah. Membantu meningkatkan sensitivitas insulin dan memperbaiki pencernaan.",
            commonPairs = "Cengkeh, Jahe, Madu, Pala"
        ),
        UsadaHerbInfo(
            indonesianName = "Cengkeh",
            javaneseName = "Cengkeh",
            latinName = "Syzygium aromaticum",
            partUsed = "Kuntum Bunga Kering",
            primaryBenefit = "Pereda nyeri gigi/mulut, antiseptik pernapasan, menghangatkan dada",
            description = "Kaya eugenol yang bertindak sebagai analgesik alami. Digunakan dalam ramuan batuk, tirta kumur sariawan, serta wedang penghangat badan.",
            commonPairs = "Jahe, Kayu Manis, Sirih"
        ),
        UsadaHerbInfo(
            indonesianName = "Sereh Wangi",
            javaneseName = "Sereh",
            latinName = "Cymbopogon citratus",
            partUsed = "Batang & Daun",
            primaryBenefit = "Penenang saraf (relaksan), penurun kolesterol, peluruh angin perut",
            description = "Batang rumput aromatik beraroma sitrun segar. Membantu detoksifikasi tubuh dan sering disandingkan bersama jahe untuk wedang tradisional Jawa.",
            commonPairs = "Jahe, Daun Salam, Pandan"
        ),
        UsadaHerbInfo(
            indonesianName = "Asam Jawa",
            javaneseName = "Asem Kawak",
            latinName = "Tamarindus indica",
            partUsed = "Daging Buah Tua",
            primaryBenefit = "Penyegar rasa, peluruh dahak kental, antioksidan pencernaan",
            description = "Daging buah polong yang diasamkan. Memberikan cita rasa segar penyeimbang rasa rimpang yang getir serta melancarkan buang air besar.",
            commonPairs = "Kunyit, Gula Aren, Kencur"
        ),
        UsadaHerbInfo(
            indonesianName = "Kumis Kucing",
            javaneseName = "Kumis Kucing / Remujung",
            latinName = "Orthosiphon aristatus",
            partUsed = "Daun",
            primaryBenefit = "Peluruh batu ginjal, melancarkan air seni, pereda asam urat",
            description = "Tanaman herba berbunga mirip sungut kucing. Terkenal berkhasiat diuretik alami yang aman untuk membersihkan saluran kemih dari kristal asam urat.",
            commonPairs = "Sambiloto, Temulawak, Akar Alang-Alang"
        ),
        UsadaHerbInfo(
            indonesianName = "Sambiloto",
            javaneseName = "Sambiloto / Ki Pait",
            latinName = "Andrographis paniculata",
            partUsed = "Daun",
            primaryBenefit = "Raja pahit penurun panas demam, pengendali gula darah, anti-infeksi",
            description = "Meski berasa sangat pahit, kandungan andrografolid di dalamnya merupakan stimulan imunitas alami terbaik dalam pengobatan tradisional Nusantara.",
            commonPairs = "Temulawak, Kumis Kucing"
        ),
        UsadaHerbInfo(
            indonesianName = "Biji Pala",
            javaneseName = "Pala",
            latinName = "Myristica fragrans",
            partUsed = "Biji Buah",
            primaryBenefit = "Obat penenang alami, mengatasi susah tidur (insomnia), penenang lambung",
            description = "Biji rempah kepulauan Maluku yang menyatu dalam tradisi keraton Jawa. Menghasilkan aroma khas yang meredakan ketegangan sistem saraf pusat.",
            commonPairs = "Kayu Manis, Jahe, Madu"
        ),
        UsadaHerbInfo(
            indonesianName = "Daun Katuk",
            javaneseName = "Godhong Katuk",
            latinName = "Sauropus androgynus",
            partUsed = "Daun Muda",
            primaryBenefit = "Pelancar air susu ibu (ASI), penyegar darah, penambah nutrisi",
            description = "Kaya klorofil, zat besi, dan senyawa steroid alami laktagogum yang sangat dianjurkan bagi ibu nifas dan menyusui.",
            commonPairs = "Bawang Merah, Temulawak, Kencur"
        ),
        UsadaHerbInfo(
            indonesianName = "Daun Salam",
            javaneseName = "Godhong Salam",
            latinName = "Syzygium polyanthum",
            partUsed = "Daun",
            primaryBenefit = "Penurun kolesterol jahat, penstabil gula darah, pereda kembung",
            description = "Daun rempah yang biasa digunakan bumbu masak sekaligus herba obat peredam lemak darah yang sangat ampuh jika digodok teratur.",
            commonPairs = "Sereh, Jahe, Ketumbar"
        ),
        UsadaHerbInfo(
            indonesianName = "Akar Alang-Alang",
            javaneseName = "Oyod Alang-Alang",
            latinName = "Imperata cylindrica",
            partUsed = "Rimpang / Akar",
            primaryBenefit = "Pereda panas dalam, penyejuk kandung kemih, penghenti mimisan",
            description = "Akar rumput liar yang memiliki sifat mendinginkan tubuh (*adhem*). Sangat mujarab meredakan anyang-anyangen dan panas dalam.",
            commonPairs = "Rambut Jagung, Kumis Kucing, Gula Batu"
        )
    )

    fun getRecipeById(id: String): UsadaRecipe? = recipes.find { it.id == id }

    fun filterRecipes(
        query: String = "",
        category: UsadaCategory? = null,
        method: UsadaMethod? = null,
        onlyFavorites: Boolean = false,
        favoriteIds: Set<String> = emptySet()
    ): List<UsadaRecipe> {
        return recipes.filter { recipe ->
            val matchesQuery = if (query.isBlank()) {
                true
            } else {
                val q = query.trim().lowercase()
                recipe.illnessName.lowercase().contains(q) ||
                    recipe.javaneseRemedyName.lowercase().contains(q) ||
                    recipe.summary.lowercase().contains(q) ||
                    recipe.ingredients.any { it.name.lowercase().contains(q) } ||
                    recipe.category.titleId.lowercase().contains(q) ||
                    recipe.method.titleId.lowercase().contains(q)
            }

            val matchesCategory = category == null || recipe.category == category
            val matchesMethod = method == null || recipe.method == method
            val matchesFavorite = !onlyFavorites || favoriteIds.contains(recipe.id)

            matchesQuery && matchesCategory && matchesMethod && matchesFavorite
        }
    }
}
