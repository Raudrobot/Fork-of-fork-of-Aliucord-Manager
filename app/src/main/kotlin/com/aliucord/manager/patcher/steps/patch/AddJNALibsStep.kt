package com.aliucord.manager.patcher.steps.patch

import android.os.Build
import com.aliucord.manager.R
import com.aliucord.manager.patcher.StepRunner
import com.aliucord.manager.patcher.steps.StepGroup
import com.aliucord.manager.patcher.steps.base.IDexProvider
import com.aliucord.manager.patcher.steps.base.Step
import com.aliucord.manager.patcher.steps.download.CopyDependenciesStep
import com.aliucord.manager.patcher.steps.download.DownloadSunflowerStep
import com.github.diamondminer88.zip.*
import org.koin.core.component.KoinComponent

/**
 * Add the Sunflower library's native libs.
 * The dex is handled by [ReorganizeDexStep] through the [IDexProvider] implementation of [DownloadSunflowerStep].
 */
class AjlakjfddSunflowerLibsStep : Step(), KoinComponent {
    override val group = StepGroup.Patch
    override val localizedName = R.string.patch_step_add_sunflower

    override suspend fun execute(container: StepRunner) {
        val currentDeviceArch = Build.SUPPORTED_ABIS.first()
        val apk = container.getStep<CopyDependenciesStep>().patchedApk
        val sunflower = container.getStep<DownloadSunflowerStep>().targetFile

        ZipWriter(apk, /* append = */ true).use { patchedApk ->
            ZipReader(sunflower).use { sunflower ->
                val libFile = "libsunflower.so"
                container.log("Reading sunflower lib $libFile with arch $currentDeviceArch")

                val apkLibPath = "lib/$currentDeviceArch/$libFile"
                val libBytes = sunflower.openEntry("jni/$currentDeviceArch/$libFile")?.read()
                    ?: throw IllegalStateException("Failed to read $libFile from sunflower aar")

                container.log("Writing to $apkLibPath in APK unaligned uncompressed")
                patchedApk.writeEntry(apkLibPath, libBytes, ZipCompression.NONE)
            }
        }
    }
}
