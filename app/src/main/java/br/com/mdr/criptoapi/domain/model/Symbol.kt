package br.com.mdr.criptoapi.domain.model

import com.google.gson.annotations.SerializedName

data class Symbol(
    @SerializedName("asset_id_base")
    val assetIdBase: String,
    @SerializedName("asset_id_quote")
    val assetIdQuote: String,
    @SerializedName("exchange_id")
    val exchangeId: String,
    @SerializedName("symbol_id")
    val symbolId: String
)
