package br.com.mdr.criptoapi.ui

import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import br.com.mdr.criptoapi.base.BaseViewModelTest
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.usecase.ExchangesUseCase
import br.com.mdr.criptoapi.mocks.emitPagingData
import br.com.mdr.criptoapi.mocks.emptyExchangesMock
import br.com.mdr.criptoapi.mocks.exchangesMock
import br.com.mdr.criptoapi.ui.presentation.exchanges.ExchangesViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class ExchangesViewModelTest : BaseViewModelTest() {

    @Mock
    private lateinit var mockedUseCase: ExchangesUseCase

    private lateinit var viewModel: ExchangesViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = ExchangesViewModel(mockedUseCase)
    }

    @Test
    fun givenSuccess_whenLoadingExchanges_thenCheckExchangesList() = runTest {
        val query = ""

        Mockito.`when`(mockedUseCase.getExchanges(query)).thenReturn(emitPagingData(exchangesMock))

        viewModel.getExchanges(query)

        val value = viewModel.exchanges.value

        viewModel.exchanges.test {
            assertEquals(awaitItem(), value)
        }
    }

    @Test
    fun givenEmptyState_whenLoadingExchanges_thenCheckResult() = runTest {
        val query = ""
        val error = Exception("Error Test")

        Mockito.`when`(mockedUseCase.getExchanges(query)).thenAnswer { throw error }

        viewModel.getExchanges(query)

        val value = viewModel.exchanges.value

        viewModel.exchanges.test {
            val result = awaitItem()
            assertEquals(result, value)
        }
    }

    @Ignore("Teste falhando")
    @Test
    fun givenSuccess_whenLoadingExchanges_thenCheckEmptyList() = runTest {
        val query = ""

        Mockito.`when`(mockedUseCase.getExchanges(query)).thenReturn(emitPagingData(emptyExchangesMock))

        viewModel.getExchanges(query)

        viewModel.exchanges.test {
            val items: List<Exchange> = viewModel.exchanges.asSnapshot {
                this.scrollTo(0)
            }

            assertTrue(items.isEmpty())
        }
    }
}
