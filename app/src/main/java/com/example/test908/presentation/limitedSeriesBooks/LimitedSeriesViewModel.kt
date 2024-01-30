package com.example.test908.presentation.limitedSeriesBooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.presentation.common.Screens
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LimitedSeriesViewModel
    @Inject
    constructor(
        private val limitedSeriesRepository: LimitedSeriesRepository,
    ) : ViewModel() {
        private val _uiLabels = MutableLiveData<LimitedSeriesView.UiLabel>()
        val uiLabels: LiveData<LimitedSeriesView.UiLabel> get() = _uiLabels
        private val state = MutableStateFlow(LimitedSeriesBooks.State())
        val uiState = state.map { it.mapToUi() }

        private suspend fun getData() {
            limitedSeriesRepository.getBooksOffer().onSuccess { response ->
                state.update {
                    it.copy(
                        books = response,
                        isLoading = false,
                    )
                }
            }
        }

        init {
            viewModelScope.launch {
                getData()
            }
        }

        fun onEvent(event: LimitedSeriesView.Event): Unit =
            when (event) {
                LimitedSeriesView.Event.OnClickBackButton -> toHomeFragment()
            }

        private fun toHomeFragment() {
            _uiLabels.value = LimitedSeriesView.UiLabel.ShowHomeFragment(Screens.HomeFragment)
        }
    }
