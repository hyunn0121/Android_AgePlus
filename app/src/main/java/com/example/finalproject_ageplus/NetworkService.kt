package com.example.finalproject_ageplus

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface NetworkService {
    @GET("B552474/SenuriService/getJobList")
    fun getXmlList(
        @Query("serviceKey") apiKey:String?,
        @Query("pageNo") page:Int,
        @Query("numOfRows") pageSize:Int
    ): Call<responseInfo>
}