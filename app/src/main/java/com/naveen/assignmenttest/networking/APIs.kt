package com.naveen.assignmenttest.networking

import com.naveen.assignmenttest.models.HitsJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APIs {

    @GET("search_by_date?tags=story")
    fun widgetList(
        @Query("page") pageNum: Int
    ): Call<HitsJson>

}