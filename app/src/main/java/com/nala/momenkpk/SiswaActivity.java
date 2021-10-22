package com.nala.momenkpk;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.rgb;
import static com.nala.momenkpk.CRUDActivity.setWindowFlag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nala.momenkpk.adapter.MainAdapter;
import com.nala.momenkpk.model.ModelInstrumen;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SiswaActivity extends AppCompatActivity implements MainAdapter.FirebaseDataListener  {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private Button btnNext;
    private RadioGroup rdGroup;
    private RadioButton rdButton;
    private ArrayList<ModelInstrumen> daftarBarang;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;
    private TextView tvNilai;
    private ImageView imgForm;
    private ImageView imgLogout;
    private Integer total;
    private String Item1;
    private String Item2;
    private String Item3;
    private String Item4;
    private String Item5;
    private Integer nilai1,nilai2,nilai3,nilai4,nilai5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        btnNext = findViewById(R.id.btn_done);
        tvNilai = findViewById(R.id.totalNilai);
        rdGroup = findViewById(R.id.opsi);
        rdButton = findViewById(R.id.radioButton);
        imgForm = findViewById(R.id.imageForm);
        imgLogout = findViewById(R.id.bt_logout);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Terima Kasih sudah melakukan penilaian";
                int duration = Toast.LENGTH_SHORT;

                btnNext.setText("Kamu sudah menilai");
                btnNext.setEnabled(false);
                btnNext.setBackgroundColor(GRAY);

                mRecyclerView.setVisibility(View.GONE);
                imgForm.setVisibility(View.VISIBLE);

//                total=(nilai1+nilai2+nilai3+nilai4+nilai5);
                tvNilai.setText("15" + " points");

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("pertanyaan");
        mDatabaseReference.child("data_instrumen-siswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                daftarBarang = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    ModelInstrumen barang = mDataSnapshot.getValue(ModelInstrumen.class);
                    barang.setKey(mDataSnapshot.getKey());
                    daftarBarang.add(barang);
                }

                mAdapter = new MainAdapter(SiswaActivity.this, daftarBarang);
                mRecyclerView.setAdapter(mAdapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(SiswaActivity.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Item1 = intent.getStringExtra("item");
            Item2 = intent.getStringExtra("item2");
            Item3 = intent.getStringExtra("item3");
            Item4 = intent.getStringExtra("item4");
            Item5 = intent.getStringExtra("item5");
        }
    };
    @Override
    public void onDataClick(@Nullable ModelInstrumen instrumen, int position) {

    }

    public void logout(View view) {

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Toast toast = Toast.makeText(SiswaActivity.this, "Terima Kasih", Toast.LENGTH_SHORT);
                toast.show();

                startActivity(new Intent(SiswaActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}