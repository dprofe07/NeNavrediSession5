package com.dprofe.nenavredi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class AuthActivity : AppCompatActivity() {
    private lateinit var btnAuth: Button
    private lateinit var txtLogin: TextView
    private lateinit var txtPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        txtLogin = findViewById(R.id.auth_txtLogin)
        txtPassword = findViewById(R.id.auth_txtPassword)
        btnAuth = findViewById(R.id.auth_btnAuth)

        btnAuth.setOnClickListener {
            val user = User.findUser(txtLogin.text.toString(), txtPassword.text.toString(), this)
            if (user == null) {
                AlertDialog.Builder(this)
                    .setTitle("Неверный логин или пароль")
                    .setPositiveButton("Ок") {it, _ -> it.dismiss()}
                    .show()
            } else {
                User.saveUser(this, user)
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        if (User.loadUser(this).let {User.findUser(it?.login ?: return@let null, it.password, this)} != null) {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}