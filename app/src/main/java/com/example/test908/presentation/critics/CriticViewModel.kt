package com.example.test908.presentation.critics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test908.presentation.common.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CriticViewModel @Inject constructor() : ViewModel() {
    private val _uiLabels = MutableLiveData<CriticView.UiLabel>()
    val uiLabels: LiveData<CriticView.UiLabel> get() = _uiLabels


}
