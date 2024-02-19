package com.example.test908.presentation.reviews.bottomSheetReviewFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.navArgs
import com.example.test908.R
import com.example.test908.databinding.DialogFragmentReviewFilterBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.Screens
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class BottomSheetReviewFilterFragment() : BottomSheetDialogFragment() {
    private var _binding: DialogFragmentReviewFilterBinding? = null
    private val binding get() = _binding!!

    private val navArgs by navArgs<BottomSheetReviewFilterFragmentArgs>()

    @Inject
    lateinit var factory: BottomSheetReviewFilterViewModel.Factory
    private val viewModel: BottomSheetReviewFilterViewModel by viewModels {
        LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(
                handle,
                favorite = navArgs.favirite,
                sortByDate = navArgs.byDescending,
                sortedByAscending = navArgs.byAscending,
            )
        }
    }

    @Inject
    @Named("Child")
    lateinit var router: Router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentReviewFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initView()
        initViewModel()
    }

    private fun initView() {
        with(binding) {
            btnApply.setOnClickListener { viewModel.onEvent(BottomSheetReviewFilterView.Event.ClickApply) }
            btnRest.setOnClickListener { viewModel.onEvent(BottomSheetReviewFilterView.Event.ClickRestAll) }
            svAscending.setOnClickListener { viewModel.onEvent(BottomSheetReviewFilterView.Event.ClickOnCheckBoxAscending) }
            svDescending.setOnClickListener { viewModel.onEvent(BottomSheetReviewFilterView.Event.ClickOnCheckBoxDescending) }
            svFavorite.setOnClickListener { viewModel.onEvent(BottomSheetReviewFilterView.Event.ClickOnCheckBoxFavorite) }
        }
    }

    private fun handleUiLabel(uiLabel: BottomSheetReviewFilterView.UiLabel): Unit =
        when (uiLabel) {
            BottomSheetReviewFilterView.UiLabel.ShowBackFragment ->
                router.navigateTo(
                    Screens.DialogToFragment(
                        fav = viewModel.fav(),
                        desc = viewModel.desc(),
                        asc = viewModel.asc(),
                    ),
                )
        }

    private fun handleState(model: BottomSheetReviewFilterView.Model): Unit =
        model.run {
            binding.svAscending.isChecked = model.checkBoxByAscending
            binding.svDescending.isChecked = model.checkBoxByDescending
            binding.svFavorite.isChecked = model.checkBoxFavorite
        }

    override fun onStart() {
        super.onStart()
        router.init(this, requireActivity().supportFragmentManager)
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { viewModel.uiState.collect(::handleState) }
        }
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog
}
