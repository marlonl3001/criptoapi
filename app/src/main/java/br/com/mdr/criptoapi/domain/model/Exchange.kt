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
    @SerializedName("volume_1hrs_usd")
    val volumeHour: Double,
    @SerializedName("volume_1day_usd")
    val volumeDay: Double,
    @SerializedName("volume_1mth_usd")
    val volumeMonth: Double,
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

data class ExchangeData(
    val exchange: Exchange,
    val history: MutableMap<String, List<OHLCVData>> = mutableMapOf(),
    val symbols: MutableList<Symbol> = mutableListOf()
)
