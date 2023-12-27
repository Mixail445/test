package com.example.test908.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test908.presentation.common.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiLabels = MutableLiveData<HomeView.UiLabel>()
    val uiLabels: LiveData<HomeView.UiLabel> get() = _uiLabels
    fun onEvent(event: HomeView.Event): Unit = when (event) {
        HomeView.Event.OnClickBook -> handlerOnCLickCritic()
        HomeView.Event.OnClickReview -> handlerOnCLickReview()
    }
    private fun handlerOnCLickCritic() {
        _uiLabels.value = HomeView.UiLabel.ShowReviewScreen(Screens.Books)
    }
    private fun handlerOnCLickReview() {
        _uiLabels.value = HomeView.UiLabel.ShowReviewScreen(Screens.Reviews)
    }





}
