package com.kotlinfirebase.utility

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.kotlinfirebase.R

class ProgressHelper(val activity: Activity) {

    private var dialog: Dialog? = null

    /**
     * Show progress
     */
    fun show() {
        dialog = Dialog(activity)
        dialog!!.setCancelable(false)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_progess)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val progressBar = dialog!!.findViewById(R.id.progressBar) as ProgressBar
        progressBar.indeterminateDrawable.setColorFilter(-0x1, android.graphics.PorterDuff.Mode.MULTIPLY)

        dialog!!.show()
    }

    /**
     * Dismiss progress
     */
    fun dismiss() {
        dialog!!.dismiss()
    }
}
