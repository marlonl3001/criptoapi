package br.com.mdr.criptoapi.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.mdr.criptoapi.data.local.CriptoDatabase
import br.com.mdr.criptoapi.data.remote.api.CriptoApi
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class ExchangeRemoteMediator(
    private val criptoApi: CriptoApi,
    private val database: CriptoDatabase
): RemoteMediator<Int, Exchange>() {

    private val exchangesDao = database.getExchangeDao()
    private val exchangesRemoteKeysDao = database.getRemoteKeysDao()

    // Verifica se os dados armazenados em cache estão desatualizados e decide se
    // deve acionar uma atualização remota, buscando dados da API.
    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = exchangesRemoteKeysDao.getRemoteKeys()?.lastUpdated ?: 0L
        val cacheTimeOut = 500000
        // Tempo em minutos para verificar quando RemoteMediator
        // precisa requisitar novos dados do servidor

        val diffInMinutes = (currentTime - lastUpdate) / 1000 / 60

        // Se o temp ode cache ainda não expirou, não atualiza
        return if (diffInMinutes.toInt() <= cacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }

    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Exchange>): MediatorResult {
        return try {

            when (loadType) {
                LoadType.REFRESH -> Unit
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state = state)
                    return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state = state)
                    return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                }
            }

            val response = criptoApi.getExchanges()
            if (response.isNotEmpty()) {

                val icons = criptoApi.getExchangeIcons()
                response.forEach { exchange ->
                    exchange.url = icons.find { icon ->
                        icon.exchangeId == exchange.id
                    }?.url.toString()
                }

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        exchangesDao.deleteAll()
                        exchangesRemoteKeysDao.deleteAllRemoteKeys()
                    }

                    with(response) {
                        val keys = map { exchange ->
                            ExchangeRemoteKeys(
                                id = exchange.id,
                                prevPage = null,
                                nextPage = null,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }

                        exchangesRemoteKeysDao.addAllRemoteKeys(keys)

                        val filteredExchanges = this.filter {
                            it.url != "null"
                        }

                        exchangesDao.insertExchanges(exchanges = filteredExchanges)
                    }
                }
            }
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Exchange>
    ): ExchangeRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            exchangesRemoteKeysDao.getRemoteKeysById(it.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Exchange>
    ): ExchangeRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            exchangesRemoteKeysDao.getRemoteKeysById(it.id)
        }
    }
}