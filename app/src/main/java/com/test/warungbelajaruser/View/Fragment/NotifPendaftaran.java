package com.test.warungbelajaruser.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Activity.BaseCatatanKursus;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifPendaftaran extends Fragment {
    private String UID, jenis_kursus, notif_baru, harga;
    private TextView tv_notif;
    private Button btn_notif;
    private DatabaseReference ref;

    public NotifPendaftaran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notif_pendaftaran, container, false);

        init(view);
        receiveData();
        viewNotif();

        return view;
    }

    private void viewNotif() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                harga = givePointString(dataSnapshot.child(UID).child("kursus").child(jenis_kursus).child("informasi_dasar").child("harga").getValue().toString());
                notif_baru = "Silahkan Melakukan Pembayaran Sebesar Rp "+harga+",00. Ke Rekening BCA 6175028238 A/n Muhammad Malian Zikri Bukti Pembayaran Harap Dikirimkan ke WA 08117199210";
                tv_notif.setText(notif_baru);

                btn_notif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BaseCatatanKursus.class);
                        intent.putExtra("UID", UID);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void receiveData() {
        UID = getArguments().getString("UID");
        jenis_kursus = getArguments().getString("jenis_kursus");
    }

    private void init(View view){
        tv_notif = view.findViewById(R.id.notif_pendaftaran);
        btn_notif = view.findViewById(R.id.btn_ok);

        ref = FirebaseDatabase.getInstance().getReference("nobel");
    }

    public String givePointString(String harga){
        String harga_baru = "";

        if(harga.length() <= 6){
            int count=0;
            int point_indeks = harga.length()-4;

            for(int i=0; i<harga.length(); i++){
                if(count == point_indeks){
                    harga_baru += String.valueOf(harga.charAt(i))+".";
                    count++;
                }
                else{
                    harga_baru += String.valueOf(harga.charAt(i));
                    count++;
                }
            }
        }
        else if(harga.length() > 6 && harga.length() <= 9){
            int count=0;
            int point_indeks1 = harga.length()-7;
            int point_indeks2 = harga.length()-4;

            for(int i=0; i<harga.length(); i++){
                if(count == point_indeks1 || count == point_indeks2){
                    harga_baru += String.valueOf(harga.charAt(i))+".";
                    count++;
                }
                else{
                    harga_baru += String.valueOf(harga.charAt(i));
                    count++;
                }
            }
        }

        return  harga_baru;
    }

}
