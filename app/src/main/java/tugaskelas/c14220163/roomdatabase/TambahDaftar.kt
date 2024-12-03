package tugaskelas.c14220163.roomdatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import tugaskelas.c14220163.roomdatabase.database.daftarBelanja
import tugaskelas.c14220163.roomdatabase.database.daftarBelanjaDB
import tugaskelas.c14220163.roomdatabase.helper.DateHelper.getCurrentDate

class TambahDaftar : AppCompatActivity() {
    var _btnTambah = findViewById<Button>(R.id.btnTambah)
    var _btnUpdate = findViewById<Button>(R.id.btnUpdate)
    var _etItem = findViewById<EditText>(R.id.etItem)
    var _etJumlah = findViewById<EditText>(R.id.etJumlah)
    var DB = daftarBelanjaDB.getDatabase(this)
    var _tanggal = getCurrentDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async{
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = _tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString()
                    )
                )
            }
        }
    }
}