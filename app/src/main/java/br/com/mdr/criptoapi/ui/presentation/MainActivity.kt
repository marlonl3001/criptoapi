package br.com.mdr.criptoapi.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.com.mdr.criptoapi.navigation.HomeNavGraph
import br.com.mdr.criptoapi.ui.presentation.theme.CriptoAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CriptoAPITheme {
                val navController = rememberNavController()
                HomeNavGraph(navController = navController)
            }
        }
    }
}