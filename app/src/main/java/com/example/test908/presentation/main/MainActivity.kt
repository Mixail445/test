package com.example.test908.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test908.databinding.ActivityMainBinding
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState()
    }
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
        is MainView.UiLabel.NavigateToNext -> router.navigateTo(uiLabel.screen)
    }
    override fun onStart() {
        super.onStart()
        router.init(supportFragmentManager)
    }
    override fun onStop() {
        super.onStop()
        router.clear()
    }
}
