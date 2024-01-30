package br.com.mdr.criptoapi.domain.repository

import androidx.paging.PagingData
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.Symbol
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllExchanges(): Flow<PagingData<Exchange>>
    suspend fun getOHLCVHistory(exchangeId: String, symbolId: String): List<OHLCVData>
    suspend fun getExchangeSymbols(exchangeId: String): List<Symbol>
}
