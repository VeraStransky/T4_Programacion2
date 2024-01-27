package cl.verastransky.taller4


import android.app.Application
import androidx.room.Room
import cl.verastransky.taller4.data.BaseDatos


class Aplicacion : Application() {

    val db by lazy { Room.databaseBuilder(this, BaseDatos::class.java, "medicion.db").build()}
    val medicionDao by lazy {db.medicionDao()}
}
