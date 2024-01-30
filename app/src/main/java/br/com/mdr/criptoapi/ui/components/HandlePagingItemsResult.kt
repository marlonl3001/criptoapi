package br.com.mdr.criptoapi.ui.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.ui.presentation.base.BaseViewModel

@Composable
fun HandlePagingItemsResult(
    items: LazyPagingItems<Exchange>,
    viewModel: BaseViewModel,
    successBlock: @Composable (LazyPagingItems<Exchange>) -> Unit
) {
    items.apply {
        val error = when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }
        when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
            }
            error != null -> {
                EmptyScreen(viewModel = viewModel, error = error)
            }
            this.itemCount < 1 -> {
                EmptyScreen(viewModel = viewModel)
            }
            else -> {
                successBlock(items)
            }
        }
    }
}
