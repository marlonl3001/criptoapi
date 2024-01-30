package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.ui.components.mpchart.base.MpChartLineView
import br.com.mdr.criptoapi.ui.presentation.theme.SecondaryColor
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun ExchangeChart(modifier: Modifier, entries: List<Entry>, onEntrySelected: (OHLCVData?, Int) -> Unit) {
    var isFirstLaunch by remember { mutableStateOf(true) }

    if (entries.isNotEmpty()) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                return@AndroidView MpChartLineView(
                    onValueSelected = {
                        onEntrySelected.invoke(it.data as? OHLCVData, entries.indexOf(it))
                    }
                ).create(context)
            },
            update = {
                it.data = LineData(
                    LineDataSet(entries, "").apply {
                        axisDependency = YAxis.AxisDependency.RIGHT
                        setDrawCircles(false)
                        setDrawValues(false)
                        color = SecondaryColor.toArgb()
                    }
                )
                it.notifyDataSetChanged()
                it.invalidate()
            }
        )
        if (isFirstLaunch) {
            onEntrySelected.invoke(entries.firstOrNull()?.data as? OHLCVData, 0)
            isFirstLaunch = false
        }
    }
}