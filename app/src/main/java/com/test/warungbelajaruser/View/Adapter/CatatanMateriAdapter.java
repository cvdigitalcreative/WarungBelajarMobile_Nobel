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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.warungbelajaruser.Model.Materi;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.CatatanMateriKursus;

import java.io.File;
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
                        System.out.println("modul belum ditambahkan");
                        Toast.makeText(activity.getApplicationContext(), "Modul belum ditambahkan", Toast.LENGTH_SHORT);
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
                    CatatanMateriKursus catatan_materi = (CatatanMateriKursus) activity.getSupportFragmentManager().findFragmentByTag("catatan_materi");

                    if(materi_list.get(i).getUrl_materi().equals("-")){
                        System.out.println("modul belum ditambahkan");
                        catatan_materi.notif("modul belum ditambahkan");
                        Toast.makeText(activity.getApplicationContext(), "Modul belum ditambahkan", Toast.LENGTH_SHORT);
                    }
                    else{
                        if (catatan_materi.checkPermissionWRITE_EXTERNAL_STORAGE(activity.getApplicationContext())) {
                            File direct = new File(Environment.getExternalStorageDirectory()
                                    + "/kursus_files");

                            if (!direct.exists()) {
                                direct.mkdirs();
                            }

                            DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

                            Uri downloadUri = Uri.parse(materi_list.get(i).getUrl_materi());
                            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setAllowedNetworkTypes(
                                    DownloadManager.Request.NETWORK_WIFI
                                            | DownloadManager.Request.NETWORK_MOBILE)
                                    .setAllowedOverRoaming(false).setTitle(materi_list.get(i).getMateri())
                                    .setDescription("Download in Progress")
                                    .setDestinationInExternalPublicDir("/kursus_files", materi_list.get(i).getMateri()+".pdf");

                            mgr.enqueue(request);
                        }
                        else{
                            Toast.makeText(activity.getApplicationContext(), "Modul gagal diunduh", Toast.LENGTH_SHORT);
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
