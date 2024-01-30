package br.com.mdr.criptoapi.domain.usecase

import br.com.mdr.criptoapi.domain.model.ExchangeData
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import kotlinx.coroutines.flow.Flow

class ExchangeDetailUseCase(
    private val repository: ExchangesRepository
) {
    fun getExchangeDetail(exchangeId: String, assets: List<String>): Flow<ExchangeData> =
        repository.getExchangeDetail(exchangeId, assets)
}
