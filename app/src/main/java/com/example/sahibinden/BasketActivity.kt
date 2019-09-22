package com.example.sahibinden

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import kotlinx.android.synthetic.main.activity_basket.*

class BasketActivity : AppCompatActivity() {

    lateinit var myAdaptor : MyAdapter
    var allItems= mutableListOf<itemDataModel>()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        logout.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            // Set the alert dialog title
            builder.setTitle("Logout")

            // Display a message on alert dialog
            builder.setMessage("Are you sure you want to logout ?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES"){dialog, which ->
                // SheredPre... verilerinin silinmesi ve outo giriş iptali
                Toast.makeText(applicationContext,"Ok, exiting.",Toast.LENGTH_SHORT).show()
                val prefences = getSharedPreferences("veri", Context.MODE_PRIVATE)
                var editor = prefences.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


            // Display a negative button on alert dialog
            builder.setNegativeButton("No"){dialog,which ->
                Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
            }


            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel"){_,_ ->
                Toast.makeText(applicationContext,"You cancelled the dialog.", Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()


        }

        dataSource()
        myAdaptor= MyAdapter(allItems)
    }

    @SuppressLint("WrongConstant")
    fun dataSource() {

        val url = "http://kariyertechchallenge.mockable.io/"

        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                for (item in 0 until response.length()) {
                    val date=response.getJSONObject(item).getString("date")
                    val month=response.getJSONObject(item).getString("month")
                    val marketName=response.getJSONObject(item).getString("marketName")
                    val orderName=response.getJSONObject(item).getString("orderName")
                    val productPrice=response.getJSONObject(item).getString("productPrice")
                    val productState=response.getJSONObject(item).getString("productState")
                    var orderDetail=response.getJSONObject(0).getJSONObject("productDetail").getString("orderDetail")
                    var summaryPrice=response.getJSONObject(0).getJSONObject("productDetail").getString("summaryPrice")

                    val data=itemDataModel(date,convertMonth(month),marketName,orderName(orderName),
                        "$productPrice TL",productState,orderDetail,summaryPrice)
                    //Log.d("TAG", "Response: $data")
                    allItems.add(data)
                }

                recyclerView.adapter=myAdaptor
                recyclerView.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false)

                //Log.d("TAG","Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                Log.d("TAG","Response: %s",error)
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    // gelen ay sayısal değerlerini çevirdik
    fun convertMonth(month:String):String?{
        var data:String?=null
        when (month) {
            "01" -> data="Ocak"
            "02" -> data="Şubat"
            "03" -> data="Mart"
            "04" -> data="Nisan"
            "05" -> data="Mayıs"
            "06" -> data="Haziran"
            "07" -> data="Temmuz"
            "08" -> data="Ağustos"
            "09" -> data="Eylül"
            "10" -> data="Ekim"
            "11" -> data="Kasım"
            "12" -> data="Aralık"
            else -> {
                data="Deger Yok"
            }
        }
        return data
    }

    // order name ismi 18 karakterden uzunsa  kesip ... ekledim.
    fun orderName(orderName:String):String{
        var new:String=orderName
        if(orderName.length>18){
            new =orderName.substring(0, 18)
            new= "$new..."

        }
        return new
    }
}
