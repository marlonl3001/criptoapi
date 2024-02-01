package br.com.mdr.criptoapi.remote.api

import br.com.mdr.criptoapi.data.remote.api.CriptoApi
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeIcon
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.Symbol
import java.io.IOException

class MockedCriptoApi: CriptoApi {

    private var exception = false

    fun addException() {
        exception = true
    }

    override suspend fun getExchanges(): List<Exchange> {
        if (exception) {
            throw IOException()
        }
        return exchangesMock
    }

    override suspend fun getExchangeIcons(): List<ExchangeIcon> {
        return exchangesIconsMock
    }

    override suspend fun getOHLCVHistory(
        symbolId: String,
        timeStart: String,
        timeEnd: String,
        periodId: String
    ): List<OHLCVData> {
        return binanceHistory
    }

    override suspend fun getExchangeSymbols(exchangeId: String): List<Symbol> {
        return exchangeSymbolsMock
    }
}