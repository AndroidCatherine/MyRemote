package com.example.myremote.view.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.myremote.basic.BasicActivity
import com.example.myremote.databinding.ActivityParkinglotBinding
import com.example.myremote.repository.Repository_ParkingLot
import com.example.myremote.repository.datasource.DataSource_Remote_ParkingLot
import com.example.myremote.utility.DialogListener
import com.example.myremote.utility.NetChecker
import com.example.myremote.utility.UtilityUI
import com.example.myremote.view.fragment.Fragment_ParkingLot
import com.example.myremote.view.fragment.Fragment_UserTimeZone
import com.example.myremote.viewmodel.ViewModel_ActivityParkingLot
import com.example.myremote.viewmodel.factory.ViewModelFactory_ActivityParkingLot

/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ActivityParkingLot:BasicActivity<ActivityParkinglotBinding, ViewModel_ActivityParkingLot>(){

    private val  TAG = this.javaClass.simpleName
    private lateinit var mVieModel:ViewModel_ActivityParkingLot
    private lateinit var mViewBinding:ActivityParkinglotBinding
    private lateinit var mContext:Context
    private val fragmentParkingLot by lazy{ Fragment_ParkingLot.instance}
    private val fragmentUserTimeZone by lazy { Fragment_UserTimeZone.instance}
    private lateinit var mDialog:AlertDialog

    companion object{
        const val TAG_FRAGMENT_PARKINGLOT = "TAG_FRAGMENT_PARKINGLOT"
        const val TAG_FRAGMENT_USER_TIMEZONE = "TAG_FRAGMENT_USER_TIMEZONE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()
        mViewBinding = createViewBinding(layoutInflater)
        mVieModel = createViewModel()
        fragmentParkingLot.setApplication(application)
        fragmentUserTimeZone.setApplication(application)
        setOnContentView(mViewBinding)
        setButtonVisible( false )
        if(!NetChecker.isNetOn(application)) {
            showNoNet(mContext)
        }else {
            setInitialFragment()
            setClick()
        }
    }

    fun initial(){
        mContext = this

    }


    override fun createViewBinding(layoutInflater: LayoutInflater): ActivityParkinglotBinding {
        return ActivityParkinglotBinding.inflate( layoutInflater )
    }

    override fun setOnContentView(viewBind: ActivityParkinglotBinding) {
        setContentView( getContentView(viewBind))
    }

    override fun createViewModel(): ViewModel_ActivityParkingLot {
        val factory = ViewModelFactory_ActivityParkingLot( application , Repository_ParkingLot( DataSource_Remote_ParkingLot.instance , application ) )
        return getViewModel(this, ViewModel_ActivityParkingLot::class.java, factory )
    }

    override fun observeViewModel_LiveData() {

    }


    override fun makeRepository() {

    }

    fun setButtonVisible( back:Boolean){
        if(back){
            mViewBinding.buttonBack.visibility =View.VISIBLE
            mViewBinding.buttonTimezone.visibility = View.INVISIBLE
        }else{
            mViewBinding.buttonBack.visibility =View.INVISIBLE
            mViewBinding.buttonTimezone.visibility = View.VISIBLE
        }
    }


    fun setInitialFragment( ){
        if(!fragmentParkingLot.isAdded) {
            supportFragmentManager.beginTransaction()
                .add(mViewBinding.container.id, fragmentParkingLot, TAG_FRAGMENT_PARKINGLOT)
                .commit()
        }
    }

    fun replaceFragment( ){
        Fragment_UserTimeZone.mSpinnerIsClick = false
        supportFragmentManager.beginTransaction()
            .replace(mViewBinding.container.id,fragmentUserTimeZone , TAG_FRAGMENT_USER_TIMEZONE)
            .addToBackStack(null)
            .commit()
    }

    fun backFragment(){
        Fragment_UserTimeZone.mIsBackPress = true
        supportFragmentManager.beginTransaction()
            .remove(fragmentUserTimeZone)
            .replace(mViewBinding.container.id,fragmentParkingLot, TAG_FRAGMENT_PARKINGLOT)
           // .addToBackStack(null)
            .commit()

    }

    override fun setClick() {
        mViewBinding.buttonTimezone.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setButtonVisible( true )
                replaceFragment()
            }
        })

        mViewBinding.buttonBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setButtonVisible( false )
                backFragment( )
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(mViewBinding.container.id)
        if(fragment is Fragment_ParkingLot){
            setButtonVisible( false )
        }else if(fragment is Fragment_UserTimeZone){

        }
    }

    fun showNoNet( context: Context ){
      mDialog = UtilityUI.getSimpleDialog_OneButton(context,"好的"
            ,UtilityUI.NO_NET_MESSAGE
            ,object :DialogListener{
                override fun clickPositiveButton(dialog: DialogInterface?){
                    dialog?.dismiss()
                    finish()
                }
            }
        )
        if(!mDialog.isShowing){
            mDialog.show()
        }
    }

}