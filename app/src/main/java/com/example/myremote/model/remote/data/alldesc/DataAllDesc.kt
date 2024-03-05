package com.example.myremote.model.remote.data.alldesc

import com.example.myremote.model.remote.data.DataObj
import com.google.gson.annotations.SerializedName
/**
 * Created by Catherine Tsai on 01/03/2024
 */
data class DataAllDesc( val UPDATETIME:String?
                       ,@SerializedName("park") val parkList:List<Park> ):DataObj(){
}