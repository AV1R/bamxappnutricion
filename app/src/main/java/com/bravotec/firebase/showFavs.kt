package com.bravotec.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class showFavs : AppCompatActivity() {
    private lateinit var titulo : String
    private lateinit var tiempo : String
    private lateinit var imgIn : ImageView
    private lateinit var ingre : String
    private lateinit var veg : String
    private lateinit var desc : String
    private lateinit var id : String
    lateinit var datosRecibidos: JSONArray
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_favs)
            datosRecibidos = JSONArray()
            val jsonTest3 = intent.getStringExtra("datosJsonFavs")
            val objeto = JSONObject(jsonTest3)
            datosRecibidos.put(objeto)
            val actual = datosRecibidos.getJSONObject(0)
       // Log.wtf("Actual: ","${actual}")
           id= actual.getString("id")
            titulo = actual.getString("titulo")
            tiempo = actual.getString("tiempo")
            imgIn=findViewById(R.id.imageViewF)
            Picasso.get()
                .load("${actual.getString("img")}")
                .placeholder(R.mipmap.load)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgIn);
            ingre = actual.getString("ingredientes")
            veg = actual.getString("vegano")
            desc = actual.getString("descripcion")

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



    fun removeFavs(view: View?){
        Log.wtf("Entra","RemoveFavs")
        var currentId = Firebase.auth.currentUser?.uid.toString()

                database= FirebaseDatabase.getInstance().getReference("favs")
                database.child(currentId).child("${id}").removeValue().addOnSuccessListener{
                    Toast.makeText(this,"Se elimino",Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this,"No se elimino",Toast.LENGTH_SHORT).show()
}
                //subir
               /* database= FirebaseDatabase.getInstance().getReference("favs")
                database.child(currentId).child("${cant+1}").removeValue().addOnSuccessListener{
                    Toast.makeText(this, "Se elimino de favoritos", Toast.LENGTH_LONG).show()
                    //bloquear textview para que ya no se pueda agregar a favoritos
                    val textFavs=findViewById<TextView>(R.id.addFavs)
                    textFavs.text=" "
                    textFavs.isEnabled=true
                    val textonFavs=findViewById<TextView>(R.id.onFavs)
                    textonFavs.text="En favoritos"
                    textFavs.isEnabled=false
//agregar variable a base de datos de recetas donde diga si esta o no en favs para no volver a mostrar el boton una ve que se actualice
//TENGO QUE USAR SAVEREFS DEBIDO A QUE LOS DATOS DE LAS RECETAS COMO TAL NO ESTAN GUARDADOS MEDIANTE ID
                    //USAR UNA VARIABLE O INDICADOR PARA VERIFICAR SI ESTA GUARDADO
//Y ASIGNARLA DESDE AQUI




                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
*/




    }

}