package br.com.mdr.criptoapi.data.remote.api

import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeIcon
import br.com.mdr.criptoapi.domain.model.OHLCVData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CriptoApi {

    @GET("exchanges")
    suspend fun getExchanges(): List<Exchange>

    @GET("exchanges/icons/32")
    suspend fun getExchangeIcons(): List<ExchangeIcon>

    @GET("ohlcv/{symbolId}/history")
    suspend fun getOHLCVHistory(
        @Path("symbolId") symbolId: String,
        @Query("time_start", encoded = true) timeStart: String,
        @Query("time_end", encoded = true) timeEnd: String,
        @Query("period_id") periodId: String = "1HRS"
    ): List<OHLCVData>
}