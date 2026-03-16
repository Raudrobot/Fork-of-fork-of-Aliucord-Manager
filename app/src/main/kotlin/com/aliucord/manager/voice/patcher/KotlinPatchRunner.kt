package com.aliucord.manager.voice.patcher

import com.aliucord.manager.voice.patcher.steps.download.*
import com.aliucord.manager.voice.patcher.steps.install.*
import com.aliucord.manager.voice.patcher.steps.patch.*
import com.aliucord.manager.voice.patcher.steps.prepare.*
import com.aliucord.manager.voice.ui.screens.patchopts.PatchOptions
import kotlinx.collections.immutable.persistentListOf

/**
 * Used for installing the old Kotlin Discord app.
 */
class KotlinPatchRunner(
    options: PatchOptions,
) : StepRunner() {
    override val steps = persistentListOf(
        // Prepare
        FetchInfoStep(),
        DowngradeCheckStep(options),
        RestoreDownloadsStep(),

        // Download
        DownloadDiscordStep(),
        DownloadInjectorStep(options.customInjector),
        DownloadAliuhookStep(),
        DownloadKotlinStep(),
        DownloadPatchesStep(options.customPatches),
        CopyDependenciesStep(),

        // Patch
        SmaliPatchStep(),
        PatchIconsStep(options),
        PatchManifestStep(options),
        PatchCertsStep(),
        ReorganizeDexStep(),
        AddAliuhookLibsStep(),
        AddSunflowerStep(),
        AddBundledAliucordStep(),
        SaveMetadataStep(options),

        // Install
        AlignmentStep(),
        SigningStep(options),
        InstallStep(options),
        CleanupStep(),
    )
}
