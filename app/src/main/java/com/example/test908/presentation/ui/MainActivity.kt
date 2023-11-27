package com.example.test908.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.test908.R
import com.example.test908.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val windows = window
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.toolbar.critics.setOnClickListener {
            navController.navigate(R.id.fragCritic)
            binding.toolbar.critics.setTextColor(Color.parseColor("#b5e2fa"))
            binding.toolbar.reviewes.setTextColor(Color.WHITE)
            binding.toolbar.reviewes.setBackgroundResource(R.drawable.rounded_left_1)
            binding.toolbar.critics.setBackgroundResource(R.drawable.rounded_right_1)
            binding.toolbar.toolbarr.setBackgroundResource(R.color.orr1)
            windows.statusBarColor = this.resources.getColor(R.color.orr1, resources.newTheme())
        }
        binding.toolbar.reviewes.setOnClickListener {
            navController.navigate(R.id.fragRewiewes2)
            binding.toolbar.critics.setTextColor(Color.WHITE)
            binding.toolbar.reviewes.setTextColor(Color.parseColor("#F7A072"))
            binding.toolbar.toolbarr.setBackgroundResource(R.color.orr)
            binding.toolbar.reviewes.setBackgroundResource(R.drawable.rounded_left)
            binding.toolbar.critics.setBackgroundResource(R.drawable.rounded_right)
            windows.statusBarColor = this.resources.getColor(R.color.orr, resources.newTheme())
        }
    }
}
