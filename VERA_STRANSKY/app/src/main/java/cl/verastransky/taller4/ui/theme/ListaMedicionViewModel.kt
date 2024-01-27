package cl.verastransky.taller4.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.verastransky.taller4.Aplicacion
import cl.verastransky.taller4.data.Medicion
import cl.verastransky.taller4.data.MedicionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaMedicionViewModel(private val medicionDao:MedicionDao, private val svedStateHandle: SavedStateHandle) : ViewModel() {

    var mediciones by mutableStateOf(listOf<Medicion>())


    fun insertarMedicion(medicion:Medicion) {
        viewModelScope.launch(Dispatchers.IO) {
            medicionDao.insertarMedicion(medicion)
            obtenerMediciones()
        }
    }

    fun obtenerMediciones(): List<Medicion> {
        viewModelScope.launch(Dispatchers.IO) {
            mediciones = medicionDao.obtenerTodasLasMediciones()
        }
        return mediciones

    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle= createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                ListaMedicionViewModel(aplicacion.medicionDao, savedStateHandle)
            }
        }
    }
}




























