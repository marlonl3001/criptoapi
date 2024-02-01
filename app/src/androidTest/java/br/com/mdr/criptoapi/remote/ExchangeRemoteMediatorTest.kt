package br.com.mdr.criptoapi.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.core.app.ApplicationProvider
import br.com.mdr.criptoapi.data.local.CriptoDatabase
import br.com.mdr.criptoapi.data.pagingsource.ExchangeRemoteMediator
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.remote.api.MockedCriptoApi
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ExchangeRemoteMediatorTest {
    private lateinit var mockedApi: MockedCriptoApi
    private lateinit var database: CriptoDatabase

    @Before
    fun setup() {
        mockedApi = MockedCriptoApi()
        database = CriptoDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @After
    fun cleanUp() {
        database.clearAllTables()
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() =
        runBlocking {
            //mockedApi.clearData()
            val remoteMediator = ExchangeRemoteMediator(
                criptoApi = mockedApi,
                database = database
            )
            val pagingState = PagingState<Int, Exchange>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 15),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            TestCase.assertTrue(result is RemoteMediator.MediatorResult.Success)
            TestCase.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() =
        runBlocking {
            mockedApi.addException()

            val remoteMediator = ExchangeRemoteMediator(
                criptoApi = mockedApi,
                database = database
            )
            val pagingState = PagingState<Int, Exchange>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 15),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            TestCase.assertTrue(result is RemoteMediator.MediatorResult.Error)
        }
}