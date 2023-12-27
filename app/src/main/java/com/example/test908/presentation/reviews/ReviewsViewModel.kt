package com.example.test908.presentation.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.presentation.common.Screens
import com.example.test908.presentation.reviews.ReviewsView.Event
import com.example.test908.presentation.reviews.ReviewsView.Model
import com.example.test908.presentation.reviews.ReviewsView.UiLabel
import com.example.test908.utils.DateUtils
import com.example.test908.utils.ErrorHandel
import com.example.test908.utils.FavoriteLocalSource
import com.example.test908.utils.UtilTimer
import com.example.test908.utils.onError
import com.example.test908.utils.onSuccess
import com.example.test908.utils.toEpochMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TIMER_KEY = "timer_key"
private const val STATE_KEY_REVIEW = "FRAGMENT_STATE_KEY"

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val errorHandler: ErrorHandel,
    private val utilTimer: UtilTimer,
    private val reviewUiMapper: ReviewUiMapper,
    private val state: SavedStateHandle,
    private val favoriteLocalSource: FavoriteLocalSource
) : ViewModel() {
    private var searchQuery: String = ""
    private var searchDateStart: LocalDateTime? = null
    private var searchDateEnd: LocalDateTime? = null
    private var _reviews: List<Review> = emptyList()

    private val _favorites: MutableSet<String> = mutableSetOf("235", "236")

    lateinit var id: String

    private val _uiState = MutableStateFlow(
        state.get<Model>(STATE_KEY_REVIEW) ?: produceInitialState()
    )


    private fun produceInitialState() = Model(
        query = searchQuery,
        date = "",
        timer = state[TIMER_KEY]
    )

    val uiState: StateFlow<Model> = _uiState.asStateFlow()
    private val _uiLabels = MutableLiveData<UiLabel>()
    val uiLabels: LiveData<UiLabel> get() = _uiLabels

    init {
        showTimer()
        viewModelScope.launch {
            initData()
        }
        (state.get<String>(TIMER_KEY))?.let { time ->
            _uiState.update { it.copy(timer = reviewUiMapper.convertTimer(time.toLong())) }
        }

    }
    fun shared() {
        favoriteLocalSource.setId("123")
      Log.d("123", favoriteLocalSource.getId("123").toString())
    }
    private suspend fun initData() {
        getDataFromDb()
        // requestReviews()
    }

    private fun processError(throwable: Throwable) {
        _uiLabels.value = UiLabel.ShowError("Error", errorHandler.handleError(throwable))
    }

    private suspend fun requestReviews() {
        if (uiState.value.isLoading) return
        _uiState.update { it.copy(isLoading = true) }

        repository.getReviewsRemote()
            .onSuccess { list ->
                _reviews = list as List<Review>
                val filteredReviews = filterByDateAndQuery(
                    items = _reviews,
                    dateStart = searchDateStart,
                    dateEnd = searchDateEnd,
                    query = searchQuery
                ).map { it.mapToUi() }

                _uiState.update { model ->
                    model.copy(reviewItems = filteredReviews)
                }
            }
            .onError(::processError)
        _uiState.update { it.copy(isLoading = false) }
    }

    private suspend fun getDataFromDb() {
            repository.fetchReviews().map { listReview ->
                _reviews = listReview as List<Review>

            }.stateIn(viewModelScope)

        val filteredReviews = filterByDateAndQuery(
            items = _reviews,
            dateStart = searchDateStart,
            dateEnd = searchDateEnd,
            query = searchQuery
        ).map { it.mapToUi() }


        _uiState.update { model ->
            model.copy(reviewItems = filteredReviews)
        }
    }
    fun addFavorite(id: String) {
//        if (_favorites.isEmpty()) {
//            sharedPreferencesOne.getData("key")?.item?.let { _favorites.addAll(it) }
//        }
        if (_favorites.contains(id)) {
            _favorites.remove(id)
        } else {
            _favorites.add(id)
        }
        Log.d("id", id.toString())
        val list = _reviews.map { it.mapToUi() }.toMutableList()
        _favorites.forEach { id ->
            list.forEachIndexed { index, reviewUi ->
                reviewUi.takeIf { it.itemId == id }?.let {
                    Log.d("index", index.toString())
                    list[index] = it.copy(favorite = reviewUiMapper.checkFlagForAdapter(true))
                }
            }
        }
            _uiState.update {
                it.copy(reviewItems = list)
            }

    }

    fun onEvent(event: Event): Unit = when (event) {
        Event.OnCalendarClick -> handleOnCalendarClick()
        is Event.OnQueryReviewsTextUpdated -> onQueryReviewsTextUpdated(event.value)
        is Event.OnReviewClick -> toDetailReview(event.id)
        Event.RefreshReviews -> refreshReviews()
        Event.OnCalendarClearDateClick -> onCalendarClearDateClick()
        is Event.OnUserSelectPeriod -> handleUserSelectPeriod(event.firstDate, event.secondDate)
    }
    private fun toDetailReview(id: String) {
        _uiLabels.value = UiLabel.ShowDetailScreen(Screens.DetailReview, id = id)
    }

    private fun handleOnCalendarClick() {
        _uiLabels.value = UiLabel.ShowDatePicker(searchDateStart?.toEpochMillis())
    }

    private fun onQueryReviewsTextUpdated(value: String) {
        searchQuery = value
        _uiState.update { it.copy(query = value) }
        val filteredReviews = filterByDateAndQuery(
            items = _reviews,
            dateStart = searchDateStart,
            dateEnd = searchDateEnd,
            query = searchQuery
        ).map { it.mapToUi() }
        _uiState.update { it.copy(reviewItems = filteredReviews) }
    }

    private fun handleUserSelectPeriod(dateStart: Long, dateEnd: Long) {
        searchDateStart = DateUtils.parseLocalDateTime(dateStart)
        searchDateEnd = DateUtils.parseLocalDateTime(dateEnd)

        val filteredReviews = filterByDateAndQuery(
            items = _reviews,
            dateStart = searchDateStart,
            dateEnd = searchDateEnd,
            query = searchQuery
        ).map { it.mapToUi() }

        _uiState.update {
            it.copy(
                isClearDateIconVisible = true,
                reviewItems = filteredReviews,
                date = DateUtils.getCalendarUiDate(searchDateStart, searchDateEnd)
            )
        }
    }

    private fun filterByDateAndQuery(
        items: List<Review>,
        dateStart: LocalDateTime?,
        dateEnd: LocalDateTime?,
        query: String
    ) = items.filter {
        (
                if (dateStart == null || dateEnd == null) {
                    true
                } else {
                    val current = it.publishedDate?.toLocalDate()
                    if (current == null) {
                        false
                    } else {
                        val first = dateStart.toLocalDate()
                        val second = dateEnd.toLocalDate()
                        !current.isBefore(first) && !current.isAfter(second)
                    }
                }
                ) && it?.title?.contains(query, true) == true
    }


    private fun refreshReviews() {
        viewModelScope.launch {
            requestReviews()
        }
    }

    private fun onCalendarClearDateClick() {
        searchDateStart = null
        searchDateEnd = null
        val filteredReviews = filterByDateAndQuery(
            items = _reviews,
            dateStart = searchDateStart,
            dateEnd = searchDateEnd,
            query = searchQuery
        ).map { it.mapToUi() }

        _uiState.update {
            it.copy(
                date = "",
                isClearDateIconVisible = false,
                reviewItems = filteredReviews
            )
        }
    }

    private fun saveTimer(newName: String) {
        state[TIMER_KEY] = newName
    }

    private fun showTimer() {
        viewModelScope.launch {
            utilTimer.start(state.get<String>(TIMER_KEY)?.toLong() ?: 0)
            utilTimer.time.asFlow().collect { time ->
                saveTimer(time.toString())
                _uiState.update {
                    it.copy(timer = reviewUiMapper.convertTimer(time))
                }
            }
        }
    }
}
