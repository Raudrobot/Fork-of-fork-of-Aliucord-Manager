package com.aliucord.manager.voice.ui.previews.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aliucord.manager.voice.manager.InstallerSetting
import com.aliucord.manager.voice.ui.screens.permissions.PermissionsScreenContent
import com.aliucord.manager.voice.ui.theme.ManagerTheme

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PermissionsScreenPreview() {
    ManagerTheme {
        PermissionsScreenContent(
            installer = InstallerSetting.PackageInstaller,
            openInstallersDialog = {},
            storagePermsGranted = true,
            onGrantStoragePerms = {},
            unknownSourcesPermsGranted = true,
            onGrantUnknownSourcesPerms = {},
            notificationsPermsGranted = false,
            onGrantNotificationsPerms = {},
            batteryPermsGranted = false,
            onGrantBatteryPerms = {},
            canContinue = true,
            onContinue = {},
        )
    }
}
