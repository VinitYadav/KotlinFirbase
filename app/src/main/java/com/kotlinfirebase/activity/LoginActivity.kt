package com.kotlinfirebase.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.kotlinfirebase.R
import com.kotlinfirebase.databinding.ActivityLoginBinding
import com.kotlinfirebase.model.LoginModel
import com.kotlinfirebase.utility.Constant
import com.kotlinfirebase.utility.ProgressHelper


class LoginActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var progressHelper: ProgressHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.activity = this
        mBinding.loginModel = LoginModel("", "")
        init()
    }

    /**
     * Click on sign up button
     */
    fun onClickSignUp() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    /**
     * Click on login button
     */
    fun onClickLogin() {
        if (validation()) {
            progressHelper.show()
            auth.signInWithEmailAndPassword(mBinding.loginModel.email, mBinding.loginModel.password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        progressHelper.dismiss()
                        if (!task.isSuccessful) {
                            // there was an error
                            Constant.showToast(this@LoginActivity, getString(R.string.login_error))
                        } else {
                            Constant.showToast(this@LoginActivity, getString(R.string.login_successfully))
                            /*val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()*/
                        }
                    }
        }
    }

    private fun init() {
        progressHelper = ProgressHelper(this@LoginActivity)
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        if (auth.currentUser != null) {
            Constant.showToast(this@LoginActivity, "Login")
        }
    }

    /**
     * Validation o all fields
     */
    private fun validation(): Boolean {
        if (mBinding.loginModel.email.isNullOrBlank()) {
            Constant.showToast(this@LoginActivity, getString(R.string.login_email_validation))
            return false
        } else if (mBinding.loginModel.password.isNullOrBlank()) {
            Constant.showToast(this@LoginActivity, getString(R.string.login_password_validation))
            return false
        }
        return true
    }
}
