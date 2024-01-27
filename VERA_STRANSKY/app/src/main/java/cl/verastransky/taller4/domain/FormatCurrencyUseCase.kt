package cl.verastransky.taller4.domain

import java.text.NumberFormat

class FormatCurrencyUseCase {

    private val formatter = NumberFormat.getCurrencyInstance()

    operator fun invoke(monto:Int):String {
        return formatter.format(monto)
    }
}
