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
        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        val _btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val _etItem = findViewById<EditText>(R.id.etItem)
        val _etJumlah = findViewById<EditText>(R.id.etJumlah)
        val DB = daftarBelanjaDB.getDatabase(this)
        _btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = _tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString(),
                        status = 0
                    )
                )
            }
        }

        //edit dan update
        var iID: Int = 0
        var AddEdit: Int = 0

        iID = intent.getIntExtra("id", 0)
        AddEdit = intent.getIntExtra("AddEdit", 0)

        if (AddEdit == 0) {
            _btnTambah.visibility = Button.VISIBLE
            _btnUpdate.visibility = Button.GONE
            _etItem.isEnabled = true
        } else {
            _btnTambah.visibility = Button.GONE
            _btnUpdate.visibility = Button.VISIBLE
            _etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundaftarBelanjaDAO().getItem(iID)
                _etItem.setText(item.item)
                _etJumlah.setText(item.jumlah)
            }
        }
        _btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().update(
                    isi_tanggal = _tanggal,
                    isi_item = _etItem.text.toString(),
                    isi_jumlah = _etJumlah.text.toString(),
                    isi_status = 0,
                    pilihid = iID
                )
            }
        }
    }
}