package com.example.test908.presentation.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test908.R
import com.example.test908.databinding.ReviewesBinding
import com.example.test908.presentation.adapters.ReviewsAdapter
import com.example.test908.presentation.reviewList.StoryUi
import com.example.test908.utils.RecyclerViewItemDecoration
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    private var _binding: ReviewesBinding? = null
    private val binding get() = _binding!!
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val viewModel: ReviewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReviewesBinding.inflate(inflater, container, false)

        val view = binding.root
        setupRcView()
        loadData()
        refresh()
        searchReviewers()
        searchDataReviewers()
        return view
    }

    private fun searchDataReviewers() {
        val dataClick = binding.dataClick
        val dataText = binding.dataTExt
        dataClick.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.MaterialCalendarTheme)
                .setTitleText("SelectDataPicker")
                .setSelection(null)
                .build()
            picker.show(this.childFragmentManager, "Tag")
            picker.addOnPositiveButtonClickListener {
                dataText.text = convertData(it)
            }
        }
    }

    private fun convertData(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }

    private fun refresh() {
        binding.swipeContainer.setOnRefreshListener {
            binding.progressBar.isVisible = true
            binding.swipeContainer.isVisible = false
            lifecycleScope.launch {
                viewModel.refreshStory()
                viewModel.uiState.collect {
                    reviewsAdapter.submitList(it.reviewRefresh)
                    binding.swipeContainer.isVisible = it.group
                    binding.progressBar.isVisible = it.loading
                }
            }
            binding.dataTExt.text = ""
            binding.swipeContainer.isRefreshing = false
        }
    }

    private fun setupRcView() {
        reviewsAdapter = ReviewsAdapter()
        binding.rcView.adapter = reviewsAdapter
        binding.rcView.layoutManager = LinearLayoutManager(context)
        binding.rcView.setHasFixedSize(true)
        binding.rcView.addItemDecoration(RecyclerViewItemDecoration())
        reviewsAdapter.setOnClickListener(object : ReviewsAdapter.OnClickListener {
            override fun onClick(position: Int, model: StoryUi) {
                Toast.makeText(activity, model.byline, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun searchReviewers() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launch {
                    viewModel.search(newText)
                    viewModel.uiState.collect {
                        reviewsAdapter.submitList(it.reviewFilter)
                    }
                }
                return true
            }
        })
    }
    private fun loadData() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                reviewsAdapter.submitList(it.reviewItems)
                binding.progressBar.isVisible = it.loading
                binding.swipeContainer.isVisible = it.group
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
