package br.com.mdr.criptoapi.navigation

import br.com.mdr.criptoapi.common.Constants.EXCHANGE_ID_KEY

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object ExchangeDetail: Screen("exchange_detail/{$EXCHANGE_ID_KEY}") {
        fun passExchangeId(id: String): String = "exchange_detail/$id"
    }
}