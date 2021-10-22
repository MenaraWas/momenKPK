package com.nala.momenkpk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nala.momenkpk.adapter.MainAdapter;
import com.nala.momenkpk.model.ModelInstrumen;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CRUDActivity extends AppCompatActivity implements MainAdapter.FirebaseDataListener {

    private ExtendedFloatingActionButton mFloatingActionButton;
    private EditText mEditNama;
    private EditText mEditHarga;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private ArrayList<ModelInstrumen> daftarBarang;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;

    String listInstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudactivity);

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

        mFloatingActionButton = findViewById(R.id.tambah_barang);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogTambahBarang();
            }
        });


    }


    public void pilihInstrumen(View view){

        Spinner spinnerPilihInstrumen = findViewById(R.id.listInstrumen);
        listInstr = String.valueOf(spinnerPilihInstrumen.getSelectedItem());

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("pertanyaan");
        mDatabaseReference.child(listInstr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                daftarBarang = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    ModelInstrumen barang = mDataSnapshot.getValue(ModelInstrumen.class);
                    barang.setKey(mDataSnapshot.getKey());
                    daftarBarang.add(barang);
                }

                mAdapter = new MainAdapter(CRUDActivity.this, daftarBarang);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(CRUDActivity.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDataClick(@Nullable final ModelInstrumen barang, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateBarang(barang);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataBarang(barang);
            }
        });

        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogTambahBarang() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Model Instrumen");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_instrumen, null);

        mEditNama = view.findViewById(R.id.nama_barang);
        mEditHarga = view.findViewById(R.id.harga_barang);
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String namaBarang = mEditNama.getText().toString();
                String hargaBarang = mEditHarga.getText().toString();

                if (!namaBarang.isEmpty() && !hargaBarang.isEmpty()) {
                    submitDataBarang(new ModelInstrumen(namaBarang, hargaBarang));
                } else {
                    Toast.makeText(CRUDActivity.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogUpdateBarang(final ModelInstrumen barang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Model Instrumen");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_instrumen, null);

        mEditNama = view.findViewById(R.id.nama_barang);
        mEditHarga = view.findViewById(R.id.harga_barang);

        mEditNama.setText(barang.getNama());
        mEditHarga.setText(barang.getId_pertanyaan());
        builder.setView(view);

        if (barang != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    barang.setNama(mEditNama.getText().toString());
                    barang.setId_pertanyaan(mEditHarga.getText().toString());
                    updateDataBarang(barang);
                }
            });
        }

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }

    private void submitDataBarang(ModelInstrumen barang) {
        mDatabaseReference.child(listInstr).push()
                .setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(CRUDActivity.this, "Data barang berhasil di simpan !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDataBarang(ModelInstrumen barang) {
        mDatabaseReference.child(listInstr).child(barang.getKey())
                .setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(CRUDActivity.this, "Data berhasil di update !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hapusDataBarang(ModelInstrumen barang) {
        if (mDatabaseReference != null) {
            mDatabaseReference.child(listInstr).child(barang.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(CRUDActivity.this, "Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}