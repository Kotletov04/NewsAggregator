package com.example.newsaggregator.data.rss.dto

import com.example.newsaggregator.domain.model.CategoryModel
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue
import java.util.Locale.Category

@Serializable
@XmlSerialName("category")
data class CategoryDto(

    val domain: String,

    @XmlValue
    val value: String
)

fun CategoryDto.toCategoryModel(): CategoryModel {
    return CategoryModel(
        domain = domain,
        value = value
    )


}