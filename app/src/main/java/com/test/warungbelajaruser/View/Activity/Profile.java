package com.test.warungbelajaruser.View.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.warungbelajaruser.R;

public class Profile extends AppCompatActivity {
    private String UID;
    private TextView tv_nama, tv_number;
    private Button btn_number_wa;
    private ImageView iv_qr_code;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        receiveData();
        viewProfile();
    }

    public void viewProfile(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nama = dataSnapshot.child(UID).child("profile_nobel").child("nama").getValue().toString();
                String number = dataSnapshot.child(UID).child("profile_nobel").child("no_hp").getValue().toString();
                String url = dataSnapshot.child(UID).child("profile_nobel").child("qr_url").getValue().toString();

                tv_nama.setText(nama);
                tv_number.setText(number);

                Glide.with(getApplicationContext()).load(url).into(iv_qr_code);

                btn_number_wa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = "628117199210";

                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Warung Belajar Nobel App Sending Messages");
                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
                        startActivity(sendIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void receiveData() {
        UID = getIntent().getStringExtra("UID");
    }

    public void init(){
        tv_nama = findViewById(R.id.your_name);
        tv_number = findViewById(R.id.your_number);

        btn_number_wa = findViewById(R.id.btn_wa);
        iv_qr_code = findViewById(R.id.qr_code);

        ref = FirebaseDatabase.getInstance().getReference("nobel");
    }
}
