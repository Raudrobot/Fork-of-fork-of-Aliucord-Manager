package com.aliucord.manager.voice.manager

import android.app.Application
import android.os.Environment
import com.aliucord.manager.voice.network.utils.SemVer
import java.io.File

/**
 * A central place to provide all system paths that are used.
 */
class PathManager(
    private val context: Application,
) {
    /**
     * The Aliucord folder in which plugins/settings/themes are stored.
     * Standard path: `~/Aliucord`
     */
    val aliucordDir = Environment.getExternalStorageDirectory().resolve("Aliucord")

    /**
     * The new Manager folder located on external storage.
     */
    val managerDir = aliucordDir.resolve("Manager")

    /**
     * The directory in external storage in which plugins are stored by Aliucord.
     */
    val pluginsDir = aliucordDir.resolve("plugins")

    /**
     * The settings file in which Aliucord's core uses.
     */
    val coreSettingsFile = aliucordDir.resolve("settings/Aliucord.json")

    /**
     * The old global keystore used for signing APKs stored in external storage.
     */
    val legacyKeystoreFile = managerDir.resolve("ks.keystore")

    /**
     * The new voice library file
     */
    val sunflowerLibFile = managerDir.resolve("sunflower.so")

    /**
     * The Aliucord core to bundle with built APKs
     */
    val bundledCoreFile = managerDir.resolve("Aliucord.zip")

    /**
     * The new global keystore used for signing APKs stored in Manager's external storage.
     */
    val keystoreFile = managerDir.resolve("aliucord.keystore")

    /**
     * The external directory used for downloading components related to patching, and
     * running the patching process itself.
     */
    val patchingDir = managerDir.resolve("patching")

    /**
     * The external app directory uses for downloads that should not be wiped,
     * to be used during the patching process. When the process completes, then this
     * is to be moved to the cache dir.
     */
    val patchingDownloadDir = patchingDir.resolve("downloads")

    /**
     * Used as a secondary location for downloads when not currently patching.
     */
    val cacheDownloadDir = managerDir.resolve("cache/downloads")

    /**
     * A permanent location used for storing custom patching components.
     * This does not get moved to an Android-managed cache dir when
     * not currently patching.
     */
    val customComponentsDir = patchingDir.resolve("custom")

    /**
     * Permanent location used for storing custom injectors pushed to the device.
     * No verification for the files placed here is done.
     */
    val customInjectorsDir = customComponentsDir.resolve("injector")

    /**
     * Permanent location used for storing custom smali patch bundles pushed to the device.
     * No verification for the files placed here is done.
     */
    val customPatchesDir = customComponentsDir.resolve("patches")

    /**
     * The temporary working directory of a currently executing patching process.
     */
    val patchingWorkingDir = patchingDir.resolve("patched")

    /**
     * The APK that is worked on during the patching process.
     */
    val patchedApk = patchingWorkingDir.resolve("patched.apk")

    init {
        // Ensure the base directories exist on the SD card
        try {
            if (!managerDir.exists()) managerDir.mkdirs()
            if (!patchingDir.exists()) patchingDir.mkdirs()
            if (!managerDir.resolve("cache").exists()) managerDir.resolve("cache").mkdirs()
        } catch (e: Exception) {
            // Ignored; if permissions are missing on Android 11+, they will be requested later.
        }
    }

    /**
     * Delete all the cache dirs and recreate them.
     */
    fun clearCache() {
        for (dir in arrayOf(patchingDir, cacheDownloadDir, context.cacheDir))
            dir.deleteRecursively()
    }

    /**
     * Create a new subfolder in the Discord APK cache for a specific version and split.
     */
    fun cachedDiscordApk(version: Int, split: String = "base"): File = patchingDownloadDir
        .resolve("discord/$version")
        .resolve("$split.apk")

    /**
     * Resolve a specific path for a cached injector.
     */
    fun cachedInjector(version: SemVer) = patchingDownloadDir
        .resolve("injector")
        .resolve("$version.dex")

    /**
     * Get all the versions of custom injector builds.
     */
    fun customInjectors() = customInjectorsDir.listFiles()?.asList() ?: emptyList()

    /**
     * Resolve a specific path for a versioned cached Aliuhook build
     */
    fun cachedAliuhookAAR(version: SemVer) = patchingDownloadDir
        .resolve("aliuhook")
        .resolve("$version.aar")

    /**
     * Resolve a specific path for a versioned smali patches archive.
     */
    fun cachedSmaliPatches(version: SemVer) = patchingDownloadDir
        .resolve("patches")
        .resolve("$version.zip")

    /**
     * Get all the versions of custom smali bundles.
     */
    fun customSmaliPatches() = customPatchesDir.listFiles()?.asList() ?: emptyList()

    /**
     * Resolve a specific path for a versioned Kotlin stdlib dex.
     */
    fun cachedKotlinDex(version: SemVer) = patchingDownloadDir
        .resolve("kotlin-stdlib")
        .resolve("$version.dex")
}

