package com.test.warungbelajaruser.View.Adapter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.test.warungbelajaruser.Model.Jadwal;
import com.test.warungbelajaruser.Model.Kursus;
import com.test.warungbelajaruser.Model.Review;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Fragment.CatatanKursus;
import com.test.warungbelajaruser.View.Fragment.DetailKursus;

import java.util.ArrayList;

public class CatatanKursusAdapter extends RecyclerView.Adapter<CatatanKursusAdapter.CatatanKursusHolder>{
    int REQUEST_CODE = 1234;
    Uri uri;
    private StorageReference storageRef, mStorageRef;
    private ArrayList<Kursus> kursus_list;
    private FragmentActivity activity;
    private CatatanKursus catatanKursus;
    private ArrayList<Integer> kuota, kuota_sesi1, kuota_sesi2;

    private DatabaseReference ref;

    public CatatanKursusAdapter(ArrayList<Integer> kuota, ArrayList<Integer> kuota_sesi1, ArrayList<Integer> kuota_sesi2, ArrayList<Kursus> kursus_list, FragmentActivity activity) {
        this.kuota = kuota;
        this.kuota_sesi1 = kuota_sesi1;
        this.kuota_sesi2 = kuota_sesi2;
        this.kursus_list = kursus_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CatatanKursusHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_kursus, viewGroup, false);

