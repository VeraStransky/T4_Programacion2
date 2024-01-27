package cl.verastransky.taller4.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.verastransky.taller4.data.Medicion
import cl.verastransky.taller4.ui.theme.ListaMedicionViewModel


@Composable
fun OpcionesCategoriasUI() {
    val categorias = listOf("Agua", "Luz", "Gas")
    var categoriaSeleccionada by rememberSaveable { mutableStateOf( categorias[0]) }
    Column(Modifier.selectableGroup()) {
        categorias.forEach { categoria ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (categoria == categoriaSeleccionada),
                        onClick = { categoriaSeleccionada = categoria },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (categoria == categoriaSeleccionada),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = categoria,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PantallaFormMedicion(
    onSaveMedicion: (medicion:Medicion) -> Unit = {},
    vmListaMedicion: ListaMedicionViewModel = viewModel(factory = ListaMedicionViewModel.Factory)
) {
    val contexto = LocalContext.current
    var medidor by rememberSaveable { mutableIntStateOf(0) }
    var fecha by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    )  {
        TextField(
            value = medidor.toString(),
            onValueChange = { medidor = it.toIntOrNull() ?: 0 },
            label = { Text("Medidor") }
        )
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            placeholder = { Text("2024-12-25") },
            label = { Text("Fecha") }
        )
        Text("Categoría:")
        OpcionesCategoriasUI()

        TextField(
            value = categoria.toString(),
            onValueChange = { categoria = it },
            label = { Text("Categoría") }
        )
        Button(onClick = {
        }) {
            Text("Registrar medición")
        }
    }
}



