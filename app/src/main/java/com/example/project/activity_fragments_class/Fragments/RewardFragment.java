package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.project.R;
import com.example.project.Utils.Adaptery.RecyclerViewAdapter_rewards;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.MyListData;

import org.w3c.dom.Document;

import java.util.Collections;
import java.util.Comparator;

public class RewardFragment extends Fragment {

    private Connection_INTERNET conn;
    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_rewards adapter_rewards;
    private SearchView searchView;
    private MenuItem searchItem;
    private SharedPreferences myPrefs;
    private Button sortuj1, sortuj2, sortuj3;

    private ArrayAdapter<String> mListViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        setHasOptionsMenu(true);

        recyclerView = rootView.findViewById(R.id.recycler_reward);
        sortuj1 = rootView.findViewById(R.id.reward_button_sortNumberDescending);
        sortuj2 = rootView.findViewById(R.id.reward_button_sortNumberAscending);
        sortuj3 = rootView.findViewById(R.id.reward_button_sortA_Z);


        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());

        fillExampleList();

        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();

        sortuj1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortNumberDescending();
            }
        });

        sortuj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortNumberAscending();
            }
        });

        sortuj3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortA_Z();
            }
        });






        return rootView;
    }

    private void sortNumberDescending() {
        Collections.sort(MyListData.exampleList, new Comparator<MyListData>() {
            @Override
            public int compare(MyListData o1, MyListData o2) {

                return (o2.getDistanceToPartner()).compareTo(o1.getDistanceToPartner());
            }
        });
        adapter_rewards.notifyDataSetChanged();
    }

    private void sortNumberAscending() {
        Collections.sort(MyListData.exampleList, new Comparator<MyListData>() {
            @Override
            public int compare(MyListData o1, MyListData o2) {

                return o1.getDistanceToPartner().compareTo(o2.getDistanceToPartner());
            }
        });
        adapter_rewards.notifyDataSetChanged();
    }

    private void sortA_Z() {
        Collections.sort(MyListData.exampleList, new Comparator<MyListData>() {
            @Override
            public int compare(MyListData o1, MyListData o2) {

                return o1.getNazwa().compareToIgnoreCase(o2.getNazwa());
            }
        });
        adapter_rewards.notifyDataSetChanged();
    }

    private void fillExampleList() {
        MyListData myListData = new MyListData();
        Log.v("App", "ExampleListSize:" + myListData.exampleListSize());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu_partnerzy_fragment, menu);
        searchItem = menu.findItem(R.id.action_szukaj_partnerow_w_liscie);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.v("App", "onQueryTextChange: " + s);
                adapter_rewards.getFilter().filter(s);
                return false;
            }
        });
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
            par.parserPartnersXMLCorreect(doc, "xd");

            adapter_rewards = new RecyclerViewAdapter_rewards(getContext(), MyListData.exampleList);
            recyclerView.setAdapter(adapter_rewards);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        }


    }


}
