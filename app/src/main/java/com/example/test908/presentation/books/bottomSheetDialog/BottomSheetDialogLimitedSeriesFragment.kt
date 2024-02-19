package com.example.test908.presentation.books.bottomSheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.test908.R
import com.example.test908.databinding.DialogFragmentLimitedThreeBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class BottomSheetDialogLimitedSeriesFragment() : BottomSheetDialogFragment() {
    private var _binding: DialogFragmentLimitedThreeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomSheetViewModel by viewModels()

    @Inject
    @Named("Child")
    lateinit var router: Router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentLimitedThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initViewModel()
        initView()
    }

    private fun initView() {
        with(binding) {
            bvLimited.setOnClickListener {
                viewModel.onEvent(
                    BottomDialogView.Event.OnClickBottom(binding.ETpromoCode.text.toString()),
                )
            }
            cbPromocod.setOnClickListener {
                viewModel.onEvent(BottomDialogView.Event.OnClickCheckBox)
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }

    private fun handleUiLabel(uiLabel: BottomDialogView.UiLabel): Unit =
        when (uiLabel) {
            BottomDialogView.UiLabel.CloseDialog -> router.back()
        }

    override fun onStart() {
        super.onStart()
        router.init(this, requireActivity().supportFragmentManager)
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme

    private fun handleState(model: BottomDialogView.Model): Unit =
        model.run {
            binding.CVEditText.isVisible = model.promoCodeETVisibility
            binding.cbPromocod.isActivated = model.checkBoxIsActive
            binding.tvBodyLimited.text = model.titleText
            binding.tvTitleLimited.text = model.bodyText
            binding.bvLimited.text = model.bottomText
            binding.ETpromoCode.hint = model.editText
        }
}
