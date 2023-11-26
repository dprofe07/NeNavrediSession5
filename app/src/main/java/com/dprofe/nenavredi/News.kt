package com.dprofe.nenavredi

class News(val title: String, val date: String, val text: String) {
    companion object {
        fun fromString(s: String): News? {
            val lst = s.split("|");
            if (lst.size != 3)
                return null
            return News(lst[0], lst[1], lst[2]);
        }
    }
}