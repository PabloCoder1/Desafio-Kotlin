package com.example.desafio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.desafio.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        db = Firebase.firestore

        binding.loginbnt.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        db.collection("users").document(user!!.uid)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("funcao")
                                    if (role != null) {
                                        if (role == "Cliente") {
                                            val intent = Intent(this, RestauranteActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else if (role == "Restaurante") {
                                            val intent = Intent(this, VePedidoActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        baseContext,
                                        "Erro ao tentar logar.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}