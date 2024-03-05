package com.example.myremote.model.remote.model

import android.util.Log
import com.example.myremote.model.remote.api.URLs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class SingletonRetrofit{

   object USER{
        //Singleton
        val retrofit_User:Retrofit by lazy {
            Log.i("User","Singleton==============retrofit user=====")
            SingletonRetrofit().make_Retrofit_HttpClient(URLs.User.BASE_URL)
        }
    }

    object  PARKINGLOT{
        //Singleton
        val retrofit_ParkingLot:Retrofit by lazy {
            Log.i("parking","Singleton==============retrofit parkinglot=====")
            SingletonRetrofit().make_Retrofit_HttpClient(URLs.ParkingLot.BASE_URL)
        }
    }



    private fun make_Retrofit_HttpClient( baseURL:String ): Retrofit {

        val interceptor =  HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout( URLs.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout( URLs.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout( URLs.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor).build()

        val  retrofit = Retrofit.Builder( )
            .baseUrl(baseURL)
            .addConverterFactory( GsonConverterFactory.create() )
            .client( okHttpClient )
            .build()
        return  retrofit
    }
}