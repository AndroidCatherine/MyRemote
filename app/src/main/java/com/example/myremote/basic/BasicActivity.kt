package com.example.myremote.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding

abstract class BasicActivity<T_ViewBind:ViewBinding ,T_ViewModel:ViewModel>: FragmentActivity(){

    protected abstract fun createViewBinding( layoutInflater:LayoutInflater ):T_ViewBind
    protected abstract fun setOnContentView( viewBind: T_ViewBind  )
    protected abstract fun createViewModel(  ):T_ViewModel

    protected fun getContentView(viewBind: T_ViewBind):View{
        return viewBind.root
    }

    protected fun getViewModel(    viewModelStoreOwner:ViewModelStoreOwner
                                 , className:Class<T_ViewModel>
                                 , factory:ViewModelProvider.Factory ):T_ViewModel{
        val viewmodel = ViewModelProvider( viewModelStoreOwner , factory ).get( className )
        return viewmodel
    }

    protected abstract fun observeViewModel_LiveData(  )
    protected abstract fun makeRepository(    )
    protected abstract fun setClick()

}