package com.test.warungbelajaruser.View.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.warungbelajaruser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeskripsiKursus extends Fragment {
    private TextView tv_jenis_kursus, tv_deskripsi_kursus, tv_harga_kursus, tv_harga_kursus_private, tv_lama_pemtemuan, tv_banyak_pertemuan;
    private ImageView iv_img_kursus;
    private Button btn_pesan_sekarang;
    private String pilihan, jenis_kursus, deskripsi_kursus, harga_kursus, lama_pertemuan, banyak_pertemuan, UID;
    private int sisa_kuota, maks_kuota, id_pendaftar;
    private DatabaseReference ref;

    public DeskripsiKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deskripsi_kursus, container, false);

        init(view);
        receiveData();
        viewDaftarKursus();

        return view;
    }

    private void viewDaftarKursus() {
        if(pilihan.equals("0")){
            tv_jenis_kursus.setText(jenis_kursus);
            tv_deskripsi_kursus.setText(deskripsi_kursus);
            tv_harga_kursus.setText(harga_kursus);
            tv_harga_kursus_private.setVisibility(View.VISIBLE);
            tv_lama_pemtemuan.setText(lama_pertemuan);
            tv_banyak_pertemuan.setText(banyak_pertemuan);
        }
        else{
            tv_jenis_kursus.setText(jenis_kursus);
            tv_deskripsi_kursus.setText(deskripsi_kursus);
            tv_harga_kursus.setText(harga_kursus);
            tv_harga_kursus_private.setVisibility(View.GONE);
            tv_lama_pemtemuan.setText(lama_pertemuan);
            tv_banyak_pertemuan.setText(banyak_pertemuan);
        }

        btn_pesan_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendedData = new Bundle();

                switch(pilihan){
                    case "0" :
                        sendedData.putString("pilihan", "0");
                        break;
                    case "1" :
                        sendedData.putString("pilihan", "1");
                        break;
                    case "2" :
                        sendedData.putString("pilihan", "2");
                        break;
                    default:
                        sendedData.putString("pilihan", "3");
                        break;
                }

                sendedData.putString("UID",UID);

                FormPendaftaranKursus fragment = new FormPendaftaranKursus();
                fragment.setArguments(sendedData);

                getFragmentManager().beginTransaction().replace(R.id.container_fragment_daftar_kursus, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void receiveData() {
        pilihan = jenis_kursus = deskripsi_kursus = harga_kursus = lama_pertemuan = banyak_pertemuan = null;

        pilihan = getArguments().getString("pilihan");
        UID = getArguments().getString("UID");

        if(pilihan != null){
            if(pilihan.equals("0")){
                jenis_kursus = "Pengenalan Pemrograman";
                deskripsi_kursus = "Deskripsi Pengenalan Pemrograman";
                harga_kursus = "Rp 600.000,-";
                lama_pertemuan = "2 jam/pertemuan";
                banyak_pertemuan = "8x pertemuan";
            }
            else if(pilihan.equals("1")){
                jenis_kursus = "Pemrograman Dekstop";
                deskripsi_kursus = "Deskripsi Pemrograman Dekstop";
                harga_kursus = "Rp 3.000.000,-";
                lama_pertemuan = "2 jam/pertemuan";
                banyak_pertemuan = "16x pertemuan";
            }
            else if(pilihan.equals("2")){
                jenis_kursus = "Pemrograman Android";
                deskripsi_kursus = "Deskripsi Pemrograman Android";
                harga_kursus = "Rp 5.000.000,-";
                lama_pertemuan = "2 jam/pertemuan";
                banyak_pertemuan = "16x pertemuan";
            }
            else if(pilihan.equals("3")){
                jenis_kursus = "Pemrograman Website";
                deskripsi_kursus = "Deskripsi Pemrograman Website";
                harga_kursus = "Rp 4.000.000,-";
                lama_pertemuan = "2 jam/pertemuan";
                banyak_pertemuan = "16x pertemuan";
            }
        }
    }

    public void init(View view){
        tv_jenis_kursus = view.findViewById(R.id.jenis_kursus);
        tv_deskripsi_kursus = view.findViewById(R.id.deskripsi_kursus);
        tv_harga_kursus = view.findViewById(R.id.harga_kursus);
        tv_harga_kursus_private = view.findViewById(R.id.harga_kursus_private);
        tv_lama_pemtemuan = view.findViewById(R.id.lama_pertemuan);
        tv_banyak_pertemuan = view.findViewById(R.id.banyak_pertemuan);

        iv_img_kursus = view.findViewById(R.id.image_kursus);

        btn_pesan_sekarang = view.findViewById(R.id.pesan_sekarang);

        ref = FirebaseDatabase.getInstance().getReference("kursus");
    }

}
