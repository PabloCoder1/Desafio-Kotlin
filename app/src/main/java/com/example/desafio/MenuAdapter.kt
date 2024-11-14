import android.content.ContentValues.TAG
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio.MainActivity
import com.example.desafio.PedidoData
import com.example.desafio.R
import com.example.desafio.databinding.ActivityRegisterBinding
import com.example.desafio.menuData
import com.example.desafio.user
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.type.Date
import java.text.SimpleDateFormat
import java.util.Locale

class MenuAdapter(private val menuList: List<menuData>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private lateinit var db: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)

        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val pedidos = menuList[position]
        holder.tvPedido.text = pedidos.pedido
        holder.tvTime.text = pedidos.preco

        holder.button.setOnClickListener {
            onButtonClick(pedidos)
        }
    }

    override fun getItemCount(): Int = menuList.size

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPedido: TextView = itemView.findViewById(R.id.tvPedido)
        val tvTime: TextView = itemView.findViewById(R.id.tvPreco)
        val button: Button = itemView.findViewById(R.id.selectButton)
    }

    private fun onButtonClick(pedido: menuData) {
        val db = Firebase.firestore
        val data = java.util.Date()
        val formata =SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dataHora = formata.format(data)
        val ped = hashMapOf(
            "numero" to pedido.pedido,
            "time" to dataHora,
            "status" to "Pendente"
        )
        db.collection("pedidos")
            .add(ped)
            .addOnSuccessListener {
                Log.w(TAG, "Sucesso")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}
