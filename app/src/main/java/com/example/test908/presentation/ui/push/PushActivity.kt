package com.example.test908.presentation.ui.push

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test908.data.model.Story
import com.example.test908.databinding.ActivityPushBinding
import com.example.test908.presentation.ui.reviews.ReviewsViewModel
import com.example.test908.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PushActivity : AppCompatActivity() {
    private lateinit var ind: String
    private lateinit var binding: ActivityPushBinding
    private val viewModel: ReviewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIntent()
    }
    private fun checkIntent() {
        val data = intent.getStringExtra("SER")
        if (data != null) loadData(data)
    }
    private fun loadData(name: String) {
        viewModel.getStory().observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.linearLoad.visibility = View.GONE
                        binding.linearData.visibility = View.VISIBLE
                        //  val arrayList =
                        //      resource.data?.results?.let { it1 -> ArrayList<Story>(it1)  }
                        //     arrayList?.let { it1 -> checkList(it1, name) }
                        //    binding.titlePush.text = resource?.data?.results?.get(ind.toInt())?.byline
                        //     binding.bodyPush.text = resource?.data?.results?.get(ind.toInt())?.abstract
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

    private fun checkList(list: ArrayList<Story>, name: String) {
        list.forEachIndexed { index, result ->
            if (result.byline == name) {
                ind = index.toString()
            }
        }
    }
}
