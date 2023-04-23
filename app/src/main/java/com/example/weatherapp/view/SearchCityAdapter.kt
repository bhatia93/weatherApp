package com.example.weather.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class SearchCityAdapter(
    private val context: MainActivity,
    private val list: ArrayList<String>,
    private val listener: SearchNameHistoryClickListener?

) :
    RecyclerView.Adapter<SearchCityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_search_city_history_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.textView.text = model
        holder.itemView.setOnClickListener {
            listener?.onNameSelected(model, position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = itemView.findViewById(R.id.tv_name)
    }

    interface SearchNameHistoryClickListener {
        fun onNameSelected(name: String, selectedPosition: Int)
    }
}
