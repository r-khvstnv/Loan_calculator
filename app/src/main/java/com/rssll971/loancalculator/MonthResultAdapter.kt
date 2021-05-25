package com.rssll971.loancalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/** Класс адаптера для rv*/
class MonthResultAdapter(private val itemsList: ArrayList<MonthCreditModel>,
                         private val context: Context) :
    RecyclerView.Adapter<MonthResultAdapter.MyViewHolder>() {

    //класс с нашими элементами
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvMonth: TextView? = null
        var tvMainMonthAmount: TextView? = null
        var tvInterestInMonth: TextView? = null
        var tvGeneralAmountInMonth: TextView? = null
        var tvRemainderInMonth: TextView? = null
        init {
            tvMonth = itemView.findViewById(R.id.tv_month)
            tvMainMonthAmount = itemView.findViewById(R.id.tv_mainMonthAmount)
            tvInterestInMonth = itemView.findViewById(R.id.tv_interestInMonth)
            tvGeneralAmountInMonth = itemView.findViewById(R.id.tv_generalAmountInMonth)
            tvRemainderInMonth = itemView.findViewById(R.id.tv_remainderInMonth)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_month_info, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //записываем в локальные переменные для дальнейшего округления
        val item: MonthCreditModel = itemsList[position]
        val mainMonthAmount = item.mainMonthDebt
        val interestInMonth = item.interestInMonth
        val generalAmountInMonth = item.generalAmountInMonth
        val remainderInMonth = item.remainder

        //присваиваем значения
        holder.tvMonth!!.text = item.month.toString()
        holder.tvMainMonthAmount!!.text = String.format("%,.2f", mainMonthAmount)
        holder.tvInterestInMonth!!.text = String.format("%,.2f", interestInMonth)
        holder.tvGeneralAmountInMonth!!.text = String.format("%,.2f", generalAmountInMonth)
        holder.tvRemainderInMonth!!.text = String.format("%,.2f", remainderInMonth)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}