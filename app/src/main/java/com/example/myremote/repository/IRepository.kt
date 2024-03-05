package com.example.myremote.repository

import com.example.myremote.model.remote.data.DataObj
/**
 * Created by Catherine Tsai on 01/03/2024
 */

interface IRepository{
    interface RemoteCallBack<D:DataObj>{
        fun onSucess(data:D)
        fun onFail( errorMsg:String)
        fun onLoading(on:Boolean)
    }
}