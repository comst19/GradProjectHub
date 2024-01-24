package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.CreateHistoryRequest
import com.project.fat.data.dto.CreateHistoryResponse
import com.project.fat.data.dto.GetHistoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalDateTime

interface HistoryService {
    @GET("history")
    fun getHistory(
        @Header("Access-token") accessToken : String,
        @Query("date") date: LocalDate,
    ) : Call<GetHistoryResponse>

    @POST("history")
    fun createHistory(
        @Header("Access-Token") accessToken : String,
        @Body createHistoryRequest: CreateHistoryRequest
    ) :Call<CreateHistoryResponse>

    @DELETE("history")
    fun deleteHistory(
        @Path("id") id : Long
    )
}