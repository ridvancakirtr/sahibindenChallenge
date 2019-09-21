package com.example.sahibinden

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.login_screen.*

class MainActivity : AppCompatActivity() {
    private val user="admin"  //"kariyer"
    private val pass="admin"  //"2019ADev"

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        val prefences = getSharedPreferences("veri", Context.MODE_PRIVATE)
        val preUsername=prefences.getString("KEY_USER","0")
        val prePass=prefences.getString("KEY_PASS","0")

        //SharedPreferences den veri ile yukarıda tanımlanan user pass değerlerinin karşılaştırılması
        if (user==preUsername.toString() && pass==prePass.toString()) {
            val intent = Intent(this@MainActivity, BasketActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@MainActivity, "Welcome to Basket Page", Toast.LENGTH_SHORT).show()
        }


        sign_in.setOnClickListener {
            if (username.text.toString()==user && password.text.toString()==pass) {
                if (rememberme.isChecked) {
                    val editor = prefences.edit()
                    editor.putString("KEY_USER", user)
                    editor.putString("KEY_PASS", pass)
                    editor.apply()
                    Log.d("TAG", "TRUE")
                }

                val intent = Intent(this@MainActivity, BasketActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this@MainActivity, "\n" + "username or password incorrect", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
