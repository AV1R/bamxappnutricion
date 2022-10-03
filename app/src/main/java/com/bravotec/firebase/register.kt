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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.model.Values.isNumber
import com.google.firebase.ktx.Firebase

class register : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var name: EditText
    lateinit var age: EditText
    val lanzador = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    }

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById(R.id.email_r)
        password=findViewById(R.id.password_r)
        name = findViewById(R.id.name_r)
        age=findViewById(R.id.age_r)

        dbRef= FirebaseDatabase.getInstance().getReference("users")

    }
    fun goMenu(){
        //Ir a menu
        val intent = Intent(this, homepage::class.java)
        lanzador.launch(intent)
    }
    fun register(view:View?){
        var validateForm = false
        var emailStr=email.text.toString()
        var passStr=password.text.toString()
        var nameStr=name.text.toString()
        var ageStr=age.text.toString()

        if(emailStr.isEmpty()){
            email.error="Ingrese un correo valido"
            validateForm=false
        }else if(nameStr.isEmpty()){
            name.error="Ingrese un nombre valido"
            validateForm=false
        }else if(ageStr.isEmpty()){
                age.error="Ingrese una edad"
                validateForm=false
        }else if((ageStr.toInt() > 99) or (ageStr.toInt() < 12)) {
                age.error = "Ingrese una edad valida"
                validateForm=false
        }else if(isNumber(nameStr)){
            name.error = "Ingrese un nombre valido"
            validateForm=false
        }else{
            validateForm=true
        }

        if(validateForm){
        var authTask=Firebase.auth.createUserWithEmailAndPassword(emailStr,passStr)
        authTask.addOnCompleteListener(this){resultado->
            if(resultado.isSuccessful){
                saveUser()
                goMenu()
                Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Error en registro",Toast.LENGTH_SHORT).show()
                Log.wtf("FIREBASE-DEV","error: ${resultado.exception}")

            }
        }
        }else{
            Toast.makeText(this,"Campos vacios",Toast.LENGTH_LONG).show()
        }
        //Si el usuario fue creado usar aqui firestore para crear una nueva coleccion con
        //datos de usuario ingresado
    }
    //Funciones de navegación
    fun goBack(view: View?){
        finish()
    }

    private fun saveUser(){

        var emailStr=email.text.toString()
        var nameStr=name.text.toString()
        var ageStr=age.text.toString()



            val userId = Firebase.auth.currentUser?.uid.toString()

            val user = UsersModel(userId, nameStr, ageStr, emailStr)
            dbRef.child(userId).setValue(user)
                .addOnCanceledListener {
                    Toast.makeText(this, "Data Inserted Succeful", Toast.LENGTH_LONG).show()
                    email.text.clear()
                    password.text.clear()
                    name.text.clear()
                    age.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

    }

    fun isNumber(s: String): Boolean {
        return when(s.toIntOrNull())
        {
            null -> false
            else -> true
        }
    }
}