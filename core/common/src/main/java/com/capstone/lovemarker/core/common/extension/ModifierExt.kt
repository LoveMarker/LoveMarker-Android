package com.capstone.lovemarker.core.common.extension

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController

fun Modifier.clearFocus(focusManager: FocusManager): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}

fun Modifier.hideKeyboard(controller: SoftwareKeyboardController?): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            controller?.hide()
        })
    }
}
