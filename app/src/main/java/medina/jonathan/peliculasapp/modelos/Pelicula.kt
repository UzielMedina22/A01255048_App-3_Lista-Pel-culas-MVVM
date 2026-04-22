package medina.jonathan.peliculasapp.modelos

data class Pelicula(
    val id: Int,
    var titulo: String,
    var categoria: String,
    var duracion: String,
    var sinopsis: String,
    var imagen: Int,
    var imagenURI: String? = null
)