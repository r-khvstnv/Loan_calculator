package com.rssll971.loancalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.databinding.ItemMonthInfoBinding
import com.rssll971.loancalculator.models.MonthCreditModel

/** Класс адаптера для rv*/
class MonthResultAdapter(private val itemsList: ArrayList<MonthCreditModel>,
                         private val context: Context) :
    RecyclerView.Adapter<MonthResultAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemMonthInfoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_month_info, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: MonthCreditModel = itemsList[position]
        with(holder.binding){
            tvMonth.text = item.month.toString()
            tvMainMonthAmount.text = String.format("%,.2f", item.mainMonthDebt)
            tvInterestInMonth.text = String.format("%,.2f", item.interestInMonth)
            tvOverallAmountInMonth.text = String.format("%,.2f", item.overallAmountInMonth)
            tvRemainderInMonth.text = String.format("%,.2f", item.remainder)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}