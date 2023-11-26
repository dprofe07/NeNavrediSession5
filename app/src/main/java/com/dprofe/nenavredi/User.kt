package com.dprofe.nenavredi

import android.content.Context

class User(val login: String, var password: String, val name: String, var phone: String, var email: String, val dateOfBirth: String) {

    override fun toString(): String {
        return "$login|$password|$name|$phone|$email|$dateOfBirth"
    }
    companion object {
        fun loadUsersListFromResources(ctx: Context): List<User> {
            val res = mutableListOf<User>();
            for(s in ctx.resources.getStringArray(R.array.baseUsers)) {
                val data = s.split("|");
                if (data.size != 6) continue
                res.add(User(data[0], data[1], data[2], data[3], data[4], data[5]))
            }
            return res
        }

        fun loadUsersListFromSharedPreferences(ctx: Context): List<User> {
            val prefs = ctx.getSharedPreferences("users", Context.MODE_PRIVATE)
            val res = mutableListOf<User>();
            for(s in prefs.getStringSet("users", null) ?: listOf()) {
                val data = s.split("|");
                if (data.size != 6) continue
                res.add(User(data[0], data[1], data[2], data[3], data[4], data[5]))
            }
            return res
        }

        fun saveUsersList(ctx: Context, users: List<User>) {
            ctx.getSharedPreferences("users", Context.MODE_PRIVATE).edit().let { edt ->
                val res = mutableListOf<String>()
                users.forEach {
                    res.add(it.toString())
                }
                edt.putStringSet("users", res.toSet())
                edt.apply()
            }
        }

        fun checkUsersListExistenceInSharedPreferences(ctx: Context): Boolean = ctx.getSharedPreferences("users", Context.MODE_PRIVATE).contains("users")


        fun authorised(ctx: Context): Boolean = ctx.getSharedPreferences("users", Context.MODE_PRIVATE).let { it.contains("savedLogin") && it.contains("savedPassword") }

        fun loadUser(ctx: Context): User? {
            val prefs = ctx.getSharedPreferences("users", Context.MODE_PRIVATE)

            return findUser(prefs.getString("savedLogin", null) ?: return null, prefs.getString("savedPassword", null) ?: return null, ctx)
        }

        fun findUser(login: String, password: String, ctx: Context): User? {
            for (usr in loadUsersListFromSharedPreferences(ctx)) {
                if (usr.login == login && usr.password == password)
                    return usr
            }
            return null
        }

        fun saveUser(ctx: Context, usr: User) {
            ctx.getSharedPreferences("users", Context.MODE_PRIVATE).edit().let {
                it.putString("savedLogin", usr.login)
                it.putString("savedPassword", usr.password)
                it.apply()
            }
        }

        fun logout(ctx: Context) {
            ctx.getSharedPreferences("users", Context.MODE_PRIVATE).edit().let {
                it.remove("savedLogin")
                it.remove("savedPassword")
                it.apply()
            }
        }

        fun saveChangedUser(ctx: Context, user: User) {
            val users = loadUsersListFromSharedPreferences(ctx).toMutableList()
            for (i in users.indices) {
                if (users[i].login == user.login) {
                    users[i] = user
                }
            }
            saveUsersList(ctx, users)
        }
    }
}