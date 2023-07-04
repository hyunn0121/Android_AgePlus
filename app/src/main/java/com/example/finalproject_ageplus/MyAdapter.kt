package com.example.finalproject_ageplus

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_ageplus.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var context: Context

    // 어댑터는 3개의 함수를 반드시 포함
    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    // 기존에 만들어놨던 ViewHolder와 연결하는 부분
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position]

        /*
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val txColor:String? = sharedPreferences.getString("tx_color", "") // sharedPreference에 있는 값을 가지고 오는 것
        binding.itemData.setTextColor(Color.parseColor(txColor))
         */
    }
}
