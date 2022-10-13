package com.bravotec.firebase

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class showRec : AppCompatActivity() {

    private lateinit var titulo : String
    private lateinit var tiempo : String
    private lateinit var imgIn : ImageView
    private lateinit var ingre : String
    private lateinit var veg : String
    private lateinit var desc : String
    lateinit var datosRecibidos: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_rec)
        datosRecibidos = JSONArray()
        val jsonTest3 = intent.getStringExtra("datosJsonRecs")
        val objeto = JSONObject(jsonTest3)
        datosRecibidos.put(objeto)
        val actual = datosRecibidos.getJSONObject(0)
        titulo = actual.getString("titulo")
        tiempo = actual.getString("tiempo")
        imgIn=findViewById(R.id.imageViewRec)
        Picasso.get()
            .load("${actual.getString("img")}")
            .placeholder(R.mipmap.load)
            .error(R.drawable.ic_launcher_foreground)
            .into(imgIn);
        ingre = actual.getString("ingredientes")
        veg = actual.getString("vegano")
        desc = actual.getString("descripcion")

        // Toast.makeText(this, intent.getStringExtra("datos2"), Toast.LENGTH_SHORT).show()
        val titulo_e = findViewById<TextView>(R.id.show_title)
        titulo_e.text =titulo
        val tiempo_e = findViewById<TextView>(R.id.show_time)
        tiempo_e.text= tiempo + " minutos"
        val ingre_e = findViewById<TextView>(R.id.show_ingre)
        ingre_e.text= ingre
        ingre_e.setMovementMethod(ScrollingMovementMethod())
        val desc_e = findViewById<TextView>(R.id.show_desc)
        desc_e.text= desc
        desc_e.setMovementMethod(ScrollingMovementMethod())
        val veg_e = findViewById<TextView>(R.id.show_veg)
        veg_e.text="Vegano: "+ veg
    }
}