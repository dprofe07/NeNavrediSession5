package com.dprofe.nenavredi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var txtFIO: TextView
    private lateinit var txtDateOfBirth: TextView
    private lateinit var txtAge: TextView
    private lateinit var txtPhone: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPassword: TextView

    private lateinit var btnSave: Button
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val user = User.loadUser(this)
        if (user == null) {
            finish()
            return
        }

        txtFIO = findViewById(R.id.profile_txtFIO)
        txtDateOfBirth = findViewById(R.id.profile_txtDateOfBirth)
        txtAge = findViewById(R.id.profile_txtAge)
        txtPhone = findViewById(R.id.profile_txtPhone)
        txtEmail = findViewById(R.id.profile_txtEmail)
        txtPassword = findViewById(R.id.profile_txtPassword)

        btnSave = findViewById(R.id.profile_btnSave)
        btnLogout = findViewById(R.id.profile_btnLogout)

        btnSave.setOnClickListener {
            user.email = txtEmail.text.toString()
            user.phone = txtPhone.text.toString()
            user.password = txtPassword.text.toString().let { if (it == "") user.password else it }
            User.saveChangedUser(this, user)
        }
        btnLogout.setOnClickListener {
            User.logout(this)
            finish()
        }

        txtFIO.text = user.name
        txtDateOfBirth.text = user.dateOfBirth
        txtAge.text = (Calendar.getInstance().get(Calendar.YEAR) - user.dateOfBirth.split('.')[2].toInt()).toString()
        txtPhone.text = user.phone
        txtEmail.text = user.email
    }
}