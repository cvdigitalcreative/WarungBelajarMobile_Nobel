package com.test.warungbelajaruser.View.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.warungbelajaruser.Model.Jadwal;
import com.test.warungbelajaruser.Model.Kursus;
import com.test.warungbelajaruser.Model.Materi;
import com.test.warungbelajaruser.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormPendaftaranKursus extends Fragment {
    private RadioButton rb_pertama, rb_kedua;
    private RadioButton rb_pertama_jam1, rb_pertama_jam2, rb_pertama_jam3, rb_pertama_jam4;
    private RadioButton rb_kedua_jam1, rb_kedua_jam2, rb_kedua_jam3, rb_kedua_jam4;
    private RadioGroup rg_pilihan1, rg_pilihan2;
    private Spinner s_batch, s_tipe_kursus, s_hari_pertama, s_hari_kedua;
    private Button btn_harga_kursus, btn_pesan_jadwal;
    private String pilihan, harga_grup_string, harga_private_string, jenis_kursus, UID, tanggal_batch;
    private String[] absensi;
    private int harga_grup, harga_private, harga, id_pendaftar, maks_kuota_private_pp, maks_kuota_grup_pp, maks_kuota_grup_pd, maks_kuota_grup_pm, maks_kuota_grup_pw;
    private ArrayList<Integer> kuota_sesi_pertama_list, kuota_sesi_kedua_list;
    private ArrayList<String> hari_pertama_list, hari_kedua_list, hari_list_grup_pp, hari_list_grup, hari_list_private;
    private ArrayList<Materi> materi_list;
    private DatabaseReference ref;

    public FormPendaftaranKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_pendaftaran_kursus, container, false);

        init(view);
        receiveData();
        init_data_hadir_n_materi();
        init_sesi_jadwal();
        fillFormPendaftaran(view);

        return view;
    }

    private void fillFormPendaftaran(final View view) {
        btn_pesan_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId1 = rg_pilihan1.getCheckedRadioButtonId();
                int selectedId2 = rg_pilihan2.getCheckedRadioButtonId();
                harga = ((harga*30)/100)+id_pendaftar;

                rb_pertama = view.findViewById(selectedId1);
                rb_kedua = view.findViewById(selectedId2);

                String batch = s_batch.getSelectedItem().toString();
                String tipe_kursus = s_tipe_kursus.getSelectedItem().toString().toLowerCase();
                String hari_pertama = s_hari_pertama.getSelectedItem().toString().toLowerCase();
                String jam1 = rb_pertama.getText().toString();
                String hari_kedua = s_hari_kedua.getSelectedItem().toString().toLowerCase();
                String jam2 = rb_kedua.getText().toString();

                String sesi_hari_pertama, sesi_hari_kedua;

                if(jam1.equals("10.00-12.00")){
                    sesi_hari_pertama = "sesi1";
                }
                else if(jam1.equals("13.00-15.00")){
                    sesi_hari_pertama = "sesi2";
                }
                else if(jam1.equals("16.00-18.00")){
                    sesi_hari_pertama = "sesi3";
                }
                else{
                    sesi_hari_pertama = "sesi4";
                }

                if(jam2.equals("10.00-12.00")){
                    sesi_hari_kedua = "sesi1";
                }
                else if(jam2.equals("13.00-15.00")){
                    sesi_hari_kedua = "sesi2";
                }
                else if(jam2.equals("16.00-18.00")){
                    sesi_hari_kedua = "sesi3";
                }
                else{
                    sesi_hari_kedua = "sesi4";
                }

                Jadwal jadwal = new Jadwal();
                jadwal.setHari1(hari_pertama);
                jadwal.setHari2(hari_kedua);
                jadwal.setSesi_pertama(sesi_hari_pertama);
                jadwal.setSesi_kedua(sesi_hari_kedua);
                jadwal.setJam_pertama(jam1);
                jadwal.setJam_kedua(jam2);

                Kursus kursus = new Kursus();
                kursus.setStatus("Menunggu Pembayaran");
                kursus.setRuangan("-");
                kursus.setMentor("-");
                kursus.setPaket(tipe_kursus);
                kursus.setHarga(harga);
                kursus.setHarga_dibayar(0);
                kursus.setTanggal_batch(tanggal_batch);
                kursus.setBatch(Integer.parseInt(batch));

                String key = ref.child("pendaftaran").child(jenis_kursus).child(batch).child(tipe_kursus).child(hari_pertama).child(sesi_hari_pertama).child("waiting_list").push().getKey();

                ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("informasi_dasar").setValue(kursus);
                ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("jadwal").setValue(jadwal);

                for(int i=0; i<materi_list.size(); i++){
                    ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("materi").child(String.valueOf(materi_list.get(i).getPertemuan())).child("status").setValue(materi_list.get(i).getStatus());
                    ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("materi").child(String.valueOf(materi_list.get(i).getPertemuan())).child("tema_materi").setValue(materi_list.get(i).getMateri());
                    ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("materi").child(String.valueOf(materi_list.get(i).getPertemuan())).child("url_modul").setValue(materi_list.get(i).getUrl_materi());
                    ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("materi").child(String.valueOf(materi_list.get(i).getPertemuan())).child("nama_modul").setValue(materi_list.get(i).getNama_modul());
                }

                for(int i=0; i<absensi.length; i++){
                    ref.child("nobel").child(UID).child("kursus").child(jenis_kursus).child("absensi").child(absensi[i]).child("status").setValue("belum berjalan");
                }

                ref.child("pendaftaran").child(jenis_kursus).child(batch).child(tipe_kursus).child(hari_pertama).child(sesi_hari_pertama).child("waiting_list").child(key).child("id_nobel").setValue(UID);
                ref.child("pendaftaran").child(jenis_kursus).child(batch).child(tipe_kursus).child(hari_kedua).child(sesi_hari_kedua).child("waiting_list").child(key).child("id_nobel").setValue(UID);

                Bundle sendedData = new Bundle();
                sendedData.putString("UID", UID);
                sendedData.putString("jenis_kursus", jenis_kursus);

                NotifPendaftaran fragment = new NotifPendaftaran();
                fragment.setArguments(sendedData);

                getFragmentManager().beginTransaction().replace(R.id.container_fragment_daftar_kursus, fragment).commit();
            }
        });
    }

    private void init_sesi_jadwal() {
        init_hari_list();
        init_spinner_hari();

        s_tipe_kursus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                init_spinner_hari();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s_hari_pertama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getInfoSesi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s_hari_kedua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getInfoSesi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getInfoSesi();
    }

    private void init_hari_list() {
        hari_list_private = new ArrayList<>();
        hari_list_grup_pp = new ArrayList<>();
        hari_list_grup = new ArrayList<>();

        hari_list_private.add("Selasa");
        hari_list_private.add("Rabu");
        hari_list_private.add("Kamis");
        hari_list_private.add("Jumat");

        hari_list_grup.add("Selasa");
        hari_list_grup.add("Rabu");
        hari_list_grup.add("Kamis");
        hari_list_grup.add("Jumat");

        hari_list_grup_pp.add("Sabtu");
        hari_list_grup_pp.add("Minggu");
    }

    private void init_spinner_hari(){
        hari_pertama_list = new ArrayList<>();
        hari_kedua_list = new ArrayList<>();

        String tipe_kursus = s_tipe_kursus.getSelectedItem().toString();

        if(tipe_kursus.equals("Grup")){
            if(pilihan.equals("0")){
                String[] entries1, entries2;

                hari_pertama_list.addAll(hari_list_grup_pp);
                entries1 = new String[hari_pertama_list.size()];
                entries2 = new String[hari_pertama_list.size()-1];

                String hari_terpilih = hari_pertama_list.get(0);

                int count = 0;
                for(int i=0; i<hari_pertama_list.size(); i++){
                    entries1[i] = hari_pertama_list.get(i);

                    if(!hari_terpilih.equals(hari_pertama_list.get(i))){
                        hari_kedua_list.add(hari_pertama_list.get(i));
                        entries2[count++] = hari_pertama_list.get(i);
                    }
                }

                ArrayAdapter<String> spinnerGrup1Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries1);
                s_hari_pertama.setAdapter(spinnerGrup1Adapter);

                ArrayAdapter<String> spinnerGrup2Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries2);
                s_hari_kedua.setAdapter(spinnerGrup2Adapter);

                btn_harga_kursus.setText(harga_grup_string);
                harga = harga_grup;
            }
            else{
                String[] entries1, entries2;

                hari_pertama_list.addAll(hari_list_grup);
                entries1 = new String[hari_pertama_list.size()];
                entries2 = new String[hari_pertama_list.size()-1];

                String hari_terpilih = hari_pertama_list.get(0);

                int count = 0;
                for(int i=0; i<hari_pertama_list.size(); i++){
                    entries1[i] = hari_pertama_list.get(i);

                    if(!hari_terpilih.equals(hari_pertama_list.get(i))){
                        hari_kedua_list.add(hari_pertama_list.get(i));
                        entries2[count++] = hari_pertama_list.get(i);
                    }
                }

                ArrayAdapter<String> spinnerGrup1Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries1);
                s_hari_pertama.setAdapter(spinnerGrup1Adapter);

                ArrayAdapter<String> spinnerGrup2Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries2);
                s_hari_kedua.setAdapter(spinnerGrup2Adapter);

                btn_harga_kursus.setText(harga_grup_string);
                harga = harga_grup;
            }
        }
        else{
            String[] entries1, entries2;

            hari_pertama_list.addAll(hari_list_private);
            entries1 = new String[hari_pertama_list.size()];
            entries2 = new String[hari_pertama_list.size()-1];

            String hari_terpilih = hari_pertama_list.get(0);

            int count = 0;
            for(int i=0; i<hari_pertama_list.size(); i++){
                entries1[i] = hari_pertama_list.get(i);

                if(!hari_terpilih.equals(hari_pertama_list.get(i))){
                    hari_kedua_list.add(hari_pertama_list.get(i));
                    entries2[count++] = hari_pertama_list.get(i);
                }
            }

            ArrayAdapter<String> spinnerGrup1Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries1);
            s_hari_pertama.setAdapter(spinnerGrup1Adapter);

            ArrayAdapter<String> spinnerGrup2Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries2);
            s_hari_kedua.setAdapter(spinnerGrup2Adapter);

            btn_harga_kursus.setText(harga_private_string);
            harga = harga_private;
        }
    }

    private void getInfoSesi(){
        String batch_kursus = s_batch.getSelectedItem().toString();

        rb_pertama_jam1.setEnabled(true);
        rb_pertama_jam2.setEnabled(true);
        rb_pertama_jam3.setEnabled(true);
        rb_pertama_jam4.setEnabled(true);

        rb_kedua_jam1.setEnabled(true);
        rb_kedua_jam2.setEnabled(true);
        rb_kedua_jam3.setEnabled(true);
        rb_kedua_jam4.setEnabled(true);

        String[] entries2;

        entries2 = new String[hari_pertama_list.size()-1];
        String hari_terpilih = s_hari_pertama.getSelectedItem().toString();

        int count = 0;
        for(int i=0; i<hari_pertama_list.size(); i++){
            if(!hari_terpilih.equals(hari_pertama_list.get(i))){
                hari_kedua_list.add(hari_pertama_list.get(i));
                entries2[count++] = hari_pertama_list.get(i);
            }
        }

        ArrayAdapter<String> spinnerGrup2Adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, entries2);
        s_hari_kedua.setAdapter(spinnerGrup2Adapter);

        ref.child("pendaftaran").child(jenis_kursus).child(batch_kursus).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> sesi_list;

                kuota_sesi_pertama_list = new ArrayList<>();
                kuota_sesi_kedua_list = new ArrayList<>();
                sesi_list = new ArrayList<>();

                String paket_kursus = s_tipe_kursus.getSelectedItem().toString().toLowerCase();
                String hari_pertama = s_hari_pertama.getSelectedItem().toString().toLowerCase();
                String hari_kedua = s_hari_kedua.getSelectedItem().toString().toLowerCase();
                String jam1 = rb_pertama_jam1.getText().toString();
                String jam2 = rb_pertama_jam2.getText().toString();
                String jam3 = rb_pertama_jam3.getText().toString();
                String jam4 = rb_pertama_jam4.getText().toString();

                if(paket_kursus.equals("private")){
                    int kuota = Integer.valueOf(dataSnapshot.child("maks_kuota_private").getValue().toString());
                    id_pendaftar = maks_kuota_private_pp-kuota+1;
                }
                else if(paket_kursus.equals("grup") && jenis_kursus.equals("pengenalan_pemrograman")){
                    int kuota = Integer.valueOf(dataSnapshot.child("maks_kuota_grup").getValue().toString());
                    id_pendaftar = maks_kuota_grup_pp-kuota+1;
                }
                else if(paket_kursus.equals("grup") && jenis_kursus.equals("pemrograman_dekstop")){
                    int kuota = Integer.valueOf(dataSnapshot.child("maks_kuota_grup").getValue().toString());
                    id_pendaftar = maks_kuota_grup_pp-kuota+1;
                }
                else if(paket_kursus.equals("grup") && jenis_kursus.equals("pemrograman_mobile")){
                    int kuota = Integer.valueOf(dataSnapshot.child("maks_kuota_grup").getValue().toString());
                    id_pendaftar = maks_kuota_grup_pm-kuota+1;
                }
                else if(paket_kursus.equals("grup") && jenis_kursus.equals("pemrograman_website")){
                    int kuota = Integer.valueOf(dataSnapshot.child("maks_kuota_grup").getValue().toString());
                    id_pendaftar = maks_kuota_grup_pw-kuota+1;
                }

                tanggal_batch = dataSnapshot.child("tanggal_mulai").getValue().toString();

                for(DataSnapshot pertamaSnapshot : dataSnapshot.child(paket_kursus).child(hari_pertama).getChildren()){
                    int kuota = Integer.valueOf(pertamaSnapshot.child("kuota_sesi").getValue().toString());
                    String jam = pertamaSnapshot.child("jam").getValue().toString();

                    kuota_sesi_pertama_list.add(kuota);
                    sesi_list.add(jam);
                }

                for(int i=0; i<kuota_sesi_pertama_list.size(); i++){
                    if(kuota_sesi_pertama_list.get(i) == 0 && sesi_list.get(i).equals(jam1)){
                        rb_pertama_jam1.setEnabled(false);
                    }
                    else if(kuota_sesi_pertama_list.get(i) == 0 && sesi_list.get(i).equals(jam2)){
                        rb_pertama_jam2.setEnabled(false);
                    }
                    else if(kuota_sesi_pertama_list.get(i) == 0 && sesi_list.get(i).equals(jam3)){
                        rb_pertama_jam3.setEnabled(false);
                    }
                    else if(kuota_sesi_pertama_list.get(i) == 0 && sesi_list.get(i).equals(jam4)){
                        rb_pertama_jam4.setEnabled(false);
                    }
                }

                sesi_list = new ArrayList<>();

                for(DataSnapshot keduaSnapshot : dataSnapshot.child(paket_kursus).child(hari_kedua).getChildren()){
                    int kuota = Integer.valueOf(keduaSnapshot.child("kuota_sesi").getValue().toString());
                    String jam = keduaSnapshot.child("jam").getValue().toString();

                    kuota_sesi_kedua_list.add(kuota);
                    sesi_list.add(jam);
                }

                for(int i=0; i<kuota_sesi_kedua_list.size(); i++){
                    if(kuota_sesi_kedua_list.get(i) == 0 && sesi_list.get(i).equals(jam1)){
                        rb_kedua_jam1.setEnabled(false);
                    }
                    else if(kuota_sesi_kedua_list.get(i) == 0 && sesi_list.get(i).equals(jam2)){
                        rb_kedua_jam2.setEnabled(false);
                    }
                    else if(kuota_sesi_kedua_list.get(i) == 0 && sesi_list.get(i).equals(jam3)){
                        rb_kedua_jam3.setEnabled(false);
                    }
                    else if(kuota_sesi_kedua_list.get(i) == 0 && sesi_list.get(i).equals(jam4)){
                        rb_kedua_jam4.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init_data_hadir_n_materi() {
        materi_list = new ArrayList<>();

        ref.child("materi_kursus").child(jenis_kursus).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materi_list = new ArrayList<>();

                for(DataSnapshot materiSnapshot : dataSnapshot.getChildren()){
                    int pertemuan = Integer.parseInt(materiSnapshot.getKey());
                    String status = materiSnapshot.child("status").getValue().toString();
                    String tema_materi = materiSnapshot.child("tema_materi").getValue().toString();
                    String url = materiSnapshot.child("url_modul").getValue().toString();
                    String nama_modul = materiSnapshot.child("nama_modul").getValue().toString();

                    Materi materi = new Materi(pertemuan, tema_materi, url, status, nama_modul);
                    materi_list.add(materi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void receiveData() {
        UID = getArguments().getString("UID");
        pilihan = getArguments().getString("pilihan");

        if(pilihan != null){
            if(pilihan.equals("0")){
                absensi = getResources().getStringArray(R.array.jumlah_kehadiran_pp);
                jenis_kursus = "pengenalan_pemrograman";
                harga_grup = 600000;
                harga_private = 1500000;
                maks_kuota_private_pp = 16;
                maks_kuota_grup_pp = 32;
                harga_private_string = "Rp 1.500.000,-";
                harga_grup_string = "Rp 600.000,-";
            }
            else if(pilihan.equals("1")){
                absensi = getResources().getStringArray(R.array.jumlah_kehadiran_grup);
                jenis_kursus = "pemrograman_dekstop";
                maks_kuota_grup_pd = 12;
                harga_grup = 3000000;
                harga_grup_string = "Rp 3.000.000,-";
            }
            else if(pilihan.equals("2")){
                absensi = getResources().getStringArray(R.array.jumlah_kehadiran_grup);
                jenis_kursus = "pemrograman_mobile";
                maks_kuota_grup_pm = 6;
                harga_grup = 5000000;
                harga_grup_string = "Rp 5.000.000,-";
            }
            else if(pilihan.equals("3")){
                absensi = getResources().getStringArray(R.array.jumlah_kehadiran_grup);
                jenis_kursus = "pemrograman_website";
                maks_kuota_grup_pw = 6;
                harga_grup = 4000000;
                harga_grup_string = "Rp 4.000.000,-";
            }
        }
    }

    public void init(View view){
        rb_pertama_jam1 = view.findViewById(R.id.pertama_jam1);
        rb_pertama_jam2 = view.findViewById(R.id.pertama_jam2);
        rb_pertama_jam3 = view.findViewById(R.id.pertama_jam3);
        rb_pertama_jam4 = view.findViewById(R.id.pertama_jam4);

        rb_kedua_jam1 = view.findViewById(R.id.kedua_jam1);
        rb_kedua_jam2 = view.findViewById(R.id.kedua_jam2);
        rb_kedua_jam3 = view.findViewById(R.id.kedua_jam3);
        rb_kedua_jam4 = view.findViewById(R.id.kedua_jam4);

        rg_pilihan1 = view.findViewById(R.id.pilihan_1);
        rg_pilihan2 = view.findViewById(R.id.pilihan_2);

        s_batch = view.findViewById(R.id.batch);
        s_tipe_kursus = view.findViewById(R.id.tipe_kursus);
        s_hari_pertama = view.findViewById(R.id.hari_pertama);
        s_hari_kedua = view.findViewById(R.id.hari_kedua);

        btn_harga_kursus = view.findViewById(R.id.harga_kursus);
        btn_pesan_jadwal = view.findViewById(R.id.pesan_jadwal);

        ref = FirebaseDatabase.getInstance().getReference();
    }

}
