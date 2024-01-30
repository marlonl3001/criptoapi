package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TextButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mdr.criptoapi.domain.model.Symbol
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.SecondaryColor

@Composable
fun AssetsList(symbols: List<Symbol>, currentAsset: String, onButtonClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(symbols) { index, symbol ->
            TextButton(
                modifier = Modifier
                    .padding(SMALL_PADDING),
                onClick = {
                    onButtonClick(symbol.assetIdBase)
                }
            ) {
                Text(
                    text = symbol.assetIdBase,
                    color = if (symbol.assetIdBase == currentAsset) SecondaryColor else Color.White
                )
            }
            if (index < symbols.lastIndex) {
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(0.4f),
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview
@Composable
fun AssetsListPreview() {
    AssetsList(symbols = getSymbols(), currentAsset = "BTC", onButtonClick = {})
}

private fun getSymbols() = listOf(
        Symbol(
            "BTC",
            "USD",
            "BINANCE",
            "BINANCE_SPOT_BTC_USD"
        ),
        Symbol(
            "ETH",
            "USD",
            "BINANCE",
            "BINANCE_SPOT_ETH_USD"
        ),
        Symbol(
            "XRP",
            "USD",
            "BINANCE",
            "BINANCE_SPOT_XRP_USD"
        ),
        Symbol(
            "SRC",
            "USD",
            "BINANCE",
            "BINANCE_SPOT_SRC_USD"
        )
    )