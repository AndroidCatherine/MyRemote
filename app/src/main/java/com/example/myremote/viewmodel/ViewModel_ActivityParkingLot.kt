package com.example.myremote.viewmodel

import android.app.Application
import com.example.myremote.basic.BasicViewModel
import com.example.myremote.repository.Repository_ParkingLot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModel_ActivityParkingLot(private val application: Application
                                   ,private val repository: Repository_ParkingLot
): BasicViewModel(application,repository){

    override fun makeLiveData() {

    }

    override fun setJobFromLaunch() {

    }

    override fun makeFactoryClass() {

    }

    override fun onCleared_Task(vararg jobArray: Job?) {

    }

    override fun launch_Task(coroutineDispatcher: CoroutineDispatcher) {

    }

}