package com.nala.momenkpk;

import static com.nala.momenkpk.CRUDActivity.setWindowFlag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nala.momenkpk.model.Admin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private TextInputEditText editEmail;
    private TextInputEditText editPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.textEditEmail);
        editPass = findViewById(R.id.textEditPass);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        String email = editEmail.getText().toString();
        String password = editPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        String password = editPass.getText().toString();

        // membuat User Admin baru
        writeNewAdmin(user.getUid(), username, user.getEmail());

        if(password.contains("siswa")){
            startActivity(new Intent(LoginActivity.this, SiswaActivity.class));
            finish();
        }

        else if(password.contains("admin")){
            // Go to MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();

        }

        else if(password.contains("kepsek") || password.contains("Kepsek")){
            startActivity(new Intent(LoginActivity.this, KepsekActivity.class));
            finish();
        }

        else if(password.contains("guru")){
            startActivity(new Intent(LoginActivity.this, GuruActivity.class));
            finish();
        }

        else if(password.contains("pengawas")){
            startActivity(new Intent(LoginActivity.this, PengawasActivity.class));
            finish();
        }
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editEmail.getText().toString())) {
            editEmail.setError("Required");
            result = false;
        } else {
            editEmail.setError(null);
        }

        if (TextUtils.isEmpty(editPass.getText().toString())) {
            editPass.setError("Required");
            result = false;
        } else {
            editPass.setError(null);
        }

        return result;
    }
    private void writeNewAdmin(String userId, String username, String email) {
        Admin admin = new Admin(username, email);

        mDatabase.child("partisipan").child(userId).setValue(admin);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_login) {
            signIn();}
    }
}