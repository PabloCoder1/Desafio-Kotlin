import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio.R
import com.example.desafio.PedidoData
import com.google.firebase.firestore.model.mutation.Precondition.updateTime

class RestauranteAdapter(private val pedidoList: List<PedidoData>) :
    RecyclerView.Adapter<RestauranteAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedidos = pedidoList[position]
        holder.tvPedido.text = pedidos.numeroPedido
        holder.tvTime.text = pedidos.time
        holder.tvStatus.selectedItem.toString()

    }

    override fun getItemCount(): Int = pedidoList.size


    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPedido: TextView = itemView.findViewById(R.id.tvPedido)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvStatus: Spinner = itemView.findViewById(R.id.tvStatus)
    }
}
