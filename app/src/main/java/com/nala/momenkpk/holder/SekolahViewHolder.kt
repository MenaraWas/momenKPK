package com.nala.momenkpk.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.R

class SekolahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    @JvmField
    var namaSekolah: TextView

    @JvmField
    var namaKepsek: TextView
    @JvmField
    var logoSekolah: ImageView
    @JvmField
    var view: CardView

    init {
        namaSekolah = itemView.findViewById(R.id.textviewSekolah)
        namaKepsek = itemView.findViewById(R.id.textviewKepsek)
        logoSekolah = itemView.findViewById(R.id.imageview)
        view = itemView.findViewById(R.id.cvSekolah)
    }
}