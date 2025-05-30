package com.example.newsaggregator.data.rss.dto

import com.example.newsaggregator.domain.model.ContentModel
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("content", "http://search.yahoo.com/mrss/", "media")
data class ContentDto (
    val type: String?,
    val width: String?,
    val url: String,
    val credit: CreditDto?,
)

fun ContentDto.toContentModel(): ContentModel {
    return ContentModel(
        type = type,
        width = width,
        url = url
    )
}