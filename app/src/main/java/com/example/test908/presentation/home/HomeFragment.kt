package com.example.test908.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.databinding.HomeFragmentBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    @Named("Child")
    lateinit var router: Router
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun handleUiLabel(uiLabel: HomeView.UiLabel): Unit = when (uiLabel) {
        is HomeView.UiLabel.ShowCriticScreen -> router.navigateTo(uiLabel.screens)
        is HomeView.UiLabel.ShowReviewScreen -> router.navigateTo(uiLabel.screens)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        binding.toolbar.critics.setOnClickListener {
            viewModel.onEvent(HomeView.Event.OnClickCritic)
        }
        binding.toolbar.reviewes.setOnClickListener {
            viewModel.onEvent(HomeView.Event.OnClickReview)
        }
    }
    private fun handleState(model: HomeView.Model): Unit = model.run {
        with(binding.toolbar) {
            critics.setTextColor(criticColor)
            reviewes.setTextColor(reviewColor)
            toolbarr.setBackgroundColor(toolbarBackgroundColor)
            reviewes.setBackgroundColor(reviewBackgroundColor)
            critics.setBackgroundColor(criticBackgroundColor)
        }
    }
    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
        }
    }
    override fun onStart() {
        super.onStart()
        router.init(requireActivity())
    }
    override fun onStop() {
        super.onStop()
        router.clear()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
