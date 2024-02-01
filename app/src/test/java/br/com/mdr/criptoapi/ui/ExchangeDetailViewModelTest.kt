package br.com.mdr.criptoapi.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import br.com.mdr.criptoapi.base.BaseViewModelTest
import br.com.mdr.criptoapi.common.Constants
import br.com.mdr.criptoapi.domain.model.Assets
import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.domain.usecase.ExchangeDetailUseCase
import br.com.mdr.criptoapi.mocks.getExchangeDetail
import br.com.mdr.criptoapi.ui.presentation.exchanges.detail.ExchangeDetailViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class ExchangeDetailViewModelTest: BaseViewModelTest() {

    @Mock
    private lateinit var mockedUseCase: ExchangeDetailUseCase
    private lateinit var viewModel: ExchangeDetailViewModel

    private val baseAssets = listOf(
        Assets.BTC.assetId,
        Assets.BTH.assetId,
        Assets.ETH.assetId,
        Assets.LTC.assetId,
        Assets.XRP.assetId
    )

    private val exchangeId = "BINANCE"

    @Before
    override fun setup() {
        super.setup()
        val savedStateHandle = SavedStateHandle().apply {
            set(Constants.EXCHANGE_ID_KEY, exchangeId)
        }
        viewModel = ExchangeDetailViewModel(mockedUseCase, savedStateHandle)
    }

    @Test
    fun givenSuccess_whenLoadingExchangeDetail_thenCheckResult() = runTest {

        val response = flowOf(PageState.Success(getExchangeDetail(exchangeId, baseAssets)))

        Mockito.`when`(mockedUseCase.getExchangeDetail(exchangeId, baseAssets))
            .thenReturn(response)

        viewModel.exchange.test {
            viewModel.getExchangeDetail()

            assertEquals(awaitItem(), PageState.Loading)
            assertEquals(awaitItem(), viewModel.exchange.value)
        }
    }

    @Test
    fun givenEmptyList_whenLoadingExchangeDetail_thenCheckResult() = runTest {

        val response = flowOf(PageState.Empty)

        Mockito.`when`(mockedUseCase.getExchangeDetail(exchangeId, baseAssets))
            .thenReturn(response)

        viewModel.exchange.test {
            viewModel.getExchangeDetail()

            assertEquals(awaitItem(), PageState.Loading)
            assertTrue(awaitItem() is PageState.Empty)
        }
    }

    @Test
    fun givenError_whenLoadingExchangeDetail_thenCheckException() = runTest {

        Mockito.`when`(mockedUseCase.getExchangeDetail(exchangeId, baseAssets))
            .thenAnswer { throw Exception("Error Test") }

        viewModel.exchange.test {
            viewModel.getExchangeDetail()

            assertEquals(awaitItem(), PageState.Loading)
            val error = awaitItem()
            assertTrue(error is PageState.Error)
            val message = (error as? PageState.Error)?.error?.message
            assertTrue( message == "Error Test")
        }
    }
}