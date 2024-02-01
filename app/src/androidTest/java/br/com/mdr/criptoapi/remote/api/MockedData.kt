package br.com.mdr.criptoapi.remote.api

import androidx.paging.PagingData
import br.com.mdr.criptoapi.common.Constants
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeData
import br.com.mdr.criptoapi.domain.model.ExchangeIcon
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.domain.model.Symbol
import br.com.mdr.criptoapi.utils.SerializationExtension.jsonToListObject
import br.com.mdr.criptoapi.utils.getJsonFromAssetsOrResources
import kotlinx.coroutines.flow.flow

val emptyExchangesMock = listOf<Exchange>()

val exchangesMock: List<Exchange> =
    getJsonFromAssetsOrResources("exchanges_list.json")
        .jsonToListObject<Exchange>() ?: emptyExchangesMock

val pagedExchanges = flow { emit(PagingData.from(exchangesMock)) }

val emptyExchangeIconsMock = listOf<ExchangeIcon>()

val exchangesIconsMock: List<ExchangeIcon> =
    getJsonFromAssetsOrResources("exchange_icons.json")
        .jsonToListObject<ExchangeIcon>() ?: emptyExchangeIconsMock

val emptySymbolsMock = listOf<Symbol>()

val exchangeSymbolsMock: List<Symbol> =
    getJsonFromAssetsOrResources("exchange_symbols.json")
        .jsonToListObject<Symbol>() ?: emptySymbolsMock

val emptyHistory = listOf<OHLCVData>()

val binanceHistory: List<OHLCVData> =
    getJsonFromAssetsOrResources("binance_btc_history.json")
        .jsonToListObject<OHLCVData>() ?: emptyHistory

val emptyExchangeDataMock = ExchangeData(
    exchangesMock.first()
)

fun getExchangeDetail(exchangeId: String, assets: List<String>): ExchangeData {
    val exchange = exchangesMock.first()
    val exchangeData = ExchangeData(exchange)

    val quoteAssets = listOf("USD", "USDC")

    val filteredSymbols = exchangeSymbolsMock.filter {
        val symbolId = "$exchangeId${Constants.SPOT_SYMBOL}${it.assetIdBase}_${it.assetIdQuote}"
        quoteAssets.contains(it.assetIdQuote) && assets.contains(it.assetIdBase) && it.symbolId == symbolId
    }.sortedBy {
        it.assetIdBase
    }.distinctBy {
        it.assetIdBase
    }

    filteredSymbols.forEach { symbol ->
        if (binanceHistory.isNotEmpty()) {
            exchangeData.history[symbol.assetIdBase] = binanceHistory
            exchangeData.symbols.add(symbol)
        }
    }

    return exchangeData
}

fun <T : Any> emitPageState(value: PageState<T>) = flow { emit(value) }
fun <T : Any> emitPagingData(value: List<T>) = flow { emit(PagingData.from(value)) }
