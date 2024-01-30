package br.com.mdr.criptoapi.ui.presentation.exchanges

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.usecase.ExchangesUseCase
import br.com.mdr.criptoapi.ui.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
    private val useCase: ExchangesUseCase
): BaseViewModel() {

    private val _exchanges = MutableStateFlow<PagingData<Exchange>>(PagingData.empty())
    val exchanges: StateFlow<PagingData<Exchange>>
        get() = _exchanges

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    override fun refresh() {
        useCase.getExchanges(_searchQuery.value)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getExchanges(query: String = _searchQuery.value) {
        launch {
            useCase
                .getExchanges(query)
                .cachedIn(viewModelScope)
                .collect {
                    _exchanges.emit(it)
                }
        }
    }
}