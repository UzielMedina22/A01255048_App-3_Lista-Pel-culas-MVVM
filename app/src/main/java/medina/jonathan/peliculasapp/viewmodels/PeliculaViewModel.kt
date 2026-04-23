package medina.jonathan.peliculasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import medina.jonathan.peliculasapp.modelos.Pelicula
import medina.jonathan.peliculasapp.modelos.PeliculaRepositorio

class PeliculaViewModel(val repo: PeliculaRepositorio): ViewModel() {
    private val _peliculas = mutableStateOf<List<Pelicula>>(emptyList())
    val peliculas: State<List<Pelicula>> = _peliculas

    init{
        getPeliculas()
    }

    private fun getPeliculas() {
        _peliculas.value = repo.getPeliculas()
    }

    fun addPelicula(
        titulo: String,
        categoria: String,
        duracion: String,
        sinopsis: String,
        imagen: Int,
        imagenURI: String?
    ) {
        repo.addPelicula(titulo, categoria, duracion, sinopsis, imagen, imagenURI)
        _peliculas.value = repo.getPeliculas()
    }

    fun deletePelicula(pelicula: Pelicula) {
        repo.deletePelicula(pelicula)
        _peliculas.value = repo.getPeliculas()
    }

    fun editPelicula(
        id: Int, titulo: String,
        categoria: String,
        duracion: String,
        sinopsis: String,
        imagen: Int,
        imagenURI: String?
    ) {
        val pelicula = Pelicula(
            id, titulo, categoria, duracion, sinopsis, imagen, imagenURI
        )
        repo.editPelicula(pelicula)
        _peliculas.value = repo.getPeliculas()
    }
}