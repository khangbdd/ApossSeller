package com.example.apossseller.uicontroler.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentLoginBinding
import com.example.apossseller.model.entity.Account
import com.example.apossseller.repository.database.AccountDatabase
import com.example.apossseller.uicontroler.activity.MainActivity
import com.example.apossseller.uicontroler.dialog.LoadingDialog
import com.example.apossseller.utils.LoginStatus
import com.example.apossseller.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: FragmentLoginBinding
    lateinit var dialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        dialog = LoadingDialog(this.requireActivity())
        onLoginStateChange()
        setCheckingEmail()
        toastMessageChange()
        setCheckingPassword()
        return binding.root
    }

    private fun onLoginStateChange() {
        viewModel.loginStatus.observe(this.viewLifecycleOwner, {
            if (it == LoginStatus.Wait) {
                dialog.dismissDialog()
            } else {
                if (it == LoginStatus.Loading) {
                    dialog.startLoading()
                } else {
                    dialog.dismissDialog()
                    if (viewModel.tokenDTO != null && viewModel.email.value != null && viewModel.password.value != null) {
                        val account: Account =
                            Account(
                                viewModel.email.value!!,
                                viewModel.password.value!!,
                                viewModel.tokenDTO!!.accessToken,
                                viewModel.tokenDTO!!.tokenType,
                                viewModel.tokenDTO!!.refreshToken
                            )
                        Log.d("login2" , account.toString())
//                        AccountDatabase.getInstance(this.requireContext()).accountDao.insertAccount(
//                            account
//                        )

                        startActivity(Intent(this.context, MainActivity::class.java))
                    } else {
                        Toast.makeText(this.context, "An error occur!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun setCheckingEmail() {
        viewModel.email.observe(this.viewLifecycleOwner, {
            viewModel.isValidEmail()
            binding.emailLayout.error = viewModel.emailErrorMessage;
        })
    }

    private fun setCheckingPassword() {
        viewModel.password.observe(this.viewLifecycleOwner, {
            viewModel.isValidPassword()
            binding.passwordLayout.error = viewModel.passwordErrorMessage
        })
    }


    private fun toastMessageChange() {
        viewModel.toastMessage.observe(this.viewLifecycleOwner, {
            Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}