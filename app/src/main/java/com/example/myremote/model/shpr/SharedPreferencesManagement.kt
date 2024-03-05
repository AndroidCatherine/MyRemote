package com.example.myremote.model.shpr

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class SharedPreferencesManagement{

    private val TAG = this.javaClass.simpleName

      companion object {
          private const val NAME = "APP_DATA"
          private const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
          private const val KEY_USER_MAIL = "KEY_USER_MAIL"
          private const val KEY_USER_PROJECT_ID = "KEY_USER_PROJECT_ID"
          private const val KEY_USER_TIMEZONE = "KEY_USER_TIMEZONE"
          private const val KEY_LOGIN_IS_SUCCESS = "KEY_LOGIN_IS_SUCCESS"

          private lateinit var mApplication: Application


          private val mSharedPreferences: SharedPreferences by lazy {
              initial_SharedPreferences( mApplication )
          }

          fun initial(application:Application){
              mApplication = application
          }

          private fun initial_SharedPreferences(application: Application): SharedPreferences {
              return application.applicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
          }

          fun writeUserToken(value: String) {
              writeString(KEY_USER_TOKEN, value)
          }

          fun writeUserMail(value: String) {
              writeString(KEY_USER_MAIL, value)
          }

          fun writeUserProjectID( value: String ){
              writeString(KEY_USER_PROJECT_ID ,value)
          }

          fun writeUserTimeZone(value: String) {
              writeString(KEY_USER_TIMEZONE, value)
          }

          fun writeIsLoginSuccess(value:Boolean){
              writeBoolean(KEY_LOGIN_IS_SUCCESS , value)
          }

          //Read
          fun readUserToken( ): String {
              return readString(KEY_USER_TOKEN)
          }

          fun readUserMail( ): String {
              return readString(KEY_USER_MAIL)
          }

          fun readUserProjectID( ):String{
              return readString( KEY_USER_PROJECT_ID )
          }

          fun readUserTimeZone( ): String {
              return readString(KEY_USER_TIMEZONE)
          }

          fun readIsLoginSuccess( ):Boolean{
              return readBoolean(KEY_LOGIN_IS_SUCCESS)
          }

          private fun writeString(key: String, value: String) {
              mSharedPreferences.edit().putString(key, value).commit()
          }

          private fun readString(key: String): String {
              val defaultValue = ""
              return mSharedPreferences.getString(key, defaultValue) ?: defaultValue
          }


          private fun writeBoolean(key: String, value:Boolean) {
              mSharedPreferences.edit().putBoolean(key,value).commit()
          }

          private fun readBoolean(key: String): Boolean {
              val defaultValue = false
              return mSharedPreferences.getBoolean(key, defaultValue)
          }



          fun clear() {
              val editor = mSharedPreferences.edit()
              editor.clear()
              editor.commit()
          }
      }

}
