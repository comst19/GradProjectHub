package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.Fatman
import retrofit2.Call
import retrofit2.http.GET

interface FatmanService {
    @GET("fatman")
    fun getFatmanList() : Call<Fatman>
}