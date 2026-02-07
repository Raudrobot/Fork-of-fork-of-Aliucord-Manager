package com.aliucord.manager.patcher.steps.patch

import android.os.Build
import com.aliucord.manager.R
import com.aliucord.manager.manager.PathManager
import com.aliucord.manager.patcher.StepRunner
import com.aliucord.manager.patcher.steps.StepGroup
import com.aliucord.manager.patcher.steps.base.IDexProvider
import com.aliucord.manager.patcher.steps.base.Step
import com.aliucord.manager.patcher.steps.base.StepState
import com.aliucord.manager.patcher.steps.download.CopyDependenciesStep
import com.github.diamondminer88.zip.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Add the Sunflower native lib.
 */
class AddSunflowerStep : Step(), KoinComponent {
    private val paths: PathManager by inject()

    override val group = StepGroup.Patch
    override val localizedName = R.string.patch_step_add_sunflower

    override suspend fun execute(container: StepRunner) {
        val currentDeviceArch = Build.SUPPORTED_ABIS.first()
        val apk = container.getStep<CopyDependenciesStep>().apk
        val sunflower = paths.sunflowerLibFile
        if (!sunflower.exists()) {
            container.log("No sunflower lib exists at ${sunflower.absolutePath}, skipping..")
            state = StepState.Skipped
            return
        }

        ZipWriter(apk, /* append = */ true).use { patchedApk ->
            container.log("Writing libdiscord with arch $currentDeviceArch")
            patchedApk.deleteEntry("lib/$currentDeviceArch/libdiscord.so")
            patchedApk.writeEntry("lib/$currentDeviceArch/libdiscord.so", sunflower.readBytes(), ZipCompression.NONE)
        }
    }
}
