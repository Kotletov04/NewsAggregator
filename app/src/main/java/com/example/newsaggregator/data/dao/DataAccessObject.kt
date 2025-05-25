package com.example.newsaggregator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsaggregator.data.entities.ItemEntity
import com.example.newsaggregator.domain.model.ItemModel


@Dao
interface DataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(data: List<ItemEntity>)

    @Query("SELECT * FROM items_table")
    fun getAllNews(): List<ItemEntity>
}