package com.example.test908.presentation.ui.reviews

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
import com.example.test908.presentation.adapters.MainAdapter
import com.example.test908.presentation.adapters.ReviewAdapterDelegate
import com.example.test908.presentation.comon.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.comon.subscribe
import com.example.test908.presentation.ui.reviews.ReviewsView.Event
import com.example.test908.presentation.ui.reviews.ReviewsView.UiLabel
import com.example.test908.utils.RecyclerViewItemDecoration
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        MainAdapter.Builder()
            .add(ReviewAdapterDelegate())
            .build()
    }
    private val viewModel: ReviewsViewModel by viewModels()
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
        searchReviewsByDate()
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
        is UiLabel.ShowCalendar -> searchReviewsByDate()
    }

    private fun handleState(model: ReviewsView.Model): Unit = model.run {
        binding.pbLoader.isVisible = model.isLoading
        binding.tvDate.text = model.date
        binding.svQuery.setQuery(model.search, false)
        adapter.submitList(model.reviewItems)
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
    private fun searchReviewsByDate() {
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
            viewModel.onEvent(Event.OnUserSelectDate(convertData(it)))
        }
    }
    private fun refreshReviews() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.onEvent(Event.RefreshReviews)
            binding.swipeContainer.isRefreshing = false
        }
    }
    private fun convertData(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
