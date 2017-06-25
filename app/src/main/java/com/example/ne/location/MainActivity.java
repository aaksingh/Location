package com.example.ne.location;

import android.content.ComponentCallbacks;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.*;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener,ComponentCallbacks{

    private final String LOG_TAG = "Location_services";
    private TextView mLatitude,mLongitude,cLatitude,cLongitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest,mLocationRequest1;
    private Location mLastlocatioon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLatitude=(TextView)findViewById(R.id.lati);
        mLongitude=(TextView)findViewById(R.id.longi);
        cLatitude=(TextView)findViewById(R.id.clati);
        cLongitude=(TextView)findViewById(R.id.clongi);
        buildGoogleApiClient();

    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    @Override
    protected void  onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onConnected(Bundle bundle) {

        mLastlocatioon=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastlocatioon!=null){
            mLatitude.setText(String.valueOf(mLastlocatioon.getLatitude()));
            mLongitude.setText(String.valueOf(mLastlocatioon.getLongitude()));
        }
        mLocationRequest1 = LocationRequest.create();
        mLocationRequest1.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest1.setInterval(1000); // Update location every second
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest1, (LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "GoogleApiClient connection has failed");
    }

    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, location.toString());

        cLatitude.setText(Double.toString(location.getLatitude()));
        cLongitude.setText(Double.toString(location.getLongitude()));
    }



}
