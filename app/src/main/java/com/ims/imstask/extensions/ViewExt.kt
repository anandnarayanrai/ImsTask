package com.ims.imstask.extensions

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.beGone() {
    this.visibility = View.GONE
}

fun View.beVisible() {
    this.visibility = View.VISIBLE
}

fun View.beInVisible() {
    this.visibility = View.INVISIBLE
}

fun View.beEditable() {
    this.isEnabled = true
}

fun View.beNotEditable() {
    this.isEnabled = false
}

fun View.makeSnackBar(
    message: String,
    actionText: String,
    onClickListener: View.OnClickListener?
) {
    var snackbar: Snackbar? = null
    if (onClickListener == null) {
        snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    } else {
        snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionText, onClickListener)
        snackbar.setActionTextColor(Color.RED)
    }
    snackbar.show()
}

