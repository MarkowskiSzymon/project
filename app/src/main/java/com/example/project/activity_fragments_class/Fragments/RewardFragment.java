package com.example.project.activity_fragments_class.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
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


public class RewardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Connection_INTERNET conn;
    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_rewards adapter_rewards;
    private SearchView searchView;
    private MenuItem searchItem;
    private SharedPreferences myPrefs;
    private Spinner spinner;
    private RelativeLayout fullLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        setHasOptionsMenu(true);

        recyclerView = rootView.findViewById(R.id.recycler_reward);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout_fragmentReward);
        fullLayout = rootView.findViewById(R.id.relativeLayout_fragmentReward_fullLayout);


        spinner = rootView.findViewById(R.id.spinner_rewardFragment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.numbers, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);



        myPrefs = getContext().getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        conn = new Connection_INTERNET(getContext());

        new checkingPartners(StartActivity.checkingPartners_fID, conn.getDeviceId(), myPrefs.getString("login", ""), myPrefs.getString("password", "")).execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyListData.exampleList.clear();
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

        fullLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                searchView.setIconifiedByDefault(false);
                searchView.clearFocus();
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                if(adapter_rewards != null){
                    Toast.makeText(parent.getContext(), "Sortuje malejaco!", Toast.LENGTH_SHORT).show();
                    sortNumberDescending();
                }
                break;
            case 1:
                Toast.makeText(parent.getContext(), "Sortuje rosnaco!", Toast.LENGTH_SHORT).show();
                sortNumberAscending();
                break;
            case 2:
                Toast.makeText(parent.getContext(), "Sortuje A-Z", Toast.LENGTH_SHORT).show();
                sortA_Z();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "onNothingSelected!", Toast.LENGTH_SHORT).show();

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

    private void sortZ_A() {
        Collections.sort(MyListData.exampleList, new Comparator<MyListData>() {
            @Override
            public int compare(MyListData o1, MyListData o2) {

                return o2.getNazwa().compareToIgnoreCase(o1.getNazwa());
            }
        });
        adapter_rewards.notifyDataSetChanged();
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
