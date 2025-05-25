package com.example.newsaggregator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsaggregator.data.dao.DataAccessObject
import com.example.newsaggregator.data.entities.ItemEntity


@Database(
    version = 1,
    entities = [
        ItemEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): DataAccessObject
}