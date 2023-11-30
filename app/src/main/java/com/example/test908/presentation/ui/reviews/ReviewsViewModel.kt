package com.example.test908.presentation.ui.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.model.mapToUi
import com.example.test908.domain.repository.ReviewRemoteSource
import com.example.test908.presentation.ReviewUiState
import com.example.test908.presentation.reviewList.StoryUi
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

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()
    private val localOriginalList = ArrayList<StoryUi>()
    private suspend fun getList() {
        val reviewItems = repository.getStory().mapToUi().results
        localOriginalList.addAll(reviewItems)
    }

    init {
        viewModelScope.launch {
            getList()
            getStory()
        }
    }

    private fun getStory() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    reviewItems = localOriginalList,
                    isLoading = false
                )
            }
        }
    }
    fun refreshStory() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    reviewItems = repository.getStory().mapToUi().results,
                    isLoading = false,
                    search = "",
                    date = ""
                )
            }
        }
    }

    fun searchByText(query: String) {
        viewModelScope.launch {
            val filteredList = ArrayList<StoryUi>()
            for (item in localOriginalList) {
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
    fun searchByDate(query: String) {
        val filteredList = ArrayList<StoryUi>()
        for (item in localOriginalList) {
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
