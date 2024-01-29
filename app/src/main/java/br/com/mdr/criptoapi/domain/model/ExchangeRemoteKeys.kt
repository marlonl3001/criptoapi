package br.com.mdr.criptoapi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_keys_table")
data class ExchangeRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?
)