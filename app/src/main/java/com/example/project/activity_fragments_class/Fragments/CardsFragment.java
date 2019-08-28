package com.example.project.activity_fragments_class.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_cards;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.CardModelTest;

import org.w3c.dom.Document;

public class CardsFragment extends Fragment {
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences myPrefs;
    private Connection_INTERNET conn;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_cards adapter_cards;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        initialize();
        final CardModelTest cardModelTest = new CardModelTest();
        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.add_new_card_popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        if(cardModelTest.listOfCards.isEmpty()) {
            new CheckingOwnedCards(StartActivity.checkingOwnedCards_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
        } else{
            adapter_cards = new RecyclerViewAdapter_cards(getContext(), CardModelTest.listOfCards);
            recyclerView.setAdapter(adapter_cards);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardModelTest.listOfCards.clear();
                        new CheckingOwnedCards(StartActivity.checkingOwnedCards_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, StartActivity.refreshDelay);
            }
        });

        return rootView;
    }

    private void initialize() {
        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_karty);
        fab = rootView.findViewById(R.id.fab);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayoutKarty);

    }


    public class CheckingOwnedCards extends AsyncTask<String, String, String> {
        Connection_API C_api = new Connection_API(getActivity());

        private String p_fID;
        private String p_dID;
        private String p_login;
        private String p_haslo;

        CheckingOwnedCards(String fID, String dID, String login, String password){
            this.p_fID = fID;
            this.p_dID = dID;
            this.p_login = login;
            this.p_haslo = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.checkingOwnedCards_API(p_fID, p_dID, p_login, p_haslo);
        }

        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            par.parserLoginXML(doc, "xd");
            initRecyclerView();
        }

        private void initRecyclerView() {
            adapter_cards = new RecyclerViewAdapter_cards(getContext(), CardModelTest.listOfCards);
            recyclerView.setAdapter(adapter_cards);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
