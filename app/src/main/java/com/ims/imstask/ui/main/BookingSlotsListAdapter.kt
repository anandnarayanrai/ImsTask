package com.ims.imstask.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ims.imstask.R
import com.ims.imstask.base.BaseViewHolder
import com.ims.imstask.databinding.LayoutBookingSlotsListItemBinding
import com.ims.imstask.retrofit.BookingSlotsResponseItem

class BookingSlotsListAdapter : RecyclerView.Adapter<BookingSlotsListAdapter.MyViewHolder>() {

    private var list = ArrayList<BookingSlotsResponseItem?>()

    fun setItemList(itemList: List<BookingSlotsResponseItem?>) {
        this.list.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding: LayoutBookingSlotsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater
                .from(parent.context), R.layout.layout_booking_slots_list_item, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(position, list[position]!!)
    }

    override fun getItemCount(): Int = list.size

    inner class MyViewHolder(private val binding: LayoutBookingSlotsListItemBinding) :
        BaseViewHolder<BookingSlotsResponseItem>(binding.root) {
        override fun onBind(position: Int, item: BookingSlotsResponseItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}