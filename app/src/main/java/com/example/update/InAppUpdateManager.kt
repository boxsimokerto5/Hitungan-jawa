package com.example.update

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Pengelola Resmi Google Play In-App Updates:
 * Mendukung pembaruan fleksibel di latar belakang (Flexible Update)
 * dan pembaruan mendesak (Immediate Update) langsung dari Google Play Store.
 */
class InAppUpdateManager private constructor(private val appContext: Context) {

    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(appContext)

    private val _isUpdateDownloaded = MutableStateFlow(false)
    val isUpdateDownloaded: StateFlow<Boolean> = _isUpdateDownloaded.asStateFlow()

    private val _isCheckingUpdate = MutableStateFlow(false)
    val isCheckingUpdate: StateFlow<Boolean> = _isCheckingUpdate.asStateFlow()

    private val installStateUpdatedListener = InstallStateUpdatedListener { state: InstallState ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> {
                Log.d(TAG, "Pembaruan Play Store telah selesai diunduh di latar belakang.")
                _isUpdateDownloaded.value = true
            }
            InstallStatus.DOWNLOADING -> {
                val bytesDownloaded = state.bytesDownloaded()
                val totalBytes = state.totalBytesToDownload()
                Log.d(TAG, "Mengunduh pembaruan: $bytesDownloaded / $totalBytes bytes")
            }
            InstallStatus.FAILED -> {
                Log.e(TAG, "Gagal mengunduh pembaruan dari Play Store: errorCode=${state.installErrorCode()}")
            }
            InstallStatus.CANCELED -> {
                Log.d(TAG, "Unduhan pembaruan dibatalkan oleh pengguna.")
            }
            else -> {}
        }
    }

    init {
        try {
            appUpdateManager.registerListener(installStateUpdatedListener)
        } catch (e: Exception) {
            Log.e(TAG, "Gagal mendaftarkan install listener: ${e.message}")
        }
    }

    /**
     * Memeriksa pembaruan secara otomatis saat aplikasi dimulai.
     * Jika ada pembaruan resmi di Google Play, memulai Flexible Update di latar belakang.
     */
    fun checkForAppUpdateOnLaunch(activity: Activity) {
        try {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    Log.d(TAG, "Pembaruan Play Store terdeteksi! Memulai proses flexible update...")
                    startFlexibleUpdate(activity, appUpdateInfo)
                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    _isUpdateDownloaded.value = true
                }
            }.addOnFailureListener { e ->
                Log.d(TAG, "Pemeriksaan pembaruan otomatis selesai: ${e.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saat pengecekan pembaruan otomatis: ${e.message}")
        }
    }

    /**
     * Dipanggil pada onResume activity untuk memastikan update yang telah siap dipasang
     * atau update mendesak yang belum selesai dapat langsung diselesaikan.
     */
    fun onResumeCheck(activity: Activity) {
        try {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    _isUpdateDownloaded.value = true
                }
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // Lanjutkan immediate update jika sebelumnya terhenti
                    try {
                        appUpdateManager.startUpdateFlow(
                            appUpdateInfo,
                            activity,
                            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                        )
                    } catch (e: Exception) {
                        Log.e(TAG, "Gagal melanjutkan immediate update: ${e.message}")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saat onResumeCheck: ${e.message}")
        }
    }

    /**
     * Memulai pembaruan di latar belakang (Flexible). Pengguna tetap bisa menggunakan aplikasi
     * saat Google Play mengunduh file update.
     */
    fun startFlexibleUpdate(activity: Activity, appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlow(
                appUpdateInfo,
                activity,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Gagal memulai startUpdateFlow: ${e.message}")
        }
    }

    /**
     * Pasang pembaruan sekarang dan mulai ulang aplikasi secara otomatis.
     */
    fun completeUpdate() {
        try {
            Log.d(TAG, "Memasang pembaruan yang telah diunduh...")
            appUpdateManager.completeUpdate()
        } catch (e: Exception) {
            Log.e(TAG, "Gagal completeUpdate: ${e.message}")
        }
    }

    /**
     * Pemeriksaan manual yang dipanggil saat pengguna menekan tombol "Periksa Pembaruan" di layar Pengaturan.
     */
    fun checkManuallyFromSettings(
        activity: Activity,
        onUpdateAvailable: () -> Unit,
        onAlreadyLatest: () -> Unit,
        onErrorOrDevBuild: (String) -> Unit
    ) {
        _isCheckingUpdate.value = true
        try {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                _isCheckingUpdate.value = false
                when (appUpdateInfo.updateAvailability()) {
                    UpdateAvailability.UPDATE_AVAILABLE -> {
                        onUpdateAvailable()
                        startFlexibleUpdate(activity, appUpdateInfo)
                    }
                    UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                        onAlreadyLatest()
                    }
                    else -> {
                        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                            _isUpdateDownloaded.value = true
                            onUpdateAvailable()
                        } else {
                            onAlreadyLatest()
                        }
                    }
                }
            }.addOnFailureListener { e ->
                _isCheckingUpdate.value = false
                onErrorOrDevBuild(e.message ?: "Tidak dapat menghubungi server Google Play")
            }
        } catch (e: Exception) {
            _isCheckingUpdate.value = false
            onErrorOrDevBuild(e.message ?: "Kesalahan layanan Google Play")
        }
    }

    /**
     * Buka halaman aplikasi resmi di Google Play Store sebagai fallback
     */
    fun openPlayStorePage(context: Context) {
        val packageName = context.packageName
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(webIntent)
        }
    }

    fun unregister() {
        try {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        } catch (_: Exception) {}
    }

    companion object {
        private const val TAG = "InAppUpdateManager"

        @Volatile
        private var instance: InAppUpdateManager? = null

        fun getInstance(context: Context): InAppUpdateManager {
            return instance ?: synchronized(this) {
                instance ?: InAppUpdateManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
