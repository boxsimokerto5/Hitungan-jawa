package com.example.ui.screens.hitungan

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class HitunganCategory(
    val title: String,
    val subtitle: String,
    val aksara: String
) {
    KANDUNGAN("Kandungan", "Perawatan janin & tradisi kandungan", "ꦏꦤ꧀ꦝꦸꦔꦤ꧀"),
    KELAHIRAN("Kelahiran", "Kelahiran, bayi, & anak", "ꦏꦭꦲꦶꦫꦤ꧀"),
    PERJODOHAN("Perjodohan", "Kecocokan jodoh & pernikahan", "ꦥꦼꦂꦗꦺꦴꦝꦺꦴꦲꦤ꧀"),
    KEMATIAN_HAJAT("Kematian & Hajat", "Slametan wafat & hajat besar", "ꦥꦺꦔꦼꦠꦤ꧀ꦲꦗꦠ꧀")
}

enum class HitunganType {
    MAPATI_4_BULAN,
    MITONI_7_BULAN,
    HPL_JAWA,
    SEPASAR_5_HARI,
    SELAPANAN_35_HARI,
    TEDHAK_SITEN,
    MENDHEM_ARI_ARI,
    WATAK_KARAKTER_BAYI,
    REKOMENDASI_NAMA_JAWA,
    KECOCOKAN_JODOH,
    HARI_BAIK_NIKAH,
    ARAH_REZEKI_PASANGAN,
    SELAMETAN_KEMATIAN,
    PINDAH_RUMAH,
    BUKA_USAHA,
    NAGA_DINA
}

data class HitunganCardInfo(
    val type: HitunganType,
    val category: HitunganCategory,
    val title: String,
    val subtitle: String,
    val description: String,
    val tag: String,
    val icon: ImageVector,
    val accentColor: Color
)
