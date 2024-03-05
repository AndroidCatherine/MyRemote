package com.example.myremote.basic

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.myremote.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job


abstract class BasicViewModel( private val _application:Application
                              ,private val repository: IRepository
)
    :AndroidViewModel( _application ){

    val mApplicationContext:Context
    init {
        mApplicationContext = _application.applicationContext
    }

    abstract fun makeLiveData( )
    abstract fun setJobFromLaunch( )
    abstract fun makeFactoryClass( )
    abstract fun onCleared_Task( vararg jobArray: Job? )
    abstract fun launch_Task( coroutineDispatcher:CoroutineDispatcher )

    fun clearTask( jobList:List<Job?> ){
        if(jobList!=null){
            for (job in jobList){
                if(job!=null) {
                    job?.cancel()
                }
            }
        }
    }

}