package com.example.test908.presentation.ui.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.model.mapToUi
import com.example.test908.domain.repository.ReviewRemoteSource
import com.example.test908.presentation.reviewList.StoryUi
import com.example.test908.utils.Constant.API_KEY
import com.example.test908.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repository: ReviewRemoteSource) :
    ViewModel() {

    init {
        viewModelScope.launch {
            getList()
        }
    }
    private suspend fun getList() {
        val originalList = repository.getStory(API_KEY).mapToUi().results
        localOriginalList.addAll(originalList)
    }
    fun getStorySearch(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = search(query = query)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }
    private val localOriginalList = ArrayList<StoryUi>()
    fun getStory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getStoryFun()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }

    private fun search(query: String): List<StoryUi> {
        val filteredList = ArrayList<StoryUi>()
        viewModelScope.launch {
            for (item in localOriginalList) {
                if (item.byline.lowercase(Locale.ROOT).contains(
                        query.lowercase(Locale.ROOT)
                    ) || item.abstract.lowercase(Locale.ROOT).contains(
                        query.lowercase(Locale.ROOT)
                    ) || item.publishedDate.lowercase(Locale.ROOT).contains(
                        query.lowercase(Locale.ROOT)

                    )
                ) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList
    }
    private suspend fun getStoryFun(): List<StoryUi> {
        val filteredList = ArrayList<StoryUi>()
        filteredList.addAll(
            repository.getStory(API_KEY).mapToUi().results
        )
        return filteredList
    }
}
