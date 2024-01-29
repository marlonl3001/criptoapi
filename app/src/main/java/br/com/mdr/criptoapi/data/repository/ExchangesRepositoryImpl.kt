package br.com.mdr.criptoapi.data.repository

import androidx.paging.PagingData
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import br.com.mdr.criptoapi.domain.repository.LocalDataSource
import br.com.mdr.criptoapi.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class ExchangesRepositoryImpl(
    private val dataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): ExchangesRepository {

    override fun getExchanges(): Flow<PagingData<Exchange>> =
        dataSource.getAllExchanges()

    override fun searchExchanges(query: String): Flow<PagingData<Exchange>> =
        localDataSource.searchExchanges(query)

    override suspend fun getExchangeDetail(exchangeId: String): Exchange {
        return localDataSource.getSelectedExchange(exchangeId)
    }

    override suspend fun getOHLCVHistory(exchangeId: String): List<OHLCVData> {
        return dataSource.getOHLCVHistory(exchangeId)
    }
}