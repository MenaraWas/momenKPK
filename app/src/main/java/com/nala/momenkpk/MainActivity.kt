package com.nala.momenkpk

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            CRUDActivity.setWindowFlag(
                this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                true
            )
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            CRUDActivity.setWindowFlag(
                this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }

        //intent for moving
        val bt_instrumen = findViewById(R.id.instrumen) as LinearLayout
        val bt_user = findViewById(R.id.partisipan) as LinearLayout
        val bt_about = findViewById(R.id.about) as LinearLayout
        val bt_hasil = findViewById(R.id.data_hasil) as LinearLayout

        bt_instrumen.setOnClickListener {
            val intent = Intent(this@MainActivity, CRUDActivity::class.java)
            startActivity(intent)
        }

        bt_user.setOnClickListener {
            val intent = Intent(this@MainActivity, PartisipanActivity::class.java)
            startActivity(intent)
        }
        bt_about.setOnClickListener {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }
        bt_hasil.setOnClickListener {
            val intent = Intent(this@MainActivity, SekolahActivity::class.java)
            startActivity(intent)
        }






    }
}