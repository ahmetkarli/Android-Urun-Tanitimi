package com.works.productapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.works.productapp.adapter.ProductAdapter
import com.works.productapp.databinding.ActivityProductShowBinding
import com.works.productapp.productmodels.Bilgiler
import com.works.productapp.productmodels.ModelProduct
import com.works.productapp.services.Service
import com.works.productapp.utils.ApiClient
import com.works.productapp.utils.UserObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductShow : AppCompatActivity() {

    private lateinit var bind : ActivityProductShowBinding

    lateinit var sha : SharedPreferences
    lateinit var edit : SharedPreferences.Editor
    private var arrPro: List<Bilgiler> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductShowBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.txtName.text = "Welcome, "+ UserObject.name +" "+ UserObject.surname

        val service = ApiClient.getClient().create(Service::class.java)
        val dataService = service.getProduct()

        dataService.enqueue(object : Callback<ModelProduct> {
            override fun onResponse(call: Call<ModelProduct>, response: Response<ModelProduct>) {
                if(response.isSuccessful){
                    val pro = response.body()
                    if(pro!=null){
                        arrPro = pro.products?.get(0)!!.bilgiler!!
                        UserObject.arrPros = arrPro
                        val adapter  = ProductAdapter(this@ProductShow,arrPro)
                        bind.listView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<ModelProduct>, t: Throwable) {
                Toast.makeText(this@ProductShow, "Product fetch failed . Please check your internet connection.", Toast.LENGTH_SHORT).show()
            }
        })

        bind.listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@ProductShow, ProductDetail::class.java)
            intent.putExtra("index",i)
            startActivity(intent)

        }

        bind.btnLogout.setOnClickListener {

            sha = getSharedPreferences("user", MODE_PRIVATE)
            edit = sha.edit()

            //edit.remove("email")
            edit.remove("userID")
            edit.remove("name")
            edit.remove("surname")
            edit.commit()

            val i = Intent(this@ProductShow,MainActivity::class.java)
            startActivity(i)
            finish()

        }
    }

    override fun onBackPressed() {
        val i = Intent(this@ProductShow, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}