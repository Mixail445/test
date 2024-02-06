package com.example.test908.presentation.books.bottomSheetDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.data.repository.books.limitedSeries.PostCompleteRegistrationsParams
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STATE_KEY_BOTTOM_SHEET_DIALOG = "bottom_sheet_dialog"

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val limitedSeriesRepository: LimitedSeriesRepository,
    state: SavedStateHandle
) : ViewModel() {
    private suspend fun sendPromoCode(promoCode: String) {
        limitedSeriesRepository.postCompleteRegistrationParams(
            PostCompleteRegistrationsParams(promoCode)
        )
    }
    private var flag = true
    private val _uiState = MutableStateFlow(
        state.get<BottomDialogView.Model>(STATE_KEY_BOTTOM_SHEET_DIALOG) ?: produceInitialState()
    )
    private fun produceInitialState() = BottomDialogView.Model(
        promoCodeETVisibility = false,
        checkBoxIsActive = false
    )

    val uiState: StateFlow<BottomDialogView.Model> = _uiState.asStateFlow()

    private val _uiLabels = MutableLiveData<BottomDialogView.UiLabel>()
    val uiLabels: LiveData<BottomDialogView.UiLabel> get() = _uiLabels

    fun onEvent(event: BottomDialogView.Event): Unit = when (event) {
        is BottomDialogView.Event.OnClickBottom -> handlerClickBottom(event.promoCode)
        BottomDialogView.Event.OnClickCheckBox -> handlerClickCheckBox()
    }

    private fun handlerClickCheckBox() {
        if (flag) {
            _uiState.update {
                it.copy(
                    promoCodeETVisibility = true,
                    checkBoxIsActive = true
                )
            }
            flag = false
        } else {
            _uiState.update {
                it.copy(
                    promoCodeETVisibility = false,
                    checkBoxIsActive = false
                )
            }
           flag = true
        }

    }
    private fun handlerClickBottom(promoCode: String) {
        viewModelScope.launch {
            sendPromoCode(promoCode)
            _uiLabels.value = BottomDialogView.UiLabel.CloseDialog
        }
    }

}
