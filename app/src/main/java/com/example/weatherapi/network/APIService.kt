package com.example.weatherapi.network

import com.example.weatherapi.model.dto.response.SearchResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {

    @GET(APIRoutes.getSearchList)
    @Headers("Accept: application/json")
    suspend fun getSearchList(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<SearchResponseModel>>

    //Login=========================================================================================
    /*  @POST(Endpoints.login)
      @Headers("Accept: application/json")
      suspend fun login(
          @Body loginRequestModel: LoginRequestModel,
      ): Response<LoginResponseModel>*/
}