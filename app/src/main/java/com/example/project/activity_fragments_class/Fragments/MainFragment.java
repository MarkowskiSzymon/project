package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_main;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.Utils.QrCodeGenerator;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.LoginModelTest;
import com.example.project.model.PartnersModel;
import com.example.project.model.RewardsModel;
import org.w3c.dom.Document;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment{
    private Connection_INTERNET conn;
    private NestedScrollView nestedScrollView;
    private View rootView;
    private RecyclerView recyclerView;
    private SharedPreferences myPrefs;
    private ImageView qrCode_image;
    private TextView textViewNumerKarty;
    private TextView textViewTwojePunkty;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_main adapter_home;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        initialize();
        PartnersModel myListData = new PartnersModel();
        LoginModelTest loginModelTest = new LoginModelTest();
        nestedScrollView.setNestedScrollingEnabled(false);
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        conn = new Connection_INTERNET(getContext());
        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);

        textViewNumerKarty.setText("Numer karty: " + loginModelTest.listOfInformation.get(0).getListOfCard().get(0).getNr());
        qrCode_image.setImageBitmap(qrCodeGenerator.QrCodeGenerator(loginModelTest.listOfInformation.get(0).getListOfCard().get(0).getNr()));




        if(myListData.listOfPartners.isEmpty()) {
            Log.v("parser", "generuje liste");
            new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
        } else{
            adapter_home = new RecyclerViewAdapter_main(getContext(), PartnersModel.listOfPartners);
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
                        RewardsModel.listOfPromo.clear();
                        PartnersModel.listOfPartners.clear();
                        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, StartActivity.refreshDelay);
            }
        });

        textViewTwojePunkty.setMovementMethod(new ScrollingMovementMethod());
        return rootView; 
    }



    private void initialize() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout_fragmentHome);
        recyclerView = rootView.findViewById(R.id.recyclerView_fragmentHome);
        qrCode_image = rootView.findViewById(R.id.imageViewQRCodeGenerator);
        nestedScrollView = rootView.findViewById(R.id.nestedScrollView);
        textViewNumerKarty = rootView.findViewById(R.id.textViewNumerKarty);
        textViewTwojePunkty = rootView.findViewById(R.id.textViewTwojePunkty);
    }


    private void sortNumberAscending() {
        Collections.sort(PartnersModel.listOfPartners, new Comparator<PartnersModel>() {
            @Override
            public int compare(PartnersModel o1, PartnersModel o2) {
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
        adapter_home = new RecyclerViewAdapter_main(getContext(), PartnersModel.listOfPartners);
        adapter_home.notifyDataSetChanged();
        recyclerView.setAdapter(adapter_home);
        sortNumberAscending();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}