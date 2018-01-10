package com.kotlinfirebase.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.kotlinfirebase.R
import com.kotlinfirebase.adapter.DashboardAdapter
import com.kotlinfirebase.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        mBinding.activity = this

        init()
        setAdapter()
    }

    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.recyclerView.layoutManager = linearLayoutManager
    }

    /**
     * Set adapter
     */
    private fun setAdapter() {
        mBinding.recyclerView.adapter = DashboardAdapter(this@DashboardActivity)
    }
}
