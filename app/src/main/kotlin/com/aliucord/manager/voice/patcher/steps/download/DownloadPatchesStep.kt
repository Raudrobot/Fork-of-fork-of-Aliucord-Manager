package com.aliucord.manager.voice.patcher.steps.download

import androidx.compose.runtime.Stable
import com.aliucord.manager.voice.R
import com.aliucord.manager.voice.manager.PathManager
import com.aliucord.manager.voice.network.utils.SemVer
import com.aliucord.manager.voice.patcher.StepRunner
import com.aliucord.manager.voice.patcher.steps.base.DownloadStep
import com.aliucord.manager.voice.patcher.steps.base.StepState
import com.aliucord.manager.voice.patcher.steps.prepare.FetchInfoStep
import com.aliucord.manager.voice.ui.screens.componentopts.PatchComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.FileNotFoundException

/**
 * Download a zip of all the smali patches to be applied to the APK during patching.
 */
@Stable
class DownloadPatchesStep(
    private val custom: PatchComponent?,
) : DownloadStep<SemVer>(), KoinComponent {
    private val paths: PathManager by inject()

    override val localizedName = R.string.patch_step_dl_smali

    override fun getRemoteUrl(container: StepRunner) = URL

    override fun getVersion(container: StepRunner) =
        custom?.version ?: container.getStep<FetchInfoStep>().data.patchesVersion

    override fun getStoredFile(container: StepRunner) =
        custom?.getFile(paths) ?: paths.cachedSmaliPatches(getVersion(container))

    override suspend fun execute(container: StepRunner) {
        if (custom != null) {
            container.log("Using custom patches with version ${custom.version} built ${custom.timestamp}")

            if (!custom.getFile(paths).exists()) {
                throw FileNotFoundException(
                    "Selected custom component does not exist on disk! If this is an update, " +
                        "updates cannot occur when the originally selected custom component has been deleted."
                )
            }

            state = StepState.Skipped
            return
        }

        super.execute(container)
    }

    private companion object {
        const val URL = "https://builds.aliucord.com/patches.zip"
    }
}
