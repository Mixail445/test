package com.example.test908.presentation.rcWithDate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.presentation.common.BaseItem
import com.example.test908.presentation.common.Screens
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface RcWithDateMain {
    data class State(
        val isLoading: Boolean = false,
        val books: List<BookOfferUi> = emptyList(),
        val date: List<BookDateUi> = emptyList()
    )
}

@HiltViewModel
class RcWithDateViewModel @Inject constructor(
    private val limitedSeriesRepository: LimitedSeriesRepository
) : ViewModel() {
    private val _uiLabels = MutableLiveData<RcWithDateView.UiLabel>()
    val uiLabels: LiveData<RcWithDateView.UiLabel> get() = _uiLabels
    private val state = MutableStateFlow(RcWithDateMain.State())
    val uiState = state.map { it.mapToUi() }
    private suspend fun getData() {
        limitedSeriesRepository.getBooksOffer().onSuccess { response ->
            state.update {
                it.copy(
                    books = response.map { it.mapToUi() },
                    isLoading = false
                )
            }
      }
    }
    init {
        viewModelScope.launch {
            getData()
        }
    }
    fun onEvent(event: RcWithDateView.Event): Unit = when (event) {
        RcWithDateView.Event.OnClickBackButton -> toHomeFragment()
    }

    private fun toHomeFragment() {
        _uiLabels.value = RcWithDateView.UiLabel.ShowHomeFragment(Screens.HomeFragment)
    }
}
private fun RcWithDateMain.State.mapToUi(): RcWithDateView.Model {
    return RcWithDateView.Model(
        items = mapToItems()
    )
}
private fun RcWithDateMain.State.mapToItems(): List<BaseItem> {
    val items = mutableListOf<BaseItem>()
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    books.sortedBy {
      LocalDate.parse(it.expiresDate, dateTimeFormatter)
    }.distinctBy { it.expiresDate }.forEach { data ->
        books.map {
            it.mapToDate()
        }.sortedBy {
            LocalDate.parse(it.date, dateTimeFormatter)
        }.distinctBy { it.date }.forEach { date ->
            if (data.expiresDate == date.date) {
                items.add(date)
                items.add(data)
            }
        }
    }
    return items
}
