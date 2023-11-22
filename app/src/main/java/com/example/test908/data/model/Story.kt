package com.example.test908.data.model

import com.example.test908.domain.entity.StoryEntity
import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("byline")
    val byline: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("des_facet")
    val desFacet: List<String>,
    @SerializedName("geo_facet")
    val geoFacet: List<String>,
    @SerializedName("item_type")
    val itemType: String,
    @SerializedName("kicker")
    val kicker: String,
    @SerializedName("material_type_facet")
    val materialTypeFacet: String,
    @SerializedName("multimedia")
    val multimedia: List<Multimedia>,
    @SerializedName("org_facet")
    val orgFacet: List<String>,
    @SerializedName("per_facet")
    val perFacet: List<String>,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("short_url")
    val shortUrl: String,
    @SerializedName("subsection")
    val subsection: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    val uri: String,
    val url: String,
)

fun Story.mapIt(): StoryEntity =
    StoryEntity(
        abstract = abstract,
        byline = byline,
        createdDate = createdDate,
        desFacet = desFacet,
        geoFacet = geoFacet,
        itemType = itemType,
        kicker = kicker,
        materialTypeFacet = materialTypeFacet,
        multimedia = multimedia.map { it.mapIt() },
        orgFacet = orgFacet,
        perFacet = perFacet,
        publishedDate = publishedDate,
        section = section,
        shortUrl = shortUrl,
        subsection = subsection,
        title = title,
        updatedDate = updatedDate,
        url = url,
        uri = uri,
    )