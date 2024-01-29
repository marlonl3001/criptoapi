package br.com.mdr.criptoapi.domain.model

import com.google.gson.annotations.SerializedName

data class ExchangeIcon(
    @SerializedName("exchange_id")
    val exchangeId: String,
    val url: String
)
