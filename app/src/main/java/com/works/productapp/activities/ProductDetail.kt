package com.works.productapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.works.productapp.databinding.ActivityProductDetailBinding
import android.text.method.ScrollingMovementMethod
import com.works.productapp.models.OrderModel
import com.works.productapp.services.Service
import com.works.productapp.utils.ApiClient
import com.works.productapp.utils.UserObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetail : AppCompatActivity() {

    private lateinit var bind:ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val index = intent.getIntExtra("index",0)

        val url = UserObject.arrPros.get(index).images!!.get(0).normal
        Glide.with(this@ProductDetail).load(url).into(bind.imgDetail)
        bind.txtTitle.text = UserObject.arrPros.get(index).productName
        bind.txtPrice.text = UserObject.arrPros.get(index).price
        bind.txtBrief.text = UserObject.arrPros.get(index).brief
        bind.txtDesc.text = UserObject.arrPros.get(index).description

        bind.txtBrief.setMovementMethod(ScrollingMovementMethod())
        bind.txtDesc.setMovementMethod(ScrollingMovementMethod())

        bind.btnOrder.setOnClickListener {

            val service = ApiClient.getClient().create(Service::class.java)
            val dataService = service.sendOrder(UserObject.userID!!, UserObject.arrPros.get(index).productId!!,UserObject.arrPros.get(index).productId!!)

            dataService.enqueue(object : Callback<OrderModel> {
                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                    if(response.isSuccessful){
                        val pro = response.body()
                        val status = pro?.order?.get(0)?.durum
                        val message = pro?.order?.get(0)?.mesaj
                        if(pro!=null){
                            if(status == true){
                                Toast.makeText(this@ProductDetail, ""+message, Toast.LENGTH_SHORT).show()
                            }
                            if(status == false){
                                Toast.makeText(this@ProductDetail, ""+message, Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                }
                override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                    Toast.makeText(this@ProductDetail, "Order failed . Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }
}