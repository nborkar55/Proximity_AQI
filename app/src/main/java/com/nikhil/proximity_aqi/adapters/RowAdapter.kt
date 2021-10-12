package com.nikhil.proximity_aqi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.proximity_aqi.R
import com.nikhil.proximity_aqi.model.AQIModelItem
import kotlinx.android.synthetic.main.item_aqi.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class RowAdapter(
    var context: Context,
    private val aqiList: List<AQIModelItem>
) : RecyclerView.Adapter<RowAdapter.RowHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_aqi, parent, false)
        return RowHolder(v)
    }

    override fun getItemCount(): Int {
        return aqiList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val aqiItem = aqiList[position]
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        val aqi=df.format(aqiItem.aqi)

        holder.itemView.tvCity.text = aqiItem.city
        holder.itemView.tvAqi.text = aqi
        when(aqiItem.aqi!!){
            in 0.0..50.0 ->{
                holder.itemView.tvAqi.setTextColor(ContextCompat.getColor(context,
                    R.color.green
                ))
            }
            in 51.0..100.0 ->{
                holder.itemView.tvAqi.setTextColor(ContextCompat.getColor(context,
                    R.color.pale_green
                ))
            }
            in 101.0..200.00->{
                holder.itemView.tvAqi.setTextColor(ContextCompat.getColor(context,
                    R.color.yellow
                ))
            }
            in 201.00..300.0->{
                holder.itemView.tvAqi.setTextColor(ContextCompat.getColor(context,
                    R.color.orange
                ))
            }
            in 301.0..400.0->{
                holder.itemView.tvAqi.setTextColor(ContextCompat.getColor(context,
                    R.color.red
                ))
            }
            in 401.0..500.0->{
                holder.itemView.tvAqi.setTextColor(ContextCompat.getColor(context,
                    R.color.maroon
                ))
            }

        }
    }


    inner class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {

        }
    }
}


