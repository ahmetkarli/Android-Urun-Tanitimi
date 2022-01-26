package com.works.productapp.activities

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.works.productapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val rotateImg = ObjectAnimator.ofFloat(bind.imgLogo,"rotation",0.0f,360.0f).apply {
            duration = 3000
        }
        rotateImg.start()

        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                val i = Intent(this@MainActivity, Login::class.java)
                startActivity(i)
                finish()
            }
        }.start()

    }

}