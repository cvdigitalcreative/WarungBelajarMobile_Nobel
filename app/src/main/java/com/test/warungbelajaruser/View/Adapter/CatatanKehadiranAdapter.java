package com.test.warungbelajaruser.View.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.warungbelajaruser.Model.Absensi;
import com.test.warungbelajaruser.R;

import java.util.ArrayList;

public class CatatanKehadiranAdapter extends RecyclerView.Adapter<CatatanKehadiranAdapter.CatatanKehadiranHolder> {
    private ArrayList<Absensi> absensi_list;

    public CatatanKehadiranAdapter(ArrayList<Absensi> absensi_list) {
        this.absensi_list = absensi_list;
    }

    @NonNull
    @Override
    public CatatanKehadiranHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_absensi, viewGroup, false);

        CatatanKehadiranHolder holder = new CatatanKehadiranHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanKehadiranHolder catatanKehadiranHolder, int i) {
        catatanKehadiranHolder.tv_pertemuan_absensi.setText("Pertemuan "+String.valueOf(absensi_list.get(i).getPertemuan()));

        if(absensi_list.get(i).getStatus().equals("belum berjalan")){
            catatanKehadiranHolder.tv_kehadiran_absensi.setTextColor(Color.RED);
            catatanKehadiranHolder.tv_kehadiran_absensi.setText(absensi_list.get(i).getStatus());
        }
        else{
            catatanKehadiranHolder.tv_kehadiran_absensi.setTextColor(Color.parseColor("#00574B"));
            catatanKehadiranHolder.tv_kehadiran_absensi.setText(absensi_list.get(i).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return absensi_list.size();
    }

    public class CatatanKehadiranHolder extends RecyclerView.ViewHolder{
        public TextView tv_pertemuan_absensi;
        public TextView tv_kehadiran_absensi;

        public CatatanKehadiranHolder(@NonNull View itemView) {
            super(itemView);

            tv_pertemuan_absensi = itemView.findViewById(R.id.pertemuan_absensi);
            tv_kehadiran_absensi = itemView.findViewById(R.id.kehadiran_absensi);
        }
    }
}
