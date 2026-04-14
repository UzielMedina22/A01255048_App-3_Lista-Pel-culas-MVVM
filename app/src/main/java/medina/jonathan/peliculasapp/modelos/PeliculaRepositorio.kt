package medina.jonathan.peliculasapp.modelos

class PeliculaRepositorio {
    fun getPeliculas(): List<Pelicula> {
        return listOf(
            Pelicula(
                1,
                "La Leyenda de Seymour",
                "Acción",
                "1h 45min",
                "Seymour es un hombre liberado del sistema popular opresor. Él se enfrentará a los jefes maestros para que muchas personas despierten y sean libres."
            ),
            Pelicula(
                2,
                "Los Remanitas: La Batalla de los Fenazitas",
                "Ciencia-Ficción",
                "1h 50min",
                "Un grupo malvado dentro del pueblo los Remanitas provoca una guerra contra uno de sus pueblos aliados: los Fenazitas. Los Remanitas buscarán cómo hacer la paz con los Fenazitas para no dejar de existir."
            ),
            Pelicula(
                3,
                "El Éxodo",
                "Histórica",
                "1h 30min",
                "En esta cinta cinematográfica se relata la historia de Moisés, quien fue un líder llamado por Dios para liberar al pueblo de Israel de la esclavitud en la tierra de Egipto."
            ),
            Pelicula(
                4,
                "El Gran Show del Payaso Bobbi: ¡En Vivo!",
                "Infantil",
                "1h 40min",
                "Esta es una grabación inédita del Gran Show del Payaso Bobbi llevado a cabo en 1989. ¡Disfruta de canciones y actos más icónicos de Bobbi!"
            )
        )
    }
}