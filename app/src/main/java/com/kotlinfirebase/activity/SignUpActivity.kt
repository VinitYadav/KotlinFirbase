package com.kotlinfirebase.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kotlinfirebase.R
import com.kotlinfirebase.databinding.ActivitySignUpBinding
import com.kotlinfirebase.model.SignUpModel
import com.kotlinfirebase.utility.Constant
import com.kotlinfirebase.utility.ProgressHelper
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.io.IOException


class SignUpActivity : AppCompatActivity() {

    lateinit var mBinding: ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    lateinit var progressHelper: ProgressHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        mBinding.activity = this
        mBinding.signUpModel = SignUpModel("Amit", "", "amit@gmail.com", "qwerty", "qwerty", Constant.DEFAULT_USER)
        init()
    }

    /**
     * Click on create account button
     */
    fun onClickCreateAccount() {
        if (validation()) {
            try {
                progressHelper.show()
                auth.createUserWithEmailAndPassword(mBinding.signUpModel.emailId,
                        mBinding.signUpModel.password)
                        .addOnCompleteListener(this@SignUpActivity, OnCompleteListener<AuthResult>
                        { task ->
                            if (!task.isSuccessful) {
                                Constant.showToast(this@SignUpActivity, getString(R.string.signup_error))
                            } else {
                                mBinding.signUpModel.userId = task.result.user.uid
                                Constant.showToast(this@SignUpActivity, getString(R.string.signup_successfully))
                                addUserDetail()
                            }
                        })
            } catch (e: IOException) {
                progressHelper.dismiss()
            }
        }
    }

    private fun init() {
        progressHelper = ProgressHelper(this@SignUpActivity)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    /**
     * Validation o all fields
     */
    private fun validation(): Boolean {
        if (mBinding.signUpModel.name.isNullOrBlank()) {
            Constant.showToast(this@SignUpActivity, getString(R.string.signup_name_validation))
            return false
        } else if (mBinding.signUpModel.emailId.isNullOrBlank()) {
            Constant.showToast(this@SignUpActivity, getString(R.string.signup_email_validation))
            return false
        } else if (!mBinding.signUpModel.emailId.contains("@") ||
                !mBinding.signUpModel.emailId.contains(".")) {
            Constant.showToast(this@SignUpActivity, getString(R.string.signup_email_format_validation))
            return false
        } else if (mBinding.signUpModel.password.isNullOrBlank()) {
            Constant.showToast(this@SignUpActivity, getString(R.string.signup_password_validation))
            return false
        } else if (mBinding.signUpModel.confirmPassword.isNullOrBlank()) {
            Constant.showToast(this@SignUpActivity, getString(R.string.signup_confirm_password_validation))
            return false
        }
        return true
    }

    /**
     * Add user detail in firebase
     */
    private fun addUserDetail() {

        val mDatabase = FirebaseDatabase.getInstance().getReference(Constant.TABLE_USER)
        // Creating new user node, which returns the unique userId
        val userId = mDatabase.push().key
        mDatabase.child(userId).setValue(mBinding.signUpModel)

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue<SignUpModel>(SignUpModel::class.java)

                Constant.showToast(this@SignUpActivity, user!!.name)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Constant.showToast(this@SignUpActivity, "Failed to read value.")
            }
        })
    }
}
