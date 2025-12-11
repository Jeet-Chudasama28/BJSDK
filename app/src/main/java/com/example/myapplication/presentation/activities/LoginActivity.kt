package com.example.myapplication.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbHelper = DatabaseHelper(this)

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()
            when {
                email.isEmpty()|| pass.isEmpty()-> {
                    Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_LONG).show()
                    binding.checkBox.isChecked = false
                }
                else -> {
                    if(isChecked){
                        binding.checkBox.isChecked = true
                        val user2 = dbHelper.getAllUsers2()
                        for (user in user2) {
                            Log.d("DB_USER", "ID: ${user.id}, Email: ${user.email}, Phone: ${user.phone}")
                            if(user.email == email){
                                Toast.makeText(this, "User is already saved", Toast.LENGTH_LONG).show()
                                binding.checkBox.isChecked = false
                                return@setOnCheckedChangeListener
                            }
                        }
                        val canSave = findUser(dbHelper, email, pass)
                        if(canSave == "success"){
                            val user = dbHelper.getUserByEmail(email = email)
                            user?.let {
                                val result = dbHelper.insertUser2(it.email, it.email, it.password)
                                if (result != -1L) {
                                    Toast.makeText(this, "Details saved successfully", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this, "Error inserting data", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            binding.checkBox.isChecked = false
                            Toast.makeText(this, canSave, Toast.LENGTH_LONG).show()
                            return@setOnCheckedChangeListener
                        }
                    } else {
                        binding.checkBox.isChecked = false
                        val result = dbHelper.removeUser2(email = email, password = pass)
                        if(result != 0){
                            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.lgnBtn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()
            when {
                email.isEmpty()|| pass.isEmpty()-> {
                    Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val canLogin = findUser(dbHelper, email, pass)
                    if(canLogin == "success"){
                        Log.d("Login Event", "Login Successfully")

                        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE).edit()
                        sharedPreferences.putBoolean("isLogin", true)
                        sharedPreferences.apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, canLogin, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.gotoRg.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun findUser(dbHelper: DatabaseHelper, email: String, pass: String): String {
        return dbHelper.loginUser(email = email, password = pass)
    }
}