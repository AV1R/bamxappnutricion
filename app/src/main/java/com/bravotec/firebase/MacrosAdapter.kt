package com.bravotec.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.json.JSONArray

class MacrosAdapter(var jsonArray: JSONArray, var listener: MacroNutriActivity): RecyclerView.Adapter<MacrosAdapter.MacrosViewHolder>() {

    //1era cosa que hay que hacer
    //View Holde
    // es similar al binding: un objeto con referencia a los elementos de una lista.

    class MacrosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var texto1_M: TextView
        var texto2_M : TextView
        var img_M : ImageView

        init{
            texto1_M=itemView.findViewById(R.id.rowtxt1Macros)
            texto2_M=itemView.findViewById(R.id.rowtxt2Macros)
            img_M=itemView.findViewById(R.id.imageViewM)


        }
    }
    //momento de creacion de una vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacrosViewHolder {
//igual que con fragmentos inflamos la view
        val view= LayoutInflater.from(parent.context).inflate(R.layout.rowmacros,parent,false)
        view.setOnClickListener(listener)

        return MacrosViewHolder(view)
    }
    //momento de asociacion de vista con datos
    override fun onBindViewHolder(holder: MacrosViewHolder, position: Int) {
        // Log.wtf("POSITION","$position")

        val actual=jsonArray.getJSONObject(position)
        holder.texto1_M.text=actual.getString("ingrediente")
        holder.texto2_M.text=actual.getString("calorias")
        Picasso.get()
            .load("${actual.getString("img")}")
            .placeholder(R.mipmap.load)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.img_M);
    }

    //obtener la cantidad de datos a mostrar
    override fun getItemCount(): Int {
        return jsonArray.length()
    }

}