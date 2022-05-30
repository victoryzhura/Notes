package com.example.notes.authentification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notes.MainActivity
import com.example.notes.databinding.FragmentEnterBinding
import com.google.firebase.auth.FirebaseAuth


class EnterFragment : Fragment() {
    private lateinit var binding: FragmentEnterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEnterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clickToSignUp.setOnClickListener {
            findNavController().navigate(EnterFragmentDirections.actionEnterFragmentToRegisterFragment())
        }

        binding.signInButtonRegister.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email = binding.enterEmail.text.toString()
        val password = binding.enterPassword.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        val mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth.currentUser

        when {
            !email.matches(emailPattern) -> binding.enterErrorEmail.error = "Enter Email"
            password.length < 6 -> binding.enterErrorPassword.error =
                "Password must be more than 6 symbols"
            else -> {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else
                        Toast.makeText(
                            requireContext(),
                            "Email or password is not correct",
                            Toast.LENGTH_SHORT
                        ).show()
                }

            }
        }
    }

}