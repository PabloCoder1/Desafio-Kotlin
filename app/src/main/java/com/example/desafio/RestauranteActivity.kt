package com.example.desafio

import MenuAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio.databinding.ActivityRestauranteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RestauranteActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityRestauranteBinding
    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private val restauranteList = mutableListOf<menuData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestauranteBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_restaurante)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)

        menuAdapter = MenuAdapter(restauranteList)
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.adapter = menuAdapter
        loadPedidos()
    }

    private fun loadPedidos() {
        db.collection("menus")
            .get()
            .addOnSuccessListener { documents ->
                restauranteList.clear()
                for (document in documents) {
                    val pedido = document.getString("pedido") ?: ""
                    val preco = document.getString("preco") ?: ""
                    restauranteList.add(menuData(pedido, preco))
                }
                menuAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao carregar Pedidos.", Toast.LENGTH_SHORT).show()
            }


        val btnBackToLogin: Button = findViewById(R.id.btnBackToLogin)
        btnBackToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}