package com.example.test908.ui.push

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test908.data.modelone.Result
import com.example.test908.databinding.ActivityPushBinding
import com.example.test908.ui.reviews.FragViewModel
import com.example.test908.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Push : AppCompatActivity() {
    private lateinit var ind: String
    private lateinit var binding: ActivityPushBinding
    private val viewModel: FragViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIntent()
    }
    private fun checkIntent() {
        val data = intent.getStringExtra("SER")
        if (data != null) {
            loadData(data)
        }
    }
    private fun loadData(name: String) {
        viewModel.getUsers().observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val arrayList = resource.data?.results?.let { it1 -> ArrayList<Result>(it1) }
                        arrayList?.forEachIndexed { index, result ->
                            if (result.byline == name) {
                                ind = index.toString() }
                        }
                        binding.titlePush.text = resource.data?.results?.get(ind.toInt())?.byline
                        binding.bodyPush.text = resource.data?.results?.get(ind.toInt())?.abstract
                        binding.linearLoad.visibility = View.GONE
                        binding.linearData.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        binding.linearLoad.visibility = View.VISIBLE
                        binding.linearData.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.linearData.visibility = View.GONE
                        binding.linearLoad.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
