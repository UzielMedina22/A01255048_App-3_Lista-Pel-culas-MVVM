package medina.jonathan.peliculasapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import medina.jonathan.peliculasapp.modelos.Repositorio

class PeliculaViewModelFactory(private val repo: Repositorio): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeliculaViewModel::class.java)) {
            return PeliculaViewModel(repo) as T
        }
        throw IllegalArgumentException("Desconocido")
    }
}