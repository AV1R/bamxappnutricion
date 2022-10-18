package com.bravotec.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.json.JSONArray

class FavsAdapter(var jsonArray: JSONArray, var listener: Favs): RecyclerView.Adapter<FavsAdapter.FavsViewHolder>() {

    class FavsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val texto1:TextView
        val texto2 : TextView
        var img_F : ImageView
        init{
            texto1=itemView.findViewById(R.id.rowtxt1Favs)
            texto2=itemView.findViewById(R.id.rowtxt2Favs)
            img_F=itemView.findViewById(R.id.imageViewFavs)


        }
    }
    //momento de creacion de una vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {
//igual que con fragmentos inflamos la view
        val view= LayoutInflater.from(parent.context).inflate(R.layout.rowfavs,parent,false)
        view.setOnClickListener(listener)

        return FavsViewHolder(view)
    }
    //momento de asociacion de vista con datos
    override fun onBindViewHolder(holder: FavsViewHolder, position: Int) {

        val actual=jsonArray.getJSONObject(position)
        holder.texto1.text=actual.getString("titulo")
     //   holder.texto2.text=actual.getInt("tiempo").toString() + " minutos"
        Picasso.get()
            .load("${actual.getString("img")}")
            .placeholder(R.mipmap.load)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.img_F);
    }

    //obtener la cantidad de datos a mostrar
    override fun getItemCount(): Int {
        return jsonArray.length()
    }

}