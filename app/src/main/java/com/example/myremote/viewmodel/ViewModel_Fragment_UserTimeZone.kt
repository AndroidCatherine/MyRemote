package com.example.myremote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myremote.basic.BasicViewModel
import com.example.myremote.model.remote.data.user.UpdateTimeZone
import com.example.myremote.model.shpr.SharedPreferencesManagement
import com.example.myremote.repository.IRepository
import com.example.myremote.repository.Repository_User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModel_Fragment_UserTimeZone( val mApplication: Application
                                      ,val repositoryUser: Repository_User
) : BasicViewModel(mApplication, repositoryUser ){

    private val  TAG = this.javaClass.simpleName

    //TimeZone Response
    private val mutableLiveData_UpdateTimeZone_Response: MutableLiveData<UpdateTimeZone> = MutableLiveData<UpdateTimeZone>( )
    val mLiveData_UpdateTimeZone:LiveData<UpdateTimeZone> = mutableLiveData_UpdateTimeZone_Response

    //Status Messeage
    private val mutableLiveData_Data_Status_Message: MutableLiveData<String> = MutableLiveData<String>()
    val mLiveData_Data_Status_Message: LiveData<String> = mutableLiveData_Data_Status_Message

    //Loading
    private val mutableLiveData_Loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val mLiveData_Loading: LiveData<Boolean> = mutableLiveData_Loading

    //Job
    private var mJob:Job? = null
    private val MESSAGE_ERROR:String = "無法更新"


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG,"onCleared========== clear ===============")
        onCleared_Task(mJob)
    }

    override fun onCleared_Task(vararg jobArray: Job?) {
        clearTask(jobArray.toList())
    }

    fun connectUpdateTimeZone( timeZone:String ){
        viewModelScope.launch(Dispatchers.IO){

            withContext(Dispatchers.Main){
                mutableLiveData_Loading.value = true
            }

            val token = SharedPreferencesManagement.readUserToken()
            val projectID = SharedPreferencesManagement.readUserProjectID()
            repositoryUser.updateUserTimeZone(
                  token
                , projectID
                , timeZone
                , object:IRepository.RemoteCallBack<UpdateTimeZone>{
                override fun onSucess(data: UpdateTimeZone) {
                     mutableLiveData_Loading.postValue( false )
                     SharedPreferencesManagement.writeUserTimeZone( timeZone )
                     mutableLiveData_Data_Status_Message.value = "更新TimeZone : $timeZone 成功"
                }

                override fun onFail(errorMsg: String) {
                    mutableLiveData_Loading.postValue( false )
                    mutableLiveData_Data_Status_Message.postValue( MESSAGE_ERROR )
                }

                override fun onLoading(on: Boolean) {
                    mutableLiveData_Loading.postValue( on )
                }

            }  )
        }
    }





    override fun makeLiveData( ){

    }

    override fun setJobFromLaunch( ){

    }

    override fun makeFactoryClass( ){

    }

    override fun launch_Task( coroutineDispatcher: CoroutineDispatcher) {

    }

}