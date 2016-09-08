package com.soldiersofmobile.atmlocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.dao.Dao;
import com.soldiersofmobile.atmlocator.db.Atm;
import com.soldiersofmobile.atmlocator.db.Bank;
import com.soldiersofmobile.atmlocator.db.DbHelper;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAtmActivity extends AppCompatActivity {


    @BindView(R.id.pickLocationButton)
    Button mPickLocationButton;
    @BindView(R.id.addressTextView)
    TextView mAddressTextView;
    @BindView(R.id.latitudeTextView)
    TextView mLatitudeTextView;
    @BindView(R.id.longitudeTextView)
    TextView mLongitudeTextView;
    @BindView(R.id.bankSpinner)
    Spinner mBankSpinner;
    @BindView(R.id.saveButton)
    Button mSaveButton;
    @BindView(R.id.activity_add_atm)
    LinearLayout mActivityAddAtm;

    private String address;
    private LatLng location;
    private ArrayAdapter<Bank> mBankArrayAdapter;
    private DbHelper mDbHelper;
    private Dao<Atm, ?> mAtmDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atm);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBankArrayAdapter =
                new ArrayAdapter<Bank>(this, android.R.layout.simple_list_item_1);

        mBankSpinner.setAdapter(mBankArrayAdapter);

        mDbHelper = new DbHelper(this);
        try {
            mAtmDao = mDbHelper.getDao(Atm.class);
            Dao<Bank, Long> bankDao = mDbHelper.getDao(Bank.class);
            mBankArrayAdapter.addAll(bankDao.queryForAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private static final int REQUEST_CODE = 123;

    public void pickPlace() {

        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil.showErrorDialogFragment(e.getConnectionStatusCode(), this, null, REQUEST_CODE, null);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);

            address = place.getAddress().toString();
            location = place.getLatLng();

            updateUi();


        }
    }

    private void updateUi() {
        mAddressTextView.setText(address);
        mLatitudeTextView.setText(String.valueOf(location.latitude));
        mLongitudeTextView.setText(String.valueOf(location.longitude));
    }

    @OnClick({R.id.pickLocationButton, R.id.saveButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pickLocationButton:
                pickPlace();
                break;
            case R.id.saveButton:
                Atm atm = new Atm();
                atm.setAddress(address);
                atm.setLat(location.latitude);
                atm.setLng(location.longitude);
                atm.setBank((Bank) mBankSpinner.getSelectedItem());

                try {
                    mAtmDao.create(atm);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();

                break;
        }
    }
}
