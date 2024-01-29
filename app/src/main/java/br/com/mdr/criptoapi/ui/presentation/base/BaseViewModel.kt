package br.com.mdr.criptoapi.ui.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    abstract fun refresh()

    protected fun launch(
        errorBlock: (suspend CoroutineScope.(Throwable) -> Unit?)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            block()
        }.onFailure {
            errorBlock?.invoke(this, it)
        }
    }
}
