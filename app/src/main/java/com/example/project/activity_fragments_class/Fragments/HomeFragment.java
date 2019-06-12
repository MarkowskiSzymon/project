package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.Utils.QrCodeGenerator;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_home;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.CardsModel;
import com.example.project.model.LoginModel;
import com.example.project.model.PartnersModel;

import org.w3c.dom.Document;

public class HomeFragment extends Fragment{
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
        final PartnersModel partnersModel = new PartnersModel();


        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayoutHome);
        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_home);
        qrCode_image = rootView.findViewById(R.id.imageViewQRCodeGenerator);
        textViewNumerKarty = rootView.findViewById(R.id.textViewNumerKarty);

        LoginModel loginModel = new LoginModel();
        CardsModel cardsModel = new CardsModel();

        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        textViewNumerKarty.setText("Numer karty: " + loginModel.getmCardsNumber().get(0));
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        qrCode_image.setImageBitmap(qrCodeGenerator.QrCodeGenerator(loginModel.getmCardsNumber().get(0)));

        conn = new Connection_INTERNET(getContext());
        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        partnersModel.clearAllList();
                        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        return rootView;
    }


    public class checkingPartners extends AsyncTask<String, String, String> {

        Connection_API C_api = new Connection_API(getActivity());
        private String p_fID;
        private String p_dID;
        private String p_login;
        private String p_haslo;
        protected LocationManager locationManager;
        protected LocationListener locationListener;

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

        //    String desc = result.replace("&#60;", "<").replace("&#62;", ">").replace("&#47;", "/");


          //  Log.v("parser", "result: " + result);
          //  Log.v("parser", "result: " + desc);
            Document doc = par.getDocument(result);
            par.parserPartnersXML(doc, "xd");
            initRecyclerView();

        }



//            Location loc1 = new Location("");
//            loc1.setLatitude(20.030172);
//            loc1.setLongitude(49.480952);
//
//            Location loc2 = new Location("");
//            loc2.setLatitude(20.025196);
//            loc2.setLongitude(49.473712);
//
//            float distanceInMeters = loc1.distanceTo(loc2);
//            Log.v("dystans", String.valueOf(distanceInMeters));

        }


    private void initRecyclerView() {
        PartnersModel partnersModel = new PartnersModel();
        adapter_home = new RecyclerViewAdapter_home(getContext(), partnersModel.mPartners_Id, partnersModel.mPartners_Wid, partnersModel.mPartners_Name, partnersModel.mPartners_Longitude, partnersModel.mPartners_Latitude, partnersModel.mPartners_Desc, partnersModel.mPartners_Picture, partnersModel.mPartners_City, partnersModel.mPartners_Multiplier, partnersModel.mPartners_OwnedPoints);
        recyclerView.setAdapter(adapter_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
