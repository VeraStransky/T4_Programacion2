package cl.verastransky.taller4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicionDao {


    @Query("SELECT * FROM Medicion ORDER BY fecha DESC")
    suspend fun obtenerTodasLasMediciones(): List<Medicion>

    @Query("SELECT * FROM Medicion WHERE id = :id")
    suspend fun obtenerPorId(id:Long): Medicion

    @Insert
    suspend fun insertarMedicion(medicion:Medicion)

    @Update
    suspend fun modificar(medicion:Medicion)

    @Delete
    suspend fun eilminarMedicion(medicion:Medicion)
}