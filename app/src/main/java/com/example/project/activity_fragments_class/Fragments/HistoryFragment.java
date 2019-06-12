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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_history;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.model.HistoryModel;
import com.example.project.activity_fragments_class.StartActivity;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    private Connection_INTERNET conn;
    private SharedPreferences myPrefs;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_history adapter_transactions_history;
    private ArrayList<String> transactionsData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);

        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());

        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_history);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_layout_fragment_history);



        new checkingTransactions(StartActivity.checkingTransactions_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();

        final HistoryModel partnersModel = new HistoryModel();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        partnersModel.clearAllList();
                        new checkingTransactions(StartActivity.checkingTransactions_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        return rootView;
    }


    public class checkingTransactions extends AsyncTask<String, String, String> {
        HistoryModel partnersModel = new HistoryModel();

        Connection_API C_api = new Connection_API(getActivity());
        private String p_fID;
        private String p_dID;
        private String p_login;
        private String p_haslo;

        checkingTransactions(String fID, String dID, String login, String password){
            this.p_fID = fID;
            this.p_dID = dID;
            this.p_login = login;
            this.p_haslo = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.checkingTransactions_API(p_fID, p_dID, p_login, p_haslo);
        }
        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            par.parserTransactionsXML(doc, "xd");


            initRecyclerView();


        }
        private void initRecyclerView() {
            adapter_transactions_history = new RecyclerViewAdapter_history(getContext(), partnersModel.getmTransactionData(), partnersModel.getmTransactionType(), partnersModel.getmTransactionExpense(), partnersModel.getmTransactionAmount(), partnersModel.getmTransactionCardNumber(), partnersModel.getmTransactionPicture(), partnersModel.getmTransactionName());
            recyclerView.setAdapter(adapter_transactions_history);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
