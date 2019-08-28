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
import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_transactions;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.TransactionsModel;
import org.w3c.dom.Document;


public class TransactionsFragment extends Fragment {

    private Connection_INTERNET conn;
    private SharedPreferences myPrefs;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_transactions adapter_transactions;
    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);
        initialize();
        TransactionsModel transactionsModel = new TransactionsModel();
        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());

        if(transactionsModel.listOfTransactions.isEmpty()) {
            new checkingTransactions(StartActivity.checkingTransactions_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
        } else{
            adapter_transactions = new RecyclerViewAdapter_transactions(getContext(), TransactionsModel.listOfTransactions);
            recyclerView.setAdapter(adapter_transactions);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransactionsModel.listOfTransactions.clear();
                        new checkingTransactions(StartActivity.checkingTransactions_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, StartActivity.refreshDelay);
            }
        });

        return rootView;
    }

    private void initialize() {
        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_history);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_layout_fragment_history);
    }


    public class checkingTransactions extends AsyncTask<String, String, String> {
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
            adapter_transactions = new RecyclerViewAdapter_transactions(getContext(), TransactionsModel.listOfTransactions);
            recyclerView.setAdapter(adapter_transactions);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
