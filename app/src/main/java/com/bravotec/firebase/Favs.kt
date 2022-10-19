package com.bravotec.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject

class Favs : AppCompatActivity() , View.OnClickListener {
    private lateinit var database: DatabaseReference

    lateinit var datosJson: JSONArray
    lateinit var recyclerView: RecyclerView
    lateinit var datosActual: JSONArray
    val lanzador = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // 1ero - revisar código
        if(result.resultCode == Activity.RESULT_OK) {
            val datos = result.data
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs)
        var currentId= Firebase.auth.currentUser?.uid.toString()
        getData(currentId)
    }

    //Funcion para obtener información del usuario
    fun getData(currentUser:String){
        datosJson=JSONArray()
        database= FirebaseDatabase.getInstance().getReference("favs")
        database.child(currentUser).get().addOnSuccessListener{
            //Utilizar un while o for e ir agregando a un json para utilizar la misma logica de la actividad del adapter
            if(it.exists()){
                var cant=it.childrenCount.toInt()
                var i=1
                //Armar Json
                while((cant+1) != i) {
                    val actual="{'descripcion':'${it.child("$i").child("descripcion").value}','img':'${it.child("$i").child("img").value.toString()}','ingredientes':'${it.child("$i").child("ingredientes").value}','tiempo':'${it.child("$i").child("tiempo").value}','titulo':'${it.child("$i").child("titulo").value}','vegano':'${it.child("$i").child("vegano").value}','id':'${it.child("$i").child("id").value}'}"
                    val jsonObj= JSONObject(actual)
                    datosJson.put(jsonObj)
                    i++ // Actualizamos la condicion
                }
                renderdata()

            }else{
                Toast.makeText(this,"Data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
        }


    }

    fun renderdata(){

//ADAPTER
        // val adapter=PerritoAdapter(datos,this)
        val adapter=FavsAdapter(datosJson,this)
        //GUI
        recyclerView=findViewById(R.id.recyclerView_Favs)

        val llm= LinearLayoutManager(this)
        llm.orientation= LinearLayoutManager.VERTICAL
        //terminamos asignando al recicler view referencias a objetos necesarios
        recyclerView.layoutManager=llm
        recyclerView.adapter=adapter
    }

    override fun onClick(row: View) {
        //Con Jason array
        val position=recyclerView.getChildLayoutPosition(row)
        //MANDAR A OTRA ACTIVIDAD CON DATOS
        val intent = Intent(this, showFavs::class.java)
        intent.putExtra("datosJsonFavs", datosJson[position].toString())
        lanzador.launch(intent)


    }



}