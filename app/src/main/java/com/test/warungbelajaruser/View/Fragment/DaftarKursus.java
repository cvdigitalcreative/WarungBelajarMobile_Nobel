package com.test.warungbelajaruser.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.warungbelajaruser.R;
import com.test.warungbelajaruser.View.Adapter.DaftarKursusAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarKursus extends Fragment {
    private RecyclerView rv_daftar_kursus;
    private SearchView sv_search;
    private ArrayList<String> jenis_pemrograman_list;
    private String UID;

    public DaftarKursus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daftar_kursus, container, false);

        init(view);
        receiveData();
        searchKursus();
        viewDaftarKursus();

        return view;
    }

    private void searchKursus() {
        jenis_pemrograman_list = new ArrayList<>();
        jenis_pemrograman_list.add("Pengenalan Pemrograman");
        jenis_pemrograman_list.add("Pemrograman Dekstop");
        jenis_pemrograman_list.add("Pemrograman Android");
        jenis_pemrograman_list.add("Pemrograman Website");

        sv_search.setActivated(true);
        sv_search.onActionViewExpanded();
        sv_search.setQueryHint("Type Your Course");
        sv_search.clearFocus();
        sv_search.setIconified(false);

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String new_text) {
                ArrayList<String> new_course_list;

                new_course_list = new ArrayList<>();

                for(int i=0; i<jenis_pemrograman_list.size(); i++){
                    String sub_text = "";

                    for(int j=0; j<new_text.length(); j++){
                        sub_text = sub_text + String.valueOf(jenis_pemrograman_list.get(i).charAt(j));
                    }

                    if(sub_text.toLowerCase().equals(new_text.toLowerCase())){
                        new_course_list.add(jenis_pemrograman_list.get(i));
                    }
                }

                rv_daftar_kursus.setAdapter(new DaftarKursusAdapter(new_course_list, getFragmentManager(), UID));

                return false;
            }
        });
    }

    private void viewDaftarKursus() {
        rv_daftar_kursus.setAdapter(new DaftarKursusAdapter(jenis_pemrograman_list, getFragmentManager(), UID));
    }

    private void receiveData() {
        UID = getArguments().getString("UID");
    }

    public void init(View view){
        rv_daftar_kursus = view.findViewById(R.id.rv_daftar_kursus);
        sv_search = view.findViewById(R.id.search);

        rv_daftar_kursus.setHasFixedSize(true);

        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        rv_daftar_kursus.setLayoutManager(MyLinearLayoutManager);
    }

}
