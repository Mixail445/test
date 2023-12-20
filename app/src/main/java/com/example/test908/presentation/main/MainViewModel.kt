package com.example.test908.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUiMapper: MainUiMapper

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainView.Model(
            reviewColor = mainUiMapper.orangeColor(),
            criticColor = mainUiMapper.whiteColor(),
            statusBarColor = mainUiMapper.orangeColor(),
            toolbarColorText = mainUiMapper.whiteColor(),
            toolbarBackgroundColor = mainUiMapper.orangeColor(),
            criticBackgroundColor = mainUiMapper.orangeColor(),
            reviewBackgroundColor = mainUiMapper.whiteColor()
        )
    )
    val uiState: StateFlow<MainView.Model> = _uiState.asStateFlow()
    private val _uiLabels = MutableLiveData<MainView.UiLabel>()
    val uiLabels: LiveData<MainView.UiLabel> get() = _uiLabels
    fun onEvent(event: MainView.Event): Unit = when (event) {
        MainView.Event.OnClickCritic -> handlerOnCLickCritic()
        MainView.Event.OnClickReview -> handlerOnCLickReview()
    }
    private fun handlerOnCLickCritic() {
        _uiLabels.value = MainView.UiLabel.NavigateToNext(Screens.Critics)
        _uiState.update {
            it.copy(
                reviewColor = mainUiMapper.whiteColor(),
                criticColor = mainUiMapper.blueColor(),
                statusBarColor = mainUiMapper.blueColor(),
                toolbarColorText = mainUiMapper.whiteColor(),
                toolbarBackgroundColor = mainUiMapper.blueColor(),
                criticBackgroundColor = mainUiMapper.whiteColor(),
                reviewBackgroundColor = mainUiMapper.blueColor()
        )
        }
    }
    private fun handlerOnCLickReview() {
        _uiLabels.value = MainView.UiLabel.NavigateToNext(Screens.Reviews)
        _uiState.update {
            it.copy(
                reviewColor = mainUiMapper.orangeColor(),
                criticColor = mainUiMapper.whiteColor(),
                statusBarColor = mainUiMapper.orangeColor(),
                toolbarColorText = mainUiMapper.whiteColor(),
                toolbarBackgroundColor = mainUiMapper.orangeColor(),
                criticBackgroundColor = mainUiMapper.orangeColor(),
                reviewBackgroundColor = mainUiMapper.whiteColor()
        )
        }
    }
}
