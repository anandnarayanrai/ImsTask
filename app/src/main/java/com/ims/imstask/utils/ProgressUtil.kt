package com.ims.imstask.utils

import android.app.Activity
import android.app.Dialog
import com.ims.imstask.R
import com.ims.imstask.extensions.hideKeyboard

class ProgressUtil constructor(var context: Activity) {

    private var dialog: Dialog? = null

    fun show() {
        hide()
        dialog = Dialog(context)
        dialog?.let {
            it.setContentView(R.layout.loader)
            it.setCancelable(false)
            it.show()
        }
        context.hideKeyboard()
    }

    fun hide() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}
