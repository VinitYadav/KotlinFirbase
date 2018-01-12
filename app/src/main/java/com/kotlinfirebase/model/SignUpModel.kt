package com.kotlinfirebase.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.google.firebase.database.Exclude
import com.kotlinfirebase.BR

data class SignUpModel(
        private var _name: String = "",
        private var _userId: String = "",
        private var _emailId: String = "",
        private var _password: String = "",
        private var _confirmPassword: String = "",
        private var _userImage: String = ""
) : BaseObservable() {

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    var userId: String
        @Bindable get() = _userId
        set(value) {
            _userId = value
            notifyPropertyChanged(BR.userId)
        }

    var emailId: String
        @Bindable get() = _emailId
        set(value) {
            _emailId = value
            notifyPropertyChanged(BR.emailId)
        }

    @get:Exclude
    var password: String
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }

    @get:Exclude
    var confirmPassword: String
        @Bindable get() = _confirmPassword
        set(value) {
            _confirmPassword = value
            notifyPropertyChanged(BR.confirmPassword)
        }

    var userImage: String
        @Bindable get() = _userImage
        set(value) {
            _userImage = value
            notifyPropertyChanged(BR.userImage)
        }
}
