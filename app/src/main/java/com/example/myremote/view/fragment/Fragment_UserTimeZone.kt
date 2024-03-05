package com.example.myremote.view.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.myremote.basic.BasicFragment
import com.example.myremote.databinding.FragmentUserUpdateTimezoneBinding
import com.example.myremote.model.remote.data.user.UpdateTimeZone
import com.example.myremote.model.shpr.SharedPreferencesManagement
import com.example.myremote.repository.Repository_User
import com.example.myremote.repository.datasource.DataSource_Remote_User
import com.example.myremote.utility.UtilityUI
import com.example.myremote.viewmodel.ViewModel_Fragment_UserTimeZone
import com.example.myremote.viewmodel.factory.ViewModelFactory_Fragment_UserTimeZone

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class Fragment_UserTimeZone:BasicFragment<FragmentUserUpdateTimezoneBinding
        ,ViewModel_Fragment_UserTimeZone
        ,Fragment_UserTimeZone>(){

    companion object{
        val instance by lazy {
            Fragment_UserTimeZone( )
        }

        var mIsBackPress = false
        var mSpinnerIsClick = false
    }

    private val  TAG = this.javaClass.simpleName
    private lateinit var mVieModel: ViewModel_Fragment_UserTimeZone
    private lateinit var mViewBinding: FragmentUserUpdateTimezoneBinding
    private lateinit var mApplication: Application
    private var mContext: Context? = null
    private val mTimeZoneList =  listOf<String>("ID","TW","US","CA","HK","JP")
    private lateinit var mStringAdapter:ArrayAdapter<String>
    private var mSpinnerIsDefaultSet = true



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
        mViewBinding.textviewUserEmail.text = SharedPreferencesManagement.readUserMail()
        observeViewModel_LiveData()
        setSpinner( )
        return setOnCreateView()
    }

    override fun onResume() {
        super.onResume()
        mSpinnerIsDefaultSet = true
    }

    private fun initial(){
        mContext =requireContext()
    }

    private fun setSpinner(){
        mStringAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, mTimeZoneList)
        mViewBinding.spinnerTimezone.adapter = mStringAdapter
        val currentTimeZone = SharedPreferencesManagement.readUserTimeZone()
        val index = mTimeZoneList.indexOf( currentTimeZone )
        mViewBinding.spinnerTimezone.setSelection(index)
        mViewBinding.spinnerTimezone.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                mSpinnerIsClick = true
                return false
            }
        })
        mViewBinding.spinnerTimezone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selectedTimeZone = mTimeZoneList.get(position)
                Log.i(TAG,"SelectTimeZone: $selectedTimeZone")
                if(mSpinnerIsClick&&!mSpinnerIsDefaultSet) {
                    mVieModel.connectUpdateTimeZone(selectedTimeZone)
                }else{
                    mSpinnerIsDefaultSet = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    override fun createViewModel(): ViewModel_Fragment_UserTimeZone {
        val factory = ViewModelFactory_Fragment_UserTimeZone( mApplication
            , Repository_User( DataSource_Remote_User.instance , mApplication ) )
        return getViewModel(this , factory, ViewModel_Fragment_UserTimeZone::class.java )
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentUserUpdateTimezoneBinding {
        return FragmentUserUpdateTimezoneBinding.inflate( inflater , container ,false)
    }

    override fun setOnCreateView(): View {
        return getOnCreateView(mViewBinding)
    }

    override fun observeViewModel_LiveData() {
        mVieModel.mLiveData_Loading.observe(viewLifecycleOwner,object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                mViewBinding.loadingUpdateTimezone.visibility = UtilityUI.showLoading( value )
            }
        })

        mVieModel.mLiveData_Data_Status_Message.observe(viewLifecycleOwner,object :
            Observer<String> {
            override fun onChanged(value: String ) {
                if (!mIsBackPress) {
                    UtilityUI.showToast(mContext, value)
                }
            }
        })

        mVieModel.mLiveData_UpdateTimeZone.observe(viewLifecycleOwner,object :Observer<UpdateTimeZone>{
            override fun onChanged(value: UpdateTimeZone) {
                mSpinnerIsClick = false
            }
        })
    }



}