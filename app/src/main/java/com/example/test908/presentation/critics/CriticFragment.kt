package com.example.test908.presentation.critics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.R
import com.example.test908.databinding.PushCriticBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.Screens
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class CriticFragment : Fragment() {
    private val viewModel: CriticViewModel by viewModels()

    private var _binding: PushCriticBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PushCriticBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
//        binding.toolbar.critics.setOnClickListener {
//            router.navigateTo(Screens.Critics)
//        }
//        binding.toolbar.reviewes.setOnClickListener {
//            router.navigateTo(Screens.Reviews)
//        }
    }
    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
        }
    }

    private fun handleUiLabel(uiLabel: CriticView.UiLabel): Unit = when (uiLabel) {
        is CriticView.UiLabel.ShowReview -> router.navigateTo(uiLabel.screens)
    }

    override fun onStart() {
        super.onStart()
       // router.initForFragment(this)
//        router.initForFragment(this)
    }
    override fun onStop() {
        super.onStop()
       // router.clear()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
