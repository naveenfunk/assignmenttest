package com.naveen.assignmenttest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.naveen.assignmenttest.BR
import com.naveen.assignmenttest.R
import com.naveen.assignmenttest.databinding.ItemListBinding
import com.naveen.assignmenttest.models.Hit

class HitsAdapter(var list: ArrayList<Hit>?, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<HitsAdapter.HitsHolder>() {

    class HitsHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Hit?) {
            binding.setVariable(BR.data, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HitsHolder {

        var binding =
            DataBindingUtil.inflate<ItemListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_list,
                parent,
                false
            )
        return HitsHolder(binding)
    }


    override fun onBindViewHolder(holder: HitsHolder, position: Int) {
        var data: Hit? = list?.get(holder.adapterPosition)
        holder.bind(data)
        holder.binding.toggle.isChecked = data?.on!!
        holder.binding.rootContainer.isActivated = data?.on!!
        holder.binding.rootContainer.setOnClickListener { v ->
            onItemClickListener.onItemClicked(
                v,
                holder.adapterPosition,
                list?.get(holder.adapterPosition)
            )
        }
    }

    override fun getItemCount() = list?.size!!

    public interface OnItemClickListener {
        fun onItemClicked(view: View, position: Int, data: Hit?)
    }
}