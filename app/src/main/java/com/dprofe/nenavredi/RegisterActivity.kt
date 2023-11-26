package com.dprofe.nenavredi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class RegisterActivity : AppCompatActivity() {
    private lateinit var txtLogin: TextView
    private lateinit var txtPassword: TextView
    private lateinit var txtFIO: TextView
    private lateinit var txtPhone: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtDateOfBirth: TextView

    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtLogin = findViewById(R.id.register_txtLogin)
        txtPassword = findViewById(R.id.register_txtPassword)
        txtFIO = findViewById(R.id.register_txtFIO)
        txtPhone = findViewById(R.id.register_txtPhone)
        txtEmail = findViewById(R.id.register_txtEmail)
        txtDateOfBirth = findViewById(R.id.register_dateOfBirth)

        btnRegister = findViewById(R.id.register_btnRegister)
        btnRegister.setOnClickListener {
            if (txtDateOfBirth.text.count { c -> c == '.' } != 2) {
                AlertDialog.Builder(this).setTitle("Неверный формат даты рождения")
                    .setPositiveButton("Ок") { it, _ -> it.dismiss() }.show()
                return@setOnClickListener
            }
            val usr = User(
                txtLogin.text.toString(),
                txtPassword.text.toString(),
                txtFIO.text.toString(),
                txtPhone.text.toString(),
                txtEmail.text.toString(),
                txtDateOfBirth.text.toString()
            )
            User.saveUsersList(this, User.loadUsersListFromSharedPreferences(this).let {
                val l = it.toMutableList()
                l.add(usr)
                l.toList()
            })
            User.saveUser(this, usr)
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}