package com.example.test908.presentation.common


sealed class Screens {
      data object Reviews : Screens()
      data object Books : Screens()
      data object DetailReview : Screens()
      data object HomeFragment : Screens()
      data object DateFragment : Screens()
}

