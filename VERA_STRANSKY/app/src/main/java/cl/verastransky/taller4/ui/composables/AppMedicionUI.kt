package cl.verastransky.taller4.ui.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.verastransky.taller4.ui.theme.ListaMedicionViewModel


@Composable
fun AppMedicionUI(
    navController: NavHostController = rememberNavController(),
    vmListaMedicion: ListaMedicionViewModel = viewModel(factory = ListaMedicionViewModel.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = "form"
    )
    {
        composable("inicio") {
            PantallaListaMedicion(
                mediciones = vmListaMedicion.mediciones,
                onAdd = { navController.navigate("form") }
            )
        }
        composable("form") {
            PantallaFormMedicion()
        }
    }
}





