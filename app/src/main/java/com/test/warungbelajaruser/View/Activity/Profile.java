package com.test.warungbelajaruser.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.warungbelajaruser.R;

public class Profile extends AppCompatActivity {
    private TextView tv_nama, tv_number;
    private Button btn_number_wa;
    private ImageView iv_qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

    }

    public void init(){
        tv_nama = findViewById(R.id.your_name);
        tv_number = findViewById(R.id.your_number);

        btn_number_wa = findViewById(R.id.btn_wa);
        iv_qr_code = findViewById(R.id.qr_code);
    }
}
