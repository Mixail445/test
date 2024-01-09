package com.example.test908.presentation.detalReview

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STATE_DETAIL = "state_detail_fragment"

class DetailReviewViewModel @AssistedInject constructor(
    @Assisted private val index: String?,
    @Assisted state: SavedStateHandle,
    private val repository: ReviewRepository
) : ViewModel() {
    private var _reviews: List<Review> = emptyList()
    private fun produceInitialState() =
        DetailReviewView.Model(
            photo = "",
            text = ""
        )

    private val _uiState =
        MutableStateFlow(state.get<DetailReviewView.Model>(STATE_DETAIL) ?: produceInitialState())
    val uiState: StateFlow<DetailReviewView.Model> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            index?.toInt()?.let { getDataFromDb(it) }
        }
    }

    private suspend fun getDataFromDb(index: Int) {
        repository.fetchReviews().map { _reviews = it as List<Review> }.stateIn(viewModelScope)
        _uiState.update { model ->
            model.copy(photo = _reviews[index].multimedia, text = _reviews[index].title)
        }

    }

    @AssistedFactory
    interface Factory {
        fun build(stateHandle: SavedStateHandle, index: String?): DetailReviewViewModel
    }
}
class LambdaFactory<T : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val create: (handle: SavedStateHandle) -> T
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return create(handle) as T
    }
}
