package br.com.mdr.criptoapi.domain.usecase

import androidx.paging.PagingData
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import kotlinx.coroutines.flow.Flow

class ExchangesUseCase(
    private val repository: ExchangesRepository
) {

    fun getExchanges(query: String): Flow<PagingData<Exchange>> =
        if (query.isEmpty()) {
            repository.getExchanges()
        } else {
            repository.searchExchanges(query)
        }
}
