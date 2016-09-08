package com.soldiersofmobile.atmlocator;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.dao.Dao;
import com.soldiersofmobile.atmlocator.db.Atm;
import com.soldiersofmobile.atmlocator.db.Bank;
import com.soldiersofmobile.atmlocator.db.DbHelper;

import java.sql.SQLException;
import java.util.List;

public class AtmLocatorActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_locator);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DbHelper dbHelper = new DbHelper(this);
        try {
            Dao<Atm, ?> atmDao = dbHelper.getDao(Atm.class);
            List<Atm> atms = atmDao.queryForAll();
            for (Atm atm : atms) {
                LatLng sydney = new LatLng(atm.getLat(), atm.getLng());
                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(atm.getBank().getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.atm_locator, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, AddAtmActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
