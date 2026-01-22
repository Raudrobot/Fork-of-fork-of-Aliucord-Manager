package com.aliucord.manager.patcher.steps.download

import androidx.compose.runtime.Stable
import com.aliucord.manager.R
import com.aliucord.manager.manager.PathManager
import com.aliucord.manager.network.services.AliucordMavenService
import com.aliucord.manager.network.utils.SemVer
import com.aliucord.manager.network.utils.getOrThrow
import com.aliucord.manager.patcher.StepRunner
import com.aliucord.manager.patcher.steps.base.DownloadStep
import com.aliucord.manager.patcher.steps.base.IDexProvider
import com.aliucord.manager.patcher.steps.patch.ReorganizeDexStep
import com.github.diamondminer88.zip.ZipReader
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Download a packaged AAR of the latest Sunflower build from the Aliucord maven.
 * Provides [ReorganizeDexStep] with the dex through the [IDexProvider] implementation.
 */
@Stable
// class DownloadSunflowerStep : DownloadStep(), IDexProvider, KoinComponent {
class DownloadSunflowerStep : DownloadStep(), KoinComponent {
    private val paths: PathManager by inject()
    private val maven: AliucordMavenService by inject()

    /**
     * This is populated right before the download starts (ref: [execute])
     */
    lateinit var targetVersion: SemVer
        private set

    override val localizedName = R.string.patch_step_dl_sunflower
    // override val targetUrl get() = AliucordMavenService.getSunflowerUrl(targetVersion.toString())
    override val targetUrl get() = AliucordMavenService.getLibDiscordUrl()
    // override val targetFile get() = paths.cachedSunflowerAAR(targetVersion)
    override val targetFile get() = paths.cachedNewLibDiscord()

    override suspend fun execute(container: StepRunner) {
        container.log("Obtaining latest sunflower version")
        targetVersion = SemVer(0, 1, 0)
        container.log("Fetched sunflower version: $targetVersion")

        super.execute(container)
    }
    //
    // override val dexPriority = 0
    // override val dexCount = 1
    // override fun getDexFiles(): List<ByteArray> {
    //     val dexBytes = ZipReader(targetFile).use { zip ->
    //         zip.openEntry("classes.dex")?.read()
    //             ?: throw IllegalStateException("No prebuilt classes.dex in downloaded sunflower build")
    //     }
    //
    //     return listOf(dexBytes)
    // }
}
