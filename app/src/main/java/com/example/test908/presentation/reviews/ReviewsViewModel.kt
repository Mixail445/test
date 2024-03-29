package com.example.test908.presentation.reviews

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.presentation.base.BaseViewModel
import com.example.test908.presentation.common.Screens
import com.example.test908.presentation.reviews.ReviewsView.Event
import com.example.test908.presentation.reviews.ReviewsView.Model
import com.example.test908.presentation.reviews.ReviewsView.UiLabel
import com.example.test908.utils.DateUtils
import com.example.test908.utils.ErrorHandel
import com.example.test908.utils.FavoriteLocalSourceInt
import com.example.test908.utils.UtilTimer
import com.example.test908.utils.onError
import com.example.test908.utils.toEpochMillis
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private const val TIMER_KEY = "timer_key"
private const val STATE_KEY_REVIEW = "FRAGMENT_STATE_KEY"

class ReviewsViewModel
    @AssistedInject
    constructor(
        private val repository: ReviewRepository,
        private val errorHandler: ErrorHandel,
        private val utilTimer: UtilTimer,
        private val reviewUiMapper: ReviewUiMapper,
        @Assisted private val state: SavedStateHandle,
        private val favoriteLocalSourceInt: FavoriteLocalSourceInt,
        @Assisted("filterByFavorite") private val showFavorite: Boolean?,
        @Assisted("filterByDate") private val filterByDate: Boolean?,
        @Assisted("filterByAscending") private val filterByDateByAscending: Boolean?,
    ) : BaseViewModel() {
        private var searchQuery: String = ""
        private var searchDateStart: LocalDateTime? = null
        private var searchDateEnd: LocalDateTime? = null
        private var _reviews: List<Review> = emptyList()
        private val _favorites: MutableSet<String> = mutableSetOf()
        private val favorite = FavoriteData(_favorites)
        lateinit var id: String
        private val _uiState =
            MutableStateFlow(
                state.get<Model>(STATE_KEY_REVIEW) ?: produceInitialState(),
            )

        private fun produceInitialState() =
            Model(
                query = searchQuery,
                date = "",
                timer = state[TIMER_KEY],
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

        private suspend fun initData() {
            getDataFromDb()
        }

        private fun processError(throwable: Throwable) {
            _uiLabels.value = UiLabel.ShowError("Error", errorHandler.handleError(throwable))
        }

        private suspend fun requestReviews() {
            if (uiState.value.isLoading) return
            _uiState.update { it.copy(isLoading = true) }

            repository.getReviewsRemote().onError(::processError)

            _uiState.update { it.copy(isLoading = false) }
        }

        private suspend fun getDataFromDb() {
            repository.fetchReviews().map { listReview ->
                _reviews = listReview
            }.stateIn(viewModelScope)

            favoriteLocalSourceInt.getId()?.item?.let { _favorites.addAll(it) }

            _uiState.update { model ->
                model.copy(reviewItems = getFinalList())
            }
        }

        fun addFavorite(id: String) {
            if (_favorites.contains(id)) {
                _favorites.remove(id)
            } else {
                _favorites.add(id)
            }

            favoriteLocalSourceInt.setId(favorite)

            _uiState.update {
                it.copy(reviewItems = getFinalList())
            }
        }

        fun onEvent(event: Event): Unit =
            when (event) {
                Event.OnCalendarClick -> handleOnCalendarClick()
                is Event.OnQueryReviewsTextUpdated -> onQueryReviewsTextUpdated(event.value)
                is Event.OnReviewClick -> toDetailReview(event.id)
                Event.RefreshReviews -> refreshReviews()
                Event.OnCalendarClearDateClick -> onCalendarClearDateClick()
                is Event.OnUserSelectPeriod ->
                    handleUserSelectPeriod(
                        event.firstDate,
                        event.secondDate,
                    )

                is Event.ShowDialogFragment -> handlerShowDialogFragment()
            }

        private fun handlerShowDialogFragment() {
            _uiLabels.value =
                UiLabel.ShowDialogFragment(
                    Screens.FragmentToDialog(
                        fav = showFavorite ?: false,
                        desc = filterByDate ?: false,
                        asc = filterByDateByAscending ?: false,
                    ),
                )
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
            _uiState.update { it.copy(reviewItems = getFinalList()) }
        }

        private fun handleUserSelectPeriod(
            dateStart: Long,
            dateEnd: Long,
        ) {
            searchDateStart = DateUtils.parseLocalDateTime(dateStart)
            searchDateEnd = DateUtils.parseLocalDateTime(dateEnd)

            _uiState.update {
                it.copy(
                    isClearDateIconVisible = true,
                    reviewItems = getFinalList(),
                    date = DateUtils.getCalendarUiDate(searchDateStart, searchDateEnd),
                )
            }
        }

        private fun filterByDateAndQuery(
            items: List<Review> = _reviews,
            dateStart: LocalDateTime? = searchDateStart,
            dateEnd: LocalDateTime? = searchDateEnd,
            query: String = searchQuery,
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

            _uiState.update {
                it.copy(
                    date = "",
                    isClearDateIconVisible = false,
                    reviewItems = getFinalList(),
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

        private fun getFinalList() =
            if (filterByDate == true) {
                filterByDateAndQuery().sortedBy { it.publishedDate }.map { item ->
                    val isInFavorite = _favorites.any { it == item.localId }
                    val icon = reviewUiMapper.getDrawable(isInFavorite)
                    item.mapToUi(icon)
                }
            } else if (filterByDateByAscending == true) {
                filterByDateAndQuery().sortedByDescending { it.publishedDate }.map { item ->
                    val isInFavorite = _favorites.any { it == item.localId }
                    val icon = reviewUiMapper.getDrawable(isInFavorite)
                    item.mapToUi(icon)
                }
            } else if (showFavorite == true) {
                val list = mutableListOf<ReviewUi>()
                _favorites.forEach { id ->
                    filterByDateAndQuery().forEach { item ->
                        if (id == item.localId) {
                            val isInFavorite = _favorites.any { it == item.localId }
                            val icon = reviewUiMapper.getDrawable(isInFavorite)
                            list.add(item.mapToUi(icon))
                        }
                    }
                }
                list
            } else {
                filterByDateAndQuery().map { item ->
                    val isInFavorite = _favorites.any { it == item.localId }
                    val icon = reviewUiMapper.getDrawable(isInFavorite)
                    item.mapToUi(icon)
                }
            }

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
                @Assisted("filterByFavorite") favorite: Boolean?,
                @Assisted("filterByDate") sortByDate: Boolean?,
                @Assisted("filterByAscending") sortedByAscending: Boolean?,
            ): ReviewsViewModel
        }
    }

class LambdaFactory<T : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val create: (handle: SavedStateHandle) -> T,
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return create(handle) as T
    }
}