        CatatanKursusHolder holder = new CatatanKursusHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CatatanKursusHolder catatanKursusHolder, final int i) {
        if(kursus_list.get(i).getStatus().equals("Menunggu Pembayaran") || kursus_list.get(i).getStatus().equals("Menunggu Konfirmasi") || kursus_list.get(i).getStatus().equals("Menunggu Penjadwalan")){

            if(kuota.get(i) != 0 && kuota_sesi1.get(i) != 0 && kuota_sesi2.get(i) != 0){
                catatanKursusHolder.ll_sebelum_pembayaran.setVisibility(View.VISIBLE);
            }
            else{
                catatanKursusHolder.ll_sebelum_pembayaran.setVisibility(View.GONE);
            }

            catatanKursusHolder.ll_kursus_berjalan.setVisibility(View.GONE);
            catatanKursusHolder.ll_kursus_selesai.setVisibility(View.GONE);

            if(kursus_list.get(i).getJenis_kursus().equals("pengenalan_pemrograman")){
                catatanKursusHolder.tv_nama_sp_kursus.setText("Pengenalan Pemrograman");
            }
            else if(kursus_list.get(i).getJenis_kursus().equals("pemrograman_dekstop")){
                catatanKursusHolder.tv_nama_sp_kursus.setText("Pemrograman Dektop");
            }
            else if(kursus_list.get(i).getJenis_kursus().equals("pemrograman_mobile")){
                catatanKursusHolder.tv_nama_sp_kursus.setText("Pemrograman Mobile");
            }
            else{
                catatanKursusHolder.tv_nama_sp_kursus.setText("Pengenalan Website");
            }

            if(kursus_list.get(i).getStatus().equals("Menunggu Pembayaran")){
                catatanKursusHolder.btn_lihat_detail.setVisibility(View.VISIBLE);
                catatanKursusHolder.btn_lihat_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                        LayoutInflater layoutInflater = activity.getLayoutInflater();
                        View mView = layoutInflater.inflate(R.layout.modal_lihat_detail_konfirmasi, null);

                        final TextView tvDetailPembayaran = mView.findViewById(R.id.detail_pembayaran);
                        final Button btnOkPembayaran = mView.findViewById(R.id.btn_ok_pembayaran);

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();

                        ref = FirebaseDatabase.getInstance().getReference("nobel");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String UID =  kursus_list.get(i).getUID();
                                String jenis_kursus = kursus_list.get(i).getJenis_kursus();

                                String harga = givePointString(dataSnapshot.child(UID).child("kursus").child(jenis_kursus).child("informasi_dasar").child("harga").getValue().toString());
                                String notif_baru = "Silahkan Melakukan Pembayaran Sebesar Rp "+harga+",00. Ke Rekening BCA 6175028238 A/n Muhammad Malian Zikri Bukti Pembayaran Harap Dikirimkan ke WA 08117199210";
                                tvDetailPembayaran.setText(notif_baru);

                                dialog.show();
                                dialog.setCanceledOnTouchOutside(false);

                                btnOkPembayaran.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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
                });

                catatanKursusHolder.btn_konfirmasi.setVisibility(View.VISIBLE);
                catatanKursusHolder.btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                        LayoutInflater layoutInflater = activity.getLayoutInflater();
                        View mView = layoutInflater.inflate(R.layout.modal_konfirmasi_pembayaran, null);

                        final TextView etJumlahDuit = mView.findViewById(R.id.jumlah_duit);
                        Button btnUpload = mView.findViewById(R.id.upload);
                        Button btnBatal = mView.findViewById(R.id.konfirmasi_batal);
                        Button btnOk = mView.findViewById(R.id.konfirmasi_ok);

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();

                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);

                        btnUpload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               catatanKursus = (CatatanKursus) activity.getSupportFragmentManager().findFragmentByTag("catatan_kursus");
                               catatanKursus.upload_bukti_pembayaran();
                            }
                        });

                        btnBatal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uri = catatanKursus.getUri();

                                if(uri != null){
                                    mStorageRef = FirebaseStorage.getInstance().getReference();
                                    storageRef = mStorageRef.child("foto_bukti_pembayaran/").child(uri.getLastPathSegment());

                                    storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            final int harga_dibayar = Integer.parseInt(etJumlahDuit.getText().toString());

                                            if(etJumlahDuit.getText().toString().equals("0")){
                                                Toast.makeText(activity.getApplicationContext(), "Maaf, jumlah harga harus diisi", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            if(harga_dibayar < kursus_list.get(i).getHarga()){
                                                Toast.makeText(activity.getApplicationContext(), "Maaf, harga yang dibayar kurang dari ketentuan", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            System.out.println("masuk");
                                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri_firebase) {
                                                    String batch = String.valueOf(kursus_list.get(i).getBatch());
                                                    String paket = kursus_list.get(i).getPaket();
                                                    String hari1 = kursus_list.get(i).getCatatan_jadwal().getHari1();
                                                    String hari2 = kursus_list.get(i).getCatatan_jadwal().getHari2();
                                                    String sesi1 = kursus_list.get(i).getCatatan_jadwal().getSesi_pertama();
                                                    String sesi2 = kursus_list.get(i).getCatatan_jadwal().getSesi_kedua();

                                                    ref = FirebaseDatabase.getInstance().getReference();
                                                    ref.child("nobel").child(kursus_list.get(i).getUID()).child("kursus").child(kursus_list.get(i).getJenis_kursus()).child("informasi_dasar").child("harga_dibayar").setValue(harga_dibayar);
                                                    ref.child("nobel").child(kursus_list.get(i).getUID()).child("kursus").child(kursus_list.get(i).getJenis_kursus()).child("informasi_dasar").child("foto_bukti_pembayaran").setValue(uri_firebase.toString());
                                                    ref.child("nobel").child(kursus_list.get(i).getUID()).child("kursus").child(kursus_list.get(i).getJenis_kursus()).child("informasi_dasar").child("status").setValue("Menunggu Konfirmasi");

                                                    ref.child("pendaftaran").child(kursus_list.get(i).getJenis_kursus()).child(batch).child(paket).child(hari1).child(sesi1).child("kuota_sesi").setValue(kuota_sesi1.get(i)-1);
                                                    ref.child("pendaftaran").child(kursus_list.get(i).getJenis_kursus()).child(batch).child(paket).child(hari2).child(sesi2).child("kuota_sesi").setValue(kuota_sesi2.get(i)-1);

                                                    if(paket.equals("private")){
                                                        ref.child("pendaftaran").child(kursus_list.get(i).getJenis_kursus()).child(batch).child("maks_kuota_private").setValue(kuota.get(i)-1);
                                                    }
                                                    else{
                                                        ref.child("pendaftaran").child(kursus_list.get(i).getJenis_kursus()).child(batch).child("maks_kuota_grup").setValue(kuota.get(i)-1);
                                                    }

                                                    catatanKursusHolder.btn_konfirmasi.setVisibility(View.GONE);
                                                    catatanKursusHolder.btn_lihat_detail.setVisibility(View.GONE);
                                                    catatanKursusHolder.tv_notif_pembayaran.setText("Menunggu Konfirmasi");

                                                    dialog.dismiss();

                                                    Toast.makeText(activity.getApplicationContext(), "Konfirmasi berhasil dilakukan!" , Toast.LENGTH_SHORT).show();
                                                }

                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(activity.getApplicationContext(), "Konfirmasi gagal dilakukan!" , Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                  Toast.makeText(activity.getApplicationContext(), "Silahkan isi foto bukti pembayaran!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                                }
                                            });
                                }
                                else{
                                    Toast.makeText(activity.getApplicationContext(), "Maaf, konfirmasi gagal dilakukan!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
            else{
                catatanKursusHolder.tv_notif_pembayaran.setText(kursus_list.get(i).getStatus());
                catatanKursusHolder.btn_lihat_detail.setVisibility(View.GONE);
                catatanKursusHolder.btn_konfirmasi.setVisibility(View.GONE);
            }
        }
        else if(kursus_list.get(i).getStatus().equals("Sedang Berlangsung")){
            catatanKursusHolder.ll_sebelum_pembayaran.setVisibility(View.GONE);
            catatanKursusHolder.ll_kursus_berjalan.setVisibility(View.VISIBLE);
            catatanKursusHolder.ll_kursus_selesai.setVisibility(View.GONE);

            catatanKursusHolder.tv_nama_kb_kursus.setText(kursus_list.get(i).getJenis_kursus());
            catatanKursusHolder.tv_tanggal_pembelajaran.setText(kursus_list.get(i).getTanggal_batch());
            catatanKursusHolder.tv_mentor_kursus.setText(kursus_list.get(i).getMentor());
            catatanKursusHolder.tv_ruangan_kursus.setText(kursus_list.get(i).getRuangan());
            catatanKursusHolder.tv_kursus_bag1.setText(kursus_list.get(i).getCatatan_jadwal().getHari1()+" "+kursus_list.get(i).getCatatan_jadwal().getJam_pertama());
            catatanKursusHolder.tv_kursus_bag2.setText(kursus_list.get(i).getCatatan_jadwal().getHari2()+" "+kursus_list.get(i).getCatatan_jadwal().getJam_kedua());

            catatanKursusHolder.ll_kursus_berjalan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle sendedData = new Bundle();
                    sendedData.putString("UID", kursus_list.get(i).getUID());
                    sendedData.putString("kursus", kursus_list.get(i).getJenis_kursus());

                    DetailKursus fragment = new DetailKursus();
                    fragment.setArguments(sendedData);

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_catatan_kursus, fragment).addToBackStack(null).commit();
                }
            });
        }
        else if(kursus_list.get(i).getStatus().equals("Selesai")){
            catatanKursusHolder.ll_sebelum_pembayaran.setVisibility(View.GONE);
            catatanKursusHolder.ll_kursus_berjalan.setVisibility(View.GONE);
            catatanKursusHolder.ll_kursus_selesai.setVisibility(View.VISIBLE);

            catatanKursusHolder.tv_nama_ks_kursus.setText(kursus_list.get(i).getJenis_kursus());
            catatanKursusHolder.btn_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                    LayoutInflater layoutInflater = activity.getLayoutInflater();
                    View mView = layoutInflater.inflate(R.layout.modal_review_kursus, null);

                    final EditText etKesan = mView.findViewById(R.id.kesan_nobel);
                    final EditText etPesan = mView.findViewById(R.id.pesan_nobel);
                    final EditText etReviewMentor = mView.findViewById(R.id.review_mentor);
                    final EditText etNilaiPuas = mView.findViewById(R.id.nilai_kepuasan);
                    Button btnBatal = mView.findViewById(R.id.batal_review);
                    Button btnKirim = mView.findViewById(R.id.kirim_review);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    btnKirim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String kesan = etKesan.getText().toString();
                            String pesan = etPesan.getText().toString();
                            String review_mentor = etReviewMentor.getText().toString();
                            int nilai_puas = Integer.parseInt(etNilaiPuas.getText().toString());

                            Review review = new Review(pesan, kesan, review_mentor, nilai_puas);

                            ref = FirebaseDatabase.getInstance().getReference("review_warung_belajar");

                            String key = ref.child(kursus_list.get(i).getUID()).push().getKey();

                            ref.child(kursus_list.get(i).getUID()).child("review").child(key).setValue(review);

                            dialog.dismiss();
                        }
                    });

                    btnBatal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return kursus_list.size();
    }

    public class CatatanKursusHolder extends RecyclerView.ViewHolder{
        public LinearLayout ll_sebelum_pembayaran;
        public LinearLayout ll_kursus_berjalan;
        public LinearLayout ll_kursus_selesai;

        public TextView tv_nama_sp_kursus;
        public TextView tv_notif_pembayaran;
        public TextView tv_nama_kb_kursus;
        public TextView tv_tanggal_pembelajaran;
        public TextView tv_kursus_bag1;
        public TextView tv_kursus_bag2;
        public TextView tv_ruangan_kursus;
        public TextView tv_mentor_kursus;
        public TextView tv_nama_ks_kursus;

        public Button btn_lihat_detail;
        public Button btn_konfirmasi;
        public Button btn_review;

        public CatatanKursusHolder(@NonNull View itemView) {
            super(itemView);

            ll_sebelum_pembayaran = itemView.findViewById(R.id.sebelum_pembayaran);
            ll_kursus_berjalan = itemView.findViewById(R.id.kursus_berjalan);
            ll_kursus_selesai = itemView.findViewById(R.id.kursus_selesai);

            tv_nama_sp_kursus = itemView.findViewById(R.id.nama_sp_kursus);
            tv_notif_pembayaran = itemView.findViewById(R.id.notif_pembayaran);
            tv_nama_kb_kursus = itemView.findViewById(R.id.nama_kb_kursus);
            tv_tanggal_pembelajaran = itemView.findViewById(R.id.tanggal_pembelajaran);
            tv_kursus_bag1 = itemView.findViewById(R.id.kursus_bag1);
            tv_kursus_bag2 = itemView.findViewById(R.id.kursus_bag2);
            tv_ruangan_kursus = itemView.findViewById(R.id.ruangan_kursus);
            tv_mentor_kursus = itemView.findViewById(R.id.mentor_kursus);
            tv_nama_ks_kursus = itemView.findViewById(R.id.nama_ks_kursus);

            btn_lihat_detail = itemView.findViewById(R.id.detail_konfirmasi);
            btn_konfirmasi = itemView.findViewById(R.id.konfirmasi);
            btn_review = itemView.findViewById(R.id.review);
        }
    }
}
