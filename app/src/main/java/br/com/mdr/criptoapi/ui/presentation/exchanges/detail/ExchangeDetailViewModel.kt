package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import androidx.lifecycle.SavedStateHandle
import br.com.mdr.criptoapi.common.Constants.EXCHANGE_ID_KEY
import br.com.mdr.criptoapi.domain.model.Assets
import br.com.mdr.criptoapi.domain.model.ExchangeData
import br.com.mdr.criptoapi.domain.model.OHLCVData
import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.domain.usecase.ExchangeDetailUseCase
import br.com.mdr.criptoapi.ui.presentation.base.BaseViewModel
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailViewModel @Inject constructor(
    private val useCase: ExchangeDetailUseCase,
    private val stateHandle: SavedStateHandle
): BaseViewModel() {
    private val _exchange = MutableStateFlow<PageState<ExchangeData>>(PageState.Loading)
    val exchange: StateFlow<PageState<ExchangeData>> = _exchange

    private val _lineChartData = MutableStateFlow<MutableList<Entry>>(mutableListOf())
    val lineChartData: StateFlow<MutableList<Entry>> = _lineChartData

    private val baseAssets = listOf(
        Assets.BTC.assetId,
        Assets.BTH.assetId,
        Assets.ETH.assetId,
        Assets.LTC.assetId,
        Assets.XRP.assetId
    )

    private val _currentAsset = MutableStateFlow(baseAssets.first())
    val currentAsset: StateFlow<String> = _currentAsset

    private val _priceTraded = MutableStateFlow("")
    val priceTraded = _priceTraded

    private val _priceTradedInterval = MutableStateFlow("")
    val priceTradedInterval = _priceTradedInterval

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
                useCase.getExchangeDetail(it, baseAssets).collect {
                    _exchange.emit(PageState.Success(it))
                    fetchChartData(it.history[_currentAsset.value])
                }
            }
        }
    }

    private fun fetchChartData(exchangesHistory: List<OHLCVData>?) {
        if (_lineChartData.value.isNotEmpty()) {
            _lineChartData.value.clear()
        }
        val lineChartData = _lineChartData.value
        exchangesHistory?.forEachIndexed { index, ohlcvData ->
            lineChartData.add(
                Entry(
                    index.toFloat(),
                    ohlcvData.volumeTraded.toFloat(),
                    ohlcvData
                )
            )
        }
        _lineChartData.value = lineChartData
    }

    fun updateAssetData(assetId: String, index: Int? = null) {
        (_exchange.value as? PageState.Success<ExchangeData>)?.apply {
            val ohlcvData = this.result.history[assetId]
            fetchChartData(ohlcvData)
            _currentAsset.value = assetId

            index?.let {
                updatePriceValues(ohlcvData?.get(it))
            }
        }
    }

    fun updatePriceValues(ohlcvData: OHLCVData?) {
        _priceTraded.value = ohlcvData?.getPriceTraded().toString()
        _priceTradedInterval.value = ohlcvData?.getIntervalPeriod().toString()
    }
}