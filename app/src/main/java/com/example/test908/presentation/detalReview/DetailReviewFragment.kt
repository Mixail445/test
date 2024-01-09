package com.example.test908.presentation.detalReview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.databinding.FragmentDetalReviewsBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.Screens
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailReviewFragment : Fragment() {
    private var _binding: FragmentDetalReviewsBinding? = null
    private val binding get() = _binding!!
    private var sendIndexToViewModel: String? = null

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: DetailReviewViewModel.Factory
    private val viewModel: DetailReviewViewModel by viewModels {
        LambdaFactory(this) {
        handle: SavedStateHandle ->
        factory.build(handle, sendIndexToViewModel)
    }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun initViewModel() {
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        setFragmentResultListener("requestKey") { _, bundle ->
            sendIndexToViewModel = bundle.getString("bundleKey")
        }
        binding.tolba.reviewes.setOnClickListener {
            router.navigateTo(Screens.ToToolbarNav)
        }
    }

    private fun handleState(model: DetailReviewView.Model): Unit = model.run {
        Glide.with(this@DetailReviewFragment).load(model.photo)
            .error(R.drawable.img)
            .placeholder(R.drawable.img)
            .into(binding.ivReview)
        binding.tvReview.text = model.text
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onStart() {
        super.onStart()
        router.initForFragment(this)
    }
    override fun onStop() {
        super.onStop()
        router.clear()
    }
}
