package com.bravotec.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    val lanzador = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = findViewById(R.id.email_l)
        password=findViewById(R.id.password_l)

    }
    //Funciones de navegación
    fun goRegister(view: View?){
        val intent = Intent(this, register::class.java)
        lanzador.launch(intent)
    }
    fun goMenu(){
        //Ir a menu
        val intent = Intent(this, homepage::class.java)
        intent.putExtra("nombre", email.text.toString())
        lanzador.launch(intent)
    }
    fun login(view:View?){
        var emailStr=email.text.toString()
        var passStr=password.text.toString()


        if(emailStr.isEmpty()){
            email.error="Ingrese un correo valido"
        }
        if(passStr.isEmpty()){
            password.error="Ingrese una contraseña valido"
        }
        if(!emailStr.isEmpty() and !passStr.isEmpty()) {
            var authTask = Firebase.auth.signInWithEmailAndPassword(emailStr, passStr)
            authTask.addOnCompleteListener(this) { resultado ->
                if (resultado.isSuccessful) {
                    goMenu()
                    Toast.makeText(this, "Iniciaste sesión", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Error al inicio sesión", Toast.LENGTH_SHORT).show()
                    Log.wtf("FIREBASE-DEV", "error: ${resultado.exception}")

                }
            }
        }else{
            Toast.makeText(this,"Campos vacios",Toast.LENGTH_LONG).show()
        }
    }


}