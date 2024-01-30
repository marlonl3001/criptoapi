package br.com.mdr.criptoapi.domain.model

import br.com.mdr.criptoapi.utils.dateFormatted
import br.com.mdr.criptoapi.utils.getDollarAmount
import br.com.mdr.criptoapi.utils.hourFormatted
import com.google.gson.annotations.SerializedName

data class OHLCVData(
    @SerializedName("price_close")
    val priceClose: Double,
    @SerializedName("price_high")
    val priceHigh: Double,
    @SerializedName("price_low")
    val priceLow: Double,
    @SerializedName("price_open")
    val priceOpen: Double,
    @SerializedName("time_close")
    val timeClose: String,
    @SerializedName("time_open")
    val timeOpen: String,
    @SerializedName("time_period_end")
    val timePeriodEnd: String,
    @SerializedName("time_period_start")
    val timePeriodStart: String,
    @SerializedName("trades_count")
    val tradesCount: Int,
    @SerializedName("volume_traded")
    val volumeTraded: Double
) {
    fun getPriceTraded(): String {
        return (priceClose * volumeTraded).getDollarAmount()
    }

    fun getIntervalPeriod(): String {
        return "${timePeriodStart.hourFormatted()} às ${timePeriodEnd.hourFormatted()}\n${timePeriodStart.dateFormatted()}"
    }
}