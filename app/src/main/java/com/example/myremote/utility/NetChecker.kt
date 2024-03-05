package com.example.myremote.utility

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class NetChecker{
    companion object{

        fun isNetOn( appliction: Application ):Boolean{
            var netOn = false
            if(Build.VERSION.SDK_INT<23){
               netOn = isNetOn_bleow23(appliction)
            }else{
               netOn = isNetOn_Above23( appliction )
            }
            return netOn
        }


        private fun isNetOn_bleow23( appliction:Application ):Boolean{
            var netOn = false
            val context =  appliction.applicationContext
            val connMgr: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            //Wifi
            var networkInfo: NetworkInfo? = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            val isWifiConn:Boolean  = networkInfo?.isConnected() ?: false
           //Mobile Net
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            val isMobileConn:Boolean  = networkInfo?.isConnected()?:false
            if(isWifiConn||isMobileConn){
                netOn = true
            }
            return netOn
        }

        private fun  isNetOn_Above23( appliction:Application ):Boolean{
            var netOn = false
            val context =  appliction.applicationContext
            val connMgr: ConnectivityManager  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as  (ConnectivityManager)

            val networkArray: Array<Network>  = connMgr.getAllNetworks();
            val sb:StringBuilder  =  StringBuilder();

            for ((i, value) in networkArray.withIndex()) {
                if(networkArray.get(i)!=null){
                    val netWork = networkArray.get(i)
                    val networkInfo :NetworkInfo? = connMgr.getNetworkInfo( netWork )
                    if(networkInfo!=null) {
                        netOn = networkInfo.isConnected()
                        val netType:String = networkInfo.getTypeName()
                    }
                }
            }
            return netOn
        }
    }
}