package com.test.warungbelajaruser.View.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.test.warungbelajaruser.Model.Materi;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.CatatanMateriKursus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CatatanMateriAdapter extends RecyclerView.Adapter<CatatanMateriAdapter.CatatanMateriHolder>{
    private ArrayList<Materi> materi_list;
    private FragmentActivity activity;

    public CatatanMateriAdapter(ArrayList<Materi> materi_list, FragmentActivity activity) {
        this.materi_list = materi_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CatatanMateriHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_materi, viewGroup, false);

        CatatanMateriHolder holder = new CatatanMateriHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanMateriHolder catatanMateriHolder, final int i) {
        catatanMateriHolder.tv_pertemuan.setText("Pertemuan "+materi_list.get(i).getPertemuan());
        catatanMateriHolder.tv_materi_kursus.setText(">> "+materi_list.get(i).getMateri());

        if(materi_list.get(i).getStatus().equals("not active")){
            catatanMateriHolder.btn_baca_modul.setVisibility(View.GONE);
            catatanMateriHolder.btn_unduh_modul.setVisibility(View.GONE);
        }
        else{
            catatanMateriHolder.btn_baca_modul.setVisibility(View.VISIBLE);
            catatanMateriHolder.btn_unduh_modul.setVisibility(View.VISIBLE);

            catatanMateriHolder.btn_baca_modul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CatatanMateriKursus catatan_materi = (CatatanMateriKursus) activity.getSupportFragmentManager().findFragmentByTag("catatan_materi");

                    if(materi_list.get(i).getUrl_materi().equals("-")){
                        catatan_materi.notif("modul belum ditambahkan");
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(materi_list.get(i).getUrl_materi()));
                        activity.startActivity(intent);
                    }
                }
            });

            catatanMateriHolder.btn_unduh_modul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(materi_list.get(i).getUrl_materi());

                    final CatatanMateriKursus catatan_materi = (CatatanMateriKursus) activity.getSupportFragmentManager().findFragmentByTag("catatan_materi");

                    if(materi_list.get(i).getUrl_materi().equals("-")){
                        catatan_materi.notif("modul belum ditambahkan");
                    }
                    else{
                        if (catatan_materi.checkPermissionWRITE_EXTERNAL_STORAGE(activity.getApplicationContext())) {
                            System.out.println("masuk 1");
                            final File file = new File(activity.getApplicationContext().getExternalFilesDir(null), materi_list.get(i).getNama_modul());

                            if (!file.exists()) {
                                System.out.println("masuk 2");
                                storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Log.e("SUCCEED", "Modul Berhasil di Download" +file.toString());
                                        catatan_materi.notif("Modul Berhasil di Download");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                        Log.e("Failed", "Modul Gagal di Download" +file.toString()+" "+exception.getLocalizedMessage());
                                        catatan_materi.notif("Modul Gagal di Download");
                                    }
                                });
                            }
                            else{
                                System.out.println("masuk 3");
                                file.delete();

                                storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Log.e("SUCCEED", "Data Berhasil di Download" +file.toString());
                                        catatan_materi.notif("Modul Berhasil di Download");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                        Log.e("Failed", "Data Gagal di Download" +file.toString()+" "+exception.getLocalizedMessage());
                                        catatan_materi.notif("Modul Gagal di Download");
                                    }
                                });
                            }
                        }
                        else{
                            catatan_materi.notif("Modul Gagal di Download");
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return materi_list.size();
    }

    public class CatatanMateriHolder extends RecyclerView.ViewHolder{
        public TextView tv_pertemuan;
        public TextView tv_materi_kursus;

        public Button btn_baca_modul;
        public Button btn_unduh_modul;

        public CatatanMateriHolder(@NonNull View itemView) {
            super(itemView);

            tv_pertemuan = itemView.findViewById(R.id.pertemuan);
            tv_materi_kursus = itemView.findViewById(R.id.materi_kursus);

            btn_baca_modul = itemView.findViewById(R.id.baca_modul);
            btn_unduh_modul = itemView.findViewById(R.id.unduh_modul);
        }
    }
}
