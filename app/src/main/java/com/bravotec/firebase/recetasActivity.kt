package com.bravotec.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray
import org.json.JSONObject

class recetasActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var database: DatabaseReference
    lateinit var datosJson: JSONArray
    lateinit var recyclerView: RecyclerView
    val lanzador = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val datos = result.data
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetas)
        getData()
    }
    //Funcion para obtener información del usuario
    fun getData(){
        datosJson= JSONArray()
        database= FirebaseDatabase.getInstance().getReference("recipes")
        database.get().addOnSuccessListener{
            //Utilizar un while o for e ir agregando a un json para utilizar la misma logica de la actividad del adapter
            if(it.exists()){
                var cant=it.childrenCount.toInt()
                var i=1
                //Armar Json
                while((cant+1) != i) {
                    val actual="{'descripcion':'${it.child("$i").child("descripcion").value}','img':'${it.child("$i").child("img").value.toString()}','ingredientes':'${it.child("$i").child("ingredientes").value}','tiempo':'${it.child("$i").child("tiempo").value}','titulo':'${it.child("$i").child("titulo").value}','vegano':'${it.child("$i").child("vegano").value}'}"
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
        val adapter=RecAdapter(datosJson,this)
        //GUI
        recyclerView=findViewById(R.id.recyclerView_Recs)

        val llm= LinearLayoutManager(this)
//orientacion de la pantalla (HORIZONTAL, VERTICAL)
        llm.orientation= LinearLayoutManager.VERTICAL
        //terminamos asignando al recicler view referencias a objetos necesarios
        recyclerView.layoutManager=llm
        recyclerView.adapter=adapter
    }

    override fun onClick(row: View) {
        //Con Jason array
        val position=recyclerView.getChildLayoutPosition(row)
        //MANDAR A OTRA ACTIVIDAD CON DATOS
        val intent = Intent(this, showRec::class.java)
        intent.putExtra("datosJsonRecs", datosJson[position].toString())
        lanzador.launch(intent)


    }
}