package tugaskelas.c14220163.roomdatabase.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface daftarBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: daftarBelanja)

    @Query("UPDATE daftarBelanja SET tanggal=:isi_tanggal, item=:isi_item, jumlah=:isi_jumlah, status=:isi_status WHERE id=:pilihid")
    fun update(
        isi_tanggal: String,
        isi_item: String,
        isi_jumlah: String,
        isi_status: Int,
        pilihid: Int
    )

    @Delete
    fun delete(daftar: daftarBelanja)

    @Query("SELECT * FROM daftarBelanja ORDER BY id asc")
    fun selectAll(): MutableList<daftarBelanja>

    @Query("SELECT * FROM daftarBelanja WHERE id =:id")
    suspend fun getItem(id: Int): daftarBelanja

    @Query("SELECT * FROM daftarBelanja WHERE status = 0 ORDER BY id ASC")
    fun selectActiveItems(): List<daftarBelanja>

    @Query("SELECT * FROM daftarBelanja WHERE status = 1 ORDER BY id ASC")
    fun selectHistory(): List<daftarBelanja>
}