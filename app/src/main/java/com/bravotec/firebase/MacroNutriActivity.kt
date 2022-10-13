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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class MacroNutriActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var database: DatabaseReference
    private lateinit var descDb : TextView
    private lateinit var imgDb : TextView
    private lateinit var ingreDb : TextView
    private lateinit var tiempoDb : TextView
    private lateinit var tituloDb : TextView
    private lateinit var vegDb : TextView
    lateinit var datosJson: JSONArray
    lateinit var recyclerView: RecyclerView
    val lanzador = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Toast.makeText(this, "RESULTADO VA AQUI", Toast.LENGTH_SHORT).show()
        // 1ero - revisar código
        if(result.resultCode == Activity.RESULT_OK) {
            val datos = result.data
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_macro_nutri)
        getData()
    }

    //Funcion para obtener información del usuario
    fun getData(){
        val gson = Gson()
        datosJson= JSONArray()
        database= FirebaseDatabase.getInstance().getReference("macros")
        database.get().addOnSuccessListener{
            //Utilizar un while o for e ir agregando a un json para utilizar la misma logica de la actividad del adapter
            if(it.exists()){
                Log.wtf("Cantidad de datos","${it.childrenCount}")
                var cant=it.childrenCount.toInt()
                var i=1
                //Armar Json
                while((cant+1) != i) {
                    //val jsonTest3="[{'nombre':'avir','edad':20},{'nombre':'ana','edad':21},{'nombre':'rojo','edad':22}]"
                    Log.wtf("RECIBE RECETAS", "${it.child("$i").child("descripcion").value}")
                    val actual="{'calorias':'${it.child("$i").child("calorias").value}','carbohidratos':'${it.child("$i").child("carbohidratos").value}','grasas':'${it.child("$i").child("grasas").value}','img':'${it.child("$i").child("img").value.toString()}','ingrediente':'${it.child("$i").child("ingrediente").value}','proteina':'${it.child("$i").child("proteina").value}'}"
                    val jsonObj= JSONObject(actual)
                    datosJson.put(jsonObj)
                    i++ // Actualizamos la condicion
                }
                Log.wtf("DATOS EN JSON ARRAY:","$datosJson")
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
        val adapter=MacrosAdapter(datosJson,this)
        //GUI
        recyclerView=findViewById(R.id.recyclerView_Macros)

        //es necesario utilizar un layout manager
        //layout manager es una clase que define como se van a desplegar
        //los items en el recycler view
        val llm= LinearLayoutManager(this)
//orientacion de la pantalla (HORIZONTAL, VERTICAL)
        llm.orientation= LinearLayoutManager.VERTICAL
        val glm= GridLayoutManager(this,2)
        //terminamos asignando al recicler view referencias a objetos necesarios
        recyclerView.layoutManager=llm
        recyclerView.adapter=adapter
    }

    override fun onClick(row: View) {
        //Con Jason array
        val position=recyclerView.getChildLayoutPosition(row)
        //MANDAR A OTRA ACTIVIDAD CON DATOS
        val intent = Intent(this, showMacros::class.java)
        intent.putExtra("datosJsonMacros", datosJson[position].toString())
        lanzador.launch(intent)


    }
}