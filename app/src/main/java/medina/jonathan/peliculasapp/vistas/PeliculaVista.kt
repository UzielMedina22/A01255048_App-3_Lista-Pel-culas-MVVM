package medina.jonathan.peliculasapp.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import medina.jonathan.peliculasapp.modelos.Pelicula
import medina.jonathan.peliculasapp.viewmodels.PeliculaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeliculaScreen(viewModel: PeliculaViewModel) {
    val peliculas = viewModel.peliculas.value
    var mostrarDialogo by remember {mutableStateOf(false)}
    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Películas")})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mostrarDialogo = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Película"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(peliculas) { pelicula ->
                PeliculaCard(pelicula)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (mostrarDialogo) {
        DialogoAgregarPelicula(
            onDismiss = { mostrarDialogo = false },
            onConfirm = { titulo, categoria, duracion, sinopsis ->
                viewModel.addPelicula(titulo, categoria, duracion, sinopsis)
                mostrarDialogo = false
            }
        )
    }
}

@Composable
fun PeliculaCard(pelicula: Pelicula) {
    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Título: \"${pelicula.titulo}\"")
            Text(text = "Categoría: \"${pelicula.categoria}\"")
            Text(text = "Duración: \"${pelicula.duracion}\"")
            Text(text = "Sinopsis: \"${pelicula.sinopsis}\"")
        }
    }
}

@Composable
fun DialogoAgregarPelicula(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nueva Película")},
        text = {
            Column {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = {titulo = it},
                    label = {Text("Título")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {categoria = it},
                    label = {Text("Categoría")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = duracion,
                    onValueChange = {duracion = it},
                    label = {Text("Duración")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = sinopsis,
                    onValueChange = {sinopsis = it},
                    label = {Text("Sinopsis")},
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (titulo.isNotBlank() && categoria.isNotBlank() && duracion.isNotBlank() &&
                        sinopsis.isNotBlank()) {
                        onConfirm(titulo, categoria, duracion, sinopsis)
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}