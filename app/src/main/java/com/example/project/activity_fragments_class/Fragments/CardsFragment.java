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
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_cards;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.CardsModel;

import org.w3c.dom.Document;

public class CardsFragment extends Fragment {
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences myPrefs;
    private Connection_INTERNET conn;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_cards adapter_transactions_cards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_karty);

        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.add_new_card_popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();


                Snackbar.make(view, "Replace wh your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        new CheckingOwnedCards(StartActivity.checkingOwnedCards_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();

        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayoutKarty);
        final CardsModel cardsModel = new CardsModel();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardsModel.clearAllList();
                        new CheckingOwnedCards(StartActivity.checkingOwnedCards_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        return rootView;
    }


    public class CheckingOwnedCards extends AsyncTask<String, String, String> {
        CardsModel cardsModel = new CardsModel();

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
            par.parserCardsXML(doc, "xd");

            initRecyclerView();


        }
        private void initRecyclerView() {
            adapter_transactions_cards = new RecyclerViewAdapter_cards(getContext(), cardsModel.getmCardsIcon(), cardsModel.getmCardsNumber());
            recyclerView.setAdapter(adapter_transactions_cards);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }


}
