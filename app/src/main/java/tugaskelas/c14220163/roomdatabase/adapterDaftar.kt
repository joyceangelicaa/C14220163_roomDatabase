package tugaskelas.c14220163.roomdatabase

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tugaskelas.c14220163.roomdatabase.database.daftarBelanja
import tugaskelas.c14220163.roomdatabase.database.daftarBelanjaDB

class adapterDaftar(private val daftarBelanja: MutableList<daftarBelanja>) :
    RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang: TextView = itemView.findViewById(R.id.tvItemBarang)
        var _tvjumlahBarang: TextView = itemView.findViewById(R.id.tvjumlahBarang)
        var _tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        var _btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        var _btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        var _btnDone: ImageButton = itemView.findViewById(R.id.btnDone)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val daftar = daftarBelanja[position]

        holder._tvItemBarang.text = daftar.item
        holder._tvTanggal.text = daftar.tanggal
        holder._tvjumlahBarang.text = daftar.jumlah

        holder._btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

        holder._btnDone.setOnClickListener {
            val intent = it.context
            val db = daftarBelanjaDB.getDatabase(it.context)
            CoroutineScope(Dispatchers.IO).launch {
                db.fundaftarBelanjaDAO().updateStatus(daftar.id)
            }

            daftarBelanja.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }
    override fun getItemCount(): Int {
        return daftarBelanja.size
    }
    fun isiData (daftarBelanja: List<daftarBelanja>){
        this.daftarBelanja.clear()
        this.daftarBelanja.addAll(daftarBelanja)
        notifyDataSetChanged()
    }
}

