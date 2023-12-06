package com.example.test908.presentation.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.data.repository.DateUtils
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.domain.repository.review.model.mapToUi
import com.example.test908.presentation.reviews.ReviewsView.Event
import com.example.test908.presentation.reviews.ReviewsView.Model
import com.example.test908.presentation.reviews.ReviewsView.UiLabel
import com.example.test908.utils.ErrorHandel
import com.example.test908.utils.onError
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val errorHandler: ErrorHandel
) : ViewModel() {

    private var searchQuery: String = ""
    private var searchDate: LocalDateTime? = null

    private val _uiState = MutableStateFlow(
        Model(
            query = searchQuery,
            date = ""
        )
    )
    val uiState: StateFlow<Model> = _uiState.asStateFlow()
    private val _uiLabels = MutableLiveData<UiLabel>()
    val uiLabels: LiveData<UiLabel> get() = _uiLabels

    private var _reviews: List<Review> = emptyList()

    init {
        viewModelScope.launch {
            initData()
        }
    }

    private suspend fun initData() {
        requestReviews()
    }

    private fun processError(throwable: Throwable) {
        _uiLabels.value = UiLabel.ShowError(errorHandler.handleError(throwable))
    }

    private suspend fun requestReviews() {
        if (uiState.value.isLoading) return
        _uiState.update { it.copy(isLoading = true) }

        repository.getReviews()
            .onSuccess { list ->
                _reviews = list
                _uiState.update { model ->
                    model.copy(reviewItems = _reviews.map { it.mapToUi() })
                }
            }
            .onError(::processError)
        _uiState.update { it.copy(isLoading = false) }
    }

    fun onEvent(event: Event): Unit = when (event) {
        Event.OnCalendarClick -> handleOnCalendarClick()
        is Event.OnQueryReviewsTextUpdated -> onQueryReviewsTextUpdated(event.value)
        Event.OnReviewClick -> TODO()
        is Event.OnUserSelectDate -> handleUserSelectDate(event.date)
        Event.RefreshReviews -> refreshReviews()
    }

    private fun handleOnCalendarClick() {
        _uiLabels.value = UiLabel.ShowDatePicker
    }

    private fun onQueryReviewsTextUpdated(value: String) {
        searchQuery = value
        _uiState.update { it.copy(query = value) }
        val filteredReviews = filterByDateAndQuery(
            items = _reviews,
            date = searchDate,
            query = searchQuery
        ).map { it.mapToUi() }
        _uiState.update { it.copy(reviewItems = filteredReviews) }
    }

    private fun handleUserSelectDate(date: Long) {
        searchDate = DateUtils.parseLocalDateTime(date)
        val filteredReviews = filterByDateAndQuery(
            items = _reviews,
            date = searchDate,
            query = searchQuery
        ).map { it.mapToUi() }
        _uiState.update { it.copy(reviewItems = filteredReviews) }
    }

    private fun filterByDateAndQuery(
        items: List<Review>,
        date: LocalDateTime? = null,
        query: String = ""
    ) = items.filter {
        if (date == null) {
            true
        } else {
            it.publishedDate?.toLocalDate() == date.toLocalDate()
        } && it.title.contains(query, true)
    }

    private fun refreshReviews() {
        viewModelScope.launch {
            requestReviews()
        }
    }
}
