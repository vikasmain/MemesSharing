package com.example.memessharing.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memessharing.R
import com.example.memessharing.api.MemeListResponse
import javax.inject.Inject

class MemeListAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<MemeViewHolder>() {
    private val memesData = mutableListOf<MemeListResponse.MemesData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = View.inflate(context, R.layout.memes_list_item, null) as ViewGroup
        return MemeViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {

    }

    override fun getItemCount() = memesData.size
}

class MemeViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

}
