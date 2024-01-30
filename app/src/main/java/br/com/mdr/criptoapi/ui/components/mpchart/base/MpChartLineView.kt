package br.com.mdr.criptoapi.ui.components.mpchart.base

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

open class MpChartLineView(private val onValueSelected: (Entry) -> Unit) : IMpChartLineView, OnChartValueSelectedListener {
    private var mLineChart: LineChart? = null
    override fun create(context: Context, colorSurface: Color, colorOnSurface: Color): LineChart {
        mLineChart = LineChart(context).apply {

            applyAxis(this)

            legend.isEnabled = false

            setDrawGridBackground(false)
            setDrawBorders(false)
            setTouchEnabled(true)
            setOnChartValueSelectedListener(this@MpChartLineView)

            description.isEnabled = false

        }
        return mLineChart ?: LineChart(context)
    }

    private fun applyAxis(lineChart: LineChart) {
        lineChart.apply {

            xAxis.granularity = 1f
            xAxis.setDrawAxisLine(false)
            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textColor = Color.White.toArgb()

            axisRight.isEnabled = false
            axisLeft.isEnabled = false
        }
    }

    override fun onValueSelected(e: Entry, h: Highlight?) {
        onValueSelected.invoke(e)
    }

    override fun onNothingSelected() = Unit
}