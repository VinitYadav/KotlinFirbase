package com.kotlinfirebase.utility

import android.app.Activity
import android.widget.Toast

class Constant {
    companion object {
        val TABLE_USER: String = "TABLE_USER"

        val DEFAULT_USER: String = "https://firebasestorage.googleapis.com/v0/b/my-firebase-flutter-app-454f5.appspot.com/o/user_default.png?alt=media&token=18fddfb1-682a-4c3f-8582-951ee8f2469c";

        fun showToast(activity: Activity, message: CharSequence) =
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
