package br.com.mdr.criptoapi.domain.model

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
)