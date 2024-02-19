package com.example.test908.presentation.limitedSeriesBooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.databinding.FragmentLimitedSeriesBooksBinding
import com.example.test908.presentation.common.RecyclerViewItemDecoration
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LimitedSeriesFragment : Fragment() {
    private var _binding: FragmentLimitedSeriesBooksBinding? = null
    private val binding get() = _binding!!

    @Inject
    @Named("Host")
    lateinit var router: Router

    private val viewModel: LimitedSeriesViewModel by viewModels()
    private val adapter = LimitedSeriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLimitedSeriesBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initViewModel()
        initView()
        binding.toolbar.ivBack.setOnClickListener {
            viewModel.onEvent(LimitedSeriesView.Event.OnClickBackButton)
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }

    private fun handleState(model: LimitedSeriesView.Model): Unit =
        model.run {
            adapter.items = model.items
        }

    private fun initView() {
        with(binding) {
            rcWithDate.apply {
                adapter = this@LimitedSeriesFragment.adapter
                setHasFixedSize(true)
                addItemDecoration(RecyclerViewItemDecoration())
            }
        }
    }

    private fun handleUiLabel(uiLabel: LimitedSeriesView.UiLabel): Unit =
        when (uiLabel) {
            is LimitedSeriesView.UiLabel.ShowHomeFragment -> router.navigateTo(uiLabel.screens)
        }

    override fun onStart() {
        super.onStart()
        router.init(this, requireActivity().supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        router.clear()
    }
}
