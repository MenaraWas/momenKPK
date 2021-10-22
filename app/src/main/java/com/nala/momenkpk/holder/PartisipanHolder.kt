package com.nala.momenkpk.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nala.momenkpk.R

class PartisipanHolder(userView : View) : RecyclerView.ViewHolder(userView) {

    @JvmField
    var uEmail : TextView

    @JvmField
    var uName : TextView

    @JvmField
    var uSekolah : TextView

    @JvmField
    var uRole : TextView

    @JvmField
    var view : CardView

    init{
        uEmail = userView.findViewById(R.id.emailUser)
        uName     = userView.findViewById(R.id.username)
        uSekolah  = userView.findViewById(R.id.sekolah)
        uRole     = userView.findViewById(R.id.role)
        view = userView.findViewById(R.id.cvUser)
    }

}