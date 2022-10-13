package com.bravotec.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class homepage : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        name=findViewById(R.id.welcome)
        var currentId=Firebase.auth.currentUser?.uid.toString()
         getData(currentId)
    }



    fun verificateUser(){
        if(Firebase.auth.currentUser==null){
            Toast.makeText(this, "Revalida tu sesion!", Toast.LENGTH_SHORT).show()

        }
       /* else{
            Toast.makeText(this, "Usuario: ${Firebase.auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Usuario: ${Firebase.auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
        }*/
    }
    //Función común para verificar validez de usuario en el ciclo de vida
    override fun onStart(){
        super.onStart()
        verificateUser()
    }

    //Funcion para obtener información del usuario
    fun getData(currentUser:String){
        database= FirebaseDatabase.getInstance().getReference("users")
        database.child(currentUser).get().addOnSuccessListener{
            if(it.exists()){
                var nameDb=it.child("userName").value
                val ageDb=it.child("userAge").value
                val emailDb=it.child("userEmail").value
                name=findViewById(R.id.welcome)
                    name.text="Bienvedid@ ${nameDb.toString()}"

            }else{
                Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }

    fun goFavs(view:View?){
        val intent=Intent(this,Favs::class.java)
        startActivity(intent)
    }


    fun goPerfil(view:View?){
        val intent=Intent(this,perfilActivity::class.java)
        startActivity(intent)
    }


    fun goInfoNutri(view:View?){
        val intent=Intent(this,InfoNutriActivity::class.java)
        startActivity(intent)
    }


    fun goMacroNutri(view:View?){
        val intent=Intent(this,MacroNutriActivity::class.java)
        startActivity(intent)
    }


    fun goRecetas(view:View?){
        val intent=Intent(this,recetasActivity::class.java)
        startActivity(intent)
    }
}