package com.kotlinfirebase.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.kotlinfirebase.R
import com.kotlinfirebase.databinding.ActivityMyProfileBinding
import com.kotlinfirebase.model.SignUpModel
import com.kotlinfirebase.utility.Constant
import com.kotlinfirebase.utility.Pref

class MyProfileActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMyProfileBinding
    private var myUserId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        mBinding.activity = this

        init()
    }

    /**
     * Click on camera icon
     */
    fun onClickCamera() {
        Constant.showToast(this@MyProfileActivity, "Click on camera")
    }

    private fun init() {
        myUserId = Pref.readString(this@MyProfileActivity, Pref.USER_ID, "")
        getUserDetail()
    }

    /**
     * Get user detail
     */
    private fun getUserDetail() {
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child(Constant.TABLE_USER).child(myUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(dataError: DatabaseError?) {
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        //dataSnapshot!!.getValue(SignUpModel::)
                    }
                })
    }
}
