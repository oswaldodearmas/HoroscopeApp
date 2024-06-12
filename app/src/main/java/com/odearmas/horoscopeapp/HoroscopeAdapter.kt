package com.odearmas.horoscopeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odearmas.horoscopeapp.model.HoroscopeItem

class HoroscopeAdapter(
    private val dataSet: List<HoroscopeItem>,
    private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<HoroscopeViewHolder>() {

    // Este método se llama para crear nuevas celdas,
    // y se crear las justas para mostrar en pantalla,
    // Ya que intentará reciclar las que no se vean
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horoscope, parent, false)

        return HoroscopeViewHolder(view)
    }

    // Este método simplemente es para decir cuantos elementos queremos mostrar
    override fun getItemCount(): Int {
        return dataSet.size
    }

    // Este método se llama cada vez que se va a visualizar una celda,
    // y lo utilizaremos para mostrar los datos de esa celda
    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = dataSet[position]
        holder.textView1.setText(horoscope.zodiacName)
        holder.textView2.setText(horoscope.date)
        holder.imageView.setImageResource(horoscope.logo)

        holder.itemView.setOnClickListener {
            onItemClickListener(position)
            //Log.i{"ADAPTER", "Hemos hecho click en un elemento ${horoscope.name}"}

        }

    }

}

