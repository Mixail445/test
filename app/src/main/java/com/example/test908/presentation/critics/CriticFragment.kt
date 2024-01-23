package com.example.test908.presentation.critics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test908.databinding.PushCriticBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CriticFragment : Fragment() {

    private var _binding: PushCriticBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PushCriticBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
