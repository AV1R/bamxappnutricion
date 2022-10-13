package com.bravotec.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.json.JSONArray


class RecAdapter(var jsonArray: JSONArray, var listener: recetasActivity): RecyclerView.Adapter<RecAdapter.RecViewHolder>() {

    //1era cosa que hay que hacer
    //View Holde
    // es similar al binding: un objeto con referencia a los elementos de una lista.

    class RecViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var texto1_R: TextView
        var texto2_R : TextView
        var img_R : ImageView

        init{
            texto1_R=itemView.findViewById(R.id.rowtxt1)
            texto2_R=itemView.findViewById(R.id.rowtxt2)
            img_R=itemView.findViewById(R.id.imageViewRec)



        }
    }
    //momento de creacion de una vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
//igual que con fragmentos inflamos la view
        val view= LayoutInflater.from(parent.context).inflate(R.layout.rowrecs,parent,false)
        view.setOnClickListener(listener)
        /*val viewHolder=PerritoViewHolder(view)
        viewHolder.boton.setOnClickListener{
            Log.wtf("hola","Como estas desde perrito adapter")
        }*/
        return RecViewHolder(view)
    }
    //momento de asociacion de vista con datos
    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        // Log.wtf("POSITION","$position")

        val actual=jsonArray.getJSONObject(position)
        holder.texto1_R.text=actual.getString("titulo")
        holder.texto2_R.text=actual.getInt("tiempo").toString() + " minutos"
        Picasso.get()
            .load("${actual.getString("img")}")
            .placeholder(R.mipmap.load)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.img_R);
    }

    //obtener la cantidad de datos a mostrar
    override fun getItemCount(): Int {
        return jsonArray.length()
    }

}