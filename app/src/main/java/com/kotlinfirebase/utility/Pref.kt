package com.kotlinfirebase.utility

import android.content.Context
import android.content.SharedPreferences

class Pref() {

    companion object {
        private val PREF_NAME = "kotlin_firebase"
        private val MODE = Context.MODE_PRIVATE

        val USER_ID = "user_id"
        val USER_NAME = "user_name"
        val USER_EMAIL = "user_email"
        val USER_IMAGE = "user_image"
        val DEVICE_WIDTH = "device_width"
        val DEVICE_HEIGHT = "device_height"

        fun writeString(context: Context, key: String, value: String) {
            getEditor(context).putString(key, value).commit()
        }

        fun readString(context: Context, key: String, defValue: String): String? {
            return getPreferences(context).getString(key, defValue)
        }

        fun writeInteger(context: Context, key: String, value: Int) {
            getEditor(context).putInt(key, value).commit()

        }

        fun readInteger(context: Context, key: String, defValue: Int): Int {
            return getPreferences(context).getInt(key, defValue)
        }

        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, MODE)
        }

        private fun getEditor(context: Context): SharedPreferences.Editor {
            return getPreferences(context).edit()
        }

        fun clear(context: Context) {
            getEditor(context).clear().commit()
            getEditor(context).clear().commit()
        }
    }
}