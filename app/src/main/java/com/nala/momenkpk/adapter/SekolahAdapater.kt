package com.nala.momenkpk;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.R
import com.nala.momenkpk.adapter.ModelClass

class Adapter(userList: List<ModelClass>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val userList: List<ModelClass>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sekolah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource: Int = userList[position].getImageview()
        val name: String = userList[position].getTextview1()
        val msg: String = userList[position].getTextview2()
        holder.setData(resource, name, msg)


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    //view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView
        private val textView: TextView
        private val textView2: TextView
        private val daftarSekolah: RelativeLayout

        fun setData(resource: Int, name: String?, msg: String?) {
            imageView.setImageResource(resource)
            textView.text = name
            textView2.text = msg
        }

        init {
            //here use xml ids
            //give different name not like constructor
            imageView = itemView.findViewById(R.id.imageview)
            textView = itemView.findViewById(R.id.textview)
            textView2 = itemView.findViewById(R.id.textview2)
            daftarSekolah = itemView.findViewById(R.id.listSekolah)
        }
    }

    init {
        this.userList = userList
    }
}