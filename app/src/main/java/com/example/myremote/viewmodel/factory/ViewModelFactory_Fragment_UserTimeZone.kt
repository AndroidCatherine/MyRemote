package com.example.myremote.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.myremote.basic.BasicViewModelFactory
import com.example.myremote.repository.Repository_User
import com.example.myremote.viewmodel.ViewModel_Fragment_UserTimeZone

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModelFactory_Fragment_UserTimeZone(   private val application: Application
                                              , private val repository: Repository_User
    ):BasicViewModelFactory<ViewModelFactory_Fragment_UserTimeZone>( application, repository){

    override fun <T : ViewModel> create(modelClass: Class<T>):T{
        return getViewModel(
            ViewModel_Fragment_UserTimeZone::class.java,
            ViewModel_Fragment_UserTimeZone( application,repository)
        ) as T
    }
}