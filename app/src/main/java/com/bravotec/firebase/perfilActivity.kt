package com.bravotec.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class perfilActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var name : TextView
    private lateinit var email : TextView
    private lateinit var age : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        var currentId=Firebase.auth.currentUser?.uid.toString()
        getData(currentId)

    }
    //Funcion para obtener información del usuario
    fun getData(currentUser:String){
        database= FirebaseDatabase.getInstance().getReference("users")
        database.child(currentUser).get().addOnSuccessListener{
            if(it.exists()){
                var nameDb=it.child("userName").value
                val ageDb=it.child("userAge").value
                val emailDb=it.child("userEmail").value
                name=findViewById(R.id.name)
                email=findViewById(R.id.email)
                age=findViewById(R.id.age)
                name.text=nameDb.toString()
                email.text=emailDb.toString()
                age.text="${ageDb.toString()} años"

            }else{
                Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }

    fun logout(view: View?){
        Firebase.auth.signOut()
        finish()
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"Cerraste sesion", Toast.LENGTH_SHORT).show()
    }

    fun resetPassword(view:View?){
        var auth=FirebaseAuth.getInstance()
        var currentEmail=Firebase.auth.currentUser?.email.toString()
        auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(currentEmail)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    Toast.makeText(this@perfilActivity,"El correo para restablecer tu contraseña se envio correctamente",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@perfilActivity,"Hubo un error al enviar el correo para restablecer tu contraseña",Toast.LENGTH_LONG).show()

                }
            }
    }
}