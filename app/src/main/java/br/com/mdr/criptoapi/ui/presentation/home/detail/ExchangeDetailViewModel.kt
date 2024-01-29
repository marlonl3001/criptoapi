package br.com.mdr.criptoapi.ui.presentation.home.detail

import androidx.lifecycle.SavedStateHandle
import br.com.mdr.criptoapi.common.Constants.EXCHANGE_ID_KEY
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeUI
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.domain.usecase.ExchangeDetailUseCase
import br.com.mdr.criptoapi.ui.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailViewModel @Inject constructor(
    private val useCase: ExchangeDetailUseCase,
    private val stateHandle: SavedStateHandle
): BaseViewModel() {
    private val _exchange = MutableStateFlow<PageState<ExchangeUI>>(PageState.Loading)
    val exchange: StateFlow<PageState<ExchangeUI>> = _exchange

    private val _ohlcvList = MutableStateFlow<PageState<List<OHLCVData>>>(PageState.Loading)
    val ohlcvList: StateFlow<PageState<List<OHLCVData>>> = _ohlcvList

    override fun refresh() {
        getExchangeDetail()
    }

    fun getExchangeDetail() {
        val exchangeId =
            (_exchange.value as? PageState.Success)?.result?.exchange?.id
                ?: stateHandle.get<String>(EXCHANGE_ID_KEY)

        exchangeId?.let {
            launch (
                errorBlock = {
                    _exchange.emit(PageState.Error(it))
                }
            ) {
                _exchange.emit(PageState.Success(useCase.getExchangeDetail(it)))
                val history = useCase.getOHLCVHistory(it)
                //(_exchange.value as? PageState.Success<ExchangeUI>)?.result?.history = history
                val oldExc: ExchangeUI? = (_exchange.value as? PageState.Success<ExchangeUI>)?.result
                oldExc?.history = history
                oldExc?.let {
                    _exchange.emit(PageState.Success(it))
                }
                //_ohlcvList.emit(PageState.Success(useCase.getOHLCVHistory(it)))
            }
        }
    }
}