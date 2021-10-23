package com.nala.momenkpk;

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.holder.SekolahViewHolder
import com.nala.momenkpk.model.ModelSekolah

class SekolahAdapter(private val context: Context,
              private val daftarSekolah: ArrayList<ModelSekolah>?
) : RecyclerView.Adapter<SekolahViewHolder>() {

    private val sekolahListener: FirebaseDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SekolahViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sekolah, parent, false)
        return SekolahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SekolahViewHolder, position: Int) {


        holder.namaSekolah.text = (daftarSekolah?.get(position)?.namaSekolah)
        holder.namaKepsek.text = (daftarSekolah?.get(position)?.namaKepsek)
        holder.view.setOnClickListener { sekolahListener.onDataClick(daftarSekolah?.get(position), position) }

    }

    override fun getItemCount(): Int {
        return daftarSekolah?.size!!
    }


    interface FirebaseDataListener {
        fun onDataClick(instrumen: ModelSekolah?, position: Int)
    }

    init {
        sekolahListener = context as FirebaseDataListener
    }
}