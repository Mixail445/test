package com.example.test908.ui.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test908.data.repository.RepositoryImpl
import com.example.test908.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class FragViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    init {
        getUsers()
    }

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getStory()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
