package com.example.myremote.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.myremote.basic.BasicViewModelFactory
import com.example.myremote.repository.Repository_ParkingLot
import com.example.myremote.viewmodel.ViewModel_Fragment_ParkingLot
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModelFactory_Fragment_ParkingLot(
    private val application: Application
  , private val repository: Repository_ParkingLot
) : BasicViewModelFactory<ViewModelFactory_Fragment_ParkingLot>(application, repository) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return getViewModel(
            ViewModel_Fragment_ParkingLot::class.java,
            ViewModel_Fragment_ParkingLot(application, repository)
        ) as T
    }

}