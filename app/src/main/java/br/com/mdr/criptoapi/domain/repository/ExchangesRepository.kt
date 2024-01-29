package br.com.mdr.criptoapi.domain.repository

import androidx.paging.PagingData
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.OHLCVData
import kotlinx.coroutines.flow.Flow

interface ExchangesRepository {
    fun getExchanges(): Flow<PagingData<Exchange>>
    fun searchExchanges(query: String): Flow<PagingData<Exchange>>
    suspend fun getExchangeDetail(exchangeId: String): Exchange
    suspend fun getOHLCVHistory(exchangeId: String): List<OHLCVData>
}