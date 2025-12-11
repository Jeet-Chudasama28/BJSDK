package com.example.myapplication.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.databinding.ActivityRegisterBinding
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.gotoLgn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.rgBtn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtNumber.text.toString()
            val pass = binding.edtPass.text.toString()
            val confirm = binding.edtCnfPass.text.toString()

            when {
                email.isEmpty() || phone.isEmpty() || pass.isEmpty() || confirm.isEmpty() -> {
                    Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_LONG).show()
                }
                email.length < 10 -> {
                    Toast.makeText(this, "Invalid number", Toast.LENGTH_LONG).show()
                }
                pass != confirm -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                }
                else -> {
                    registerUser(
                        email,
                        phone,
                        pass
                    )
                }
            }
        }
    }

    private fun registerUser(email: String, phone: String, pass: String) {
        val dbHelper by inject<DatabaseHelper>()

        val users = dbHelper.getAllUsers()
        for (user in users) {
            Log.d("DB_USER", "ID: ${user.id}, Email: ${user.email}, Phone: ${user.phone}")
            if(user.email == email){
                Toast.makeText(this, "User already registered, please login to continue", Toast.LENGTH_LONG).show()
                return
            }
        }

        val result = dbHelper.insertUser(email, phone, pass)

        if (result != -1L) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_LONG).show()

            val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE).edit()
            sharedPreferences.putBoolean("isLogin", true)
            sharedPreferences.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Error inserting data", Toast.LENGTH_LONG).show()
        }
    }
}