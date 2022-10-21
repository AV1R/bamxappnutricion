package com.bravotec.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class showMacros : AppCompatActivity() {
    private lateinit var nombre : String
    private lateinit var carb : String
    private lateinit var prot : String
    private lateinit var gras : String
    private lateinit var imgIn : ImageView
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
        carb = actual.getString("carbohidratos")
        prot = actual.getString("proteina")
        gras = actual.getString("grasas")
        imgIn=findViewById(R.id.imageViewRec)
        Picasso.get()
            .load("${actual.getString("img")}")
            .placeholder(R.mipmap.load)
            .error(R.drawable.ic_launcher_foreground)
            .into(imgIn);

        val nombre_e = findViewById<TextView>(R.id.show_title)
        nombre_e.text = nombre

        val carb_e = findViewById<TextView>(R.id.show_carb)
        carb_e.text="Carbohidratos: " + carb

        val prot_e = findViewById<TextView>(R.id.show_prot)
        prot_e.text = "Proteinas: " + prot

        val gras_e = findViewById<TextView>(R.id.show_gras)
        gras_e.text = "Grasas: " + gras
    }
}