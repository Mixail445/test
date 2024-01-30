package com.example.test908.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.domain.repository.books.BooksRepository
import com.example.test908.domain.repository.featureFlag.FeatureFlagRepository
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.presentation.common.Screens
import com.example.test908.utils.DateUtils
import com.example.test908.utils.onError
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel
    @Inject
    constructor(
        private val repository: BooksRepository,
        private val featureFlagRepository: FeatureFlagRepository,
        private val limitedSeriesRepository: LimitedSeriesRepository,
        private val booksUiMapper: BooksUiMapper,
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

        private suspend fun initData() =
            coroutineScope {
                DateUtils.getCurrentDate()?.let { requestBooks(it) }
                val featureFlagsJob =
                    launch {
                        val value = featureFlagRepository.obtainFlags()
                        value.onSuccess { flag ->
                            state.update { it.copy(featureFlags = flag) }
                        }
                    }
                val limitedSeriesBannerStatusJob =
                    launch {
                        val value = limitedSeriesRepository.status()
                        value.onSuccess { limitedSeriesStatus ->
                            state.update { it.copy(limitedSeriesBannerStatus = limitedSeriesStatus) }
                        }
                    }
                joinAll(featureFlagsJob, limitedSeriesBannerStatusJob)
            }

        fun onEvent(event: BooksView.Event): Unit =
            when (event) {
                is BooksView.Event.OnClickNestedRc -> onClickNestedRc(event.url)
                BooksView.Event.RefreshBooks -> refreshBooks()
                is BooksView.Event.OnClickBannerToNextFragment -> handlerClickBanner(event.id)
                is BooksView.Event.OnClickBottomBanner -> handlerOnClickBannerStarted()
                is BooksView.Event.OnClickBannerGreen -> handlerClickBannerGreen()
            }

        private fun handlerClickBannerGreen() {
            viewModelScope.launch {
                featureFlagRepository.obtainFlags().onSuccess {
                    state.update {
                        it.copy(
                            limitedSeriesBannerStatus = LimitedSeriesRegistrationStatus.COMPLETED,
                            blackBannerUi =
                                listOf(
                                    BlackBannerUi(
                                        itemId = "1",
                                        textBottom = booksUiMapper.textBottomBlackBanner,
                                    ),
                                ),
                        )
                    }
                }.onError { state.update { it.copy(isLoading = false) } }
            }
        }

        private fun handlerOnClickBannerStarted() {
            viewModelScope.launch {
                featureFlagRepository.obtainFlags().onSuccess {
                    if (it.isLimitedSeriesEnabled) {
                        state.update { state ->
                            state.copy(
                                limitedSeriesBannerStatus = LimitedSeriesRegistrationStatus.STARTED,
                                bannerUiEmpty =
                                    listOf(
                                        BannerUiEmpty(
                                            itemId = "1",
                                            textBottom = booksUiMapper.textEmptyBannerBottom,
                                            titleText = booksUiMapper.title,
                                            bodyText = booksUiMapper.body,
                                        ),
                                    ),
                            )
                        }
                    }
                    _uiLabels.value = BooksView.UiLabel.ShowBottomSheetDialog
                }.onError { state.update { it.copy(isLoading = false) } }
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
                            bannerUi =
                                listOf(
                                    BannerUi(
                                        itemId = "1",
                                        title = booksUiMapper.title,
                                        body = booksUiMapper.body,
                                        bvVisible = true,
                                        cBVisible = false,
                                        pbVisible = false,
                                        textBottom = booksUiMapper.textBottom,
                                    ),
                                ),
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
