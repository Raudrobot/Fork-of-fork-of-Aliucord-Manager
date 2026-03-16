package com.aliucord.manager.voice.patcher.steps.patch

import android.os.Build
import com.aliucord.manager.voice.R
import com.aliucord.manager.voice.manager.PathManager
import com.aliucord.manager.voice.patcher.StepRunner
import com.aliucord.manager.voice.patcher.steps.StepGroup
import com.aliucord.manager.voice.patcher.steps.base.Step
import com.aliucord.manager.voice.patcher.steps.base.StepState
import com.aliucord.manager.voice.patcher.steps.download.CopyDependenciesStep
import com.github.diamondminer88.zip.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Bundle the Aliucord core inside the apk.
 */
class AddBundledAliucordStep : Step(), KoinComponent {
    private val paths: PathManager by inject()

    override val group = StepGroup.Patch
    override val localizedName = R.string.patch_step_add_bundled

    override suspend fun execute(container: StepRunner) {
        val apk = container.getStep<CopyDependenciesStep>().apk
        val bundle = paths.bundledCoreFile
        if (!bundle.exists()) {
            container.log("No bundle exists at ${bundle.absolutePath}, skipping..")
            state = StepState.Skipped
            return
        }

        ZipWriter(apk, /* append = */ true).use { patchedApk ->
            container.log("Writing bundle to apk")
            patchedApk.writeEntry("Aliucord.zip", bundle.readBytes(), ZipCompression.NONE)
        }
    }
}
