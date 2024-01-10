package com.example.test908.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.test908.R
import com.example.test908.databinding.HomeFragmentBinding
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null

    @Inject
    lateinit var router: Router
    private val binding get() = _binding!!
    private var navControllerrr:NavController? = null

//   private val viewModel: ReviewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.critics.setOnClickListener {
val navController = requireActivity().findNavController(R.id.childNavHostFragment)
            navController.navigate(R.id.criticFragment)

            //router.navigateTo(Screens.Critics)
        }
        binding.toolbar.reviewes.setOnClickListener {
            navControllerrr= requireActivity().findNavController(R.id.childNavHostFragment)
            navControllerrr!!.navigate(R.id.reviewsFragment)
         //   router.navigateTo(Screens.Reviews)
        }
    }
    override fun onStart() {
        super.onStart()
        //router.initForFragment(this)
       // router.init(childFragmentManager)
    }
    override fun onStop() {
        navControllerrr = null
        super.onStop()
       // router.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
