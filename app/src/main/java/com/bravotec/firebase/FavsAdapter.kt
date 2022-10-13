package com.bravotec.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class FavsAdapter(var jsonArray: JSONArray, var listener: Favs): RecyclerView.Adapter<FavsAdapter.FavsViewHolder>() {

    //1era cosa que hay que hacer
    //View Holde
    // es similar al binding: un objeto con referencia a los elementos de una lista.

    class FavsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val texto1:TextView
        val texto2 : TextView
        //var boton:Button

        init{
            texto1=itemView.findViewById(R.id.rowtxt1Cal)
            texto2=itemView.findViewById(R.id.rowtxt2Cal)
            // boton=itemView.findViewById(R.id.rowbutton)


        }
    }
    //momento de creacion de una vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {
//igual que con fragmentos inflamos la view
        val view= LayoutInflater.from(parent.context).inflate(R.layout.rowfavs,parent,false)
        view.setOnClickListener(listener)
        /*val viewHolder=PerritoViewHolder(view)
        viewHolder.boton.setOnClickListener{
            Log.wtf("hola","Como estas desde perrito adapter")
        }*/
        return FavsViewHolder(view)
    }
    //momento de asociacion de vista con datos
    override fun onBindViewHolder(holder: FavsViewHolder, position: Int) {
        // Log.wtf("POSITION","$position")

        val actual=jsonArray.getJSONObject(position)
        holder.texto1.text=actual.getString("titulo")
        holder.texto2.text=actual.getInt("tiempo").toString()

    }

    //obtener la cantidad de datos a mostrar
    override fun getItemCount(): Int {
        return jsonArray.length()
    }

}