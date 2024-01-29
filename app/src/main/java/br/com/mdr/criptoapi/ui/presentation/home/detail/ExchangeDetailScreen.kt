package br.com.mdr.criptoapi.ui.presentation.home.detail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mdr.criptoapi.R
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeUI
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.ui.components.HandlePageStateResult
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.PrimaryColor
import br.com.mdr.criptoapi.ui.presentation.theme.SecondaryColor
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
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

    HandlePageStateResult(pageState = exchange, viewModel = viewModel) { success ->
        (success.result as? ExchangeUI)?.let {
            ExchangeDetailItem(exchangeData = it)
        }
    }
}

@Composable
fun ExchangeDetailItem(exchangeData: ExchangeUI) {

    Scaffold(
        containerColor = PrimaryColor
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            val (icon, name, chart,
                volumes, volDayLabel, volDay, volHourLabel, volHour,
                volMonthLabel, volMonth) = createRefs()

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

            ExchangeLineChartItem(
                modifier = Modifier
                    .constrainAs(chart) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(icon.bottom, MEDIUM_PADDING)
                        width = Dimension.fillToConstraints
                    },
                history = exchangeData.history)

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
            Text(
                modifier = Modifier
                    .constrainAs(volumes) {
                        end.linkTo(parent.end, MEDIUM_PADDING)
                        top.linkTo(chart.bottom, MEDIUM_PADDING)
                        start.linkTo(parent.start, MEDIUM_PADDING)
                        width = Dimension.fillToConstraints
                    },
                text = stringResource(id = R.string.volumes),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .constrainAs(volHourLabel) {
                        top.linkTo(volumes.bottom)
                        start.linkTo(volumes.start)
                    },
                text = stringResource(id = R.string.vol_last_hour),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                modifier = Modifier
                    .constrainAs(volHour) {
                        top.linkTo(volHourLabel.bottom)
                        start.linkTo(volumes.start)
                    },
                text = exchangeData.exchange.volumeLast24,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                modifier = Modifier
                    .constrainAs(volDayLabel) {
                        top.linkTo(volHour.bottom, SMALL_PADDING)
                        start.linkTo(volumes.start)
                    },
                text = stringResource(id = R.string.last_24_hours),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                modifier = Modifier
                    .constrainAs(volDay) {
                        top.linkTo(volDayLabel.bottom)
                        start.linkTo(volumes.start)
                    },
                text = exchangeData.exchange.volumeLastHour,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                modifier = Modifier
                    .constrainAs(volMonthLabel) {
                        top.linkTo(volDay.bottom, SMALL_PADDING)
                        start.linkTo(volumes.start)
                    },
                text = stringResource(id = R.string.vol_last_month),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                modifier = Modifier
                    .constrainAs(volMonth) {
                        top.linkTo(volMonthLabel.bottom)
                        start.linkTo(volumes.start)
                    },
                text = exchangeData.exchange.volumeLastMonth,
                style = MaterialTheme.typography.labelMedium
            )
            createHorizontalChain(icon, name, chainStyle = ChainStyle.Packed)
        }
    }
}

@Composable
fun ExchangeLineChartItem(modifier: Modifier, history: List<OHLCVData>?) {
    if (history?.isNotEmpty() == true) {
        val pointsData = mutableListOf<Point>()
//            mutableListOf(Point(0f, 40f), Point(4f, 90f), Point(8f, 0f),
//                Point(12f, 60f), Point(16f, 110f), Point(20f, 70f), Point(24f, 130f))

        val points = history.map {
            Point(
                it.
            )
        }

        //{exchange_id}_SPOT_{asset_id_base}_{asset_id_quote}
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        //val stepSize = screenWidth / pointsData.size

        val xAxisData = AxisData.Builder()
            //.axisStepSize(stepSize)
            .axisLineColor(Color.Transparent)
            .axisLabelColor(Color.White)
            .backgroundColor(PrimaryColor)
            .steps(7)
            .labelData { i -> i.toString() }
            .build()

        val steps = pointsData.size - 1

        val yAxisData = AxisData.Builder()
            .steps(steps)
            .axisLineColor(PrimaryColor)
            .backgroundColor(PrimaryColor)
            .labelData { i ->
                i.toString()
            }.build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(
                            lineType = LineType.Straight(),
                            color = SecondaryColor
                        ),
                        null,
                        SelectionHighlightPoint(),
                        null,
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = null,
            backgroundColor = PrimaryColor
        )

        LineChart(
            modifier = modifier
                .height(300.dp),
            lineChartData = lineChartData
        )
    }
}

@Preview
@Composable
fun ExchangeDetailPreview() {
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
        listOf(
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
        )))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExchangeDetailDarkPreview() {
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
        listOf(
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
    ))
}