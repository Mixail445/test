package com.example.test908.presentation.common

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.test908.R
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

const val DATE_PICKER_TAG = "date_picker_tag"
inline fun Fragment.showDatePickers(
    date: Long?,
    crossinline onDateSelectClick: (date: Long) -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker()
        .setTheme(R.style.MaterialCalendarTheme)
        .setTitleText("SelectDataPicker")
        .setSelection(date)
        .build()
    picker.addOnPositiveButtonClickListener {
        onDateSelectClick(it)
    }
    picker.show(childFragmentManager, DATE_PICKER_TAG)
}

fun Fragment.showDialogError(
    title: String,
    message: String
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .show()
}
