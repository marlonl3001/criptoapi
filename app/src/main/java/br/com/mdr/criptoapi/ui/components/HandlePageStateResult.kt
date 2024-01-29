package br.com.mdr.criptoapi.ui.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import br.com.mdr.criptoapi.domain.model.PageState
import br.com.mdr.criptoapi.ui.presentation.base.BaseViewModel

@Composable
fun HandlePageStateResult(
    pageState: PageState<Any>,
    viewModel: BaseViewModel,
    successBlock: @Composable (PageState.Success<Any>) -> Unit
) {
    when (pageState) {
        is PageState.Empty -> { EmptyScreen(viewModel = viewModel) }
        is PageState.Error -> {
            EmptyScreen(
                viewModel = viewModel,
                error = (pageState as? PageState.Error)?.error?.let { LoadState.Error(it) }
            )
        }
        is PageState.Loading -> { ShimmerEffect() }
        is PageState.Success -> {
            successBlock(pageState)
        }
    }
}
