package br.com.mdr.criptoapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.mdr.criptoapi.common.Constants.DEFAULT_PAGE_SIZE
import br.com.mdr.criptoapi.data.local.CriptoDatabase
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.repository.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSourceImpl(
    private val database: CriptoDatabase
) : LocalDataSource {
    private val exchangeDao = database.getExchangeDao()

    override suspend fun getSelectedExchange(exchangeId: String): Exchange {
        return exchangeDao.getSelectedExchange(exchangeId)
    }

    override fun searchExchanges(query: String): Flow<PagingData<Exchange>> {
        val pagingSourceFactory = { exchangeDao.searchExchanges(query) }
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(Dispatchers.IO)
    }
}
