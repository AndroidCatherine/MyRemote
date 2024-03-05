package com.example.myremote.model.remote.api

import com.example.myremote.model.remote.data.user.LoginData
import com.example.myremote.model.remote.data.user.UpdateTimeZone
import com.example.myremote.model.remote.data.user.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Created by Catherine Tsai on 01/03/2024
 */
interface Api_User {

    @Headers("X-Parse-Application-Id:vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD")
    @POST(URLs.User.PATH_LOGIN)
    fun login(@Body loginObj: LoginData):Call<UserInfo>


    @Headers("X-Parse-Application-Id:vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD")
    @FormUrlEncoded
    @PUT(URLs.User.PATH_UPDATE_TIMEZONE.plus("{objectID}"))
    fun updateTimeZone(  @Header("X-Parse-Session-Token") token: String
                       , @Path("objectID") objectID:String
                        ,@Field("timezone") timezone:String
    ):Call<UpdateTimeZone>
}