package com.example.myremote.model.remote.data.alldesc

import com.example.myremote.model.remote.data.DataObj
import com.example.myremote.model.remote.data.FareInfo
/**
 * Created by Catherine Tsai on 01/03/2024
 */
data class Park( val id: String,
                 val name: String?,
                 val address:String?,
                 val totalcar: String?,
                 val ChargingStation:String?,
                 ):DataObj()