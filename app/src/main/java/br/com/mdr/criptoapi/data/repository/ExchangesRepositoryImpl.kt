package br.com.mdr.criptoapi.data.repository

import androidx.paging.PagingData
import br.com.mdr.criptoapi.common.Constants
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeData
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import br.com.mdr.criptoapi.domain.repository.LocalDataSource
import br.com.mdr.criptoapi.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangesRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ExchangesRepository {

    override fun getExchanges(): Flow<PagingData<Exchange>> =
        dataSource.getAllExchanges()

    override fun searchExchanges(query: String): Flow<PagingData<Exchange>> =
        localDataSource.searchExchanges(query)

    override fun getExchangeDetail(exchangeId: String, assets: List<String>): Flow<ExchangeData> = flow {
        val exchange = localDataSource.getSelectedExchange(exchangeId)
        val exchangeData = ExchangeData(exchange)
        val symbols = dataSource.getExchangeSymbols(exchangeId)

        val quoteAssets = listOf("USD", "USDC")

        val filteredSymbols = symbols.filter {
            val symbolId = "$exchangeId${Constants.SPOT_SYMBOL}${it.assetIdBase}_${it.assetIdQuote}"
            quoteAssets.contains(it.assetIdQuote) && assets.contains(it.assetIdBase) && it.symbolId == symbolId
        }.sortedBy {
            it.assetIdBase
        }.distinctBy {
            it.assetIdBase
        }

        filteredSymbols.forEach { symbol ->
            val symbolId = "$exchangeId${Constants.SPOT_SYMBOL}${symbol.assetIdBase}_${symbol.assetIdQuote}"
            val exchangeHistory = dataSource.getOHLCVHistory(exchangeId, symbolId)
            if (exchangeHistory.isNotEmpty()) {
                exchangeData.history[symbol.assetIdBase] = exchangeHistory
                exchangeData.symbols.add(symbol)
            }
        }

        emit(exchangeData)
    }
}
