package com.example.finalproject_ageplus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject_ageplus.databinding.ItemMainBinding
class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class XmlAdapter (val context: Context, val datas:MutableList<myItem>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    //전달된 객체를 저장할 변수 정의
    private lateinit var itemClickListener: OnItemClickListener

    //리스너 인터페이스 객체를 전달하는 메서드 선언
    fun setOnItemclickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]
        binding.jobName.text = model.recrtTitle
        binding.place.text = model.workPlcNm
        binding.date.text = model.frDd + " ~ " + model.toDd
    }

    /*
    inner class Holder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.jobName.setOnClickListener {
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION && itemClickListener != null){
                    itemClickListener.onItemClick(binding.jobName, pos)
                }
            }
        }
    }
     */
}