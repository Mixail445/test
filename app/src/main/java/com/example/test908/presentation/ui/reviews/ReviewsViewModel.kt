package com.example.test908.presentation.ui.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.model.NumResult
import com.example.test908.domain.model.mapToUi
import com.example.test908.domain.repository.ReviewRemoteSource
import com.example.test908.presentation.reviewList.ReviewUi
import com.example.test908.presentation.ui.reviews.ReviewsView.Event
import com.example.test908.presentation.ui.reviews.ReviewsView.Model
import com.example.test908.presentation.ui.reviews.ReviewsView.UiLabel
import com.example.test908.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repository: ReviewRemoteSource) :
    ViewModel() {

    private val _uiState = MutableStateFlow(Model())
    val uiState: StateFlow<Model> = _uiState.asStateFlow()
    private val _uiLabels = MutableLiveData<UiLabel>()
    val uiLabels: LiveData<UiLabel> get() = _uiLabels

    private var _reviews: NumResult? = null
    private var _errorMessage: String? = ""

    init {
        viewModelScope.launch {
            initData()
        }
    }
    private suspend fun initData() {
        repository.getStory().collect { resource ->
            when (resource) {
                is Resource.Error -> _errorMessage = resource.message
                is Resource.Success -> { _reviews = resource.data }
            }
            _uiState.update { model ->
                model.copy(
                    reviewItems = resource.data?.results?.map { it.mapToUi() } ?: emptyList(),
                    isErrorMessage = _errorMessage,
                    isLoading = false
                )
            }
        }
    }

    fun onEvent(event: Event): Unit = when (event) {
        Event.OnCalendarClick -> handleOnCalendarClick()
        is Event.OnQueryReviewsTextUpdated -> filterByQueryReviews(event.values)
        Event.OnReviewClick -> TODO()
        is Event.OnUserSelectDate -> filterByDateReviews(event.date)
        Event.RefreshReviews -> refreshReviews()
    }

    private fun handleOnCalendarClick() {
        _uiLabels.value = UiLabel.ShowCalendar
    }
    private fun refreshReviews() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    reviewItems = _reviews?.results?.map { it.mapToUi() } ?: emptyList(),
                    isLoading = false,
                    search = "",
                    date = ""
                )
            }
        }
    }

    private fun filterByQueryReviews(query: String) {
        viewModelScope.launch {
            val filteredList = ArrayList<ReviewUi>()
            for (item in _reviews?.results?.map { it.mapToUi() } ?: emptyList()) {
                if (item.byline.lowercase(Locale.ROOT).contains(
                        query.lowercase(Locale.ROOT)
                    ) || item.abstract.lowercase(Locale.ROOT).contains(
                        query.lowercase(Locale.ROOT)
                    )
                ) {
                    filteredList.add(item)
                }
            }
            _uiState.update {
                it.copy(
                    reviewItems = filteredList,
                    search = query,
                    date = "",
                    isLoading = false
                )
            }
        }
    }
    private fun filterByDateReviews(query: String) {
        val filteredList = ArrayList<ReviewUi>()
        for (item in _reviews?.results?.map { it.mapToUi() } ?: emptyList()) {
            if (item.publishedDate.lowercase(Locale.ROOT).contains(
                    query.lowercase(Locale.ROOT)
                )
            ) {
                filteredList.add(item)
            }
        }
        _uiState.update {
            it.copy(
                reviewItems = filteredList,
                date = query,
                search = "",
                isLoading = false
            )
        }
    }
}
