package com.example.test908.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test908.databinding.FragmentHomeBinding
import com.example.test908.presentation.common.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    @Inject
    @Named("Child")
    lateinit var router: Router
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        router.init(this, childFragmentManager, binding.bottomNavigationView)
    }

    override fun onStop() {
        super.onStop()
        router.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
