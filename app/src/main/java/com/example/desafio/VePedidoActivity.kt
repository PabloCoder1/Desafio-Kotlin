package com.example.desafio

import RestauranteAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VePedidoActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerViewAgendamentos: RecyclerView
    private lateinit var restauranteAdapter: RestauranteAdapter
    private val restauranteList = mutableListOf<PedidoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exibir_pedido)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        recyclerViewAgendamentos = findViewById(R.id.recyclerViewAppointments)

        restauranteAdapter = RestauranteAdapter(restauranteList)
        recyclerViewAgendamentos.layoutManager = LinearLayoutManager(this)
        recyclerViewAgendamentos.adapter = restauranteAdapter

        loadPedidos()
    }

    private fun loadPedidos() {
        db.collection("pedidos")
            .get()
            .addOnSuccessListener { documents ->
                restauranteList.clear()
                for (document in documents) {
                    val numero = document.getString("numero") ?: ""
                    val time = document.getString("time") ?: ""
                    val status = document.getString("status") ?: ""
                    restauranteList.add(PedidoData(numero, time, status))
                }
                restauranteAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao carregar Pedidos.", Toast.LENGTH_SHORT).show()
            }

        val btnBackToLogin: Button = findViewById(R.id.btnBackToLogin)
        btnBackToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
