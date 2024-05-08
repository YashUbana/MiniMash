package com.example.minimash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.minimash.MainActivity
import com.example.minimash.databinding.ActivitySignupPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupPage : AppCompatActivity() {
    private lateinit var binding: ActivitySignupPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.textView3.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.patientEmail.text.toString()
            val pass = binding.patientPass.text.toString()
            val confirmpass = binding.confirmPass.text.toString()
            val name = binding.userName.text.toString()

            if(name.isEmpty()){
                binding.userName.error = "Please enter your name"
                return@setOnClickListener
            }
            if(email.isEmpty()){
                binding.patientEmail.error = "Enter Email"
                return@setOnClickListener
            }
            if(pass.isEmpty()){
                binding.patientPass.error = "Enter Password"
                return@setOnClickListener
            }
            if(confirmpass.isEmpty()){
                binding.confirmPass.error = "Enter Confirm Password"
                return@setOnClickListener
            }
            if(pass.length < 6 || confirmpass.length < 6){
                if (pass.length < 6)
                    binding.patientPass.error = "Password must be 6 characters long"
                if (confirmpass.length < 6)
                    binding.confirmPass.error = "Password must be 6 characters long"
                return@setOnClickListener
            }


            if (email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty() && name.isNotEmpty()) {
                if (pass == confirmpass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, LoginPage::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password not Match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter every detail", Toast.LENGTH_SHORT).show()
            }



        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}