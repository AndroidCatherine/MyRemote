package com.example.myremote.model.remote.data.allavailable

import com.example.myremote.model.remote.data.DataObj
import com.google.gson.annotations.SerializedName
/**
 * Created by Catherine Tsai on 01/03/2024
 */
data class ParkEnergyInfo(
    val id: String?,
    val availablecar: String?,
    val availablemotor: String?,
    val availablebus: String?,
    @SerializedName("ChargeStation")
    val chargeStation:ChargeStation?
    ):DataObj()