package com.example.weatherapi.network

import com.example.weatherapi.model.dto.response.SearchResponseModel
import com.example.weatherapi.model.dto.response.SportResponseModel
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

    @GET(APIRoutes.getSportList)
    @Headers("Accept: application/json")
    suspend fun getSportList(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<SportResponseModel>

    //Login=========================================================================================
    /*  @POST(Endpoints.login)
      @Headers("Accept: application/json")
      suspend fun login(
          @Body loginRequestModel: LoginRequestModel,
      ): Response<LoginResponseModel>*/
}