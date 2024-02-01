package br.com.mdr.criptoapi.remote.api

import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeIcon
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.Symbol
import br.com.mdr.criptoapi.utils.SerializationExtension.jsonToListObject
import br.com.mdr.criptoapi.utils.getJsonFromAssetsOrResources

val emptyExchangesMock = listOf<Exchange>()

val exchangesMock: List<Exchange> =
    getJsonFromAssetsOrResources("exchanges_list.json")
        .jsonToListObject<Exchange>() ?: emptyExchangesMock

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
