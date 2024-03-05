package com.example.myremote.basic

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myremote.repository.IRepository


abstract class BasicViewModelFactory<T>(
    private val application: Application
   ,private val repository: IRepository
):ViewModelProvider.Factory{

//    abstract fun createViewModel():T

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(modelClass)){
//            return modelClass as T
//        }else{
//            throw java.lang.IllegalArgumentException("This is Not ${modelClass.simpleName} ViewModel ")
//        }
//    }

    fun <T : ViewModel> getViewModel( viewModelClass:Class<T> ,  viewModel:T  ):T{
        if(viewModelClass.isAssignableFrom(viewModelClass)){
            return viewModel as T
        }else{
            throw java.lang.IllegalArgumentException("This is Not ${viewModelClass.simpleName} ViewModel ")
        }
    }

}