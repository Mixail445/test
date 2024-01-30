package com.example.test908.presentation.reviews.bottomSheetReviewFilter

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val STATE_KEY_BOTTOM_SHEET_FILTER = "filter_bottom_sheet"

class BottomSheetReviewFilterViewModel
    @AssistedInject
    constructor(
        @Assisted private val state: SavedStateHandle,
        @Assisted("filterByFavorite") private val showFavorite: Boolean?,
        @Assisted("filterByDate") private val filterByDate: Boolean?,
        @Assisted("filterByAscending") private val filterByDateByAscending: Boolean?,
    ) : ViewModel() {
        private var _favorite: Boolean = showFavorite ?: false
        private var _ascending: Boolean = filterByDateByAscending ?: false
        private var _descending: Boolean = filterByDate ?: false
        private val _uiState =
            MutableStateFlow(
                state.get<BottomSheetReviewFilterView.Model>(STATE_KEY_BOTTOM_SHEET_FILTER)
                    ?: produceInitialState(),
            )

        private fun produceInitialState() =
            BottomSheetReviewFilterView.Model(
                checkBoxByAscending = _ascending,
                checkBoxByDescending = _descending,
                checkBoxFavorite = _favorite,
            )

        fun fav() = _favorite

        fun asc() = _ascending

        fun desc() = _descending

        val uiState: StateFlow<BottomSheetReviewFilterView.Model> = _uiState.asStateFlow()
        private val _uiLabels = MutableLiveData<BottomSheetReviewFilterView.UiLabel>()
        val uiLabels: LiveData<BottomSheetReviewFilterView.UiLabel> get() = _uiLabels

        fun onEvent(event: BottomSheetReviewFilterView.Event): Unit =
            when (event) {
                BottomSheetReviewFilterView.Event.ClickOnCheckBoxAscending -> handlerClickAscending()
                BottomSheetReviewFilterView.Event.ClickOnCheckBoxDescending -> handlerClickDescending()
                BottomSheetReviewFilterView.Event.ClickOnCheckBoxFavorite -> handlerClickFavorite()
                BottomSheetReviewFilterView.Event.ClickApply -> handlerClickApply()
                BottomSheetReviewFilterView.Event.ClickRestAll -> handlerClickRestAll()
            }

        private fun handlerClickRestAll() {
            _favorite = false
            _ascending = false
            _descending = false
            _uiState.update {
                it.copy(
                    checkBoxFavorite = _favorite,
                    checkBoxByAscending = _ascending,
                    checkBoxByDescending = _descending,
                )
            }
            _uiLabels.value = BottomSheetReviewFilterView.UiLabel.ShowBackFragment
        }

        private fun handlerClickApply() {
            _uiLabels.value = BottomSheetReviewFilterView.UiLabel.ShowBackFragment
        }

        private fun handlerClickFavorite() {
            _favorite = !_favorite
            _uiState.update {
                it.copy(checkBoxFavorite = _favorite)
            }
        }

        private fun handlerClickDescending() {
            _descending = !_descending
            _uiState.update {
                it.copy(checkBoxByDescending = _descending)
            }
        }

        private fun handlerClickAscending() {
            _ascending = !_ascending
            _uiState.update {
                it.copy(checkBoxByAscending = _ascending)
            }
        }

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
                @Assisted("filterByFavorite") favorite: Boolean?,
                @Assisted("filterByDate") sortByDate: Boolean?,
                @Assisted("filterByAscending") sortedByAscending: Boolean?,
            ): BottomSheetReviewFilterViewModel
        }
    }

class LambdaFactory<T : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val create: (handle: SavedStateHandle) -> T,
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return create(handle) as T
    }
}
