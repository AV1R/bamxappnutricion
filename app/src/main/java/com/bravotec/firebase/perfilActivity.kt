package com.bravotec.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class perfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
    }

    fun logout(view: View?){
        Firebase.auth.signOut()
        finish()
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"Cerraste sesion", Toast.LENGTH_SHORT).show()
    }
}