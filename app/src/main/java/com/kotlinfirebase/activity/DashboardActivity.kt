package com.kotlinfirebase.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.*

import com.kotlinfirebase.R
import com.kotlinfirebase.adapter.DashboardAdapter
import com.kotlinfirebase.databinding.ActivityDashboardBinding
import com.kotlinfirebase.utility.Constant
import com.kotlinfirebase.utility.Pref
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.kotlinfirebase.bean.AllPostBean
import com.kotlinfirebase.model.GetPostBean
import com.kotlinfirebase.model.SignUpModel
import java.util.ArrayList


class DashboardActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityDashboardBinding
    var postList = ArrayList<AllPostBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        mBinding.activity = this

        init()
        getAllPost()
    }

    /**
     * Click on my profile icon
     */
    fun onClickProfile() {
        val intent = Intent(this@DashboardActivity, MyProfileActivity::class.java)
        startActivity(intent)
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
        if (postList.size == 0) {
            return
        }
        mBinding.recyclerView.adapter = DashboardAdapter(this@DashboardActivity, postList)
    }

    /**
     * Get all post
     */
    private fun getAllPost() {
        mBinding.progressBar.visibility = View.VISIBLE
        val database = FirebaseDatabase.getInstance().reference
        database.child(Constant.TABLE_POST).addChildEventListener(object : ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {

                val tempDatabase = FirebaseDatabase.getInstance().reference
                tempDatabase.child(Constant.TABLE_USER).child(dataSnapshot!!.key)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {
                                Constant.showToast(this@DashboardActivity, "Child user error")
                            }

                            override fun onDataChange(userDataSnapshot: DataSnapshot?) {
                                mBinding.progressBar.visibility = View.GONE
                                // Get post from parent datasnapshot
                                for (data: DataSnapshot in dataSnapshot.children) {
                                    val getPostBean: GetPostBean? = data.
                                            getValue(GetPostBean::class.java)

                                    if (userDataSnapshot!!.value != null) {
                                        val getUserBean: SignUpModel? = userDataSnapshot.
                                                getValue(SignUpModel::class.java)
                                        postList.add(AllPostBean(getPostBean!!.commentCount,
                                                getPostBean.description, getPostBean.likeCount,
                                                getPostBean.postImage, getPostBean.shareCount,
                                                getPostBean.timestamp, getUserBean!!.name,
                                                getUserBean.userId, getUserBean.emailId,
                                                getUserBean.userImage))
                                    }
                                }
                                setAdapter()
                            }
                        })
            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }

            override fun onCancelled(error: DatabaseError?) {
                mBinding.progressBar.visibility = View.GONE
                Constant.showToast(this@DashboardActivity, "Error in get all post")
            }
        })
    }
}
