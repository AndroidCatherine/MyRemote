package com.example.myremote.basic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding


abstract class BasicFragment<T_ViewBinding: ViewBinding,T_ViewModel: ViewModel,F_Fragment:Fragment>:Fragment(){

    protected abstract fun createViewModel( ):T_ViewModel
    protected abstract fun createViewBinding(  inflater: LayoutInflater
                                             , container: ViewGroup?,
                                               attachToParent: Boolean ):T_ViewBinding


    fun getViewModel(   owner: ViewModelStoreOwner
                   , factory:ViewModelProvider.Factory
                 , viewModelClass:Class<T_ViewModel>):T_ViewModel {
         return ViewModelProvider(owner ,factory ).get( viewModelClass )
    }

    protected abstract fun setOnCreateView(  ):View
    protected fun getOnCreateView(viewBind: T_ViewBinding):View{
        return viewBind.root
    }

    protected abstract fun observeViewModel_LiveData()
}