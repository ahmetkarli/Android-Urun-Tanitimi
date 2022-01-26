package com.works.productapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.works.productapp.databinding.ActivityRegisterBinding
import com.works.productapp.models.UserRegister
import com.works.productapp.services.Service
import com.works.productapp.utils.ApiClient
import com.works.productapp.utils.UserObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var bind : ActivityRegisterBinding

    lateinit var sha : SharedPreferences
    lateinit var edit : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnRegister.setOnClickListener {

            val name:String = bind.txtName.text.toString().trim()
            val surname:String = bind.txtSurname.text.toString().trim()
            val phone:String = bind.txtPhone.text.toString().trim()
            val email:String = bind.txtEmail.text.toString().trim()
            val password:String = bind.txtPassword.text.toString().trim()

            fun isEmailValid(email:CharSequence):Boolean{
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(surname)){
                Toast.makeText(this, "Surname cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this, "Phone cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!isEmailValid(email)){
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val service = ApiClient.getClient().create(Service::class.java)
            val dataService = service.userRegister(name,surname,phone,email,password)

            dataService.enqueue(object : Callback<UserRegister> {
                override fun onResponse(call: Call<UserRegister>, response: Response<UserRegister>) {
                    if(response.isSuccessful){
                        val u = response.body()
                        if(u!=null){
                            val status = u.user?.get(0)?.durum
                            val message =  u.user?.get(0)?.mesaj
                            if(status == true){
                                sha = getSharedPreferences("user", MODE_PRIVATE)
                                edit = sha.edit()

                               // edit.putString("email",email)
                                edit.putString("userID",u.user?.get(0)?.kullaniciId)
                                edit.putString("name",name)
                                edit.putString("surname",surname)
                                edit.commit()

                                UserObject.userID = u.user?.get(0)?.kullaniciId
                                UserObject.name = name
                                UserObject.surname = surname

                                Toast.makeText(this@Register, ""+message, Toast.LENGTH_SHORT).show()
                                val i = Intent(this@Register, ProductShow::class.java)
                                startActivity(i)
                                finish()
                            }
                            if(status == false){
                                Toast.makeText(this@Register, ""+message, Toast.LENGTH_SHORT).show()

                            }
                        }

                    }

                }
                override fun onFailure(call: Call<UserRegister>, t: Throwable) {
                    Toast.makeText(this@Register, "Register failed. Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }

            })


        }


    }


}