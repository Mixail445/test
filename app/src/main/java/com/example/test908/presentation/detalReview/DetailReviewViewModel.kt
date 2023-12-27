package com.example.test908.presentation.detalReview

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.presentation.common.Screens
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
    @Assisted private val id: String?,
    @Assisted state: SavedStateHandle,
    private val repository: ReviewRepository
) : ViewModel() {
    private lateinit var _reviews: Review
    private fun produceInitialState() =
        DetailReviewView.Model(
            photo = "",
            text = ""
        )

    private val _uiState =
        MutableStateFlow(state.get<DetailReviewView.Model>(STATE_DETAIL) ?: produceInitialState())
    val uiState: StateFlow<DetailReviewView.Model> = _uiState.asStateFlow()
    private val _uiLabels = MutableLiveData<DetailReviewView.UiLabel>()
    val uiLabels: LiveData<DetailReviewView.UiLabel> get() = _uiLabels
    fun onEvent(event: DetailReviewView.Event): Unit = when (event) {
        DetailReviewView.Event.OnClickBackButton -> toHomeFragment()
    }

    private fun toHomeFragment() {
        _uiLabels.value = DetailReviewView.UiLabel.ShowHomeFragment(Screens.HomeFragment)
    }
    init {
        viewModelScope.launch {
            id?.let { getDataFromDb(id) }
        }
    }

    private suspend fun getDataFromDb(index: String) {
       repository.fetchReviewsById(index).map { _reviews = it }.stateIn(viewModelScope)
        _uiState.update { model ->
            model.copy(photo = _reviews.multimedia, text = _reviews.byline)
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
