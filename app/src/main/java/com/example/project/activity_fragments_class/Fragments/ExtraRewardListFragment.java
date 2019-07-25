package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_extraRewards;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_main;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_partners;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.PartnersModel;
import com.example.project.model.RewardsModel;

import org.w3c.dom.Document;

public class ExtraRewardListFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_extraRewards adapter_rewards;
    private SharedPreferences myPrefs;
    private Connection_INTERNET conn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_extra_reward_list, container, false);
        initialize();
        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);

        conn = new Connection_INTERNET(getContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RewardsModel.listOfPromo.clear();
                        PartnersModel.listOfPartners.clear();
                        new checkingExtraRewards(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        return rootView;
    }

    private void initialize() {
        recyclerView = rootView.findViewById(R.id.recyclerView_fragmentExtraRewardList);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout_fragmentExtraRewardList);
    }

    public class checkingExtraRewards extends AsyncTask<String, String, String> {
        Connection_API C_api = new Connection_API(getActivity());

        private String p_fID;
        private String p_dID;
        private String p_login;
        private String p_haslo;

        checkingExtraRewards(String fID, String dID, String login, String password){
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

        private void initRecyclerView() {
            adapter_rewards = new RecyclerViewAdapter_extraRewards(getContext(), PartnersModel.listOfPartners);
            adapter_rewards.notifyDataSetChanged();
            recyclerView.setAdapter(adapter_rewards);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
