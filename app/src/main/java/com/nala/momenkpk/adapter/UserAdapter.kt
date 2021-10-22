package com.nala.momenkpk.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.R
import com.nala.momenkpk.holder.PartisipanHolder
import com.nala.momenkpk.model.ModelPartisipan

class UserAdapter (private val context: Context, private val daftarPartisipan: ArrayList<ModelPartisipan>?
) : RecyclerView.Adapter<PartisipanHolder>() {

    private val listen: FirebaseDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartisipanHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partisipan, parent, false)
        return PartisipanHolder(view)

    }

    override fun onBindViewHolder(holder: PartisipanHolder, position: Int) {

        val color = Color.WHITE
        holder.view.setCardBackgroundColor(color)
        holder.uEmail.text = "Email  : " + (daftarPartisipan?.get(position)?.email)
        holder.uName.text = "Username  : " + (daftarPartisipan?.get(position)?.username)
        holder.uSekolah.text = "Sekolah      : " + (daftarPartisipan?.get(position)?.sekolah)
        holder.uRole.text = "Role            : " + (daftarPartisipan?.get(position)?.role)
        holder.view.setOnClickListener { listen.onDataClick(daftarPartisipan?.get(position), position) }

    }

    override fun getItemCount(): Int {
        return daftarPartisipan?.size!!
    }

    //interface data listener
    interface FirebaseDataListener {
        fun onDataClick(partisipan: ModelPartisipan?, position: Int)
    }

    init {
        listen = context as FirebaseDataListener
    }
}