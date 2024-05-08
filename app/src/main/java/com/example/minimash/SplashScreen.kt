package com.example.minimash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils


import com.example.minimash.R
import com.example.minimash.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var a: Animation
    lateinit var b: Animation
    lateinit var c: Animation
    lateinit var d: Animation

    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val image1 = binding.imageView27
        val image2 = binding.imageView28
        val image3 = binding.imageView29
        val image4 = binding.imageView30
        val text = binding.textView35

        a = AnimationUtils.loadAnimation(applicationContext, R.anim.sideslide)
        image1.startAnimation(a)

        b = AnimationUtils.loadAnimation(applicationContext, R.anim.reversesideslide)
        image2.startAnimation(b)

        c = AnimationUtils.loadAnimation(applicationContext, R.anim.sideslide)
        image3.startAnimation(c)

        d = AnimationUtils.loadAnimation(applicationContext, R.anim.reversesideslide)
        image4.startAnimation(d)


        text.alpha = 0f
        text.animate().setDuration(5500).alpha(1f).withEndAction{
            val i = Intent(this, LoginPage::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, R.anim.reversesideslide)
            finish()
        }

        supportActionBar?.hide()
    }
}