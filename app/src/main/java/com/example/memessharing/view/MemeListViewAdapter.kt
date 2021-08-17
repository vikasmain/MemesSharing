package com.example.memessharing.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memessharing.StateFlows
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.databinding.MemeListItemViewBinding
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.android.synthetic.main.meme_list_item_view.view.*
import javax.inject.Inject

@ActivityScoped
class MemeListViewAdapter @Inject constructor(
    private val context: Activity
) : RecyclerView.Adapter<MemeListViewHolder>() {
    private val memeList = mutableListOf<MemeListResponse.MemesData>()
    internal fun updateList(memeListResponse: List<MemeListResponse.MemesData>) {
        memeList.clear()
        memeList.addAll(memeListResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeListViewHolder {
        val binding = MemeListItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return MemeListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MemeListViewHolder, position: Int) {
        holder.bindData(memeList[position], memeList)
    }

    override fun getItemCount(): Int {
        return memeList.size
    }
}

class MemeListViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
    fun bindData(
        memesData: MemeListResponse.MemesData,
        memeList: MutableList<MemeListResponse.MemesData>
    ) {
        itemView.title.text = memesData.title
        itemView.setOnClickListener {
            StateFlows.clickListenerStateFlow.value = Pair(first = memesData, second = memeList.toList())
        }
    }
}
