package com.example.newsaggregator.data.rss.dto

import com.example.newsaggregator.domain.model.ItemModel
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("item")
data class ItemDto(
    @XmlSerialName("title")
    @XmlElement(true)
    val title: String,

    @XmlSerialName("link")
    @XmlElement(true)
    val link: String,

    @XmlSerialName("description")
    @XmlElement(true)
    val description: String,

    val categories: List<CategoryDto>,

    @XmlSerialName("pubDate")
    @XmlElement(true)
    val pubDate: String,

    @XmlSerialName("guid")
    @XmlElement(true)
    val guid: String,

    val contents: List<ContentDto>,

    @XmlSerialName("creator", "http://purl.org/dc/elements/1.1/", "dc")
    @XmlElement(true)
    val dcCreator: String,


    @XmlSerialName("date", "http://purl.org/dc/elements/1.1/", "dc")
    @XmlElement(true)
    val dcDate: String

)

fun ItemDto.toItemModel(): ItemModel {
    return ItemModel(
        title = title,
        link = link,
        description = description,
        categories = categories.map { it.toCategoryModel()},
        pubDate = pubDate,
        guid = guid,
        dcCreator = dcCreator,
        contents = contents.map { it.toContentModel() }
    )

}
