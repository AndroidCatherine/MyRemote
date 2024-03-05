package com.example.myremote.repository.datasource

import android.util.Log
import com.example.myremote.model.remote.api.URLs
import com.example.myremote.model.remote.data.user.LoginData
import com.example.myremote.model.remote.data.user.UserInfo
import com.example.myremote.model.remote.api.Api_User
import com.example.myremote.model.remote.data.user.UpdateTimeZone
import com.example.myremote.model.remote.model.ServerResponse
import com.example.myremote.model.remote.model.SingletonRetrofit
import com.example.myremote.repository.IRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Catherine Tsai on 01/03/2024
 */
open class DataSource_Remote_User(): IDataSource.IRemote {

    private val TAG = this.javaClass.simpleName
    private val ERROR_MESSAGE_NO_USER = "無法取得使用者"
    private val ERROR_OCCOR = "錯誤發生"

    companion object{
        private val  TAG = "DataSource_Remote_User"
        //Singleton
        val instance by lazy {
            DataSource_Remote_User( )
        }

        val mApiUser by lazy{
            //Singleton
            SingletonRetrofit.USER.retrofit_User.create( Api_User::class.java)
        }
    }


    fun connect_Login(loginData: LoginData, mDataCallBack:IRepository.RemoteCallBack<UserInfo> ){
        mApiUser.login( loginData  ).enqueue(object:Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                mDataCallBack.onLoading(false)
                val responseCode = response.code()
                val serverResponse:ServerResponse<UserInfo> = ServerResponse(response)
                serverResponse.toTEXT()
                if(response.isSuccessful){
                    when(responseCode){
                        URLs.CODE_SUCESS ->{
                            val userInfo: UserInfo? = response.body()
                            if(userInfo!=null) {
                                mDataCallBack.onSucess(userInfo)
                            }else{
                                Log.i(TAG,"Login======User Null======")
                                mDataCallBack.onFail( ERROR_MESSAGE_NO_USER )
                            }

                        }
                        else->{
                            Log.i(TAG,"Login======Get User Error Code:$responseCode=====")
                            mDataCallBack.onFail( ERROR_MESSAGE_NO_USER )
                        }
                    }


                }else{
                    Log.i(TAG,"Login=======Response get user not Success ErrorCode:$responseCode========")
                    mDataCallBack.onFail( ERROR_MESSAGE_NO_USER )
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                mDataCallBack.onLoading(false)
                Log.i(TAG,"Error : ${t.toString()}")
                val errorMessage = t.message?: ERROR_OCCOR
                mDataCallBack.onFail(errorMessage)
            }
        })
    }


    fun connect_updateTimeZone(token:String,
                               projectID:String,
                               timezone:String,
                               mDataCallBack:IRepository.RemoteCallBack<UpdateTimeZone> ){
        mApiUser.updateTimeZone( token, projectID, timezone  ).enqueue(object:Callback<UpdateTimeZone>{
            override fun onResponse(
                call: Call<UpdateTimeZone>,
                response: Response<UpdateTimeZone>
            ) {
                mDataCallBack.onLoading(false)
                val responseCode = response.code()
                val serverResponse:ServerResponse<UpdateTimeZone> = ServerResponse(response)
                serverResponse.toTEXT()
                if(response.isSuccessful){
                    when(responseCode){
                        URLs.CODE_SUCESS ->{
                            val updateTimeZone:UpdateTimeZone? = response.body()
                            if(updateTimeZone!=null) {
                                mDataCallBack.onSucess(updateTimeZone)
                            }else{
                                mDataCallBack.onFail( ERROR_OCCOR )
                            }

                        }
                        else->{
                            mDataCallBack.onFail( ERROR_OCCOR )
                        }
                    }


                }else{
                    mDataCallBack.onFail( ERROR_OCCOR )
                }
            }

            override fun onFailure(call: Call<UpdateTimeZone>, t: Throwable) {
                mDataCallBack.onLoading(false)
                Log.i(TAG,"Error : ${t.toString()}")
                val errorMessage = t.message?: ERROR_OCCOR
                mDataCallBack.onFail(errorMessage)
            }
        })
    }


}