package com.example.myremote.model.remote.api

import com.example.myremote.model.remote.data.allavailable.AllAvailable
import com.example.myremote.model.remote.data.alldesc.AllDesc
import retrofit2.http.GET
/**
 * Created by Catherine Tsai on 01/03/2024
 */
interface Api_Park {

    @GET(URLs.ParkingLot.PATH_PARKINGPOT_ALLDESC)
    suspend fun getDataAllDesc( ):AllDesc?

    @GET(URLs.ParkingLot.PATH_PARKINGPOT_ALLAVAILABLE)
    suspend fun getDataAllAvailable( ): AllAvailable?
}