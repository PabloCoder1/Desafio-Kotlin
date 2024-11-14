package com.example.desafio

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafio.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val role = binding.spRole.selectedItem.toString()
            val db = Firebase.firestore
            val use = hashMapOf(
                "email" to email,
                "funcao" to role,
                "password" to password
            )

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful)
                    {
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show()

                        db.collection("users").document(user!!.uid)
                            .set(use)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(baseContext, "Erro ao cadastrar.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        binding.btnVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}