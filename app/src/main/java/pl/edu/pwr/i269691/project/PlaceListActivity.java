package pl.edu.pwr.i269691.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Place;

public class PlaceListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private PlaceAdapter placeAdapter;
    public RecyclerView recyclerView;
    Connector connector = new Connector();
    private Integer current=0;
    ArrayList<Place> places = new ArrayList<Place>();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connector.Connect();
        places = connector.Get_Places();
        initRecyclerView();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.place);

    }

    private void initRecyclerView(){
        PlaceAdapter.OnPlaceClickListener onPlaceClickListener = new PlaceAdapter.OnPlaceClickListener() {
            @Override
            public void OnTaskClick(Place place) {
                Log.d("TAG","The choosed place is " + Integer.toString(place.getId()));
                Intent intent = new Intent(PlaceListActivity.this,PlaceInfoActivity.class);
                Intent intent2 = new Intent("Chosen place");
                current = place.getId();
                intent2.putExtra("Chosen",place.getId());
                intent.putExtra("Chosen",place.getId());
                LocalBroadcastManager.getInstance(PlaceListActivity.this).sendBroadcast(intent2);
                startActivity(intent);
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.list);
        placeAdapter = new PlaceAdapter(this, places, onPlaceClickListener);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.map:
                intent = new Intent(PlaceListActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.event:
                intent = new Intent(PlaceListActivity.this,EventsListActivity.class);
                startActivity(intent);
                return true;

            case R.id.place:
                return true;

            case R.id.accommodation:
                intent = new Intent(PlaceListActivity.this,AccommodationListActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}