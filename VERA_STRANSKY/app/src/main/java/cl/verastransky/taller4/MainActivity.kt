package cl.verastransky.taller4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import cl.verastransky.taller4.data.BaseDatos
import cl.verastransky.taller4.data.Medicion
import cl.verastransky.taller4.data.TipoMedicion
import cl.verastransky.taller4.ui.composables.AppMedicionUI
import cl.verastransky.taller4.ui.theme.ListaMedicionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    lifecycleScope.launch(Dispatchers.IO) {
        val db = Room.databaseBuilder(
            applicationContext,
            BaseDatos::class.java,
            name = "mediciones.db"
        ).build()
        val dao = db.medicionDao()
        medicionesDePrueba().forEach {
            dao.insertarMedicion(it)
        }

    }
        setContent {
            AppMedicionUI()

        }
    }
}

fun medicionesDePrueba():List<Medicion> {
    return listOf(
        Medicion(1, 1_000, LocalDate.now(), TipoMedicion.GAS.toString()),
        Medicion(2, 2_000, LocalDate.now(), TipoMedicion.AGUA.toString()),
        Medicion(3, 10_000, LocalDate.now(), TipoMedicion.LUZ.toString())
    )
}

@Preview(showSystemUi = true)
@Composable
fun AppMedicionesUI() {
    val contexto = LocalContext.current
    val corrutina = rememberCoroutineScope()
    val mediciones by remember { mutableStateOf(medicionesDePrueba()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                corrutina.launch(Dispatchers.IO) {
                    val db = Room.databaseBuilder(
                        contexto.applicationContext,
                        BaseDatos::class.java,
                        "mediciones.db"
                    ).build()
                    val dao = db.medicionDao()
                    medicionesDePrueba().forEach {
                        dao.insertarMedicion(it)
                    }
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = innerPadding.calculateLeftPadding(LayoutDirection.Ltr))
        ) {
            LazyColumn {
                items(mediciones) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.monto.toString(),
                                style = TextStyle(
                                    fontSize = 10.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            // Assuming you have an IconoMedicion function
                            IconoMedicion(it)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column() {
                                Text(it.categoria)
                                Text(
                                    text = it.monto.toString(),
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Row() {


                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
fun IconoMedicion(medicion: Medicion) {
    when (TipoMedicion.valueOf(medicion.categoria)) {
        TipoMedicion.GAS -> Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = TipoMedicion.GAS.toString()
        )
        TipoMedicion.AGUA -> Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = TipoMedicion.AGUA.toString()
        )
        TipoMedicion.LUZ -> Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = TipoMedicion.LUZ.toString()
        )
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
    ) {
        // monto, fecha, categoria
        TextField(
            value = fecha,
            onValueChange = { fecha = it},
            placeholder = { Text(if (fecha.isEmpty())"2024-12-25" else fecha) },
            label = { Text("Fecha") }
        )
        TextField(
            value = medidor.toString(),
            onValueChange = { medidor = it.toIntOrNull() ?: 0},
            label = { Text("Medidor") }
        )
        Text("Categoría:")
        cl.verastransky.taller4.ui.composables.OpcionesCategoriasUI()
        TextField(
            value = categoria.toString(),
            onValueChange = { categoria = it },
            label = { Text("Monto") }
        )
        Button(onClick = {

        }) {
            Text("Registrar medición")
        }
    }
}

@Preview(showSystemUi = true)
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




