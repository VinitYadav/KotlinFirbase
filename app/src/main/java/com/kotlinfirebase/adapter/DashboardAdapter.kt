package com.kotlinfirebase.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlinfirebase.R
import com.kotlinfirebase.bean.AllPostBean
import com.kotlinfirebase.utility.Constant
import java.sql.Date
import java.text.SimpleDateFormat
import com.kotlinfirebase.utility.Pref

class DashboardAdapter(activity: Activity, list: ArrayList<AllPostBean>)
    : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var mActivity = activity
    var mList = list

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mActivity).inflate(R.layout.dashboard_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        // Set image view height
        setImageViewHeight(holder!!.imageViewPost)
        // Set user name
        holder.textViewName.setText(mList.get(position).name)
        // Set post date time
        holder.textViewDateTime.setText(getDateTime(mList.get(position).timestamp))
        // Set user profile image
        Constant.setImageFromUrlGlide(mActivity, mList.get(position).
                userImage, holder.imageViewProfile, Constant.USER_PROFILE_SIZE, Constant.DEFAULT_IMAGE)
        // Set description
        holder.textViewDateDescription.setText(mList.get(position).description)
        // Set post image
        Constant.setImageFromUrlGlide(mActivity, mList.get(position).
                postImage, holder.imageViewPost, Constant.POST_PROFILE_SIZE, Constant.DEFAULT_IMAGE)
    }

    /**
     * Set image view height
     */
    private fun setImageViewHeight(imageView: ImageView) {
        val width = Pref.readInteger(mActivity, Pref.DEVICE_WIDTH, 0)
        imageView.getLayoutParams().height = width;
    }

    /**
     * Convert time stamp to date time
     */
    private fun getDateTime(time: Long): String? {
        try {
            val sdf = SimpleDateFormat(Constant.DATE_FORMAT)
            val netDate = Date(time)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: TextView
        var textViewDateTime: TextView
        var imageViewProfile: ImageView
        var textViewDateDescription: TextView
        var imageViewPost: ImageView

        init {
            textViewName = view.findViewById(R.id.textViewName)
            textViewDateTime = view.findViewById(R.id.textViewDateTime)
            imageViewProfile = view.findViewById(R.id.imageViewProfile)
            textViewDateDescription = view.findViewById(R.id.textViewDateDescription)
            imageViewPost = view.findViewById(R.id.imageViewPost)
        }
    }
}