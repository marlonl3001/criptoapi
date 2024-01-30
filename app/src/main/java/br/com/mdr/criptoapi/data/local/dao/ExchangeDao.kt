package br.com.mdr.criptoapi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.mdr.criptoapi.domain.model.Exchange

@Dao
interface ExchangeDao {

    @Query("select * from exchange_table")
    fun getExchanges(): PagingSource<Int, Exchange>

    @Query("select * from exchange_table where id = :id")
    fun getSelectedExchange(id: String): Exchange

    @Query("select * from exchange_table where name LIKE '%' || :query || '%'")
    fun searchExchanges(query: String): PagingSource<Int, Exchange>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchanges(exchanges: List<Exchange>)

    @Query("DELETE FROM exchange_table")
    fun deleteAll()
}
