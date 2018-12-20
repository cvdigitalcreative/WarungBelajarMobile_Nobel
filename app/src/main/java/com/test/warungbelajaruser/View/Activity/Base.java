package com.test.warungbelajaruser.View.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.warungbelajaruser.Model.Kursus;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.CatatanKursus;

public class Base extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnCatatanKursus, btnKursus, btnProfile, btnLogout;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        init();
        receiveData();
        goToMenu();
    }

    public void goToMenu(){
        btnCatatanKursus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Base.this, BaseCatatanKursus.class);
                intent.putExtra("UID", UID);
                startActivity(intent);
            }
        });

        btnKursus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Base.this, BaseDaftarKursus.class);
                intent.putExtra("id_user", UID);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Base.this, Profile.class);
                intent.putExtra("UID", UID);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Intent intent = new Intent(Base.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void receiveData() {
        UID = getIntent().getStringExtra("id_user");
    }

    public void init(){
        btnCatatanKursus = findViewById(R.id.btn_catatan_kursus);
        btnKursus = findViewById(R.id.btn_kursus);
        btnProfile = findViewById(R.id.btn_profile);
        btnLogout = findViewById(R.id.btn_Logout);

        mAuth = FirebaseAuth.getInstance();
    }
}
