package cl.verastransky.taller4.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id:Long? = null,
    val monto:Long,
    val fecha: LocalDate,
    val categoria: String

)

enum class TipoMedicion {
    GAS,
    AGUA,
    LUZ

}
