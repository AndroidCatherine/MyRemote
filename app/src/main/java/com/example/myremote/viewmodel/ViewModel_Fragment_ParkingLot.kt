package com.example.myremote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myremote.basic.BasicViewModel
import com.example.myremote.model.remote.data.allavailable.AllAvailable
import com.example.myremote.model.remote.data.alldesc.AllDesc
import com.example.myremote.model.remote.data.park.ParkingInfo
import com.example.myremote.repository.Repository_ParkingLot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModel_Fragment_ParkingLot( val mApplication: Application
                                    ,val repositoryParkinglot:Repository_ParkingLot
) : BasicViewModel(mApplication, repositoryParkinglot){

    private val  TAG = this.javaClass.simpleName

    //ParkingLotInfo List
    private val mutableLiveData_List_ParkingLotInfo: MutableLiveData<List<ParkingInfo>> = MutableLiveData<List<ParkingInfo>>()
    val mLiveData_List_ParkingLotInfo: LiveData<List<ParkingInfo>> = mutableLiveData_List_ParkingLotInfo

    //ParkingLot Status Messeage
    private val mutableLiveData_Data_Status_Message: MutableLiveData<String> = MutableLiveData<String>()
    val mLiveData_Data_Status_Message: LiveData<String> = mutableLiveData_Data_Status_Message

    //Loading
    private val mutableLiveData_Loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val mLiveData_Loading: LiveData<Boolean> = mutableLiveData_Loading

    //Job
    private var mJob:Job? = null
    private val STATUS_MESSAGE_NO_DATA:String = "無法取得停車位資料"


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG,"onCleared========== clear ===============")
        onCleared_Task(mJob)
    }

    override fun onCleared_Task(vararg jobArray: Job?) {
        clearTask(jobArray.toList())
    }


    fun connectParkingLotInfo( ){
        mJob = viewModelScope.launch (Dispatchers.Main){
            try {
                mutableLiveData_Loading.value = true
                val allDesc: AllDesc? = withContext(Dispatchers.IO) {
                    repositoryParkinglot.connect_ParkingLotInfo_AllDesc()
                }

                val allAvailable: AllAvailable? = withContext(Dispatchers.IO) {
                    repositoryParkinglot.connect_ParkingLotInfo_AllAvailable()
                }

                val dataList_ParkingLot: List<ParkingInfo> = withContext(Dispatchers.IO) {
                    repositoryParkinglot.combineData_ParkingLot(
                        allDesc,
                        allAvailable
                    )
                }

                if (dataList_ParkingLot != null) {
                    mutableLiveData_List_ParkingLotInfo.value = dataList_ParkingLot
                    mutableLiveData_Loading.value = false
                } else {
                    mutableLiveData_Data_Status_Message.value = STATUS_MESSAGE_NO_DATA
                    mutableLiveData_Loading.value = false
                }
            }catch (e:Exception){
                Log.i(TAG," launch connectParkingLotInfo error ${e.toString()} ")
            }

        }
    }


    override fun launch_Task(coroutineDispatcher: CoroutineDispatcher) {

    }

    override fun makeLiveData() {

    }

    override fun setJobFromLaunch() {

    }

    override fun makeFactoryClass() {

    }

}