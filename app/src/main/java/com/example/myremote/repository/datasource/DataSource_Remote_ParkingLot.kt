package com.example.myremote.repository.datasource

import android.util.Log
import com.example.myremote.model.remote.api.Api_Park
import com.example.myremote.model.remote.data.park.ParkingInfo
import com.example.myremote.model.remote.data.allavailable.AllAvailable
import com.example.myremote.model.remote.data.alldesc.AllDesc
import com.example.myremote.model.remote.model.SingletonRetrofit
import java.lang.NumberFormatException

/**
 * Created by Catherine Tsai on 01/03/2024
 */

class DataSource_Remote_ParkingLot:IDataSource.IRemote {

    private val TAG = this.javaClass.simpleName

    companion object {
        private val TAG = "DataSource_Remote_ParkingLot"

        //Singleton
        val instance by lazy {
            DataSource_Remote_ParkingLot()
        }

        val mApi_Park by lazy {
            //Singleton
            SingletonRetrofit.PARKINGLOT.retrofit_ParkingLot.create(Api_Park::class.java)
        }
    }


    suspend fun connect_ParkingLotInfo_AllDesc(): AllDesc? {
        val allDesc = DataSource_Remote_ParkingLot.mApi_Park.getDataAllDesc()
        return allDesc
    }

    suspend fun connect_ParkingLotInfo_AllAvailable(): AllAvailable? {
        val allAvailable = DataSource_Remote_ParkingLot.mApi_Park.getDataAllAvailable()
        return allAvailable
    }

    suspend fun combineData_ParkingLot(
        allDesc: AllDesc?,
        allAvailable: AllAvailable?
    ): List<ParkingInfo> {

        val mutableListData: MutableList<ParkingInfo> = mutableListOf()
        if (allDesc != null) {
            if (allAvailable != null) {
                for (desc in allDesc.data.parkList) {
                    val id = desc.id
                    for (available in allAvailable.data.park) {
                        val _id = available.id ?: ""
                        if (id.equals(_id)) {
                            val _id = available.id
                            val name = desc.name
                            val address = desc.address
                            val totalCar = desc.totalcar
                            val availablecar = available.availablecar
                            val totalStandby ="" //desc.ChargingStation
                            val totalRecharge =""
                              // available.chargeStation?.scoketStatusList?
                            val parkingLotInfo = ParkingInfo(
                                id,
                                name,
                                address,
                                totalCar,
                                availablecar,
                                available.chargeStation,
                                totalStandby,
                                totalRecharge
                            )
                            mutableListData.add(parkingLotInfo)
                        }
                    }
                }


            } else {

            }
        } else {

        }
        return checkList(mutableListData)
    }


   private suspend fun checkValue( text:String? ):Boolean{
        var pass = true
        try {
            text?.toInt()
        }catch (e: NumberFormatException){
            pass = false
            Log.i(TAG,"NumberFormatException ${e.toString()}")
        }

        if(text==null){
            pass = false
        }else{
            if(text.trim().contains("-")){
                pass = false
            }
        }
       return pass
    }

    private suspend fun checkList(list:MutableList<ParkingInfo> ):List<ParkingInfo>{
        val mutableListData: MutableList<ParkingInfo> = mutableListOf()
        for(item in list){
            val containZero:Boolean = item.id?.contains("0")?:true
            var id :String? = item.id?.trim()
            if(containZero) {
                val tmplist = item.id?.split("0")
                val tmpSize = tmplist?.size ?: 0
                if(tmpSize >1){
                    id = tmplist?.get(1)?:null
                }
            }
              if(!checkValue(id)
                  ||!checkValue(item.totalcar?.trim())
                  ||!checkValue(item.availablecar?.trim()))
            {
               continue
            }
            mutableListData.add(item)

        }
        val resultList = mutableListData.distinctBy { it.id }.sortedBy{
            it.id?.toInt()
        }
        return resultList.toList()
    }





}