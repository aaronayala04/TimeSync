package com.example.timesyncproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val context: Context, private var dataList: List<DataClass>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.recTitle.text = currentItem.dataTitle
        holder.recDesc.text = currentItem.dataDesc
        holder.recLang.text = currentItem.dataLang

        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("Description", currentItem.dataDesc)
                putExtra("Title", currentItem.dataTitle)
                putExtra("Key", currentItem.key)
                putExtra("Language", currentItem.dataLang)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setSearchDataList(searchList: List<DataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recTitle: TextView = itemView.findViewById(R.id.recTitle)
    val recDesc: TextView = itemView.findViewById(R.id.recDesc)
    val recLang: TextView = itemView.findViewById(R.id.recLang)
    val recCard: CardView = itemView.findViewById(R.id.recCard)
}
