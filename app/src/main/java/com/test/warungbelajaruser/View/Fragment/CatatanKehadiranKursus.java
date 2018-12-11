package com.test.warungbelajaruser.View.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.warungbelajaruser.Model.Absensi;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Adapter.CatatanKehadiranAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatatanKehadiranKursus extends Fragment {
    private RecyclerView rv_absensi_kursus;
    private ArrayList<Absensi> absensi_list;
    private String UID, jenis_kursus;

    private DatabaseReference ref;

    public CatatanKehadiranKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catatan_kehadiran_kursus, container, false);

        init(view);
        receiveData();
        viewKehadiranKursus();

        return view;
    }

    private void viewKehadiranKursus() {
        absensi_list = new ArrayList<>();

        ref.child(UID).child("kursus").child(jenis_kursus).child("absensi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                absensi_list = new ArrayList<>();

                for(DataSnapshot absensiSnapshot : dataSnapshot.getChildren()){
                    int pertemuan = Integer.parseInt(absensiSnapshot.getKey());
                    String status = absensiSnapshot.child("status").getValue().toString();

                    Absensi absensi = new Absensi(pertemuan, status);
                    absensi_list.add(absensi);
                 }

                rv_absensi_kursus.setAdapter(new CatatanKehadiranAdapter(absensi_list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void receiveData() {
        jenis_kursus = getArguments().getString("kursus");
        UID = getArguments().getString("UID");
    }

    public void init(View view){
        rv_absensi_kursus = view.findViewById(R.id.rv_absensi_kursus);
        rv_absensi_kursus.setHasFixedSize(true);

        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        rv_absensi_kursus.setLayoutManager(MyLinearLayoutManager);

        ref = FirebaseDatabase.getInstance().getReference("nobel");
    }

}
