package com.example.test908.presentation.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.R
import com.example.test908.databinding.FragmentReviewsBinding
import com.example.test908.presentation.common.RecyclerViewItemDecoration
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import com.example.test908.presentation.reviews.ReviewsView.Event
import com.example.test908.presentation.reviews.ReviewsView.UiLabel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReviewsViewModel by viewModels()

    private val adapter = ReviewsScreenAdapter(
        onItemClicked = {
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
        searchReviewersByQuery()
        refreshReviews()
        showDatePicker()
    }

    private fun initViews() {
        binding.rvContent.apply {
            adapter = this@ReviewsFragment.adapter
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }

    private fun handleUiLabel(uiLabel: UiLabel): Unit = when (uiLabel) {
        is UiLabel.ShowDatePicker -> showDatePicker()
        is UiLabel.ShowError -> Unit // TODO()
    }

    private fun handleState(model: ReviewsView.Model): Unit = model.run {
        binding.pbLoader.isVisible = model.isLoading
        binding.tvDate.text = model.date
        binding.svQuery.setQuery(model.query, false)
        adapter.items = model.reviewItems
    }
    private fun searchReviewersByQuery() {
        binding.svQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onEvent(Event.OnQueryReviewsTextUpdated(newText))
                return true
            }
        })
    }

    // todo move in fragment extension
    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.MaterialCalendarTheme)
            .setTitleText("SelectDataPicker")
            .setSelection(null)
            .build()
        binding.clCalendarContainer.setOnClickListener {
            viewModel.onEvent(Event.OnCalendarClick)
            picker.show(this.childFragmentManager, "Tag")
        }
        picker.addOnPositiveButtonClickListener {
            viewModel.onEvent(Event.OnUserSelectDate(it))
        }
    }
    private fun refreshReviews() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.onEvent(Event.RefreshReviews)
            binding.swipeContainer.isRefreshing = false
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}