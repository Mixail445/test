package com.example.test908.presentation.books.bottomSheetDialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.test908.databinding.DialogFragmentLimitedThreeBinding
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetDialogLimitedSeriesFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFragmentLimitedThreeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomSheetViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentLimitedThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (view.parent as? View)?.setBackgroundColor(Color.TRANSPARENT)
        initViewModel()
        initView()

    }
   private fun initView() {
        with(binding) {
            bvLimited.setOnClickListener {
                viewModel.onEvent(
                    BottomDialogView.Event.OnClickBottom(binding.ETpromoCode.text.toString())
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
    private fun handleUiLabel(uiLabel: BottomDialogView.UiLabel): Unit = when (uiLabel) {
        BottomDialogView.UiLabel.CloseDialog -> closeDialog()
    }
    private fun closeDialog() {
        this.dismiss()
    }
    private fun handleState(model: BottomDialogView.Model): Unit = model.run {
        binding.CVEditText.isVisible = model.promoCodeETVisibility
        binding.cbPromocod.isActivated = model.checkBoxIsActive
    }
}
