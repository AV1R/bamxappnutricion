package com.bravotec.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.json.JSONArray

class CalAdapter(var jsonArray: JSONArray, var listener: InfoNutriActivity): RecyclerView.Adapter<CalAdapter.CalViewHolder>() {

    //1era cosa que hay que hacer
    //View Holde
    // es similar al binding: un objeto con referencia a los elementos de una lista.

    class CalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var texto1_C: TextView
        var texto2_C : TextView
        var img_C : ImageView

        //var boton:Button

        init{
            texto1_C=itemView.findViewById(R.id.rowtxt1Cal)
            texto2_C=itemView.findViewById(R.id.rowtxt2Cal)
            img_C=itemView.findViewById(R.id.imageViewSR)


        }
    }
    //momento de creacion de una vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalViewHolder {
//igual que con fragmentos inflamos la view
        val view= LayoutInflater.from(parent.context).inflate(R.layout.rowcal,parent,false)
        view.setOnClickListener(listener)
        /*val viewHolder=PerritoViewHolder(view)
        viewHolder.boton.setOnClickListener{
            Log.wtf("hola","Como estas desde perrito adapter")
        }*/
        return CalViewHolder(view)
    }
    //momento de asociacion de vista con datos
    override fun onBindViewHolder(holder: CalViewHolder, position: Int) {
        // Log.wtf("POSITION","$position")

        val actual=jsonArray.getJSONObject(position)
        holder.texto1_C.text=actual.getString("ingrediente")
        holder.texto2_C.text=actual.getString("calorias")
        Picasso.get()
            .load("${actual.getString("img")}")
            .placeholder(R.mipmap.load)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.img_C);

    }

    //obtener la cantidad de datos a mostrar
    override fun getItemCount(): Int {
        return jsonArray.length()
    }

}