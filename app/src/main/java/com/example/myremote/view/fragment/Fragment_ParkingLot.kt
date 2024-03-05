package com.example.myremote.view.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myremote.basic.BasicFragment
import com.example.myremote.databinding.FragmentParkinglotInfoBinding
import com.example.myremote.model.remote.data.park.ParkingInfo
import com.example.myremote.repository.Repository_ParkingLot
import com.example.myremote.repository.datasource.DataSource_Remote_ParkingLot
import com.example.myremote.utility.UtilityUI
import com.example.myremote.view.adapter.Adapter_Recyclearview_ParkingLot
import com.example.myremote.viewmodel.ViewModel_Fragment_ParkingLot
import com.example.myremote.viewmodel.factory.ViewModelFactory_Fragment_ParkingLot
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class Fragment_ParkingLot:BasicFragment<FragmentParkinglotInfoBinding,ViewModel_Fragment_ParkingLot,Fragment_ParkingLot>(){

    private val  TAG = this.javaClass.simpleName
    private lateinit var mVieModel: ViewModel_Fragment_ParkingLot
    private lateinit var mViewBinding: FragmentParkinglotInfoBinding
    private lateinit var mApplication: Application
    private lateinit var mAdapter:Adapter_Recyclearview_ParkingLot
    private lateinit var mContext:Context

    companion object{
        val instance by lazy {
            Fragment_ParkingLot( )
        }
    }

    fun setApplication(application: Application){
        mApplication = application
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initial()
        mViewBinding = createViewBinding( inflater ,container ,false)
        mVieModel = createViewModel()
        setRecyclerViewStyle( mContext )
        initialRecyclerView()
        observeViewModel_LiveData( )
        mVieModel.connectParkingLotInfo( )
        return setOnCreateView()
    }

    private fun initial(){
       mContext = activity as Context
       mAdapter =  Adapter_Recyclearview_ParkingLot( null )
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentParkinglotInfoBinding {
        return FragmentParkinglotInfoBinding.inflate( inflater , container ,false)
    }

    override fun createViewModel():ViewModel_Fragment_ParkingLot{
        val factory = ViewModelFactory_Fragment_ParkingLot( mApplication
            , Repository_ParkingLot( DataSource_Remote_ParkingLot.instance , mApplication ) )
        return getViewModel(this , factory, ViewModel_Fragment_ParkingLot::class.java )
    }

    private fun setRecyclerViewStyle(context: Context){
        UtilityUI.set_RecyclerView_Style_LinearLayou(true,mViewBinding.recyclerviewParking , context )
    }

    private fun initialRecyclerView(){
        mViewBinding.recyclerviewParking.adapter = mAdapter
    }

    override fun setOnCreateView(): View {
        return getOnCreateView(mViewBinding)
    }

    override fun observeViewModel_LiveData() {
        mVieModel.mLiveData_Loading.observe(viewLifecycleOwner,object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                mViewBinding.loadingParkinglot.visibility = UtilityUI.showLoading( value )
            }
        })

        mVieModel.mLiveData_Data_Status_Message.observe(viewLifecycleOwner,object : Observer<String> {
            override fun onChanged(value: String ) {
               UtilityUI.showToast(mContext , value)
            }
        })

        mVieModel.mLiveData_List_ParkingLotInfo.observe(viewLifecycleOwner,object :Observer<List<ParkingInfo>>{
            override fun onChanged(value: List<ParkingInfo>) {
                mAdapter.setData( value )
            }
        })
    }


}