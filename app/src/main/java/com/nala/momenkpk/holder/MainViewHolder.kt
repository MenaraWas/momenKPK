package com.nala.momenkpk.holder

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.R

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    @JvmField
    var namaBarang: TextView

    @JvmField
    var radioButtonOpsi: RadioButton
    @JvmField
    var radioButtonOpsi2: RadioButton
    @JvmField
    var radioButtonOpsi3: RadioButton
    @JvmField
    var radioButtonOpsi4: RadioButton
    @JvmField
    var radioButtonOpsi5: RadioButton


    @JvmField
    var  rdGroup : RadioGroup


    @JvmField
    var view: CardView

    init {
        namaBarang = itemView.findViewById(R.id.nama_barang)
        radioButtonOpsi = itemView.findViewById(R.id.radioButton)
        radioButtonOpsi2 = itemView.findViewById(R.id.radioButton2)
        radioButtonOpsi3 = itemView.findViewById(R.id.radioButton3)
        radioButtonOpsi4 = itemView.findViewById(R.id.radioButton4)
        radioButtonOpsi5 = itemView.findViewById(R.id.radioButton5)
        rdGroup = itemView.findViewById(R.id.opsi)
        view = itemView.findViewById(R.id.cvMain)
    }
}