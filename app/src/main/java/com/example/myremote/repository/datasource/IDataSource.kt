package com.example.myremote.repository.datasource

/**
 * Created by Catherine Tsai on 01/03/2024
 */

interface IDataSource {
    interface IRemote
    interface ILocal: IRemote
}