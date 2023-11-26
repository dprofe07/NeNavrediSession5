package com.dprofe.nenavredi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rec: RecyclerView
    private lateinit var btnProfile: Button
    private lateinit var btnRegister: Button
    private lateinit var txtUser: TextView
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val news = mutableListOf<News>()
        for (n in resources.getStringArray(R.array.news)) {
            news.add(News.fromString(n) ?: continue);
        }

        rec = findViewById(R.id.main_newsRecycler)
        rec.adapter = NewsRecyclerAdapter(news)
        rec.layoutManager = LinearLayoutManager(this)

        btnProfile = findViewById(R.id.main_btnProfile);
        btnProfile.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        btnRegister = findViewById(R.id.main_btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        txtUser = findViewById(R.id.main_txtUser)

        if (!User.checkUsersListExistenceInSharedPreferences(this)) {
            User.saveUsersList(this, User.loadUsersListFromResources(this))
        }


    }

    override fun onStart() {
        super.onStart()


        currentUser = User.loadUser(this)
        txtUser.text = "Вы зашли как ${currentUser?.login ?: "гость"}"

        if (currentUser == null) {
            btnRegister.visibility = View.VISIBLE
            btnProfile.text = "Войти"
        } else {
            btnRegister.visibility = View.INVISIBLE
            btnProfile.text = "Профиль"
        }
    }
}