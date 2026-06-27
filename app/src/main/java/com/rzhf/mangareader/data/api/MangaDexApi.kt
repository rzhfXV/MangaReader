package com.rzhf.mangareader.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface MangaDexApi {
    @GET("/manga")
    suspend fun getMangaList(
        @Query("limit") limit: Int = 10,
        @Query("title") title: String? = null,
        @Query("includes[]") includes: String = "cover_art" // Biar dapet cover-nya sekalian
    ): Response<MangaResponse>

    @GET("/at-home/server/{chapterId}")
    suspend fun getChapterPages(
        @Path("chapterId") chapterId: String,
        @Query("forcePort443") forcePort443: Boolean = true
    ): Response<AtHomeResponse>

    @GET("/manga/{id}")
    suspend fun getMangaDetail(
        @Path("id") id: String
    ): Response<MangaDetailResponse>

    @GET("/manga/{id}/feed")
    suspend fun getMangaFeed(
        @Path("id") id: String,
        @Query("translatedLanguage[]") translatedLanguage: List<String> = listOf("en"), // Ambil bahasa Inggris saja biar rapi
        @Query("order[chapter]") order: String = "asc" // Urutkan dari chapter awal
    ): Response<ChapterListResponse>
}