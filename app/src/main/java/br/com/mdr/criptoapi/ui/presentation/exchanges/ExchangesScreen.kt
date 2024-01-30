package br.com.mdr.criptoapi.ui.presentation.exchanges

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.navigation.Screen
import br.com.mdr.criptoapi.ui.components.HandlePagingItemsResult
import br.com.mdr.criptoapi.ui.components.SearchTopBar
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.utils.isScrollingUp

@Composable
fun ExchangesScreen(
    navController: NavHostController,
    viewModel: ExchangesViewModel = hiltViewModel()
) {
    val exchanges = viewModel.exchanges.collectAsLazyPagingItems()
    val query by viewModel.searchQuery

    LaunchedEffect(key1 = null) {
        viewModel.getExchanges(query)
    }

    HandlePagingItemsResult(
        items = exchanges,
        viewModel = viewModel
    ) {
        ListContent(
            exchanges = it,
            query = query,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
private fun ListContent(exchanges: LazyPagingItems<Exchange>,
                        query: String,
                        viewModel: ExchangesViewModel,
                        navController: NavHostController
) {

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = listState.isScrollingUp().value
        ) {
            SearchTopBar(
                text = query,
                onTextChange = {
                    viewModel.updateSearchQuery(it)
                },
                onSearchClicked = {
                    viewModel.getExchanges(it)
                },
                onCloseClicked = {
                    viewModel.getExchanges()
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .semantics {
                    contentDescription = "Lista de exchanges"
                },
            contentPadding = PaddingValues(MEDIUM_PADDING),
            verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
            state = listState
        ) {
            items(count = exchanges.itemCount) { index ->
                exchanges[index]?.let {
                    ExchangeItem(exchange = it) { exchange ->
                        navController.navigate(Screen.ExchangeDetail.passExchangeId(exchange.id))
                    }
                }
            }
        }
    }
}