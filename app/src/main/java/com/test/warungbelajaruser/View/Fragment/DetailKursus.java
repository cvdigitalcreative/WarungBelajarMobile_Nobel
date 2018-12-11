package com.test.warungbelajaruser.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.test.warungbelajaruser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailKursus extends Fragment {
    private String jenis_kursus, UID;
    private TextView tv_jenis_kursus;
    private Button btn_materi, btn_absensi;

    public DetailKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_kursus, container, false);

        init(view);
        receiveData();
        viewDetailKursus();

        return view;
    }

    private void viewDetailKursus() {
        String jenis_pemrograman;

        if(jenis_kursus.equals("pengenalan_pemrograman")){
            jenis_pemrograman = "Pengenalan Pemrograman";
        }
        else if(jenis_kursus.equals("pemrograman_dekstop")){
            jenis_pemrograman = "Pemrograman Dekstop";
        }
        else if(jenis_kursus.equals("pemrograman_mobile")){
            jenis_pemrograman = "Pemrograman Mobile";
        }
        else{
            jenis_pemrograman = "Pemrograman Website";
        }

        tv_jenis_kursus.setText(jenis_pemrograman);

        do_button_action();
    }

    private void do_button_action(){
        btn_materi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendedData = new Bundle();
                sendedData.putString("UID", UID);
                sendedData.putString("kursus", jenis_kursus);

                CatatanMateriKursus fragment = new CatatanMateriKursus();
                fragment.setArguments(sendedData);

                getFragmentManager().beginTransaction().replace(R.id.container_fragment_catatan_kursus, fragment, "catatan_materi").addToBackStack(null).commit();
            }
        });

        btn_absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendedData = new Bundle();
                sendedData.putString("UID", UID);
                sendedData.putString("kursus", jenis_kursus);

                CatatanKehadiranKursus fragment = new CatatanKehadiranKursus();
                fragment.setArguments(sendedData);

                getFragmentManager().beginTransaction().replace(R.id.container_fragment_catatan_kursus, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void receiveData() {
        jenis_kursus = getArguments().getString("kursus");
        UID = getArguments().getString("UID");
    }

    public void init(View view){
        tv_jenis_kursus = view.findViewById(R.id.your_kursus);

        btn_materi = view.findViewById(R.id.btn_materi);
        btn_absensi = view.findViewById(R.id.btn_kehadiran);
    }

}
