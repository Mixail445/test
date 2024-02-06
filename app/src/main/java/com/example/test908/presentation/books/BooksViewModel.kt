package com.example.test908.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.domain.repository.books.BooksRepository
import com.example.test908.domain.repository.books.model.Books
import com.example.test908.domain.repository.featureFlag.FeatureFlagRepository
import com.example.test908.domain.repository.featureFlag.FeatureFlags
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.presentation.common.BaseItem
import com.example.test908.presentation.common.Screens
import com.example.test908.utils.DateUtils
import com.example.test908.utils.onError
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch


interface BooksStore {
    data class State(
        val isLoading: Boolean = false,
        val books: List<Books> = emptyList(),
        val bannerUi: List<BannerUi> = emptyList(),
        val featureFlags: FeatureFlags? = null,
        val limitedSeriesBannerStatus: LimitedSeriesRegistrationStatus? = null
    )
}

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: BooksRepository,
    private val featureFlagRepository: FeatureFlagRepository,
    private val limitedSeriesRepository: LimitedSeriesRepository
) : ViewModel() {
    private val state = MutableStateFlow(BooksStore.State())
    val uiState = state.map { it.mapToUi() }
    private val _uiLabels = MutableLiveData<BooksView.UiLabel>()
    val uiLabels: LiveData<BooksView.UiLabel> get() = _uiLabels

    init {
        viewModelScope.launch {
            initData()
        }
    }

    private suspend fun initData() = coroutineScope {
        DateUtils.getCurrentDate()?.let { requestBooks(it) }
        val featureFlagsJob = launch {
            val value = featureFlagRepository.obtainFlags()
            value.onSuccess { flag ->
                state.update { it.copy(featureFlags = flag) }
            }

        }
        val limitedSeriesBannerStatusJob = launch {
            val value = limitedSeriesRepository.status()
            state.update { it.copy(limitedSeriesBannerStatus = value) }
        }
        joinAll(featureFlagsJob, limitedSeriesBannerStatusJob)

    }

    fun onEvent(event: BooksView.Event): Unit = when (event) {
        is BooksView.Event.OnClickNestedRc -> onClickNestedRc(event.url)
        BooksView.Event.RefreshBooks -> refreshBooks()
        is BooksView.Event.OnClickBannerToNextFragment -> handlerClickBanner(event.id)
        is BooksView.Event.OnClickBottomBanner -> handlerOnClickBannerStarted()
        is BooksView.Event.OnClickBannerGreen -> handlerClickBannerGreen()
    }

    private fun handlerClickBannerGreen() {
        state.update {
            it.copy(limitedSeriesBannerStatus = LimitedSeriesRegistrationStatus.COMPLETED)
        }
    }

    private fun handlerOnClickBannerStarted() {
        state.update {
            it.copy(
                bannerUi = listOf(
                    BannerUi(
                itemId = "1",
                title = "Limited Series!!!",
                body = "Start you treasure with limited book series with our new service",
                bvVisible = false,
                cBVisible = false,
                pbVisible = true,
                textBottom = ""
            )
                )
            )
        }
        viewModelScope.launch {
            // requestHelper()
            limitedSeriesRepository.startRegistration()
            state.update {
                it.copy(
                    limitedSeriesBannerStatus = LimitedSeriesRegistrationStatus.STARTED
                )
            }
            _uiLabels.value = BooksView.UiLabel.ShowBottomSheetDialog
        }
    }
    private suspend fun requestHelper() {
        var num = 0
        while (num++ < 5) {
            delay(1000)
             limitedSeriesRepository.status()
             if (limitedSeriesRepository.status() == LimitedSeriesRegistrationStatus.COMPLETED) {
                 break
             }
        }
    }
    private fun onClickNestedRc(url: String) {
        _uiLabels.value = BooksView.UiLabel.ShowBrowse(url)
    }

    private fun handlerClickBanner(id: String) {
        _uiLabels.value = BooksView.UiLabel.ShowFragmentWithDate(Screens.DateFragment, id)
    }

    private suspend fun requestBooks(data: String) {
        state.update { it.copy(isLoading = true) }
        repository.getBooksRemote(data)
            .onSuccess { response ->
                state.update {
                    it.copy(
                        books = response,
                        isLoading = false,
                        bannerUi = listOf(
                            BannerUi(
                                itemId = "1",
                            title = "Limited Series!!!",
                            body = "Start you treasure with limited book series with our new service",
                            bvVisible = true,
                            cBVisible = false,
                            pbVisible = false,
                            textBottom = "Get started"
                            )
                        )
                    )
                }
            }
            .onError {
                state.update { it.copy(isLoading = false) }
            }
    }

    private fun refreshBooks() {
        viewModelScope.launch {
            requestBooks(DateUtils.getCurrentDate().toString())
        }
    }
}

private fun BooksStore.State.mapToUi(): BooksView.Model {
    return BooksView.Model(
        isLoading = isLoading,
        items = mapToItems()
    )
}

private fun BooksStore.State.mapToItems(): List<BaseItem> {
    val items = mutableListOf<BaseItem>()

    if (featureFlags?.isLimitedSeriesEnabled == true) {
        when (limitedSeriesBannerStatus) {
            LimitedSeriesRegistrationStatus.NOT_STARTED -> {
                items += bannerUi
            }
            LimitedSeriesRegistrationStatus.STARTED -> {
               items += BannerUiEmpty(
                   itemId = "1"
               )
            }
            LimitedSeriesRegistrationStatus.COMPLETED -> {
                items += BlackBannerUi(
                    itemId = "1"
                )
            }
            LimitedSeriesRegistrationStatus.NOT_ALLOWED, null -> Unit
        }
    }
    items += books.map { it.mapToUi() }
    return items
}
