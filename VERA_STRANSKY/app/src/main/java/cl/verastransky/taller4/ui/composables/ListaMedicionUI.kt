package cl.verastransky.taller4.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cl.verastransky.taller4.data.Medicion


@Preview(showSystemUi = true)
@Composable
fun PantallaListaMedicion(
    mediciones: List<Medicion> = listOf(),
    onAdd:() -> Unit = {}

) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAdd()
            }) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Agregar registro de medición"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = it.calculateTopPadding())
        ) {
            items(mediciones) {
                Text(it.categoria)
            }
        }
    }

}
