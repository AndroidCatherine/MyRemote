package com.example.myremote.view.activity


import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.myremote.R
import com.example.myremote.databinding.ActivityLoginBinding
import com.example.myremote.repository.datasource.DataSource_Remote_User
import com.example.myremote.utility.NetChecker
import com.example.myremote.basic.BasicActivity
import com.example.myremote.repository.Repository_User
import com.example.myremote.utility.DialogListener
import com.example.myremote.utility.Utility
import com.example.myremote.utility.UtilityUI
import com.example.myremote.viewmodel.factory.ViewModelFactory_ActivityLogin
import com.example.myremote.viewmodel.ViewModel_ActivityLogin


/**
 * Created by Catherine Tsai on 01/03/2024
 */
class ActivityLogin: BasicActivity<ActivityLoginBinding, ViewModel_ActivityLogin>(){

    private val  TAG = this.javaClass.simpleName
    private lateinit var mVieModel:ViewModel_ActivityLogin
    private lateinit var mViewBinding: ActivityLoginBinding
    private lateinit var mContext: Context
    private val MESSAGE_NO_NET:String = "請開啟網路"
    private val MESSAGE_CANNOT_LOGIN:String =""
    private var mPassword_Visiable:Boolean = false
    private lateinit var mDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()
        mViewBinding = createViewBinding( layoutInflater )
        mVieModel = createViewModel( )
        if(mVieModel.isLoginSuccess()){
            loginSucess()
        }else {
            setOnContentView(mViewBinding)
            observeViewModel_LiveData()
            setClick()
        }
    }

    fun initial(){
        mContext = this
    }

    override fun createViewBinding(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate( layoutInflater )
    }

    override fun setOnContentView(viewBind: ActivityLoginBinding) {
        setContentView( getContentView(viewBind))
    }

    override fun createViewModel(): ViewModel_ActivityLogin {
        val factory = ViewModelFactory_ActivityLogin( application , Repository_User( DataSource_Remote_User.instance , application ) )
        return getViewModel(this, ViewModel_ActivityLogin::class.java, factory )
    }

    override fun observeViewModel_LiveData() {
        mVieModel.mLiveData_LoginStatus_Message.observe(this,object:Observer<String>{
            override fun onChanged(value: String) {
                UtilityUI.showToast(mContext,value)
            }
        })
        mVieModel.mLiveData_Loading.observe(this,object :Observer<Boolean>{
            override fun onChanged(value: Boolean) {
                mViewBinding.progressBarLoading.visibility = UtilityUI.showLoading( value )
            }
        })
        mVieModel.mLiveData_Login_Is_Success.observe(this,object :Observer<Boolean>{
            override fun onChanged(value: Boolean) {
                when(value){
                    true->{
                        //登入成功
                        loginSucess( )
                    }
                    false->{
                        UtilityUI.showToast(mContext,MESSAGE_CANNOT_LOGIN)
                    }
                }
            }
        })

    }

    private fun loginSucess( ){
        Utility.goToPage(this,ActivityParkingLot::class.java)
        finish()
    }

    override fun makeRepository(){

    }

    override fun setClick() {
        mViewBinding.buttonLogin.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(NetChecker.isNetOn(application)) {
                    val email: String? = mViewBinding.edittextUserEmail.text?.toString()
                    val password: String? = mViewBinding.edittextUserPassword.text?.toString()
                    mVieModel.click_Login(email, password)
                }else{
                    showNoNet(mContext)
                }
            }
        })

        mViewBinding.imageViewPassword.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                if(mPassword_Visiable){
                    mViewBinding.edittextUserPassword.setTransformationMethod( PasswordTransformationMethod())
                    mViewBinding.imageViewPassword.setBackgroundResource(R.drawable.password_off)
                    mPassword_Visiable = false
                }else{
                    mViewBinding.edittextUserPassword.setTransformationMethod(null)
                    mViewBinding.imageViewPassword.setBackgroundResource(R.drawable.password_on)
                    mPassword_Visiable = true
                }
            }
        })
    }

    fun showNoNet( context: Context ){
        mDialog = UtilityUI.getSimpleDialog_OneButton(context,"好的"
            ,UtilityUI.NO_NET_MESSAGE
            ,object : DialogListener {
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