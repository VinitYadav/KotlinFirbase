package com.kotlinfirebase.utility

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat

class Constant {
    companion object {
        val TABLE_USER: String = "TABLE_USER"
        val TABLE_POST: String = "TABLE_POST"

        val DEFAULT_USER: String = "https://firebasestorage.googleapis.com/v0/b/my-firebase-flutter-app-454f5.appspot.com/o/user_default.png?alt=media&token=18fddfb1-682a-4c3f-8582-951ee8f2469c";

        val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        val USER_PROFILE_SIZE = 90
        val DEFAULT_IMAGE = 0
        val POST_PROFILE_SIZE = 700

        private val SECOND_MILLIS = 1000
        private val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private val DAY_MILLIS = 24 * HOUR_MILLIS

        fun showToast(activity: Activity, message: String?) =
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

        /**
         * Set image from url
         */
        fun setImageFromUrlGlide(activity: Activity, image: String,
                                 imageView: ImageView, size: Int,
                                 defaultImage: Int) {
            if (defaultImage == 0) {
                Glide.with(activity)
                        .load(image)
                        //.override(size, size)
                        .into(imageView)
            } else {
                Glide.with(activity)
                        .load(image)
                        //.override(size, size)
                        .placeholder(defaultImage)
                        .into(imageView)
            }
        }

        /**
         * Set date time
         */
        /*fun setDateTime(dateTime: String): String? {
            var time: Long = 0

            val sdf = SimpleDateFormat(DATE_FORMAT)
            try {
                val mDate = sdf.parse(convertInLocalTime(dateTime))
                time = mDate.time
                //if timestamp given in seconds, convert to millis time *= 1000; }
                val now = System.currentTimeMillis()
                if (time > now || time <= 0) {
                    return null
                }
                val diff = now - time
                return if (diff < MINUTE_MILLIS) {
                    "just now"
                } else if (diff < 2 * MINUTE_MILLIS) {
                    "a minute ago"
                } else if (diff < 50 * MINUTE_MILLIS) {
                    diff / MINUTE_MILLIS + " min ago"
                } else if (diff < 90 * MINUTE_MILLIS) {
                    "an hour ago"
                } else if (diff < 24 * HOUR_MILLIS) {
                    diff / HOUR_MILLIS + " hrs ago"
                } else if (diff < 48 * HOUR_MILLIS) {
                    "yesterday"
                } else {
                    diff / DAY_MILLIS + " days ago"
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return ""
        }*/

        /**
         * Get device with and height
         */
        fun getDeviceWidthHeight(activity: Activity) {
            val displaymetrics = DisplayMetrics()
            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics)
            val height = displaymetrics.heightPixels
            val width = displaymetrics.widthPixels

            if (width <= 0) {
                getDeviceWidthHeight(activity)
            } else {
                Pref.writeInteger(activity, Pref.DEVICE_WIDTH, width)
                Pref.writeInteger(activity, Pref.DEVICE_HEIGHT, height)
            }
        }
    }
}
