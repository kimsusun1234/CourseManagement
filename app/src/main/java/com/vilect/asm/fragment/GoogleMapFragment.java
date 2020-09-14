package com.vilect.asm.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vilect.asm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private Context context;
    private GoogleMap map;

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    public GoogleMapFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_map, container, false);

        MapFragment mapFragment = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.frgMap);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        MapFragment mapFragment = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.frgMap);
        getActivity().getFragmentManager().beginTransaction().remove(mapFragment).commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        //bật các chức năng
        //bật my location
        map.setMyLocationEnabled(true);
        //thêm tọa độ của FPT Polytechnic
        //di chuyển camera đến tọa độ
        map.addMarker(new MarkerOptions().position(new LatLng(10.7909, 106.6822)).title("FPT Polytechnic HCM CS1"));
        map.addMarker(new MarkerOptions().position(new LatLng(12.6868781,108.0512069)).title("FPT Polytechnic Tây Nguyên"));
        map.addMarker(new MarkerOptions().position(new LatLng(10.8529444,106.6273561)).title("FPT Polytechnic HCM CS3"));
        map.addMarker(new MarkerOptions().position(new LatLng(10.8118623,106.6777233)).title("FPT Polytechnic HCM CS2"));

        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(10.7909, 106.6822)));

        //bật các UI
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
    }
}
