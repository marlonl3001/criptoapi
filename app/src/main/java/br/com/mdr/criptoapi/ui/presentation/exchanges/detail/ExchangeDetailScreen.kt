package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import android.content.res.Configuration
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeUI
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.ui.components.HandlePageStateResult
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.PrimaryColor
import coil.compose.AsyncImage
import com.github.mikephil.charting.data.Entry

@Composable
fun ExchangeDetailScreen(
    navController: NavHostController,
    viewModel: ExchangeDetailViewModel = hiltViewModel()
) {
    val exchange by viewModel.exchange.collectAsState()
    val lineChartData by viewModel.lineChartData.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.getExchangeDetail()
    }

    HandlePageStateResult(pageState = exchange, viewModel = viewModel) { success ->
        (success.result as? ExchangeUI)?.let {
            ExchangeDetailItem(exchangeData = it, lineChartData = lineChartData)
        }
    }
}

@Composable
fun ExchangeDetailItem(exchangeData: ExchangeUI, lineChartData: List<Entry>) {
    var priceTraded by remember {
        mutableStateOf("")
    }

    var priceTradedInterval by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = PrimaryColor
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            val (icon, name, chart, transactionInfo, detailInfo) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .size(35.dp)
                    .constrainAs(icon) {
                        end.linkTo(name.start)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, MEDIUM_PADDING)
                    },
                model = exchangeData.exchange.url,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = MEDIUM_PADDING)
                    .constrainAs(name) {
                        end.linkTo(parent.end)
                        bottom.linkTo(icon.bottom)
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                    },
                text = exchangeData.exchange.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            ExchangeTransactionInfo(
                modifier = Modifier
                    .constrainAs(transactionInfo) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(icon.bottom, MEDIUM_PADDING)
                        width = Dimension.fillToConstraints
                    },
                price = priceTraded,
                interval = priceTradedInterval
            )
            ExchangeChart(
                modifier = Modifier
                    .constrainAs(chart) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(transactionInfo.bottom, MEDIUM_PADDING)
                        width = Dimension.fillToConstraints
                    }
                    .height(300.dp)
                    .padding(SMALL_PADDING),
                entries = lineChartData
            ) { data ->
                priceTraded = data?.getPriceTraded().toString()
                priceTradedInterval = data?.getIntervalPeriod().toString()
            }
            ExchangeDetailInfo(
                modifier = Modifier
                    .constrainAs(detailInfo) {
                        end.linkTo(parent.end, MEDIUM_PADDING)
                        top.linkTo(chart.bottom, MEDIUM_PADDING)
                        start.linkTo(parent.start, MEDIUM_PADDING)
                        width = Dimension.fillToConstraints
                    },
                exchange = exchangeData.exchange
            )
            createHorizontalChain(icon, name, chainStyle = ChainStyle.Packed)
        }
    }
}

@Preview
@Composable
fun ExchangeDetailPreview() {
    val ohlcvData = listOf(
        OHLCVData(40092.1, 40095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(41092.1, 41095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(42092.1, 42095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(43092.1, 43095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(44092.1, 44095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(45092.1, 45095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(46092.1, 46095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(47092.1, 47095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(48092.1, 48095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(49092.1, 49095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(50092.1, 50095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(51092.1, 51095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786)
    )
    ExchangeDetailItem(exchangeData = ExchangeUI(
        Exchange(
            "ETHERIUM",
            "Etherium",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            0,
//            0,
//            0.0,
            3456538.57,
            654665865.0,
            3698218468.75,
//            0.0,
//            listOf(),
//            listOf(),
            null
        ),
        ohlcvData
    ),
        fetchLineChartData(ohlcvData)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExchangeDetailDarkPreview() {
    val ohlcvData = listOf(
        OHLCVData(40092.1, 40095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(41092.1, 41095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(42092.1, 42095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(43092.1, 43095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(44092.1, 44095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(45092.1, 45095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(46092.1, 46095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(47092.1, 47095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(48092.1, 48095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(49092.1, 49095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(50092.1, 50095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786),
        OHLCVData(51092.1, 51095.89, 39939.04, 40012.0, "2024-01-25T10:59:58.7670000Z", "2024-01-25T10:00:07.3530000Z", "2024-01-25T11:00:00.0000000Z", "2024-01-25T10:00:00.0000000Z", 1158, 39.9786)
    )
    ExchangeDetailItem(exchangeData = ExchangeUI(
        Exchange(
        "ETHERIUM",
        "Etherium",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            0,
//            0,
//            0.0,
        3456538.57,
        654665865.0,
        3698218468.75,
//            0.0,
//            listOf(),
//            listOf(),
        null
    ),
        ohlcvData
    ),
        fetchLineChartData(ohlcvData)
    )
}

private fun fetchLineChartData(exchangesHistory: List<OHLCVData>?): List<Entry> {
    val chartData = mutableListOf<Entry>()
    exchangesHistory?.forEachIndexed { index, ohlcvData ->
        chartData.add(
            Entry(
                index.toFloat(),
                ohlcvData.volumeTraded.toFloat()
            )
        )
    }
    return chartData
}