package com.example.project.activity_fragments_class.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.model.PartnersModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private View rootView;
    private SearchView searchView;
    private MenuItem searchItem;
    private GoogleMap mMap;
    private ArrayList<String> Latitude;
    private ArrayList<String> Longitude;
    private ArrayList<String> Title;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mapy, container, false);
        setHasOptionsMenu(true);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    ;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu_mapy_fragment, menu);
        searchItem = menu.findItem(R.id.action_szukaj_partnerow_w_mapie);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_pokazListePartnerow):
                Fragment newFragment = new PartnersFragment();
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

    public void onMapReady(GoogleMap googleMap) {
        PartnersModel partnersModel = new PartnersModel();
        Latitude = partnersModel.getmPartners_Latitude();
        Longitude = partnersModel.getmPartners_Longitude();
        Title = partnersModel.getmPartners_Name();

        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(getActivity(), "brak uprawnien", Toast.LENGTH_SHORT).show();

        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        for(int i =0; i<partnersModel.getmPartners_Id().size(); i++){
            LatLng name = new LatLng(Double.valueOf(Longitude.get(i)), Double.valueOf(Latitude.get(i)));
            googleMap.addMarker(new MarkerOptions().position(name).title(Title.get(i)));
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

}
