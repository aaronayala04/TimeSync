package com.example.timesyncproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TimeAdapter(private val context: Context, private var dataList: List<DataClass2>) :
    RecyclerView.Adapter<MyViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_event, parent, false)
        return MyViewHolder2(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val currentItem = dataList[position]

        holder.recEvent.text = currentItem.dataEvent
        holder.recDay.text = currentItem.dataDay
        holder.recDate.text = currentItem.dataDate
        holder.recTime.text = currentItem.dataTime
        holder.recPeriod.text = currentItem.dataPeriod



        holder.recCard2.setOnClickListener {
            val intent = Intent(context, ViewActivity::class.java).apply {
                putExtra("Event", currentItem.dataEvent)
                putExtra("Day", currentItem.dataDay)
                putExtra("Date", currentItem.dataDate)
                putExtra("Time", currentItem.dataTime)
                putExtra("Period", currentItem.dataPeriod)
                putExtra("Key", currentItem.key)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setSearchDataList(searchList: List<DataClass2>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}

class MyViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recEvent: TextView = itemView.findViewById(R.id.recEvent)
    val recDay: TextView = itemView.findViewById(R.id.recDay)
    val recDate: TextView = itemView.findViewById(R.id.recDate)
    val recTime: TextView = itemView.findViewById(R.id.recTime)
    val recPeriod: TextView = itemView.findViewById(R.id.recPeriod)
    val recCard2: CardView = itemView.findViewById(R.id.recCard2)
}
