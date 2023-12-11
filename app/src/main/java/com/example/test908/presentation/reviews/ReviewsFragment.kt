package com.example.test908.presentation.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.databinding.FragmentReviewsBinding
import com.example.test908.presentation.common.RecyclerViewItemDecoration
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.showDatePickers
import com.example.test908.presentation.common.showDialogError
import com.example.test908.presentation.common.subscribe
import com.example.test908.presentation.reviews.ReviewsView.Event
import com.example.test908.presentation.reviews.ReviewsView.UiLabel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReviewsViewModel by viewModels()
    private val adapter = ReviewsScreenAdapter(
        onItemClicked = {
            viewModel.onEvent(Event.OnReviewClick)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.rvContent.apply {
            adapter = this@ReviewsFragment.adapter
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
        }
        binding.ivIcCalendar.setOnClickListener {
            viewModel.onEvent(Event.OnCalendarClick)
        }
        binding.ivIcClose.setOnClickListener {
            viewModel.onEvent(Event.OnCalendarClearDateClick)
        }
        binding.svQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onEvent(Event.OnQueryReviewsTextUpdated(newText))
                return true
            }
        })
        binding.swipeContainer.setOnRefreshListener {
            viewModel.onEvent(Event.RefreshReviews)
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }

    private fun handleUiLabel(uiLabel: UiLabel): Unit = when (uiLabel) {
        is UiLabel.ShowDatePicker -> showFilterDatePicker(uiLabel.date)
        is UiLabel.ShowError -> showDialogError(
            title = "Error",
            message = uiLabel.message
        )
    }

    private fun showFilterDatePicker(date: Long?) {
        showDatePickers(
            date = date,
            onDateSelectClick = { firstDate ->
                showDatePickers(
                    date = date,
                    onDateSelectClick = { secondDate ->
                        viewModel.onEvent(Event.OnUserSelectPeriod(firstDate, secondDate))
                    }
                )
            }
        )
    }

    private fun handleState(model: ReviewsView.Model): Unit = model.run {
        binding.tvDate.text = model.date
        binding.svQuery.setQuery(model.query, false)
        adapter.items = model.reviewItems
        binding.ivIcClose.isVisible = model.isClearDateIconVisible
        binding.swipeContainer.isRefreshing = model.isLoading
        binding.tvTimer.text = model.timer
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
