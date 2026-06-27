package com.rzhf.mangareader.data.api

import com.google.gson.annotations.SerializedName

data class MangaResponse(
    @SerializedName("data") val data: List<MangaData>
)

data class MangaData(
    @SerializedName("id") val id: String,
    @SerializedName("attributes") val attributes: MangaAttributes,
    // Tambahkan baris ini untuk menangkap data cover
    @SerializedName("relationships") val relationships: List<Relationship>?
)

data class MangaAttributes(
    @SerializedName("title") val title: Map<String, String>,
    @SerializedName("description") val description: Map<String, String>?
)

data class Relationship(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: RelationshipAttributes?
)

data class RelationshipAttributes(
    @SerializedName("fileName") val fileName: String?
)

data class MangaDetailResponse(
    @SerializedName("data") val data: MangaData
)

data class ChapterListResponse(
    @SerializedName("data") val data: List<ChapterData>
)

data class ChapterData(
    @SerializedName("id") val id: String,
    @SerializedName("attributes") val attributes: ChapterAttributes
)

data class ChapterAttributes(
    @SerializedName("title") val title: String?,
    @SerializedName("chapter") val chapter: String?
)

data class AtHomeResponse(
    @SerializedName("baseUrl") val baseUrl: String,
    @SerializedName("chapter") val chapter: AtHomeChapter
)

data class AtHomeChapter(
    @SerializedName("hash") val hash: String,
    @SerializedName("dataSaver") val dataSaver: List<String>
)