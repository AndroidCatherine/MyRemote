package com.example.myremote.model.remote.api

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class URLs {
    companion object{
        const val CONNECT_TIMEOUT:Long  = 10*1000L
        const val READ_TIMEOUT:Long     = 10*1000L
        const val WRITE_TIMEOUT:Long    = 10*1000L
        const val CODE_SUCESS = 200
        const val CODE_FAIL = 403
    }

   object User{
       const val BASE_URL ="https://noodoe-app-development.web.app/"
       const val PATH_LOGIN = "api/login"
       const val PATH_UPDATE_TIMEZONE ="api/users/"
   }

    object ParkingLot{
        const val BASE_URL ="https://tcgbusfs.blob.core.windows.net/blobtcmsv/"
        const val PATH_PARKINGPOT_ALLDESC = "TCMSV_alldesc.json"
        const val PATH_PARKINGPOT_ALLAVAILABLE = "TCMSV_allavailable.json"
    }
}