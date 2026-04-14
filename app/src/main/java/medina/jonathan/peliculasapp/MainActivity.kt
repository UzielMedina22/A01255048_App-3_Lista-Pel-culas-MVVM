package medina.jonathan.peliculasapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import medina.jonathan.peliculasapp.modelos.PeliculaRepositorio
import medina.jonathan.peliculasapp.viewmodels.PeliculaViewModel
import medina.jonathan.peliculasapp.viewmodels.PeliculaViewModelFactory
import medina.jonathan.peliculasapp.vistas.PeliculaScreen

class MainActivity : ComponentActivity() {
    private val TAG = "PELÍCULAS"

    private val viewModel: PeliculaViewModel by viewModels {
        PeliculaViewModelFactory(PeliculaRepositorio())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Create")
        setContent {
            PeliculaScreen(viewModel = viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Start")
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Resume")
        Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroy")
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Stop")
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Pause")
        Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Restart")
        Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}