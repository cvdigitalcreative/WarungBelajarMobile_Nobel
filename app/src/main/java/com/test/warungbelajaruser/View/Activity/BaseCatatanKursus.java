package com.test.warungbelajaruser.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.CatatanKursus;

public class BaseCatatanKursus extends AppCompatActivity {
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_catatan_kursus);

        receiveData();
        goToCatatanKursus();
    }

    private void goToCatatanKursus(){
        Bundle sendedData = new Bundle();
        sendedData.putString("UID", UID);

        CatatanKursus frament = new CatatanKursus();
        frament.setArguments(sendedData);
        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment_catatan_kursus, frament, "catatan_kursus").commit();
    }

    private void receiveData() {
        UID = getIntent().getStringExtra("UID");
    }
}
