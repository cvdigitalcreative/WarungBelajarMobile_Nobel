package com.test.warungbelajaruser.View.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.warungbelajaruser.Model.Materi;
import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Adapter.CatatanMateriAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatatanMateriKursus extends Fragment {
    private RecyclerView rv_materi_kursus;
    private ArrayList<Materi> materi_list;
    private String UID, jenis_kursus;

    private DatabaseReference ref;


    public CatatanMateriKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materi_kursus, container, false);

        init(view);
        receiveData();
        viewMateriKursus();

        return view;
    }

    private void viewMateriKursus() {
        materi_list = new ArrayList<>();

        ref.child(UID).child("kursus").child(jenis_kursus).child("materi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materi_list = new ArrayList<>();

                for(DataSnapshot absensiSnapshot : dataSnapshot.getChildren()) {
                    int pertemuan = Integer.parseInt(absensiSnapshot.getKey());
                    String status = absensiSnapshot.child("status").getValue().toString();
                    String tema_materi = absensiSnapshot.child("tema_materi").getValue().toString();
                    String url_modul = absensiSnapshot.child("url_modul").getValue().toString();
                    String nama_modul = absensiSnapshot.child("nama_modul").getValue().toString();

                    Materi materi = new Materi(pertemuan, tema_materi, url_modul, status, nama_modul);
                    materi_list.add(materi);
                }

                rv_materi_kursus.setAdapter(new CatatanMateriAdapter(materi_list, getActivity()));
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
        rv_materi_kursus = view.findViewById(R.id.rv_materi_kursus);
        rv_materi_kursus.setHasFixedSize(true);

        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        rv_materi_kursus.setLayoutManager(MyLinearLayoutManager);

        ref = FirebaseDatabase.getInstance().getReference("nobel");
    }

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionWRITE_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                else{
                    ActivityCompat.requestPermissions((Activity) context, new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE },
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(getContext(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public void notif(String pesan){
        Toast.makeText(getContext(), pesan, Toast.LENGTH_SHORT).show();
    }
}
