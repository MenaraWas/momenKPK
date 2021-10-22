package com.nala.momenkpk;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nala.momenkpk.adapter.UserAdapter;
import com.nala.momenkpk.model.ModelInstrumen;
import com.nala.momenkpk.model.ModelPartisipan;

import java.util.ArrayList;
import java.util.Objects;

public class PartisipanActivity extends AppCompatActivity implements UserAdapter.FirebaseDataListener  {

    private ExtendedFloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private UserAdapter uAdapter;
    private EditText mEditEmail;
    private EditText mEditUser;
    private EditText mEditPass;
    private EditText mEditSekolah;
    private EditText mEditRole;
    private ArrayList<ModelPartisipan> daftarPartisipan;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partisipan);

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
        mDatabaseReference = mFirebaseInstance.getReference("partisipan");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                daftarPartisipan = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    ModelPartisipan barang = mDataSnapshot.getValue(ModelPartisipan.class);
                    barang.setKey(mDataSnapshot.getKey());
                    daftarPartisipan.add(barang);
                }

                uAdapter = new UserAdapter(PartisipanActivity.this, daftarPartisipan);
                mRecyclerView.setAdapter(uAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(PartisipanActivity.this,
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
    public void onDataClick(@Nullable ModelPartisipan partisipan, int position) {

    }
}
