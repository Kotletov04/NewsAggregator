package com.example.newsaggregator.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsaggregator.domain.model.CategoryModel
import com.example.newsaggregator.domain.model.ContentModel
import com.example.newsaggregator.domain.model.ItemModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "items_table")
@TypeConverters(ItemTypeConverter::class)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "categories") val categories: List<CategoryModel>,
    @ColumnInfo(name = "pubDate") val pubDate: String,
    @ColumnInfo(name = "guid") val guid: String,
    @ColumnInfo(name = "contents") val contents: List<ContentModel>,
    @ColumnInfo(name = "dcCreator") val dcCreator: String
)

@TypeConverters
class ItemTypeConverter() {
    @TypeConverter
    fun categoriesToString(categories: List<CategoryModel>): String {
        return Gson().toJson(categories)
    }
    @TypeConverter
    fun stringToCategories(string: String): List<CategoryModel> {
        val type = object : TypeToken<List<CategoryModel>>() {}.type
        return Gson().fromJson(string, type)
    }
    @TypeConverter
    fun contentsToString(categories: List<ContentModel>): String {
        return Gson().toJson(categories)
    }
    @TypeConverter
    fun stringToContents(string: String): List<ContentModel> {
        val type = object : TypeToken<List<ContentModel>>() {}.type
        return Gson().fromJson(string, type)
    }
}


fun ItemEntity.toItemModel(): ItemModel {
    return ItemModel(
        title = title,
        link = link,
        description = description,
        categories = categories,
        pubDate = pubDate,
        guid = guid,
        dcCreator = dcCreator,
        contents = contents
    )
}
