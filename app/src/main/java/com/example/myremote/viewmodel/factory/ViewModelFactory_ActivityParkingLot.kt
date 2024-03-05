package com.example.myremote.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.myremote.basic.BasicViewModelFactory
import com.example.myremote.repository.Repository_ParkingLot
import com.example.myremote.viewmodel.ViewModel_ActivityParkingLot
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ViewModelFactory_ActivityParkingLot( private val application: Application
                                         , private val repository: Repository_ParkingLot
) : BasicViewModelFactory<ViewModelFactory_ActivityParkingLot>(application, repository) {

    override fun <T : ViewModel> create(modelClass: Class<T>):T{
        return getViewModel(
            ViewModel_ActivityParkingLot::class.java,
            ViewModel_ActivityParkingLot( application,repository )
        ) as T
    }
}