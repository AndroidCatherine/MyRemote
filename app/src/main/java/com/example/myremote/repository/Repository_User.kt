package com.example.myremote.repository

import android.app.Application
import com.example.myremote.repository.datasource.DataSource_Remote_User
import com.example.myremote.model.remote.data.user.LoginData
import com.example.myremote.model.remote.data.user.UpdateTimeZone
import com.example.myremote.model.remote.data.user.UserInfo
import com.example.myremote.model.shpr.SharedPreferencesManagement


/**
 * Created by Catherine Tsai on 01/03/2024
 */
class Repository_User(private val datasourceRemoteUser: DataSource_Remote_User,private val application: Application):IRepository{

    init{
        SharedPreferencesManagement.initial(application)
    }

    fun login(loginData: LoginData, callback:IRepository.RemoteCallBack<UserInfo>){
        datasourceRemoteUser.connect_Login( loginData , object:IRepository.RemoteCallBack<UserInfo>{
            override fun onSucess(data: UserInfo) {
                callback.onSucess( data )
            }

            override fun onFail(errorMsg: String) {
                callback.onFail(errorMsg)
            }

            override fun onLoading(on: Boolean) {
                callback.onLoading( on )
            }
        }

        )
    }


    fun updateUserTimeZone(token:String,
                           projectID:String,
                           timezone:String,
                           callback:IRepository.RemoteCallBack<UpdateTimeZone> ){
        datasourceRemoteUser.connect_updateTimeZone( token, projectID, timezone
            , object:IRepository.RemoteCallBack<UpdateTimeZone>{
                override fun onSucess(data: UpdateTimeZone) {
                    callback.onSucess( data )
                }

                override fun onFail(errorMsg: String) {
                    callback.onFail(errorMsg)
                }

                override fun onLoading(on: Boolean) {
                    callback.onLoading( on )
                }
            }

        )
    }



    suspend fun saveUserInfo( userInfo: UserInfo){
        saveUserToken( userInfo.sessionToken )
        saveUserMail(userInfo.name)
        saveUserProjectID(userInfo.objectId)
        saveUserTimeZone(userInfo.timezone)
    }


    fun clearUserInfo(){
        SharedPreferencesManagement.clear()
    }

    private fun saveUserToken( token:String ){
        SharedPreferencesManagement.writeUserToken(token)
    }


    private fun saveUserMail( mail:String ){
        SharedPreferencesManagement.writeUserMail( mail )
    }

    private fun saveUserTimeZone( timeZone:String ){
        SharedPreferencesManagement.writeUserTimeZone( timeZone )
    }

    private fun saveUserProjectID( userProjectID:String ){
        SharedPreferencesManagement.writeUserProjectID( userProjectID )
    }

    suspend fun saveIsLoginSuccess( success:Boolean ){
        SharedPreferencesManagement.writeIsLoginSuccess( success )
    }


    fun getUserToken( ):String{
        return SharedPreferencesManagement.readUserToken()
    }

    fun getUserMail( ):String{
        return SharedPreferencesManagement.readUserMail( )
    }

    fun getUserProjectID( ):String{
        return SharedPreferencesManagement.readUserProjectID()
    }

    fun getUserTimeZone( ):String{
        return SharedPreferencesManagement.readUserTimeZone( )
    }

    fun getIsLoginSuccess():Boolean{
        return SharedPreferencesManagement.readIsLoginSuccess( )
    }

}