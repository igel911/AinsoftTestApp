package com.vladimir_khm.ainsofttestapp.ui.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.model.Shop
import kotlinx.android.synthetic.main.rv_item.view.*

class ShopRvAdapter(private val interaction: Interaction) :
    ListAdapter<Shop, ShopRvAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item, parent, false),
            interaction
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(itemView: View, private val interaction: Interaction) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Shop) = with(itemView) {
            tv_rv_item.text = item.name
            tv_rv_item.setOnClickListener { interaction.onNameClick(item) }
            iv_edit_rv_item.setOnClickListener { interaction.onEditClick(item) }
            iv_remove_rv_item.setOnClickListener { interaction.onDeleteClick(item) }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Shop>() {
    override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
        return oldItem == newItem
    }
}

interface Interaction {
    fun onNameClick(item: Shop)
    fun onEditClick(item: Shop)
    fun onDeleteClick(item: Shop)
}