package com.example.firstapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, val factory:SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id TNT PRIMARY KEY, login TEXT, email TEXT, password TEXT)"
        // !! для обработки возможного значения db = NULL
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser (user: User) {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase // We need to WRITE
        db.insert("users", null, values)

        db.close()
    }

    @SuppressLint("Recycle")
    fun getUser(login: String, password: String): Boolean {
        val db = this.readableDatabase // We need to READ

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)
        return result.moveToFirst() // Values of return (True/ False). If this user if their data exists here
    }

    @SuppressLint("Recycle")
    fun checkUserLogin(login: String): Boolean {
        val db = this.readableDatabase // We need to READ

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login'", null)
        return result.moveToFirst() // Values of return (True/ False). If this user if their data exists here
    }

    @SuppressLint("Recycle")
    fun checkUserEmail(email: String): Boolean {
        val db = this.readableDatabase // We need to READ

        val result = db.rawQuery("SELECT * FROM users WHERE email = '$email'", null)
        return result.moveToFirst() // Values of return (True/ False). If this user if their data exists here
    }

}