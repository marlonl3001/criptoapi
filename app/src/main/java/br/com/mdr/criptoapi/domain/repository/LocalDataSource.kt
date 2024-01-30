package br.com.mdr.criptoapi.domain.repository

import androidx.paging.PagingData
import br.com.mdr.criptoapi.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getSelectedExchange(exchangeId: String): Exchange
    fun searchExchanges(query: String): Flow<PagingData<Exchange>>
}
