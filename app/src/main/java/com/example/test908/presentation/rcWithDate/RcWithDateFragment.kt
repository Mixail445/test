package com.example.test908.presentation.rcWithDate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.databinding.FragmentRcWithDateBinding
import com.example.test908.presentation.common.RecyclerViewItemDecoration
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named
@AndroidEntryPoint
class RcWithDateFragment : Fragment() {
    private var _binding: FragmentRcWithDateBinding? = null
    private val binding get() = _binding!!

    @Inject
    @Named("Host")
    lateinit var router: Router

    private val viewModel: RcWithDateViewModel by viewModels()
    private val adapter = RcWithDateAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRcWithDateBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initView()
        binding.toolbar.ivBack.setOnClickListener {
            viewModel.onEvent(RcWithDateView.Event.OnClickBackButton)
        }
    }
    private fun initViewModel() {
        with(viewModel) {
        subscribe(uiLabels, ::handleUiLabel)
        launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }
    private fun handleState(model: RcWithDateView.Model): Unit = model.run {
            adapter.items = model.items
    }
    private fun initView() {
        with(binding) {
            rcWithDate.apply {
                adapter = this@RcWithDateFragment.adapter
                setHasFixedSize(true)
                addItemDecoration(RecyclerViewItemDecoration())
            }
        }
    }
    private fun handleUiLabel(uiLabel: RcWithDateView.UiLabel): Unit = when (uiLabel) {
        is RcWithDateView.UiLabel.ShowHomeFragment -> router.navigateTo(uiLabel.screens)
    }

    override fun onStart() {
        super.onStart()
        router.init(requireActivity().supportFragmentManager)
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
