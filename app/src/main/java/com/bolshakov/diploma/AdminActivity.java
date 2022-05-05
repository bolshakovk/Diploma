package com.bolshakov.diploma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bolshakov.diploma.drawing.FetchURL;
import com.bolshakov.diploma.drawing.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap gMap;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyLine;
    List<MarkerOptions> markerOptionsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getDirection = findViewById(R.id.btnPath);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(AdminActivity.this)
                        .execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });
        place1 = new MarkerOptions().position(new LatLng(27.6345, 85.1235)).title("Location1");
        place2 = new MarkerOptions().position(new LatLng(28.32156, 85.2313578)).title("Location2");
        markerOptionsList.add(place1);
        markerOptionsList.add(place2);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gMap = googleMap;
        gMap.addMarker(place1);
        gMap.addMarker(place2);

        showAllMarkers();
    }
    private void showAllMarkers(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions markerOptions: markerOptionsList){
            builder.include(markerOptions.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int wight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (wight * 0.30);
        CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngBounds(bounds, wight, height, padding);
        gMap.animateCamera(cameraUpdate);
    }

    private String getUrl(LatLng origin, LatLng destination, String directionMode){
        String str_origin = "origin=" + origin.latitude + ", " + origin.longitude;
        String str_dest = "destination=" + destination.latitude + ", " + destination.longitude;
        String mode = "mode=" + directionMode;
        String parameter = str_origin + '&' + str_dest + '&' + mode;
        String format = "json";
        String url = "https://maps.googleapis.com/maps/api/direction/" + format +"?" + parameter + "&key=AIzaSyBMSPQ7HNRVgNrzxvHnCYdjVK9FPN8E_ZY";
        return  url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyLine != null){
            currentPolyLine.remove();
        }
        currentPolyLine = gMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}