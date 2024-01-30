package br.com.mdr.criptoapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.mdr.criptoapi.common.Constants.EXCHANGE_ID_KEY
import br.com.mdr.criptoapi.ui.presentation.exchanges.ExchangesScreen
import br.com.mdr.criptoapi.ui.presentation.exchanges.detail.ExchangeDetailScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            ExchangesScreen(navController = navController)
        }
        composable(
            route = Screen.ExchangeDetail.route,
            arguments = listOf(
                navArgument(name = EXCHANGE_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            ExchangeDetailScreen(navController = navController)
        }
    }
}