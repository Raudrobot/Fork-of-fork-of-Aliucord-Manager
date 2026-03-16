package com.aliucord.manager.voice.ui.screens.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.aliucord.manager.voice.ui.util.DiscordVersion

@Immutable
data class InstallData(
    val name: String,
    val packageName: String,
    val version: DiscordVersion,
    val icon: BitmapPainter,
    val isUpToDate: Boolean?,
)
