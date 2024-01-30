package br.com.mdr.criptoapi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mdr.criptoapi.data.local.dao.ExchangeDao
import br.com.mdr.criptoapi.data.local.dao.ExchangeRemoteKeysDao
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.domain.model.ExchangeRemoteKeys

@Database(entities = [Exchange::class, ExchangeRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class CriptoDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): CriptoDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, CriptoDatabase::class.java)
            } else {
                Room.databaseBuilder(context, CriptoDatabase::class.java, "test_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getExchangeDao(): ExchangeDao
    abstract fun getRemoteKeysDao(): ExchangeRemoteKeysDao
}
