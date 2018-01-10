package com.kotlinfirebase.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.kotlinfirebase.R

class DashboardAdapter(activity: Activity) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var mActivity = activity

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mActivity).inflate(R.layout.dashboard_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.textViewName.setText("Vinit")
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var textViewName: TextView

        init {
            textViewName = v.findViewById(R.id.textViewName)
        }
    }

    /*class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var textViewTitle: TextView
        var textViewDate: TextView
        var imageView: ImageView
        var progressBar: ProgressBar

        init {

            textViewTitle = itemView.findViewById(R.id.textViewTitle) as TextView
            textViewDate = itemView.findViewById(R.id.textViewDate) as TextView
            imageView = itemView.findViewById(R.id.imageView) as ImageView
            progressBar = itemView.findViewById(R.id.progressBar) as ProgressBar

            imageView.setOnClickListener(this)
        }
    }*/

}