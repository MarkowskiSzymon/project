package com.example.project.activity_fragments_class.Fragments;

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
import com.example.project.Utils.listaPartnerow;

import java.util.ArrayList;

public class PartnersFragment extends Fragment implements SearchView.OnQueryTextListener {

    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter_partners adapter_partnerzy;
    private SearchView searchView;
    private MenuItem searchItem;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_partnerzy, container, false);
        setHasOptionsMenu(true);

        zmienne();

        initRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initRecyclerView();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        return rootView;
    }


    private void zmienne() {
        recyclerView = rootView.findViewById(R.id.recyclerViewFragmentPartnerzy);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayoutPartnerzy);

    }

    private void initRecyclerView() {
        adapter_partnerzy = new RecyclerViewAdapter_partners(getContext(), listaPartnerow.listaNazw, listaPartnerow.listaZdjecUrl, listaPartnerow.listaOpisow, listaPartnerow.listaPunktow);
        recyclerView.setAdapter(adapter_partnerzy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initRecyclerView(ArrayList<String> filter) {
        listaPartnerow listaPartnerow = new listaPartnerow();
        adapter_partnerzy = new RecyclerViewAdapter_partners(getContext(), filter, listaPartnerow.listaZdjecUrl, listaPartnerow.listaOpisow, listaPartnerow.listaPunktow);
        recyclerView.setAdapter(adapter_partnerzy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        listaPartnerow listaPartnerow = new listaPartnerow();
        if (s == null || s.trim().isEmpty()) {
            initRecyclerView();
        }
        ArrayList<String> filteredValues = new ArrayList<String>(listaPartnerow.listaNazw);
        for (String value : listaPartnerow.listaNazw) {
            if (!value.toLowerCase().contains(s.toLowerCase())) {
                filteredValues.remove(value);
                Log.v("App", value);
            }
            initRecyclerView(filteredValues);
        }

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

}
