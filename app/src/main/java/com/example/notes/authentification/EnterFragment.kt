package com.example.notes.authentification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentEnterBinding


class EnterFragment: Fragment() {
    private lateinit var binding: FragmentEnterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clickToSignUp.setOnClickListener {
            findNavController().navigate(EnterFragmentDirections.actionEnterFragmentToRegisterFragment())
        }

    }
}