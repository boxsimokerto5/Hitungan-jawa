package com.example.data.usada

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Kategori Usada (Pengobatan Tradisional Jawa)
 */
enum class UsadaCategory(
    val id: String,
    val titleId: String,
    val titleJv: String,
    val description: String,
    val icon: ImageVector,
    val accentColor: Color
) {
    PERNAPASAN(
        id = "pernapasan",
        titleId = "Pernapasan & Masuk Angin",
        titleJv = "Napas & Angin-anginen",
        description = "Batuk, pilek, flu, meriang, sesak, dan perut kembung masuk angin",
        icon = Icons.Default.Air,
        accentColor = Color(0xFF0284C7)
    ),
    PENCERNAAN(
        id = "pencernaan",
        titleId = "Pencernaan & Lambung",
        titleJv = "Weteng & Padharan",
        description = "Sakit maag, mulas, kembung, diare/murus, perih lambung, dan sembelit",
        icon = Icons.Default.SoupKitchen,
        accentColor = Color(0xFFD97706)
    ),
    STAMINA_SENDI(
        id = "stamina_sendi",
        titleId = "Kebugaran, Pegal & Sendi",
        titleJv = "Kasantosan & Sendhi",
        description = "Pegal linu, capek lelah, rematik, asam urat, encok, dan boyok linu",
        icon = Icons.Default.FitnessCenter,
        accentColor = Color(0xFFB45309)
    ),
    KULIT_LUAR(
        id = "kulit_luar",
        titleId = "Kulit & Pengobatan Luar",
        titleJv = "Kulit & Tamba Njawi",
        description = "Gatal alergi, biduran, bisul, sariawan mulut, luka memar, dan panu",
        icon = Icons.Default.Healing,
        accentColor = Color(0xFF059669)
    ),
    DARAH_ORGAN(
        id = "darah_organ",
        titleId = "Peredaran Darah & Organ",
        titleJv = "Rah & Piranti Raga",
        description = "Darah tinggi/hipertensi, kolesterol, gula darah, ginjal, dan anyang-anyangen",
        icon = Icons.Default.Favorite,
        accentColor = Color(0xFFDC2626)
    ),
    KEPALA_TIDUR(
        id = "kepala_tidur",
        titleId = "Sakit Kepala & Relaksasi",
        titleJv = "Sirah & Katentreman",
        description = "Migrain, pusing cumleng, susah tidur/insomnia, dan pikiran tegang",
        icon = Icons.Default.Bedtime,
        accentColor = Color(0xFF7C3AED)
    ),
    IBU_ANAK(
        id = "ibu_anak",
        titleId = "Ibu & Anak Keluarga",
        titleJv = "Ibu & Bocah Cilik",
        description = "Pelancar ASI, demam panas anak, cacingan, dan penambah nafsu makan",
        icon = Icons.Default.ChildCare,
        accentColor = Color(0xFFE11D48)
    )
}

/**
 * Metode Pengolahan Ramuan Tradisional Jawa
 */
enum class UsadaMethod(
    val id: String,
    val titleId: String,
    val titleJv: String,
    val shortName: String,
    val description: String,
    val icon: ImageVector
) {
    GODOKAN(
        id = "godokan",
        titleId = "Jamu Godok (Rebusan)",
        titleJv = "Jamu Godhogan",
        shortName = "Godokan",
        description = "Bahan direbus dalam kuali/panci hingga sarinya larut pekat lalu disaring",
        icon = Icons.Default.LocalDrink
    ),
    PERAS_SEDOH(
        id = "peras_sedoh",
        titleId = "Jamu Peras & Seduh (Loloh)",
        titleJv = "Perasan / Loloh",
        shortName = "Peras / Loloh",
        description = "Rimpang diparut halus, diseduh air hangat matang, dan diperas sarinya",
        icon = Icons.Default.LocalDrink
    ),
    PAREM_BALUR(
        id = "parem_balur",
        titleId = "Parem & Baluran Kulit",
        titleJv = "Parem & Baluran",
        shortName = "Parem / Balur",
        description = "Ramuan dihaluskan menjadi pasta lalu dibalurkan ke tangan, kaki, atau badan",
        icon = Icons.Default.PanTool
    ),
    TAPEL_PILIS(
        id = "tapel_pilis",
        titleId = "Tapel & Pilis Kening/Perut",
        titleJv = "Tapel & Pilis",
        shortName = "Tapel / Pilis",
        description = "Adonan ditempelkan pada dahi/pelipis (pilis) atau dibalurkan di perut (tapel)",
        icon = Icons.Default.Healing
    ),
    TETES_KUMUR(
        id = "tetes_kumur",
        titleId = "Air Kumur & Tetes",
        titleJv = "Kumur & Tetes",
        shortName = "Kumur / Tetes",
        description = "Air rebusan herbal antiseptik untuk berkumur mulut atau tetes luar",
        icon = Icons.Default.WaterDrop
    )
}

/**
 * Komposisi bahan herbal
 */
data class UsadaIngredient(
    val name: String,
    val amount: String,
    val preparationNote: String = ""
)

/**
 * Resep Ramuan Usada Jawa
 */
data class UsadaRecipe(
    val id: String,
    val illnessName: String,       // Nama keluhan / sakit apa (e.g. Batuk Kering & Radang Tenggorokan)
    val javaneseRemedyName: String,// Nama tradisi ramuan (e.g. Jamu Cengkeh Madu Jahe)
    val category: UsadaCategory,
    val method: UsadaMethod,
    val summary: String,           // Ringkasan manfaat
    val ingredients: List<UsadaIngredient>,
    val steps: List<String>,       // Cara membuatnya langkah demi langkah
    val usageRule: String,         // Aturan minum / cara penggunaan
    val contraindications: String? = null, // Pantangan & hal yang dihindari
    val primbonWisdom: String? = null,    // Petuah / filosofi Usada Jawa (Serat Centhini/Primbon)
    val estimatedMinutes: Int = 15,
    val difficulty: String = "Mudah",      // Mudah, Sedang
    val isRecommended: Boolean = false     // Ramuan unggulan / populer
)

/**
 * Glosarium Tanaman Obat Tradisional Jawa (Kamus Herbal Usada)
 */
data class UsadaHerbInfo(
    val indonesianName: String,
    val javaneseName: String,
    val latinName: String,
    val partUsed: String,          // Rimpang, Daun, Bunga, Kulit Kayu, dsb.
    val primaryBenefit: String,
    val description: String,
    val commonPairs: String        // Pasangan racikan tradisional
)
