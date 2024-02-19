package com.example.test908.presentation.reviews

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.navArgs
import com.example.test908.databinding.FragmentReviewsBinding
import com.example.test908.presentation.base.BaseFragment
import com.example.test908.presentation.common.RecyclerViewItemDecoration
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.showDatePickers
import com.example.test908.presentation.common.showDialogError
import com.example.test908.presentation.common.subscribe
import com.example.test908.presentation.reviews.ReviewsView.Event
import com.example.test908.presentation.reviews.ReviewsView.UiLabel
import com.example.test908.presentation.reviews.bottomSheetReviewFilter.LambdaFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class ReviewsFragment :
    BaseFragment<FragmentReviewsBinding, ReviewsViewModel>(
        FragmentReviewsBinding::inflate,
    ) {
    @Inject
    @Named("Host")
    lateinit var router: Router

    @Inject
    @Named("Child")
    lateinit var routerMain: Router

    private val navArgs by navArgs<ReviewsFragmentArgs>()

    @Inject
    lateinit var factory: ReviewsViewModel.Factory
    override val viewModel: ReviewsViewModel by viewModels {
        LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(
                handle,
                favorite = navArgs.favirite,
                sortByDate = navArgs.byDescending,
                sortedByAscending = navArgs.byAscending,
            )
        }
    }
    private val adapter =
        ReviewsScreenAdapter(
            onFavoriteClick = { id ->
                viewModel.addFavorite(id)
            },
            onItemClicked = { id ->
                viewModel.onEvent(Event.OnReviewClick(id))
            },
        )

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initView()
        initViewModel()
    }

    override fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }

    override fun initView() {
        with(binding) {
            rvContent.apply {
                adapter = this@ReviewsFragment.adapter
                setHasFixedSize(true)
                addItemDecoration(RecyclerViewItemDecoration())
            }
            mcvDate.setOnClickListener {
                viewModel.onEvent(Event.OnCalendarClick)
            }
            ivIcClose.setOnClickListener {
                viewModel.onEvent(Event.OnCalendarClearDateClick)
            }
            svQuery.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = false

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.onEvent(Event.OnQueryReviewsTextUpdated(newText))
                        return true
                    }
                },
            )
            scReview.setOnRefreshListener {
                viewModel.onEvent(Event.RefreshReviews)
            }
            filter.mcvIvFilter.setOnClickListener {
                viewModel.onEvent(Event.ShowDialogFragment)
            }
        }
    }

    private fun handleUiLabel(uiLabel: UiLabel): Unit =
        when (uiLabel) {
            is UiLabel.ShowDatePicker -> showFilterDatePicker(uiLabel.date)
            is UiLabel.ShowError ->
                showDialogError(
                    title = "Error",
                    message = uiLabel.message,
                )

            is UiLabel.ShowDetailScreen ->
                router.navigateTo(
                    uiLabel.screens,
                    bundleOf("key" to uiLabel.id),
                )

            is UiLabel.ShowDialogFragment -> routerMain.navigateTo(uiLabel.screens)
        }

    private fun showFilterDatePicker(date: Long?) {
        showDatePickers(
            date = date,
            onDateSelectClick = { firstDate ->
                showDatePickers(
                    date = date,
                    onDateSelectClick = { secondDate ->
                        viewModel.onEvent(Event.OnUserSelectPeriod(firstDate, secondDate))
                    },
                )
            },
        )
    }

    private fun handleState(model: ReviewsView.Model): Unit =
        model.run {
            binding.tvDate.text = date
            binding.svQuery.setQuery(query, false)
            adapter.items = reviewItems
            binding.ivIcClose.isVisible = isClearDateIconVisible
            binding.scReview.isRefreshing = isLoading
            binding.tvTimer.text = timer
        }

    override fun onStart() {
        super.onStart()
        routerMain.init(this, requireActivity().supportFragmentManager)
        router.init(this, requireActivity().supportFragmentManager)
    }

    override fun onStop() {
        super.onStop()
        router.clear()
        routerMain.clear()
    }
}
