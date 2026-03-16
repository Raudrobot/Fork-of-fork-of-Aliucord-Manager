package com.aliucord.manager.voice.ui.screens.about

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.aliucord.manager.voice.network.models.Contributor
import com.aliucord.manager.voice.network.services.HttpService
import com.aliucord.manager.voice.network.utils.fold
import com.aliucord.manager.voice.ui.util.toUnsafeImmutable
import com.aliucord.manager.voice.util.launchIO
import io.ktor.client.request.url

class AboutModel(
    private val http: HttpService,
) : StateScreenModel<AboutScreenState>(AboutScreenState.Loading) {
    init {
        fetchContributors()
    }

    fun fetchContributors() = screenModelScope.launchIO {
        mutableState.value = AboutScreenState.Loading

        val response = http.request<List<Contributor>> { url(CONTRIBUTORS_API_URL) }

        mutableState.value = response.fold(
            success = { AboutScreenState.Loaded(it.toUnsafeImmutable()) },
            fail = { AboutScreenState.Failure },
        )
    }

    companion object {
        private const val CONTRIBUTORS_API_URL = "https://api.rushii.dev/aliucord/contributors"
    }
}
