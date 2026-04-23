package medina.jonathan.peliculasapp.vistas

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import medina.jonathan.peliculasapp.R
import medina.jonathan.peliculasapp.modelos.Pelicula
import medina.jonathan.peliculasapp.viewmodels.PeliculaViewModel
import java.net.URI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeliculaScreen(viewModel: PeliculaViewModel) {
    val context = LocalContext.current
    val peliculas = viewModel.peliculas.value
    var mostrarDialogo by remember {mutableStateOf(false)}
    var peliculaAEditar by remember {mutableStateOf<Pelicula?>(null)}

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
                PeliculaCard(pelicula, viewModel, onLongClick = {
                    peliculaAEditar = pelicula
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (mostrarDialogo) {
        DialogoAgregarPelicula(
            onDismiss = { mostrarDialogo = false },
            onConfirm = { titulo, categoria, duracion, sinopsis, imagen, imagenURI ->
                viewModel.addPelicula(titulo, categoria, duracion, sinopsis, imagen, imagenURI)
                mostrarDialogo = false
            }
        )
    }

    peliculaAEditar?.let { pelicula ->
        DialogoEditarPelicula(
            pelicula = pelicula,
            onDismiss = { peliculaAEditar = null },
            onConfirm = { titulo, categoria, duracion, sinopsis, imagen, imagenURI ->
                viewModel.editPelicula(
                    pelicula.id, titulo, categoria, duracion, sinopsis, imagen, imagenURI
                )
                peliculaAEditar = null
            }
        )
    }
}

@Composable
fun PeliculaCard(
    pelicula: Pelicula,
    viewModel: PeliculaViewModel,
    onLongClick: () -> Unit
) {
    var mostrarDialogoEliminarPelicula by remember {mutableStateOf(false)}

    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (pelicula.imagenURI != null) {
                AsyncImage(
                    model = pelicula.imagenURI,
                    contentDescription = pelicula.titulo,
                    modifier = Modifier.size(180.dp)
                )
            } else {
                Image(
                    painter = painterResource(pelicula.imagen),
                    contentDescription = pelicula.titulo,
                    modifier = Modifier.size(180.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Título: \"${pelicula.titulo}\"")
            Text(text = "Categoría: \"${pelicula.categoria}\"")
            Text(text = "Duración: \"${pelicula.duracion}\"")
            Text(text = "Sinopsis: \"${pelicula.sinopsis}\"")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    mostrarDialogoEliminarPelicula = true
                }
            ) {
                Text("Eliminar")
            }
        }

        if (mostrarDialogoEliminarPelicula) {
            DialogoEliminarPelicula(
                pelicula = pelicula,
                onDismiss = { mostrarDialogoEliminarPelicula = false },
                onConfirm = { pelicula ->
                    viewModel.deletePelicula(pelicula)
                    mostrarDialogoEliminarPelicula = false
                }
            )
        }
    }
}

@Composable
fun DialogoAgregarPelicula(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, Int, String?) -> Unit
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf(R.drawable.bootstrap_camera_video) }
    var imagenURI by remember { mutableStateOf<Uri?>(null) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenURI = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nueva Película")},
        text = {
            Column {
                Text("Seleccionar imagen")
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                   modifier = Modifier
                       .size(80.dp)
                       .clickable{
                           launcher.launch("image/*")
                       }
                ){
                    if (imagenURI != null) {
                        AsyncImage(
                            model = imagenURI,
                            contentDescription = titulo,
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Imagen",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
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
                        onConfirm(titulo, categoria, duracion, sinopsis, imagen, imagenURI.toString())
                    } else {
                        Toast.makeText(
                            context,
                            "Falta llenar uno o más campos.",
                            Toast.LENGTH_SHORT
                        ).show()
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

@Composable
fun DialogoEditarPelicula(
    pelicula: Pelicula,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, Int, String?) -> Unit
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf(pelicula.titulo) }
    var categoria by remember { mutableStateOf(pelicula.categoria) }
    var duracion by remember { mutableStateOf(pelicula.duracion) }
    var sinopsis by remember { mutableStateOf(pelicula.sinopsis) }
    var imagen by remember { mutableStateOf(pelicula.imagen) }
    var imagenURI by remember { mutableStateOf<Uri?>(pelicula.imagenURI?.let {Uri.parse(it)}) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenURI = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Editar Película")},
        text = {
            Column {
                Text("Seleccionar imagen")
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable{
                            launcher.launch("image/*")
                        }
                ){
                    if (imagenURI != null) {
                        AsyncImage(
                            model = imagenURI,
                            contentDescription = titulo,
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Imagen",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
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
                        if (imagenURI == null) {
                            onConfirm(titulo, categoria, duracion, sinopsis, imagen, null)
                        } else {
                            onConfirm(titulo, categoria, duracion, sinopsis, imagen, imagenURI.toString())
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Falta llenar uno o más campos.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DialogoEliminarPelicula(
    pelicula: Pelicula,
    onDismiss: () -> Unit,
    onConfirm: (Pelicula) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Eliminar Película")},
        text = {
            Column {
                Text("¿Está seguro de que desea eliminar la película \"${pelicula.titulo}\"?")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(pelicula)
                }
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}