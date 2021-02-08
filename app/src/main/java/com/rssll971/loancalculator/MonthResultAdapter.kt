package com.rssll971.loancalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

//класс адаптера для rv
class MonthResultAdapter(private val itemsList: ArrayList<MonthCreditInfo>,
                         private val context: Context) :
    RecyclerView.Adapter<MonthResultAdapter.MyViewHolder>() {
    //класс с нашими элементами
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var llItem: LinearLayout? = null
        var tvMonth: TextView? = null
        var tvMainMonthAmount: TextView? = null
        var tvInterestInMonth: TextView? = null
        var tvGeneralAmountInMonth: TextView? = null
        var tvRemainderInMonth: TextView? = null
        init {
            llItem = itemView.findViewById(R.id.ll_item)
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
        val layoutItem: MonthCreditInfo = itemsList[position]
        val mainMonthAmount = layoutItem.getMainMonthDebt()
        val interestInMonth = layoutItem.getInterestInMonth()
        val generalAmountInMonth = layoutItem.getGeneralAmountInMonth()
        val remainderInMonth = layoutItem.getRemainder()
        //todo ниже имеются проблемы, стандратное значения проходит через особое
        //проверка на конечный элемент массива
        if (position == (itemsList.size - 1)){
            //меняем цвета
            holder.llItem!!.setBackgroundColor(ContextCompat.getColor(context, R.color.myDarkBlue_BlackBlue))
            holder.tvMonth!!.setTextColor(ContextCompat.getColor(context, R.color.myPink_GoldTransparent))
            holder.tvMainMonthAmount!!.setTextColor(ContextCompat.getColor(context, R.color.myPink_GoldTransparent))
            holder.tvInterestInMonth!!.setTextColor(ContextCompat.getColor(context, R.color.myPink_GoldTransparent))
            holder.tvGeneralAmountInMonth!!.setTextColor(ContextCompat.getColor(context, R.color.myPink_GoldTransparent))

            //вводим финальный подсчет
            holder.tvMonth!!.text = context.resources.getString(R.string.st_title_total)
            holder.tvMainMonthAmount!!.text = String.format("%.2f", mainMonthAmount)
            holder.tvInterestInMonth!!.text = String.format("%.2f", interestInMonth)
            holder.tvGeneralAmountInMonth!!.text = String.format("%.2f", generalAmountInMonth)

        }
        //присваиваем значения
        else {
            holder.tvMonth!!.text = layoutItem.getMonth().toString()
            holder.tvMainMonthAmount!!.text = String.format("%.2f", mainMonthAmount)
            holder.tvInterestInMonth!!.text = String.format("%.2f", interestInMonth)
            holder.tvGeneralAmountInMonth!!.text = String.format("%.2f", generalAmountInMonth)
            holder.tvRemainderInMonth!!.text = String.format("%.2f", remainderInMonth)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}