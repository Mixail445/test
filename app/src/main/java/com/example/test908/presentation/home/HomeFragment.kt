package com.example.test908.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.R
import com.example.test908.databinding.FragmentHomeBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun handleUiLabel(uiLabel: HomeView.UiLabel): Unit = when (uiLabel) {
        is HomeView.UiLabel.ShowBookScreen -> router.navigateTo(uiLabel.screens)
        is HomeView.UiLabel.ShowReviewScreen -> router.navigateTo(uiLabel.screens)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.reviewTab -> {
                    viewModel.onEvent(HomeView.Event.OnClickReview)
                    true
                }
                R.id.bookTab -> {
                    viewModel.onEvent(HomeView.Event.OnClickBook)
                    true
                }
                else -> false
            }
        }
     }
    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
        }
    }
    override fun onStart() {
        super.onStart()
        router.init(childFragmentManager)
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
