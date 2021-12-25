package com.example.apossseller.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var passwordErrorMessage: String? = ""
    var emailErrorMessage: String? = ""
    var toastMessage: MutableLiveData<String> = MutableLiveData()


    private fun isEmailRightFormat(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    fun onLoginClick() {
        if (email.value != null && password.value != null) {
            if (isValidEmail() && isValidPassword()) {
                //signIn()
            } else {
                toastMessage.value = "Email or password is invalid!"
            }
        } else {
            toastMessage.value = "Email or password is invalid!"
        }
    }

    fun isValidPassword(): Boolean {
        return if (password.value!!.isBlank()) {
            passwordErrorMessage = "Password can not empty"
            false
        } else {
            if (password.value!!.count() < 8) {
                passwordErrorMessage = "Password least than 8 character"
                false
            } else {
                passwordErrorMessage = null
                true
            }
        }
    }

    fun isValidEmail(): Boolean {
        return if (email.value!!.isBlank()) {
            emailErrorMessage = "Email can not empty"
            false
        } else {
            if (isEmailRightFormat(email.value!!.trim())) {
                emailErrorMessage = null
                true
            } else {
                emailErrorMessage = "Invalid email"
                false
            }
        }
    }
}