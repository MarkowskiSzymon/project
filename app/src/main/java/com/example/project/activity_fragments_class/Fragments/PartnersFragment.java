package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_partners;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.MyListData;
import com.example.project.model.PartnersModel;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class PartnersFragment extends Fragment implements SearchView.OnQueryTextListener {
    private Connection_INTERNET conn;
    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_partners adapter_partnerzy;
    private SearchView searchView;
    private MenuItem searchItem;
    private SharedPreferences myPrefs;
    public ArrayList<String> filteredNames, filteredIcons, names, lol, lol2, filteredlol, filteredlol2;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_partnerzy, container, false);
        setHasOptionsMenu(true);

        recyclerView = rootView.findViewById(R.id.recyclerViewFragmentPartnerzy);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayoutPartnerzy);

        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());
        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu_partnerzy_fragment, menu);
        searchItem = menu.findItem(R.id.action_szukaj_partnerow_w_liscie);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

     @Override
        public boolean onQueryTextChange(String s) {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_pokazMapePartnerow):
                Fragment newFragment = new MapaFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
                break;
            case (R.id.action_szukaj_partnerow_w_liscie):
                Log.v("App", "ACTION_SEARCH");
                break;
        }

        return super.onOptionsItemSelected(item);
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
            Log.v("parser", "result: " + result);
            Document doc = par.getDocument(result);
            par.parserPartnersXML(doc, "xd");
            initRecyclerView();

        }

    }

    private void initRecyclerView() {
        PartnersModel partnersModel = new PartnersModel();
        adapter_partnerzy = new RecyclerViewAdapter_partners(getContext(), partnersModel.mPartners_Id, partnersModel.mPartners_Wid, filteredNames, partnersModel.mPartners_Longitude, partnersModel.mPartners_Latitude, partnersModel.mPartners_Desc, filteredIcons, partnersModel.mPartners_City, partnersModel.mPartners_Multiplier, partnersModel.mPartners_OwnedPoints);
        recyclerView.setAdapter(adapter_partnerzy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
