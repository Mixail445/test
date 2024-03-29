package com.example.test908.presentation.books

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test908.databinding.FragmentBooksBinding
import com.example.test908.presentation.common.RecyclerViewItemDecoration
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.launchAndRepeatWithViewLifecycle
import com.example.test908.presentation.common.subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class BooksFragment : Fragment() {
    @Inject
    @Named("Child")
    lateinit var router: Router

    @Inject
    @Named("Host")
    lateinit var routerForMain: Router
    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BooksViewModel by viewModels()
    private val adapter =
        BooksScreenAdapter(
            onNestedClicked = { url ->
                viewModel.onEvent(BooksView.Event.OnClickNestedRc(url))
            },
            onClickBannerBottom = {
                viewModel.onEvent(BooksView.Event.OnClickBottomBanner(it))
            },
            onClickBlackBanner = {
                viewModel.onEvent(BooksView.Event.OnClickBannerToNextFragment(it))
            },
            onClickBannerGreen = {
                viewModel.onEvent(BooksView.Event.OnClickBannerGreen(it))
            },
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
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
            rvBooks.apply {
                adapter = this@BooksFragment.adapter
                setHasFixedSize(true)
                addItemDecoration(RecyclerViewItemDecoration())
            }
            srBook.setOnRefreshListener {
                viewModel.onEvent(BooksView.Event.RefreshBooks)
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
        }
    }

    private fun handleUiLabel(uiLabel: BooksView.UiLabel): Unit =
        when (uiLabel) {
            is BooksView.UiLabel.ShowBrowse -> showBrowser(uiLabel.uri)
            is BooksView.UiLabel.ShowFragmentWithDate -> routerForMain.navigateTo(uiLabel.screens)
            is BooksView.UiLabel.ShowBottomSheetDialog -> router.navigateTo(uiLabel.screens)
        }

    private fun handleState(model: BooksView.Model): Unit =
        model.run {
            adapter.items = model.items
            binding.srBook.isRefreshing = model.isLoading
        }

    private fun showBrowser(uri: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        routerForMain.init(this, requireActivity().supportFragmentManager)
        router.init(this, requireActivity().supportFragmentManager)
    }

    override fun onStop() {
        super.onStop()
        router.clear()
    }
}
