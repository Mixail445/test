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
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repository: ReviewRemoteSource) :
    ViewModel() {
    private val localOriginalList = ArrayList<StoryUi>()
    fun getStorySearch(query: String, point: String = "") = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = search(query, point = point)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }
    private suspend fun search(query: String, point: String = ""): List<StoryUi> {
        val filteredList = ArrayList<StoryUi>()
        if (localOriginalList.isEmpty()) {
            localOriginalList.addAll(repository.getStory(API_KEY).mapToUi().results)
        } else if (point == "1") { filteredList.addAll(
            repository.getStory(API_KEY).mapToUi().results
        ) }
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
}
