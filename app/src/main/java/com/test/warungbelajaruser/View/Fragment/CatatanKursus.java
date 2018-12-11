package com.test.warungbelajaruser.View.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.test.warungbelajaruser.Model.Jadwal;
import com.test.warungbelajaruser.Model.Kursus;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Adapter.CatatanKursusAdapter;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatatanKursus extends Fragment {
    private int REQUEST_CODE = 1234;
    private Uri uri;
    private String UID;
    private ArrayList<Kursus> kursus_list;
    private ArrayList<Integer> kuota, kuota_sesi1, kuota_sesi2;
    private RecyclerView rv_catatan_kursus;
    private DatabaseReference ref;

    public CatatanKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catatan_kursus, container, false);

        init(view);
        receiveData();
        viewCatatanKursus();

        return view;
    }

    private void viewCatatanKursus() {
        kursus_list = new ArrayList<>();
        kuota = new ArrayList<>();
        kuota_sesi1 = new ArrayList<>();
        kuota_sesi2 = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kursus_list = new ArrayList<>();
                kuota = new ArrayList<>();
                kuota_sesi1 = new ArrayList<>();
                kuota_sesi2 = new ArrayList<>();

                for(DataSnapshot kursusSnapshot : dataSnapshot.child("nobel").child(UID).child("kursus").getChildren()){
                    String jenis_kursus = kursusSnapshot.getKey();

                    Jadwal jadwal = new Jadwal();
                    jadwal.setHari1(kursusSnapshot.child("jadwal").child("hari1").getValue().toString());
                    jadwal.setHari2(kursusSnapshot.child("jadwal").child("hari2").getValue().toString());
                    jadwal.setSesi_pertama(kursusSnapshot.child("jadwal").child("sesi_pertama").getValue().toString());
                    jadwal.setSesi_kedua(kursusSnapshot.child("jadwal").child("sesi_kedua").getValue().toString());
                    jadwal.setJam_pertama(kursusSnapshot.child("jadwal").child("jam_pertama").getValue().toString());
                    jadwal.setJam_kedua(kursusSnapshot.child("jadwal").child("jam_kedua").getValue().toString());

                    Kursus kursus = new Kursus();
                    kursus.setUID(UID);
                    kursus.setPaket(kursusSnapshot.child("informasi_dasar").child("paket").getValue().toString());
                    kursus.setMentor(kursusSnapshot.child("informasi_dasar").child("mentor").getValue().toString());
                    kursus.setRuangan(kursusSnapshot.child("informasi_dasar").child("ruangan").getValue().toString());
                    kursus.setStatus(kursusSnapshot.child("informasi_dasar").child("status").getValue().toString());
                    kursus.setHarga(Integer.valueOf(kursusSnapshot.child("informasi_dasar").child("harga").getValue().toString()));
                    kursus.setHarga_dibayar(Integer.valueOf(kursusSnapshot.child("informasi_dasar").child("harga_dibayar").getValue().toString()));
                    kursus.setTanggal_batch(kursusSnapshot.child("informasi_dasar").child("tanggal_batch").getValue().toString());
                    kursus.setBatch(Integer.parseInt(kursusSnapshot.child("informasi_dasar").child("batch").getValue().toString()));
                    kursus.setCatatan_jadwal(jadwal);
                    kursus.setJenis_kursus(jenis_kursus);

                    String kuota_str, kuota_pertama_str, kuota_kedua_str;

                    if(jenis_kursus.equals("pengenalan_pemrograman")){
                        if(kursus.getPaket().equals("private")){
                            kuota_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child("maks_kuota_private").getValue().toString();
                        }
                        else{
                            kuota_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child("maks_kuota_grup").getValue().toString();
                        }

                        kuota_pertama_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child(kursus.getPaket()).child(jadwal.getHari1()).child(jadwal.getSesi_pertama()).child("kuota_sesi").getValue().toString();
                        kuota_kedua_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child(kursus.getPaket()).child(jadwal.getHari2()).child(jadwal.getSesi_kedua()).child("kuota_sesi").getValue().toString();
                    }
                    else {
                        kuota_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child("maks_kuota_grup").getValue().toString();
                        kuota_pertama_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child(kursus.getPaket()).child(jadwal.getHari1()).child(jadwal.getSesi_pertama()).child("kuota_sesi").getValue().toString();
                        kuota_kedua_str = dataSnapshot.child("pendaftaran").child(jenis_kursus).child(String.valueOf(kursus.getBatch())).child(kursus.getPaket()).child(jadwal.getHari2()).child(jadwal.getSesi_kedua()).child("kuota_sesi").getValue().toString();
                    }

                    kuota.add(Integer.parseInt(kuota_str));
                    kuota_sesi1.add(Integer.parseInt(kuota_pertama_str));
                    kuota_sesi2.add(Integer.parseInt(kuota_kedua_str));
                    kursus_list.add(kursus);
                }

                rv_catatan_kursus.setAdapter(new CatatanKursusAdapter(kuota, kuota_sesi1, kuota_sesi2, kursus_list, getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void receiveData() {
        UID = getArguments().getString("UID");
    }

    public void init(View view){
        rv_catatan_kursus = view.findViewById(R.id.rv_catatan_kursus);
        rv_catatan_kursus.setHasFixedSize(true);

        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        rv_catatan_kursus.setLayoutManager(MyLinearLayoutManager);

        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void upload_bukti_pembayaran(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto Bukti Pembayaran : "), REQUEST_CODE);
    }

    public FragmentActivity getAktifitasFragment(){
        return getActivity();
    }

    public Uri getUri(){
        return uri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
        }
    }
}
