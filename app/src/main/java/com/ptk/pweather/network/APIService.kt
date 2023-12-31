package com.ptk.pweather.network

import com.ptk.pweather.model.dto.response.AstronomyResponseModel
import com.ptk.pweather.model.dto.response.SearchResponseModel
import com.ptk.pweather.model.dto.response.SportResponseModel
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

    @GET(APIRoutes.getAstronomy)
    @Headers("Accept: application/json")
    suspend fun getAstronomy(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("dt") dateTime: String,
    ): Response<AstronomyResponseModel>


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