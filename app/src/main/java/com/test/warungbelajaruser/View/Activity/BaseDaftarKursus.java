package com.test.warungbelajaruser.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.DaftarKursus;

public class BaseDaftarKursus extends AppCompatActivity {
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_daftar_kursus);

        receiveData();
        goToDaftarKursus();
    }

    private void goToDaftarKursus(){
        Bundle sendedData = new Bundle();
        sendedData.putString("UID", UID);

        DaftarKursus frament = new DaftarKursus();
        frament.setArguments(sendedData);
        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment_daftar_kursus, frament).commit();
    }

    private void receiveData() {
        UID = getIntent().getStringExtra("id_user");
    }
}
