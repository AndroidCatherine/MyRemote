package com.example.myremote.model.remote.model

import android.util.Log
import retrofit2.Response
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ServerResponse<T>(val response:Response<T>){

    private val TAG = "RetroResponse"

    val code = response.code()
    val isSucessful = response.isSuccessful
    val header = response.headers()
    val errorBody = response.errorBody()
    val message = response.message()
    var mData:T? = response.body()

    fun toSimpleTEXT( ){
        val response = """
             ,RetroResponse -----------------------------START--------------------------- 
             ,Code : $code  
             ,isSucessful : $isSucessful 
             ,ErrorBody : ${errorBody.toString()}
             ,Message : ${message.toString()}
             ,Data : ${mData?.toString()}
             ,  ---------------RetroResponse--------------END--------------------------- 
        """.trimMargin(",").toString()
        Log.i(TAG,response)
    }

    fun toTEXT( ){
        val response = """
             ,RetroResponse -----------------------------START--------------------------- 
             ,Code : $code  
             ,isSucessful : $isSucessful 
             ,ErrorBody : ${errorBody.toString()}
             ,Message : ${message.toString()}
             ,Data : ${mData?.toString()}
             ,-------------------------------
             ,Header : ${header.toString()}
             ,-------------------------------
             ,  ---------------RetroResponse--------------END--------------------------- 
        """.trimMargin(",").toString()
        Log.i(TAG,response)
    }

}