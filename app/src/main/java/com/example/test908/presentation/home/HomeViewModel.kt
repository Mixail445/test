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
    private fun produceInitialState() = HomeView.Model(
        reviewColor = homeUiMapper.orangeColor(),
        criticColor = homeUiMapper.whiteColor(),
        statusBarColor = homeUiMapper.orangeColor(),
        toolbarColorText = homeUiMapper.whiteColor(),
        toolbarBackgroundColor = homeUiMapper.orangeColor(),
        criticBackgroundColor = homeUiMapper.orangeColor(),
        reviewBackgroundColor = homeUiMapper.whiteColor()
    )
    private fun handlerOnCLickCritic() {
        _uiLabels.value = HomeView.UiLabel.ShowReviewScreen(Screens.Critics)
        _uiState.update {
            it.copy(
                reviewColor = homeUiMapper.whiteColor(),
                criticColor = homeUiMapper.blueColor(),
                statusBarColor = homeUiMapper.blueColor(),
                toolbarColorText = homeUiMapper.whiteColor(),
                toolbarBackgroundColor = homeUiMapper.blueColor(),
                criticBackgroundColor = homeUiMapper.whiteColor(),
                reviewBackgroundColor = homeUiMapper.blueColor()
            )
        }
    }
    private fun handlerOnCLickReview() {
        _uiLabels.value = HomeView.UiLabel.ShowReviewScreen(Screens.Reviews)
        _uiState.update {
            it.copy(
                reviewColor = homeUiMapper.orangeColor(),
                criticColor = homeUiMapper.whiteColor(),
                statusBarColor = homeUiMapper.orangeColor(),
                toolbarColorText = homeUiMapper.whiteColor(),
                toolbarBackgroundColor = homeUiMapper.orangeColor(),
                criticBackgroundColor = homeUiMapper.orangeColor(),
                reviewBackgroundColor = homeUiMapper.whiteColor()
            )
        }
    }





}
