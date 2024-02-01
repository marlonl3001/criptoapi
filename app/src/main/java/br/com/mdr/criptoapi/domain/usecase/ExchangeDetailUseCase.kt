package br.com.mdr.criptoapi.domain.usecase

import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class ExchangeDetailUseCase @Inject constructor(
    private val repository: ExchangesRepository
) {
    fun getExchangeDetail(exchangeId: String, assets: List<String>) = flow {
        repository
            .getExchangeDetail(exchangeId, assets)
            .onEmpty {
                emit(PageState.Empty)
            }
            .catch { error ->
                emit(PageState.Error(error))
            }
            .collect {
                emit(PageState.Success(it))
            }
    }
}
