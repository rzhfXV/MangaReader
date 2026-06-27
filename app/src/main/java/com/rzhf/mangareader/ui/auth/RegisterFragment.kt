package com.rzhf.mangareader.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.auth.AuthRepository

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authRepository = AuthRepository()
        val etEmail = view.findViewById<EditText>(R.id.etRegEmail)
        val etPassword = view.findViewById<EditText>(R.id.etRegPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                authRepository.register(email, password) { success, errorMessage ->
                    if (success) {
                        Toast.makeText(requireContext(), "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack() // Kembali ke halaman Login
                    } else {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Email tidak valid atau Password kurang dari 6 karakter", Toast.LENGTH_SHORT).show()
            }
        }
    }
}