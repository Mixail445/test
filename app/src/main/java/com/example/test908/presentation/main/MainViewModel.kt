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
class MainViewModel @Inject constructor(private val mainUiMapper: MainUiMapper) : ViewModel() {
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
    val uiRouter: StateFlow<MainView.Model> = _uiState.asStateFlow()
    private val _uiRouter = MutableLiveData<MainView.Router>()
    val uiLabels: LiveData<MainView.Router> get() = _uiRouter
    fun onEvent(event: MainView.Event): Unit = when (event) {
        MainView.Event.OnClickCritic -> handlerOnCLickCritic()
        MainView.Event.OnClickReview -> handlerOnCLickReview()
    }
    private fun handlerOnCLickCritic() {
        _uiRouter.value = MainView.Router.NavigateTo(mainUiMapper.getIdFragmentCritic())
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
        _uiRouter.value = MainView.Router.NavigateTo(mainUiMapper.getIdFragmentReview())
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
