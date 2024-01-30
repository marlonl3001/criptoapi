package br.com.mdr.criptoapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.mdr.criptoapi.domain.model.ExchangeRemoteKeys

@Dao
interface ExchangeRemoteKeysDao {
    @Query("SELECT * FROM exchange_keys_table WHERE id = :id")
    suspend fun getRemoteKeysById(id: String): ExchangeRemoteKeys?

    @Query("SELECT * FROM exchange_keys_table limit 1")
    suspend fun getRemoteKeys(): ExchangeRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(heroRemoteKeys: List<ExchangeRemoteKeys>)

    @Query("DELETE FROM exchange_keys_table")
    suspend fun deleteAllRemoteKeys()
}
