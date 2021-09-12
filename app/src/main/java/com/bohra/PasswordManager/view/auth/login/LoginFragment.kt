package com.bohra.PasswordManager.view.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bohra.PasswordManager.R
import com.bohra.PasswordManager.databinding.FragmentLoginBinding
import com.bohra.PasswordManager.utils.Constants
import com.bohra.PasswordManager.utils.emptyCheck
import com.bohra.PasswordManager.utils.checkLengthForTen
import com.bohra.PasswordManager.utils.showToast
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navigationHost: NavHostFragment
    private lateinit var controller: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        navigationHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.authNavHostFragmentContainer) as NavHostFragment
        controller = navigationHost.navController

        binding.btnProceed.setOnClickListener {

            val mobile=binding.edtMobileNum.text.toString().trim()
            when {
                emptyCheck(mobile) -> {
                   showToast(requireContext(),getString(R.string.enter_mobile_no), true)
                }
                checkLengthForTen(mobile)-> {
                    showToast(requireContext(),getString(R.string.enter_valid_mobile_no), true)
                }
                else -> {
                    val bundle = Bundle()
                    bundle.putString(Constants.mobile, binding.edtMobileNum.text.toString().trim())
                    controller.navigate(R.id.action_loginFragment_to_otpFragment, bundle)
                }
            }
        }
    }
}
