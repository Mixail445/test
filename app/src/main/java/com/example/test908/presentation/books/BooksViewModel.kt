package com.example.test908.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test908.domain.repository.books.BooksRepository
import com.example.test908.domain.repository.books.model.Books
import com.example.test908.utils.DateUtils
import com.example.test908.utils.onError
import com.example.test908.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STATE_KEY_BOOKS = "FRAGMENT_STATE_KEY_BOOKS"

@HiltViewModel
class BooksViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repository: BooksRepository
) : ViewModel() {
    private var _books: List<Books> = emptyList()
    private val _uiState = MutableStateFlow(
        state.get<BooksView.Model>(STATE_KEY_BOOKS) ?: produceInitialState()
    )
    val uiState: StateFlow<BooksView.Model> = _uiState.asStateFlow()
    private val _uiLabels = MutableLiveData<BooksView.UiLabel>()
    val uiLabels: LiveData<BooksView.UiLabel> get() = _uiLabels
    private fun produceInitialState() = BooksView.Model(booksItems = _books.map { it.mapToUi() })
    init {
        viewModelScope.launch {
            initData()
        }

    }
    private suspend fun initData() {
        DateUtils.getCurrentDate()?.let { requestBooks(it) }
    }
    fun onEvent(event: BooksView.Event): Unit = when (event) {
        is BooksView.Event.OnClickNestedRc -> onClickNestedRc(event.url)
        BooksView.Event.RefreshBooks -> refreshBooks()
    }
    private fun onClickNestedRc(url: String) {
        _uiLabels.value = BooksView.UiLabel.ShowBrowse(url)
    }

    private suspend fun requestBooks(data: String) {
        if (uiState.value.isLoading) return
        _uiState.update { it.copy(isLoading = true) }
        repository.getBooksRemote(data).onSuccess {
            _books = it
        }.onError {}

        _uiState.update { it.copy(isLoading = false, booksItems = _books.map { it.mapToUi() }) }
    }
    private fun refreshBooks() {
        viewModelScope.launch {
            requestBooks(DateUtils.getCurrentDate().toString())
        }
    }
}
