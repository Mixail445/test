package com.example.test908.presentation.books.bottomSheetDialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.data.repository.books.limitedSeries.PostCompleteRegistrationsParams
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.utils.onError
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STATE_KEY_BOTTOM_SHEET_DIALOG = "bottom_sheet_dialog"

@HiltViewModel
class BottomSheetViewModel
    @Inject
    constructor(
        private val limitedSeriesRepository: LimitedSeriesRepository,
        private val bottomSheetMapperUi: BottomSheetMapperUi,
        state: SavedStateHandle,
    ) : ViewModel() {
        private suspend fun sendPromoCode(promoCode: String) {
            limitedSeriesRepository.postCompleteRegistrationParams(
                PostCompleteRegistrationsParams(promoCode),
            ).onSuccess { }.onError { Log.e("error", "error ${it.message}") }
        }

        private var flag = true
        private val _uiState =
            MutableStateFlow(
                state.get<BottomDialogView.Model>(STATE_KEY_BOTTOM_SHEET_DIALOG)
                    ?: produceInitialState(),
            )

        private fun produceInitialState() =
            BottomDialogView.Model(
                promoCodeETVisibility = false,
                checkBoxIsActive = false,
                bottomText = bottomSheetMapperUi.textBottom,
                titleText = bottomSheetMapperUi.title,
                bodyText = bottomSheetMapperUi.body,
                editText = bottomSheetMapperUi.editText,
            )

        val uiState: StateFlow<BottomDialogView.Model> = _uiState.asStateFlow()

        private val _uiLabels = MutableLiveData<BottomDialogView.UiLabel>()
        val uiLabels: LiveData<BottomDialogView.UiLabel> get() = _uiLabels

        fun onEvent(event: BottomDialogView.Event): Unit =
            when (event) {
                is BottomDialogView.Event.OnClickBottom -> handlerClickBottom(event.promoCode)
                BottomDialogView.Event.OnClickCheckBox -> handlerClickCheckBox()
            }

        private fun handlerClickCheckBox() {
            if (flag) {
                _uiState.update {
                    it.copy(
                        promoCodeETVisibility = true,
                        checkBoxIsActive = true,
                    )
                }
                flag = false
            } else {
                _uiState.update {
                    it.copy(
                        promoCodeETVisibility = false,
                        checkBoxIsActive = false,
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
