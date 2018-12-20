package com.test.warungbelajaruser.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.DeskripsiKursus;

import java.util.ArrayList;

public class DaftarKursusAdapter extends RecyclerView.Adapter<DaftarKursusAdapter.DaftarKursusHolder>{
    private ArrayList<String> kursus_list;
    private FragmentManager manager;
    private String UID;

    public DaftarKursusAdapter(ArrayList<String> kursus_list, FragmentManager manager, String UID) {
        this.kursus_list = kursus_list;
        this.manager = manager;
        this.UID = UID;
    }

    @NonNull
    @Override
    public DaftarKursusHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_daftar_kursus, viewGroup, false);

        DaftarKursusHolder holder = new DaftarKursusHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarKursusHolder daftarKursusHolder, final int i) {
        System.out.println("Kursus : "+kursus_list.get(i));
        daftarKursusHolder.tv_jenis_pemrograman.setText(kursus_list.get(i));
        daftarKursusHolder.cv_daftar_kursus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendedData = new Bundle();

                switch(kursus_list.get(i)){
                    case "Pengenalan Pemrograman" :
                        sendedData.putString("pilihan", "0");
                        break;
                    case "Pemrograman Dekstop" :
                        sendedData.putString("pilihan", "1");
                        break;
                    case "Pemrograman Android" :
                        sendedData.putString("pilihan", "2");
                        break;
                    default:
                        sendedData.putString("pilihan", "3");
                        break;
                }

                sendedData.putString("UID", UID);

                DeskripsiKursus fragment = new DeskripsiKursus();
                fragment.setArguments(sendedData);

                manager.beginTransaction().replace(R.id.container_fragment_daftar_kursus, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kursus_list.size();
    }

    public class DaftarKursusHolder extends RecyclerView.ViewHolder{
        public ImageView iv_img_pemrograman;
        public TextView tv_jenis_pemrograman;
        public CardView cv_daftar_kursus;

        public DaftarKursusHolder(@NonNull View itemView) {
            super(itemView);

            iv_img_pemrograman = itemView.findViewById(R.id.img_pemrograman);
            tv_jenis_pemrograman = itemView.findViewById(R.id.jenis_pemrograman);
            cv_daftar_kursus = itemView.findViewById(R.id.cardview_daftar_kursus);
        }
    }
}
