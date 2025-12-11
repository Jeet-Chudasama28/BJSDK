package com.example.myapplication.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.model.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "myapp_db"
        private const val DATABASE_VERSION = 2
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUsers = "CREATE TABLE users ( id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, phone TEXT, password TEXT)"
        val createTableUsers2 = "CREATE TABLE users2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, phone TEXT, password TEXT)"

        db?.execSQL(createTableUsers)
        db?.execSQL(createTableUsers2)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        olVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        db?.execSQL("DROP TABLE IF EXISTS users2")
        onCreate(db)
    }

    fun insertUser(email: String, phone: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("email", email)
            put("phone", phone)
            put("password", password)
        }
        val result = db.insert("users", null, values)
        db.close()
        return result
    }

    fun insertUser2(email: String, phone: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("email", email)
            put("phone", phone)
            put("password", password)
        }
        val result = db.insert("users2", null, values)
        db.close()
        return result
    }

    fun removeUser2(email: String, password: String): Int {
        val db = writableDatabase
        val result = db.delete(
            "users2",
            "email = ? AND password = ?",
            arrayOf(email, password)
        )
        db.close()
        return result
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                userList.add(User(id, email, phone, password))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userList
    }

    fun getUserByEmail(email: String): User? {
        val db = readableDatabase
        var user: User? = null

        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE email = ?",
            arrayOf(email)
        )

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val emailValue = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))

            user = User(id = id, email = emailValue, phone = phone, password = password)
        }

        cursor.close()
        return user
    }

    fun getUser2ByEmail(email: String): User{
        val db = readableDatabase
        val user = User(
            id = 0,
            email = "",
            phone = "",
            password = ""
        )
        val emailCursor = db.rawQuery(
            "SELECT * FROM users2 WHERE email = ?",
            arrayOf(email)
        )

        if (emailCursor.moveToFirst()) {
            do {
                val id = emailCursor.getInt(emailCursor.getColumnIndexOrThrow("id"))
                val email = emailCursor.getString(emailCursor.getColumnIndexOrThrow("email"))
                val phone = emailCursor.getString(emailCursor.getColumnIndexOrThrow("phone"))
                val password = emailCursor.getString(emailCursor.getColumnIndexOrThrow("password"))
                user.copy(id = id, email = email, phone = phone, password = password)
            } while (emailCursor.moveToNext())
        }
        return user
    }

    fun getAllUsers2(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users2", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                userList.add(User(id, email, phone, password))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userList
    }

    fun loginUser(email: String, password: String): String {
        val db = readableDatabase

        val emailCursor = db.rawQuery(
            "SELECT * FROM users WHERE email = ?",
            arrayOf(email)
        )

        if (emailCursor.count == 0) {
            emailCursor.close()
            db.close()
            return "User not found, please register to continue"
        }

        val userCursor = db.rawQuery(
            "SELECT * FROM users WHERE email = ? AND password = ?",
            arrayOf(email, password)
        )

        val message: String = if (userCursor.count > 0) {
            "success"
        } else {
            "Incorrect password"
        }

        userCursor.close()
        db.close()

        return message
    }


}