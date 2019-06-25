package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_home;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.Utils.QrCodeGenerator;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.LoginModelTest;
import com.example.project.model.MyListData;

import org.w3c.dom.Document;

import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment{
    private Connection_INTERNET conn;
    private View rootView;
    private RecyclerView recyclerView;
    private SharedPreferences myPrefs;
    private ImageView qrCode_image;
    private TextView textViewNumerKarty;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_home adapter_home;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        final MyListData myListData = new MyListData();
        LoginModelTest loginModelTest = new LoginModelTest();

        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayoutHome);
        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_home);
        qrCode_image = rootView.findViewById(R.id.imageViewQRCodeGenerator);
        textViewNumerKarty = rootView.findViewById(R.id.textViewNumerKarty);

        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        textViewNumerKarty.setText("Numer karty: " + loginModelTest.listOfInformation.get(0).getListOfCard().get(0).getNr());
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        qrCode_image.setImageBitmap(qrCodeGenerator.QrCodeGenerator(loginModelTest.listOfInformation.get(0).getListOfCard().get(0).getNr()));


        conn = new Connection_INTERNET(getContext());

        if(myListData.listOfPartners.isEmpty()) {
            new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
        }else{
            adapter_home = new RecyclerViewAdapter_home(getContext(), MyListData.listOfPartners);
            recyclerView.setAdapter(adapter_home);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyListData.listOfPartners.clear();
                        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        return rootView;
    }


    private void sortNumberAscending() {
        Collections.sort(MyListData.listOfPartners, new Comparator<MyListData>() {
            @Override
            public int compare(MyListData o1, MyListData o2) {

                return o1.getDistanceToPartner().compareTo(o2.getDistanceToPartner());
            }
        });
        adapter_home.notifyDataSetChanged();
    }


    public class checkingPartners extends AsyncTask<String, String, String> {

        Connection_API C_api = new Connection_API(getActivity());
        private String p_fID;
        private String p_dID;
        private String p_login;
        private String p_haslo;

        checkingPartners(String fID, String dID, String login, String password){
            this.p_fID = fID;
            this.p_dID = dID;
            this.p_login = login;
            this.p_haslo = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.checkingPartners_API(p_fID, p_dID, p_login, p_haslo);
        }
        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            par.parserPartnersXML(doc, "xd");
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        adapter_home = new RecyclerViewAdapter_home(getContext(), MyListData.listOfPartners);
        sortNumberAscending();
        adapter_home.notifyDataSetChanged();
        recyclerView.setAdapter(adapter_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}