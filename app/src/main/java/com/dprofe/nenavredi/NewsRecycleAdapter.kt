package com.dprofe.nenavredi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsRecyclerAdapter(val news: List<News>) :
    RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.recItem_txtTitle)
        val txtText = itemView.findViewById<TextView>(R.id.recItem_txtText)
        val txtDate = itemView.findViewById<TextView>(R.id.recItem_txtDate)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val vh = LayoutInflater.from(p0.context).inflate(R.layout.rec_item, p0, false)
        return ViewHolder(vh)
    }


    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtDate.text = news[p1].date
        p0.txtText.text = news[p1].text
        p0.txtTitle.text = news[p1].title
    }
}