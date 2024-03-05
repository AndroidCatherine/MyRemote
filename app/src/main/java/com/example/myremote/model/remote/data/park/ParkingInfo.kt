package com.example.myremote.model.remote.data.park

import com.example.myremote.model.remote.data.DataObj
import com.example.myremote.model.remote.data.allavailable.ChargeStation
/**
 * Created by Catherine Tsai on 01/03/2024
 */
data class ParkingInfo(val id:String?,
                       val name:String?,
                       val address:String?,
                       val totalcar:String?,
                       val availablecar:String?,
                       val chargeStation:ChargeStation?,
                       val totalStandby:String?,
                       val totalRecharge:String?
                      ):DataObj(){
}