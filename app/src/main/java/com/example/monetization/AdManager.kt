package com.example.monetization

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.BuildConfig
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldContainer
import com.example.ui.theme.KremJawa
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary
import com.ironsource.mediationsdk.ISBannerSize
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.IronSourceBannerLayout
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.sdk.InitializationListener
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener

object AdManager {
    private const val TAG = "AdManager"

    val APP_KEY: String get() = BuildConfig.IRONSOURCE_APP_KEY
    val BANNER_1_ID: String get() = BuildConfig.IRONSOURCE_BANNER_1_ID
    val BANNER_2_ID: String get() = BuildConfig.IRONSOURCE_BANNER_2_ID
    val INTERSTITIAL_ID: String get() = BuildConfig.IRONSOURCE_INTERSTITIAL_ID
    val NATIVE_ID: String get() = BuildConfig.IRONSOURCE_NATIVE_ID
    val REWARDED_ID: String get() = BuildConfig.IRONSOURCE_REWARDED_ID

    // Aturan Google Play & Optimasi Penghasilan
    const val INTERSTITIAL_CLICK_THRESHOLD = 16
    const val INTERSTITIAL_COOLDOWN_MILLIS = 90_000L // 90 detik jeda minimal antar interstitial

    private var transitionClickCounter = 0
    private var lastInterstitialTimeMillis = 0L
    private var isInitialized = false

    private var pendingRewardCallback: (() -> Unit)? = null

    fun initialize(activity: Activity) {
        val appKey = APP_KEY.trim()
        if (appKey.isEmpty() || appKey == "your_ironsource_app_key_here") {
            Log.d(TAG, "IronSource AppKey belum diatur di .env / Secrets. Berjalan dalam mode aman.")
            return
        }

        if (isInitialized) return

        try {
            IronSource.init(activity, appKey, object : InitializationListener {
                override fun onInitializationComplete() {
                    isInitialized = true
                    Log.d(TAG, "IronSource initialized successfully with AppKey: $appKey")
                    Log.d(TAG, "Bidding adapters active: Meta Audience Network & Yandex")
                    IronSource.loadInterstitial()
                }
            }, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.BANNER, IronSource.AD_UNIT.REWARDED_VIDEO)

            setupListeners()
        } catch (e: Exception) {
            Log.e(TAG, "Gagal menginisialisasi IronSource: ${e.message}")
        }
    }

    private fun setupListeners() {
        IronSource.setLevelPlayInterstitialListener(object : LevelPlayInterstitialListener {
            override fun onAdReady(adInfo: AdInfo?) {
                Log.d(TAG, "Interstitial ready")
            }

            override fun onAdLoadFailed(error: IronSourceError?) {
                Log.d(TAG, "Interstitial load failed: ${error?.errorMessage}")
            }

            override fun onAdOpened(adInfo: AdInfo?) {}

            override fun onAdClosed(adInfo: AdInfo?) {
                Log.d(TAG, "Interstitial closed, preload next")
                IronSource.loadInterstitial()
            }

            override fun onAdShowFailed(error: IronSourceError?, adInfo: AdInfo?) {
                IronSource.loadInterstitial()
            }

            override fun onAdClicked(adInfo: AdInfo?) {}
            override fun onAdShowSucceeded(adInfo: AdInfo?) {}
        })

        IronSource.setLevelPlayRewardedVideoListener(object : LevelPlayRewardedVideoListener {
            override fun onAdAvailable(adInfo: AdInfo?) {}
            override fun onAdUnavailable() {}
            override fun onAdOpened(adInfo: AdInfo?) {}
            override fun onAdShowFailed(error: IronSourceError?, adInfo: AdInfo?) {}
            override fun onAdClicked(placement: com.ironsource.mediationsdk.model.Placement?, adInfo: AdInfo?) {}

            override fun onAdRewarded(placement: com.ironsource.mediationsdk.model.Placement?, adInfo: AdInfo?) {
                Log.d(TAG, "User rewarded")
                pendingRewardCallback?.invoke()
                pendingRewardCallback = null
            }

            override fun onAdClosed(adInfo: AdInfo?) {
                pendingRewardCallback = null
            }
        })
    }

    /**
     * Dipanggil HANYA saat transisi layar / menu utama (pindah tab),
     * BUKAN pada klik mikro (memilih tanggal kalender / checkbox).
     * Menerapkan aturan: (Klik >= 16) DAN (Jeda waktu >= 90 detik).
     */
    fun recordScreenTransition(activity: Activity): Boolean {
        transitionClickCounter++
        val now = System.currentTimeMillis()
        val timeSinceLast = now - lastInterstitialTimeMillis

        Log.d(TAG, "Transition click count: $transitionClickCounter/$INTERSTITIAL_CLICK_THRESHOLD, Cooldown: ${timeSinceLast / 1000}s")

        if (transitionClickCounter >= INTERSTITIAL_CLICK_THRESHOLD && timeSinceLast >= INTERSTITIAL_COOLDOWN_MILLIS) {
            if (IronSource.isInterstitialReady()) {
                val placement = INTERSTITIAL_ID.trim()
                if (placement.isNotEmpty() && placement != "your_ironsource_interstitial_id_here") {
                    IronSource.showInterstitial(placement)
                } else {
                    IronSource.showInterstitial()
                }
                transitionClickCounter = 0
                lastInterstitialTimeMillis = now
                return true
            } else {
                IronSource.loadInterstitial()
            }
        }
        return false
    }

    /**
     * Menampilkan interstitial saat transisi aksi penting (setelah kalkulasi selesai).
     * Tetap menghormati jeda waktu aman minimal 90 detik.
     */
    fun showInterstitialAfterAction(activity: Activity): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastInterstitialTimeMillis >= INTERSTITIAL_COOLDOWN_MILLIS) {
            if (IronSource.isInterstitialReady()) {
                val placement = INTERSTITIAL_ID.trim()
                if (placement.isNotEmpty() && placement != "your_ironsource_interstitial_id_here") {
                    IronSource.showInterstitial(placement)
                } else {
                    IronSource.showInterstitial()
                }
                lastInterstitialTimeMillis = now
                transitionClickCounter = 0
                return true
            } else {
                IronSource.loadInterstitial()
            }
        }
        return false
    }

    /**
     * Menampilkan Rewarded Ad saat pengguna sukarela ingin membuka fitur primbon lengkap.
     */
    fun showRewardedAd(activity: Activity, onRewarded: () -> Unit, onUnavailable: () -> Unit) {
        if (IronSource.isRewardedVideoAvailable()) {
            pendingRewardCallback = onRewarded
            val placement = REWARDED_ID.trim()
            if (placement.isNotEmpty() && placement != "your_ironsource_rewarded_id_here") {
                IronSource.showRewardedVideo(placement)
            } else {
                IronSource.showRewardedVideo()
            }
        } else {
            onUnavailable()
        }
    }

    fun onResume(activity: Activity) {
        IronSource.onResume(activity)
    }

    fun onPause(activity: Activity) {
        IronSource.onPause(activity)
    }

    fun getTransitionClickCount(): Int = transitionClickCounter
    fun getLastAdTime(): Long = lastInterstitialTimeMillis
}
