package cl.verastransky.taller4.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Medicion::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class BaseDatos  : RoomDatabase() {
    abstract fun medicionDao():MedicionDao
}
