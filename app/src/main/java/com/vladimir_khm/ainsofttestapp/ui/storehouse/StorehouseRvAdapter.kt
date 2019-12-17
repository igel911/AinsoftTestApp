package com.vladimir_khm.ainsofttestapp.ui.storehouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import kotlinx.android.synthetic.main.rv_item.view.*

class StorehouseRvAdapter(private val interaction: Interaction) :
    ListAdapter<Storehouse, StorehouseRvAdapter.ItemViewHolder>(DiffCallback()) {

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

        fun bind(item: Storehouse) = with(itemView) {
            tv_rv_item.text = item.name
            tv_rv_item.setOnClickListener { interaction.onNameClick(item) }
            iv_edit_rv_item.setOnClickListener { interaction.onEditClick(item) }
            iv_remove_rv_item.setOnClickListener { interaction.onDeleteClick(item) }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Storehouse>() {
    override fun areItemsTheSame(oldItem: Storehouse, newItem: Storehouse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Storehouse, newItem: Storehouse): Boolean {
        return oldItem == newItem
    }
}

interface Interaction {
    fun onNameClick(item: Storehouse)
    fun onEditClick(item: Storehouse)
    fun onDeleteClick(item: Storehouse)
}