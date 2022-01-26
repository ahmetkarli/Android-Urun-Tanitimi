package com.works.productapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.works.productapp.databinding.ActivityLoginBinding
import com.works.productapp.models.UserLogin
import com.works.productapp.services.Service
import com.works.productapp.utils.ApiClient
import com.works.productapp.utils.UserObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var bind :ActivityLoginBinding

    lateinit var sha : SharedPreferences
    lateinit var edit : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sha = getSharedPreferences("user", MODE_PRIVATE)
        edit = sha.edit()

        //val email = sha.getString("email","")
        val userID = sha.getString("userID","")
        val name = sha.getString("name","")
        val surname = sha.getString("surname","")


        if(userID != ""){
            UserObject.userID = userID
            UserObject.name = name
            UserObject.surname = surname
            val i = Intent(this@Login,ProductShow::class.java)
            startActivity(i)
        }

        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnLogin.setOnClickListener {

            val email:String = bind.txtEmail.text.toString().trim()
            val password:String = bind.txtPassword.text.toString().trim()

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "E-mail cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val service = ApiClient.getClient().create(Service::class.java)
            val dataService = service.userLogin(email,password)

            dataService.enqueue(object : Callback<UserLogin> {
                override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                    if(response.isSuccessful){
                        val u = response.body()
                        if(u!=null){
                            val status = u.user?.get(0)?.durum
                            val message = u.user?.get(0)?.mesaj
                            if(status == true){

                                //edit.putString("email",email)
                                edit.putString("userID",u.user?.get(0)?.bilgiler?.userId)
                                edit.putString("name",u.user?.get(0)?.bilgiler?.userName)
                                edit.putString("surname",u.user?.get(0)?.bilgiler?.userSurname)
                                edit.commit()

                                UserObject.userID = u.user?.get(0)?.bilgiler?.userId
                                UserObject.name = u.user?.get(0)!!.bilgiler?.userName
                                UserObject.surname = u.user?.get(0)!!.bilgiler?.userSurname

                                Toast.makeText(this@Login,""+message, Toast.LENGTH_SHORT).show()
                                val i = Intent(this@Login, ProductShow::class.java)
                                startActivity(i)
                                finish()
                            }
                            if(status == false) {
                                Toast.makeText(this@Login, "" +message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                }
                override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                    Toast.makeText(this@Login, "Login failed. Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }

            })

        }

        bind.btnRegister.setOnClickListener {
            val i = Intent(this@Login, Register::class.java)
            startActivity(i)
        }
    }
}