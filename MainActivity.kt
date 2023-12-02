package com.example.firstapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Registration form
        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPassword: EditText = findViewById(R.id.user_password)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || email == "" || password == "")
                Toast.makeText(this, "Не все поля заполнены.", Toast.LENGTH_LONG).show()
            else {
                val user = User(login, email, password)

                val db = DbHelper(this, null)
                if (db.checkUserLogin(login)) {
                    Toast.makeText(this, "Пользователь с таким именем уже существует.", Toast.LENGTH_LONG).show()
                }
                else if (db.checkUserEmail(email)) {
                    Toast.makeText(this, "Пользователь с таким email уже существует.", Toast.LENGTH_LONG).show()
                }
                else {
                    db.addUser(user)
                    Toast.makeText(this, "Пользователь $login был добавлен.", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userEmail.text.clear()
                    userPassword.text.clear()
                }
            }
        }

    }
}