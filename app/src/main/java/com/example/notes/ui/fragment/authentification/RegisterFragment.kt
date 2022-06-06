package com.example.notes.ui.fragment.authentification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.App
import com.example.notes.App.Companion.mAuth
import com.example.notes.ui.MainActivity
import com.example.notes.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButtonRegister.setOnClickListener {
            performAuth()
        }

    }

    private fun performAuth() {
        val email = binding.registerEmail.text.toString().trim()
        val password = binding.registerPassword.text.toString()
        val confPassword = binding.registerConfPassword.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()


        when {
            !email.matches(emailPattern) -> binding.registerErrorEmail.error = "Enter correct Email"

            password.length < 6 -> binding.registerErrorPassword.error =
                "Password must be more than 6 symbols"

            password != confPassword -> binding.registerErrorConfPassword.error =
                "Password not match both field"
            else -> {
                mAuth?.createUserWithEmailAndPassword(email, password)
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}