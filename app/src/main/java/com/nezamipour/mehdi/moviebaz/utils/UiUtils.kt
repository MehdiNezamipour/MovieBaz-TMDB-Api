package com.nezamipour.mehdi.moviebaz.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager

class UiUtils {

    companion object {
        fun showKeyboard(context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}