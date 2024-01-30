package com.example.test908.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.test908.presentation.common.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
private const val STATE_KEY = "home_state"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUiMapper: HomeUiMapper,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiLabels = MutableLiveData<HomeView.UiLabel>()
    val uiLabels: LiveData<HomeView.UiLabel> get() = _uiLabels
    fun onEvent(event: HomeView.Event): Unit = when (event) {
        HomeView.Event.OnClickCritic -> handlerOnCLickCritic()
        HomeView.Event.OnClickReview -> handlerOnCLickReview()
    }
    
    private val _uiState =
        MutableStateFlow(stateHandle.get<HomeView.Model>(STATE_KEY) ?: produceInitialState())
    val uiState: StateFlow<HomeView.Model> = _uiState
    private fun produceInitialState() = with(homeUiMapper) {
        HomeView.Model(
            reviewColor = orangeColor(),
            criticColor = whiteColor(),
            statusBarColor = orangeColor(),
            toolbarColorText = whiteColor(),
            toolbarBackgroundColor = orangeColor(),
            criticBackgroundColor = orangeColor(),
            reviewBackgroundColor = whiteColor()
        )
    }
    private fun handlerOnCLickCritic() = with(homeUiMapper) {
        _uiLabels.value = HomeView.UiLabel.ShowReviewScreen(Screens.Critics)
        _uiState.update {
            it.copy(
                reviewColor = whiteColor(),
                criticColor = blueColor(),
                statusBarColor = blueColor(),
                toolbarColorText = whiteColor(),
                toolbarBackgroundColor = blueColor(),
                criticBackgroundColor = whiteColor(),
                reviewBackgroundColor = blueColor()
            )
        }
    }
    private fun handlerOnCLickReview()= with(homeUiMapper){
        _uiLabels.value = HomeView.UiLabel.ShowReviewScreen(Screens.Reviews)
        _uiState.update {
            it.copy(
                reviewColor = orangeColor(),
                criticColor = whiteColor(),
                statusBarColor = orangeColor(),
                toolbarColorText = whiteColor(),
                toolbarBackgroundColor = orangeColor(),
                criticBackgroundColor = orangeColor(),
                reviewBackgroundColor = whiteColor()
            )
        }
    }





}
