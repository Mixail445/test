package com.example.test908.presentation.ui.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test908.domain.model.mapFromEntity
import com.example.test908.domain.repository.ReviewRemoteSource
import com.example.test908.utils.Constant.API_KEY
import com.example.test908.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repository: ReviewRemoteSource) :
    ViewModel() {

    init {
        getStory()
    }

    fun getStory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getStory(API_KEY).mapFromEntity()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
