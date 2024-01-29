package br.com.mdr.criptoapi.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import br.com.mdr.criptoapi.utils.getDollarAmount
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exchange_table")
data class Exchange(
    @PrimaryKey
    @SerializedName("exchange_id")
    val id: String,
    val name: String,
//    @SerializedName("data_quote_end")
//    val dataQuoteEnd: String,
//    @SerializedName("data_quote_start")
//    val dataQuoteStart: String,
//    @SerializedName("data_overbook_end")
//    val dataOverbookEnd: String,
//    @SerializedName("data_overbook_start")
//    val dataOverbookStart: String,
//    @SerializedName("data_trade_end")
//    val dataTradeEnd: String,
//    @SerializedName("data_trade_start")
//    val dataTradeStart: String,
//    @SerializedName("data_trade_count")
//    val dataTradeCount: Int,
//    @SerializedName("data_symbols_count")
//    val dataSymbolsCount: Int,
    @SerializedName("volume_1hrs_usd")
    val volumeHour: Double,
    @SerializedName("volume_1day_usd")
    val volumeDay: Double,
    @SerializedName("volume_1mth_usd")
    val volumeMonth: Double,
//    @SerializedName("metric_id")
//    val metricId: List<String>,
//    val icons: List<String>,
    var url: String?
) {
    @get:Ignore
    val exchangeId: String
        get() = "ID: $id"

    val volumeLastHour: String
        get() = volumeHour.getDollarAmount()
    @get:Ignore
    val volumeLast24: String
        get() = volumeDay.getDollarAmount()

    val volumeLastMonth: String
        get() = volumeMonth.getDollarAmount()
}

data class ExchangeUI(
    val exchange: Exchange,
    var history: List<OHLCVData>? = null
)

fun Exchange.toExchangeUI() = ExchangeUI(exchange = this)
