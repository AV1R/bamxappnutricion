package com.bravotec.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

class showFavs : AppCompatActivity() {
    private lateinit var nombre : String
    private lateinit var anio : String
    private lateinit var img : String
    lateinit var datosRecibidos: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_favs)
        datosRecibidos = JSONArray()
        val jsonTest3 = intent.getStringExtra("datosJsonFavs")
        val objeto = JSONObject(jsonTest3)
        datosRecibidos.put(objeto)
        val actual = datosRecibidos.getJSONObject(0)
        Log.wtf("JSON", actual.getString("titulo"))
        Log.wtf("JSON", actual.getInt("tiempo").toString())
        nombre = actual.getString("titulo")
        anio = actual.getString("tiempo")
        img = actual.getString("img")

        // Toast.makeText(this, intent.getStringExtra("datos2"), Toast.LENGTH_SHORT).show()
        val nombre_e = findViewById<TextView>(R.id.show_title)
        nombre_e.text = "Nombre: " + nombre


        val anio_e = findViewById<TextView>(R.id.show_time)
        anio_e.text="Tiempo: " + anio

        val img_e = findViewById<TextView>(R.id.show_img)
        img_e.text="URL: " + img

    }
}