package medina.jonathan.peliculasapp.modelos

import medina.jonathan.peliculasapp.R

class PeliculaRepositorio {
    private val _peliculas = mutableListOf<Pelicula>(
        Pelicula(
            1,
            "La Leyenda de Seymour",
            "Acción",
            "1h 45min",
            "Seymour es un hombre liberado del sistema popular opresor. Él se enfrentará a los jefes maestros para que muchas personas despierten y sean libres.",
            R.drawable.bootstrap_camera_video
        ),
        Pelicula(
            2,
            "Los Remanitas: La Batalla de los Fenazitas",
            "Ciencia-Ficción",
            "1h 50min",
            "Un séquito malévolo de un pueblo cercano a los Remanitas conspira contra uno de sus pueblos aliados: los Fenazitas. Los Remanitas buscarán la manera de salvar a los Fenazitas, procurando la paz en medio de la guerra.",
            R.drawable.remanitas
        ),
        Pelicula(
            3,
            "El Éxodo",
            "Historia",
            "1h 30min",
            "En esta cinta cinematográfica se relata la historia de Moisés, un líder llamado por Dios para liberar al pueblo de Israel de la esclavitud en Egipto.",
            R.drawable.bootstrap_camera_video
        ),
        Pelicula(
            4,
            "El Gran Show del Payaso Bobbi: ¡En Vivo!",
            "Infantil",
            "1h 40min",
            "Esta es una grabación inédita del Gran Show del Payaso Bobbi llevado a cabo en 1989. ¡Disfruta de canciones y actos más icónicos de Bobbi!",
            R.drawable.bobbi
        )
    )

    fun getPeliculas(): List<Pelicula> {
        return _peliculas.toList()
    }

    fun addPelicula(
        titulo: String,
        categoria: String,
        duracion: String,
        sinopsis: String,
        imagen: Int,
        imagenURI: String?
    ) {
        var id = 0
        for (pelicula in _peliculas) {
            if (pelicula.id > id) { id = pelicula.id }
        }
        val pelicula = Pelicula(id, titulo, categoria, duracion, sinopsis, imagen, imagenURI)
        _peliculas.add(pelicula)
    }

    fun deletePelicula(pelicula: Pelicula) {
        if (_peliculas.contains(pelicula)) { _peliculas.remove(pelicula) }
    }
}