package com.example.myremote.model.remote.data.allavailable

import com.example.myremote.model.remote.data.DataObj
/**
 * Created by Catherine Tsai on 01/03/2024
 */
data class DataAvailable(val UPDATETIME:String? ,val park:List<ParkEnergyInfo> ):DataObj()
