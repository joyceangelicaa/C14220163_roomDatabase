package tugaskelas.c14220163.roomdatabase.helper


import android.content.Context
import androidx.room.Room
import tugaskelas.c14220163.roomdatabase.database.daftarBelanjaDB
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
    fun getDatabase(context: Context): daftarBelanjaDB {
        return Room.databaseBuilder(
            context.applicationContext,
            daftarBelanjaDB::class.java,
            "daftarBelanjaDB"
        ).build()
    }
}