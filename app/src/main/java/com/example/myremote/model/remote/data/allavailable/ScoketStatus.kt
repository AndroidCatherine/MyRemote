package com.example.myremote.model.remote.data.allavailable

import com.example.myremote.model.remote.data.DataObj
import com.google.gson.annotations.SerializedName

data class ScoketStatus(@SerializedName("availableCount") val standbyRecharge:String?,
                        @SerializedName("scoketCount") val totalRecharge:String?):DataObj(){
}