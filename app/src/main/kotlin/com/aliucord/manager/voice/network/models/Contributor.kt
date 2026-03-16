package com.aliucord.manager.voice.network.models

import androidx.compose.runtime.Immutable
import com.aliucord.manager.voice.util.serialization.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Contributor(
    val username: String,
    val avatarUrl: String,
    val commits: Int,
    @Serializable(with = ImmutableListSerializer::class)
    val repositories: ImmutableList<Repository>,
) {
    @Immutable
    @Serializable
    data class Repository(
        val name: String,
        val commits: Int,
    )
}
