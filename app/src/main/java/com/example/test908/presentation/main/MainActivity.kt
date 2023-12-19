package com.example.test908.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.test908.R
import com.example.test908.databinding.ActivityMainBinding
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initViews()
    }
    private fun initViews() {
        binding.toolbar.critics.setOnClickListener {
            viewModel.onEvent(MainView.Event.OnClickCritic)

        }
        binding.toolbar.reviewes.setOnClickListener {
            viewModel.onEvent(MainView.Event.OnClickReview)
        }
    }
    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }
    private fun handleState(model: MainView.Model): Unit = model.run {
        with(binding.toolbar) {
            critics.setTextColor(criticColor)
            reviewes.setTextColor(reviewColor)
            toolbarr.setBackgroundColor(toolbarBackgroundColor)
            reviewes.setBackgroundColor(reviewBackgroundColor)
            critics.setBackgroundColor(criticBackgroundColor)
        }
    }
    private fun handleUiLabel(uiLabel: MainView.UiLabel): Unit = when (uiLabel) {
        MainView.UiLabel.MoveFragmentCritic -> moveToFragmentCritic()
        MainView.UiLabel.MoveFragmentReview -> moveToFragmentReview()
    }

    private fun moveToFragmentReview() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.fragRewiewes2)
    }

    private fun moveToFragmentCritic() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.fragCritic)
    }
}
