package com.kotlinfirebase.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.internal.pr
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
import com.kotlinfirebase.utility.Pref
import java.io.IOException


class SignUpActivity : AppCompatActivity() {

    lateinit var mBinding: ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    lateinit var progressHelper: ProgressHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        mBinding.activity = this
        mBinding.signUpModel = SignUpModel("", "", "", "", "", Constant.DEFAULT_USER)
        init()
    }

    /**
     * Click on create account button
     */
    fun onClickCreateAccount() {
        if (validation()) {
            createAccount()
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
     * Create new user account
     */
    private fun createAccount() {
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
                            addUserDetail()
                        }
                    })
        } catch (e: IOException) {
            progressHelper.dismiss()
        }
    }

    /**
     * Add user detail in firebase
     */
    private fun addUserDetail() {

        val mDatabase = FirebaseDatabase.getInstance().getReference(Constant.TABLE_USER)
        mDatabase.child(mBinding.signUpModel.userId).setValue(mBinding.signUpModel)

        val dataBaseGetUser = FirebaseDatabase.getInstance().getReference(Constant.TABLE_USER)
        dataBaseGetUser.child(mBinding.signUpModel.userId)

        dataBaseGetUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressHelper.dismiss()
                Constant.showToast(this@SignUpActivity, getString(R.string.signup_successfully))
                // Save user id in local pref
                Pref.writeString(this@SignUpActivity, Pref.USER_ID, mBinding.signUpModel.userId)
                // Open dashboard screen
                val intent = Intent(this@SignUpActivity, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK;
                startActivity(intent)
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                progressHelper.dismiss()
                Constant.showToast(this@SignUpActivity, getString(R.string.signup_error))
            }
        })
    }
}
