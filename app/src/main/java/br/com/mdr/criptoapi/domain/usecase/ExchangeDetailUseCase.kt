package br.com.mdr.criptoapi.domain.usecase

import br.com.mdr.criptoapi.domain.model.ExchangeUI
import br.com.mdr.criptoapi.domain.model.toExchangeUI
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository

class ExchangeDetailUseCase(
    private val repository: ExchangesRepository
) {
    suspend fun getExchangeDetail(exchangeId: String): ExchangeUI =
        repository.getExchangeDetail(exchangeId).toExchangeUI()

    suspend fun getOHLCVHistory(exchangeId: String) =
        repository.getOHLCVHistory(exchangeId)
}