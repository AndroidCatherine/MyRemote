package com.example.myremote.repository

import android.app.Application
import com.example.myremote.model.remote.data.allavailable.AllAvailable
import com.example.myremote.model.remote.data.alldesc.AllDesc
import com.example.myremote.model.remote.data.park.ParkingInfo
import com.example.myremote.repository.datasource.DataSource_Remote_ParkingLot

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class Repository_ParkingLot( private val datasourceRemoteParkingLot:DataSource_Remote_ParkingLot
                           , private val application: Application):IRepository{

    suspend fun connect_ParkingLotInfo_AllDesc( ): AllDesc?{
        return datasourceRemoteParkingLot.connect_ParkingLotInfo_AllDesc()
    }

    suspend fun connect_ParkingLotInfo_AllAvailable( ): AllAvailable?{
        return datasourceRemoteParkingLot.connect_ParkingLotInfo_AllAvailable()
    }

    suspend fun combineData_ParkingLot( allDesc: AllDesc?, allAvailable: AllAvailable? ):List<ParkingInfo> {
        return datasourceRemoteParkingLot.combineData_ParkingLot(  allDesc , allAvailable )
    }

}