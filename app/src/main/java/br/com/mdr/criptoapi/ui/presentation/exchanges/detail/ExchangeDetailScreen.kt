package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeData
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.ui.components.DetailScreenTopBar
import br.com.mdr.criptoapi.ui.components.HandlePageStateResult
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.PrimaryColor
import coil.compose.AsyncImage

@Composable
fun ExchangeDetailScreen(
    navController: NavHostController,
    viewModel: ExchangeDetailViewModel = hiltViewModel()
) {
    val exchange by viewModel.exchange.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.getExchangeDetail()
    }

    Scaffold(
        containerColor = PrimaryColor,
        topBar = {
            DetailScreenTopBar(navController = navController)
        }
    ) { paddingValues ->
        HandlePageStateResult(pageState = exchange, viewModel = viewModel) { success ->
            (success.result as? ExchangeData)?.let {
                ExchangeDetailItem(
                    modifier = Modifier.padding(paddingValues),
                    exchangeData = it,
                    viewModel = viewModel
                )
            }
        }
    }

}

@Composable
fun ExchangeDetailItem(modifier: Modifier, exchangeData: ExchangeData,
                       viewModel: ExchangeDetailViewModel
) {
    val lineChartData by viewModel.lineChartData.collectAsState()
    val currentAsset by viewModel.currentAsset.collectAsState()

    val priceTraded by viewModel.priceTraded.collectAsState()

    val priceTradedInterval by viewModel.priceTradedInterval.collectAsState()

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(35.dp),
                model = exchangeData.exchange.url,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = MEDIUM_PADDING),
                text = exchangeData.exchange.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }

        AssetsList(exchangeData.symbols, currentAsset) { selectedAsset ->
            viewModel.updateAssetData(assetId = selectedAsset, selectedIndex)
        }

        ExchangeTransactionInfo(
            modifier = Modifier.fillMaxWidth(),
            price = priceTraded,
            interval = priceTradedInterval
        )
        ExchangeChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(SMALL_PADDING),
            entries = lineChartData
        ) { data, index ->
            selectedIndex = index
            viewModel.updatePriceValues(data)
        }
        ExchangeDetailInfo(
            modifier = Modifier
                .fillMaxWidth(),
            exchange = exchangeData.exchange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeDetailPreview() {
    val ohlcvData = mutableMapOf("ETH" to
            listOf(
                OHLCVData(40092.1, 40095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
                OHLCVData(41092.1, 41095.89, 39939.04, 40012.0, "2024-01-25T11:59:58.7670000Z", "2024-01-25T11:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 42.9786),
                OHLCVData(42092.1, 42095.89, 39939.04, 40012.0, "2024-01-25T12:59:58.7670000Z", "2024-01-25T12:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 31.9786),
                OHLCVData(43092.1, 43095.89, 39939.04, 40012.0, "2024-01-25T13:59:58.7670000Z", "2024-01-25T13:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 37.9786),
                OHLCVData(44092.1, 44095.89, 39939.04, 40012.0, "2024-01-25T14:59:58.7670000Z", "2024-01-25T14:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 30.9786),
                OHLCVData(45092.1, 45095.89, 39939.04, 40012.0, "2024-01-25T15:59:58.7670000Z", "2024-01-25T15:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 43.9786),
                OHLCVData(46092.1, 46095.89, 39939.04, 40012.0, "2024-01-25T16:59:58.7670000Z", "2024-01-25T16:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 49.9786),
                OHLCVData(47092.1, 47095.89, 39939.04, 40012.0, "2024-01-25T17:59:58.7670000Z", "2024-01-25T17:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 36.9786),
                OHLCVData(48092.1, 48095.89, 39939.04, 40012.0, "2024-01-25T18:59:58.7670000Z", "2024-01-25T18:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 33.9786),
                OHLCVData(49092.1, 49095.89, 39939.04, 40012.0, "2024-01-25T19:59:58.7670000Z", "2024-01-25T19:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 42.9786),
                OHLCVData(50092.1, 50095.89, 39939.04, 40012.0, "2024-01-25T20:59:58.7670000Z", "2024-01-25T20:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 45.9786),
                OHLCVData(51092.1, 51095.89, 39939.04, 40012.0, "2024-01-25T21:59:58.7670000Z", "2024-01-25T21:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 37.9786)
            ))
    ExchangeDetailItem(modifier = Modifier, exchangeData = ExchangeData(
        Exchange(
            "ETHERIUM",
            "Etherium",
            3456538.57,
            654665865.0,
            3698218468.75,
            null
        ),
        ohlcvData
    ),
        hiltViewModel()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ExchangeDetailDarkPreview() {
    val ohlcvData = mutableMapOf("ETH" to
            listOf(
                OHLCVData(40092.1, 40095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
                OHLCVData(41092.1, 41095.89, 39939.04, 40012.0, "2024-01-25T11:59:58.7670000Z", "2024-01-25T11:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 42.9786),
                OHLCVData(42092.1, 42095.89, 39939.04, 40012.0, "2024-01-25T12:59:58.7670000Z", "2024-01-25T12:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 31.9786),
                OHLCVData(43092.1, 43095.89, 39939.04, 40012.0, "2024-01-25T13:59:58.7670000Z", "2024-01-25T13:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 37.9786),
                OHLCVData(44092.1, 44095.89, 39939.04, 40012.0, "2024-01-25T14:59:58.7670000Z", "2024-01-25T14:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 30.9786),
                OHLCVData(45092.1, 45095.89, 39939.04, 40012.0, "2024-01-25T15:59:58.7670000Z", "2024-01-25T15:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 43.9786),
                OHLCVData(46092.1, 46095.89, 39939.04, 40012.0, "2024-01-25T16:59:58.7670000Z", "2024-01-25T16:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 49.9786),
                OHLCVData(47092.1, 47095.89, 39939.04, 40012.0, "2024-01-25T17:59:58.7670000Z", "2024-01-25T17:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 36.9786),
                OHLCVData(48092.1, 48095.89, 39939.04, 40012.0, "2024-01-25T18:59:58.7670000Z", "2024-01-25T18:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 33.9786),
                OHLCVData(49092.1, 49095.89, 39939.04, 40012.0, "2024-01-25T19:59:58.7670000Z", "2024-01-25T19:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 42.9786),
                OHLCVData(50092.1, 50095.89, 39939.04, 40012.0, "2024-01-25T20:59:58.7670000Z", "2024-01-25T20:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 45.9786),
                OHLCVData(51092.1, 51095.89, 39939.04, 40012.0, "2024-01-25T21:59:58.7670000Z", "2024-01-25T21:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 37.9786)
            ))
    ExchangeDetailItem(modifier = Modifier, exchangeData = ExchangeData(
        Exchange(
        "ETHERIUM",
        "Etherium",
        3456538.57,
        654665865.0,
        3698218468.75,
        null
    ),
        ohlcvData
    ),
        hiltViewModel()
    )
}