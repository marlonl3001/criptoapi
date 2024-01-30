package br.com.mdr.criptoapi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.mdr.criptoapi.common.Constants.DEFAULT_PAGE_SIZE
import br.com.mdr.criptoapi.data.local.CriptoDatabase
import br.com.mdr.criptoapi.data.pagingsource.ExchangeRemoteMediator
import br.com.mdr.criptoapi.data.remote.api.CriptoApi
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.Symbol
import br.com.mdr.criptoapi.domain.repository.RemoteDataSource
import br.com.mdr.criptoapi.utils.toIsoFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalPagingApi::class)
class RemoteDataSourceImpl(
    private val api: CriptoApi,
    private val dataBase: CriptoDatabase
) : RemoteDataSource {

    private val exchangeDao = dataBase.getExchangeDao()

    override fun getAllExchanges(): Flow<PagingData<Exchange>> {
        val pagingSourceFactory = { exchangeDao.getExchanges() }
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            remoteMediator = ExchangeRemoteMediator(
                criptoApi = api,
                database = dataBase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(Dispatchers.IO)
    }

    override suspend fun getOHLCVHistory(exchangeId: String, symbolId: String): List<OHLCVData> {
        val dateEnd = Date()
        val dateStart = Calendar.getInstance()
        dateStart.time = dateEnd
        dateStart.add(Calendar.DATE, -1)

        val paramDateStart = dateStart.time.toIsoFormat()
        val paramDateEnd = dateEnd.toIsoFormat()

        return api.getOHLCVHistory(
                symbolId = symbolId,
                timeStart = paramDateStart,
                timeEnd = paramDateEnd
            )
    }

    override suspend fun getExchangeSymbols(exchangeId: String): List<Symbol> =
        api.getExchangeSymbols(exchangeId)
}
