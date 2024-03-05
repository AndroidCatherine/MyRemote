package com.example.myremote.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myremote.databinding.ItemviewParkinglotInfoBinding
import com.example.myremote.model.remote.data.park.ParkingInfo
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class Adapter_Recyclearview_ParkingLot(private var mDataList:List<ParkingInfo>?)
    :RecyclerView.Adapter<Adapter_Recyclearview_ParkingLot.ViewHolder_ParkingLotInfo>(){

    private val TAG = this.javaClass.simpleName
    class ViewHolder_ParkingLotInfo(val viewBinding:ItemviewParkinglotInfoBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_ParkingLotInfo {
       return ViewHolder_ParkingLotInfo(
            ItemviewParkinglotInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder:ViewHolder_ParkingLotInfo, position: Int) {
        if(mDataList!=null) {
            val data = mDataList?.get(position)
            setDataToView(data, holder.viewBinding)
        }
    }

    override fun getItemCount(): Int {
        return mDataList?.size?:0
    }

    private fun setDataToView(data:ParkingInfo?, viewBinding: ItemviewParkinglotInfoBinding ){
        if(data!=null) {
            viewBinding.textviewId.text = data.id
            viewBinding.textviewName.text = data.name ?:"無資料"
            viewBinding.textviewAddress.text =  data.address?:"無資料"
            viewBinding.textviewTotalcar.text = data.totalcar
            viewBinding.textviewAvailablecar.text =data.availablecar
            if(data.chargeStation!=null) {
                viewBinding.layoutTotalStandby.visibility = View.VISIBLE
                viewBinding.layoutTotalRecharge.visibility = View.VISIBLE
                viewBinding.textviewTotalStandby.text = data.totalStandby
                viewBinding.textviewTotalRecharge.text = data.totalRecharge
            }else{
                viewBinding.layoutTotalStandby.visibility = View.GONE
                viewBinding.layoutTotalRecharge.visibility = View.GONE
            }
        }
    }

     fun  setData( listData:List<ParkingInfo> ) {
        mDataList = listData
        notifyDataSetChanged()
    }

}