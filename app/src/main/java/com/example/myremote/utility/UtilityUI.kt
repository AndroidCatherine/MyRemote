package com.example.myremote.utility

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Catherine Tsai on 01/03/2024
 */

class UtilityUI {
    companion object{
        const val NO_NET_MESSAGE = "請開啟網路"
        fun showToast(context: Context?, message:String ){
            if(context!=null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        fun showLoading( show:Boolean):Int{
            val visible = when(show){
                true->{
                    View.VISIBLE}
                false->{
                    View.INVISIBLE}
            }
            return visible
        }

        private fun set_RecyclerView_Style( fix:Boolean,recyclerView: RecyclerView , layoutManager : RecyclerView.LayoutManager? ): RecyclerView{
            recyclerView.setHasFixedSize(fix)
            recyclerView.layoutManager = layoutManager
            return recyclerView
        }

        fun set_RecyclerView_Style_LinearLayou(fix:Boolean, recyclerView: RecyclerView , context: Context ): RecyclerView{
           return set_RecyclerView_Style(fix ,recyclerView,LinearLayoutManager(context))
        }

        fun set_RecyclerView_Style_GirdLayout( fix:Boolean,recyclerView: RecyclerView , context: Context ,  spanCount :Int  ): RecyclerView{
           return set_RecyclerView_Style(fix ,recyclerView,GridLayoutManager(context , spanCount))
        }

        fun getSimpleDialog_OneButton( context: Context,buttonTEXT:String ,message: String,listener: DialogListener ): AlertDialog {
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton(buttonTEXT, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener.clickPositiveButton( dialog )
                }
            })
            builder.setMessage(message)
            val dialog: AlertDialog = builder.create()
            return dialog
        }



    }
}