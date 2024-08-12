package com.example.projet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MonAdapteur(private val context: Context, private val data: List<Products>): BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return  data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.product_items, parent, false)
        val product = data[position]
        val nameProduct = view.findViewById<TextView>(R.id.emplacemtNameTxt)
        val date = view.findViewById<TextView>(R.id.DateTxt)
        val temp = view.findViewById<TextView>(R.id.TempTxt)
        nameProduct.text = product.name
        date.text = product.date
        temp.text = product.TempMax.toString()
        return view


    }
}