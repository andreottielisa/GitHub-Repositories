package com.andreotti.github.data

import com.andreotti.github.data.dto.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Query

private const val ORDER = "desc"
private const val SORT = "stars"

internal interface GitHubApi {

    @GET("search/repositories")
    suspend fun getSearchRepositories(
        @Query("sort") sort: String = SORT,
        @Query("order") order: String = ORDER,
        @Query("page") page: Int,
        @Query("q") language: String
    ): SearchDTO

}