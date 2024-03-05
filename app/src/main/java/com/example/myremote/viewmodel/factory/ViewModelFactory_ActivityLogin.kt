package com.example.myremote.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.myremote.basic.BasicViewModelFactory
import com.example.myremote.repository.Repository_User
import com.example.myremote.viewmodel.ViewModel_ActivityLogin
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModelFactory_ActivityLogin(
    private val application: Application, private val repository: Repository_User
) : BasicViewModelFactory<ViewModel_ActivityLogin>(application, repository) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return getViewModel(
            ViewModel_ActivityLogin::class.java,
            ViewModel_ActivityLogin(application, repository)
        ) as T
    }


}