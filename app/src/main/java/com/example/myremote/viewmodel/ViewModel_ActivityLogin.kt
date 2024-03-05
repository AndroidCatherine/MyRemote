package com.example.myremote.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myremote.model.remote.data.user.LoginData
import com.example.myremote.model.remote.data.user.UserInfo
import com.example.myremote.repository.IRepository
import com.example.myremote.basic.BasicViewModel
import com.example.myremote.repository.Repository_User
import com.example.myremote.utility.Utility
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModel_ActivityLogin( private val application: Application
                              ,private val repository: Repository_User
): BasicViewModel(application,repository){

    private val  TAG = this.javaClass.simpleName
    //Login Status Message
    private val mutableLiveData_LoginStatus_Message:MutableLiveData<String>  = MutableLiveData<String>()
    val mLiveData_LoginStatus_Message:LiveData<String> = mutableLiveData_LoginStatus_Message
    //Login Status
    private val mutableLiveData_Login_Is_Success:MutableLiveData<Boolean>  = MutableLiveData<Boolean>()
    val mLiveData_Login_Is_Success:LiveData<Boolean> = mutableLiveData_Login_Is_Success
    //Loading
    private val mutableLiveData_Loading:MutableLiveData<Boolean>  = MutableLiveData<Boolean>()
    val mLiveData_Loading:LiveData<Boolean> = mutableLiveData_Loading

    //Job
    private var mJob_Login:Job? = null
    private var mJob_SaveUser:Job? = null

    class LoginMessage{
        companion object{
            val EMAIL_EMPTY = "Email 不可以為空白"
            val EMAIL_ERROR = "Email 格式錯誤"
            val PASSWORD_EMPTY = "密碼不可以為空白"
            val PASSWORD_ERROR = "密碼錯誤"
            val LOGIN_SUCCESS = "登入成功"
        }
    }

    fun isLoginSuccess():Boolean{
      return repository.getIsLoginSuccess()
    }


    fun click_Login( inputEmail: String? , inputPassword: String?){
        if(inputEmail.isNullOrEmpty()){
            mutableLiveData_LoginStatus_Message.value = LoginMessage.EMAIL_EMPTY
        } else if(!Utility.checkEmailStyle(  inputEmail )){
            mutableLiveData_LoginStatus_Message.value = LoginMessage.EMAIL_ERROR
        }else if(inputPassword.isNullOrEmpty()) {
            mutableLiveData_LoginStatus_Message.value = LoginMessage.PASSWORD_EMPTY
        }else{
            mutableLiveData_Loading.value = true
            startLogin( inputEmail , inputPassword)
        }
    }

    private fun startLogin( inputEmail: String , inputPassword: String){
        mJob_Login = viewModelScope.launch(Dispatchers.IO){
            connect_login(inputEmail , inputPassword )
        }
    }

    fun connect_login(email:String , password:String){
        repository.login( LoginData(email,password) , object :IRepository.RemoteCallBack<UserInfo>{
            override fun onSucess(data: UserInfo) {
                val userInfo = data
                saveUser(userInfo)
            }

            override fun onFail(errorMsg: String) {
                  viewModelScope.launch(Dispatchers.Main){
                    mutableLiveData_LoginStatus_Message.value = errorMsg
                  }
            }

            override fun onLoading(on: Boolean) {
                viewModelScope.launch(Dispatchers.Main) {
                    mutableLiveData_Loading.value = on
                }
            }
        })
    }

    private fun saveUser(userInfo: UserInfo){
       mJob_SaveUser =  viewModelScope.launch{
           withContext(Dispatchers.Default){
               repository.saveUserInfo(userInfo)
               repository.saveIsLoginSuccess(true)
           }
           withContext(Dispatchers.Main){
               mutableLiveData_Loading.value = false
               mutableLiveData_LoginStatus_Message.value = LoginMessage.LOGIN_SUCCESS
               mutableLiveData_Login_Is_Success.value = true
           }
        }
    }

    override fun onCleared() {
        super.onCleared()
        onCleared_Task(mJob_Login,mJob_SaveUser)
    }

    override fun onCleared_Task(vararg jobArray: Job?) {
        clearTask(jobArray.toList())
    }


    override fun makeLiveData() {

    }

    override fun setJobFromLaunch() {

    }

    override fun makeFactoryClass() {

    }


    override fun launch_Task(coroutineDispatcher: CoroutineDispatcher) {

    }




}