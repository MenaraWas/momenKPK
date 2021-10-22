package com.nala.momenkpk.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.R
import com.nala.momenkpk.holder.MainViewHolder
import com.nala.momenkpk.model.ModelInstrumen

import android.widget.RadioGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.nala.momenkpk.holder.PartisipanHolder
import kotlin.collections.ArrayList
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_instrumen_user.view.*


class MainAdapter(private val context: Context,
                  private val daftarInstrumen: ArrayList<ModelInstrumen>?
) : RecyclerView.Adapter<MainViewHolder>() {

    private val listener: FirebaseDataListener
    var nilai : String = ""
    var nilai2 : String = ""
    var nilai3 : String = ""
    var nilai4 : String = ""
    var nilai5 : String = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instrumen_user, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val color = Color.WHITE
        holder.view.setCardBackgroundColor(color)
        holder.namaBarang.text = "Pertanyaan  : \n" + (daftarInstrumen?.get(position)?.nama)
        holder.view.setOnClickListener { listener.onDataClick(daftarInstrumen?.get(position), position) }

        holder.rdGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            nilai = holder.radioButtonOpsi.getText().toString()
            nilai2 = holder.radioButtonOpsi2.getText().toString()
            nilai3 = holder.radioButtonOpsi3.getText().toString()
            nilai4 = holder.radioButtonOpsi4.getText().toString()
            nilai5 = holder.radioButtonOpsi5.getText().toString()

            Toast.makeText(context, nilai, Toast.LENGTH_SHORT).show()
        })


        val intent = Intent("custom-message")
        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

            intent.putExtra("item", nilai)
            intent.putExtra("item2", nilai2)
            intent.putExtra("item3", nilai3)
            intent.putExtra("item4", nilai4)
            intent.putExtra("item5", nilai5)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

    }
    
    override fun getItemCount(): Int {

        return daftarInstrumen?.size!!
    }

    //interface data listener
    interface FirebaseDataListener {
        fun onDataClick(instrumen: ModelInstrumen?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }

}


