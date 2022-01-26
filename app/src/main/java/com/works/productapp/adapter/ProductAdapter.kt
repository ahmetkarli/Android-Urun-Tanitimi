package com.works.productapp.adapter

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.works.productapp.databinding.ProductRowBinding.*
import com.works.productapp.productmodels.Bilgiler

class ProductAdapter(private val context: Context, private val arrPro: List<Bilgiler> = ArrayList()): BaseAdapter() {
    override fun getCount(): Int {
        return arrPro.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val bind = inflate(LayoutInflater.from(context),p2,false)
        val item = arrPro.get(p0)

        bind.txtTitle.text = item.productName.toString()
        bind.txtPrice.text = "${item.price.toString()} TL"

        val url = item.images!!.get(0).normal
        Glide.with(context).load(url).into(bind.imgView)

        return bind.root
    }
}