package com.example.test908.presentation.ui.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test908.data.repository.RemoteData
import com.example.test908.utils.Constant.API_KEY
import com.example.test908.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repository: RemoteData) :
    ViewModel() {

    init {
        getStory()
    }

    fun getStory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getStory(API_KEY)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
