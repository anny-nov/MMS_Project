package pl.edu.pwr.i269691.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Place;
import pl.edu.pwr.i269691.project.entity.Tour;

public class TourInfoActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    Fragment mapFragment = new MapFragment();

    private TourAdapter TourAdapter;
    public RecyclerView recyclerView;
    Connector connector = new Connector();
    private Integer current=0;
    ArrayList<Place> places = new ArrayList<Place>();
    TextView nameView;
    TextView descriptionName;
    Button button;

    boolean isClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connector.Connect();
        places = connector.Get_CityTourPlaces();
        initRecyclerView();

        nameView = (TextView) findViewById(R.id.nameView);
        descriptionName = (TextView) findViewById(R.id.descriptionName);
        button = (Button) findViewById(R.id.button);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.map);

        try {
            connector.Connect();
            Tour tour = connector.Get_Tour_by_ID(1);
            nameView.setText(tour.getName());
            descriptionName.setText(tour.getDescription());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isClicked) {
                    isClicked = true;
                    nameView.setVisibility(View.GONE);
                    descriptionName.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mapFragment).commit();
                }
                else {
                    isClicked = false;
                    nameView.setVisibility(View.VISIBLE);
                    descriptionName.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .remove(mapFragment).commit();

                }
            }
        });


    }

    private void initRecyclerView(){
        TourAdapter.OnPlaceClickListener onPlaceClickListener = new TourAdapter.OnPlaceClickListener() {
            @Override
            public void OnTaskClick(Place place) {
                Log.d("TAG","The choosed place is " + Integer.toString(place.getId()));
                Intent intent = new Intent(TourInfoActivity.this,PlaceInfoActivity.class);
                Intent intent2 = new Intent("Chosen place");
                current = place.getId();
                intent2.putExtra("Chosen",place.getId());
                LocalBroadcastManager.getInstance(TourInfoActivity.this).sendBroadcast(intent2);
                startActivity(intent);
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.list);
        TourAdapter = new TourAdapter(this, places, onPlaceClickListener);
        recyclerView.setAdapter(TourAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.map:
                return true;

            case R.id.event:
                intent = new Intent(TourInfoActivity.this,EventsListActivity.class);
                startActivity(intent);
                return true;

            case R.id.place:
                intent = new Intent(TourInfoActivity.this,PlaceListActivity.class);
                startActivity(intent);
                return true;

            case R.id.accommodation:
                intent = new Intent(TourInfoActivity.this,AccommodationListActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

}
