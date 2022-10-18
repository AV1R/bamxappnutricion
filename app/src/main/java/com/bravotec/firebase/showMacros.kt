package com.bravotec.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

class showMacros : AppCompatActivity() {
    private lateinit var nombre : String
    private lateinit var anio : String
    private lateinit var img : String
    lateinit var datosRecibidos: JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_macros)
        datosRecibidos = JSONArray()
        val jsonTest3 = intent.getStringExtra("datosJsonMacros")
        val objeto = JSONObject(jsonTest3)
        datosRecibidos.put(objeto)
        val actual = datosRecibidos.getJSONObject(0)
        nombre = actual.getString("ingrediente")
        anio = actual.getString("carbohidratos")
        img = actual.getString("img")

        val nombre_e = findViewById<TextView>(R.id.show_title)
        nombre_e.text = "Nombre: " + nombre


        val anio_e = findViewById<TextView>(R.id.show_time)
        anio_e.text="Carbos: " + anio

        val img_e = findViewById<TextView>(R.id.show_img)
        img_e.text="URL: " + img
//GENERAR EL LAYOUT BIEN CON SUS CONSTRAINS Y ASIGNAR LOS VALORES NECESARIOS DESDE AQUI
    }
}