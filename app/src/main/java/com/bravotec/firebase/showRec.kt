package com.bravotec.firebase

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
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
    private lateinit var database: DatabaseReference

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

    fun addFavs(view: View?){
        val cont=1;
        var currentId = Firebase.auth.currentUser?.uid.toString()
        datosRecibidos = JSONArray()
        val jsonTest3 = intent.getStringExtra("datosJsonRecs")
        val objeto = JSONObject(jsonTest3)
        datosRecibidos.put(objeto)
        val actual = datosRecibidos.getJSONObject(0)
        val desc=actual.getString("descripcion")
        val titulo = actual.getString("titulo")
        val tiempo = actual.getString("tiempo").toInt()
        val ingre = actual.getString("ingredientes")
        val veg = actual.getString("vegano")
        val img = actual.getString("img")
        val favItem = FavsModel(desc,img,ingre,tiempo,titulo,veg)

        //Conocer cantidad de datos
        database= FirebaseDatabase.getInstance().getReference("favs")
        database.child(currentId).get().addOnSuccessListener{
            //Utilizar un while o for e ir agregando a un json para utilizar la misma logica de la actividad del adapter
            if(it.exists()){
                var cant=it.childrenCount.toInt()
                //subir
                database= FirebaseDatabase.getInstance().getReference("favs")
                database.child(currentId).child("${cant+1}").setValue(favItem).addOnSuccessListener{
                    Toast.makeText(this, "Se agrego a favoritos", Toast.LENGTH_LONG).show()
                    //bloquear textview para que ya no se pueda agregar a favoritos
                    val textFavs=findViewById<TextView>(R.id.addFavs)
                    textFavs.text=" "
                    textFavs.isEnabled=false
                    val textonFavs=findViewById<TextView>(R.id.onFavs)
                    textonFavs.text="En favoritos"
                    textFavs.isEnabled=true
//agregar variable a base de datos de recetas donde diga si esta o no en favs para no volver a mostrar el boton una ve que se actualice
//TENGO QUE USAR SAVEREFS DEBIDO A QUE LOS DATOS DE LAS RECETAS COMO TAL NO ESTAN GUARDADOS MEDIANTE ID
                    //USAR UNA VARIABLE O INDICADOR PARA VERIFICAR SI ESTA GUARDADO
//Y ASIGNARLA DESDE AQUI

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(this,"Data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
        }




    }



}