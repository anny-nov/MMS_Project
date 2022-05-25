package pl.edu.pwr.i269691.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Tour;

public class AllToursActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private AllToursAdapter AllToursAdapter;
    public RecyclerView recyclerView;
    Connector connector = new Connector();
    private Integer current=0;
    ArrayList<Tour> tours = new ArrayList<Tour>();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours_all);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connector.Connect();
        tours = connector.Get_Tours();
        initRecyclerView();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.map);

    }

    private void initRecyclerView(){
        AllToursAdapter.OnTourClickListener onTourClickListener = new AllToursAdapter.OnTourClickListener() {
            @Override
            public void OnTaskClick(Tour Tour) {
                Log.d("TAG","The chosen Tour is " + Integer.toString(Tour.getId()));
                Intent intent = new Intent(AllToursActivity.this,TourInfoActivity.class);
                Intent intent2 = new Intent("Chosen Tour");
                current = Tour.getId();
                intent2.putExtra("Chosen",Tour.getId());
                intent.putExtra("Chosen",Tour.getId());
                LocalBroadcastManager.getInstance(AllToursActivity.this).sendBroadcast(intent2);
                startActivity(intent);
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.list);
        AllToursAdapter = new AllToursAdapter(this, tours, onTourClickListener);
        recyclerView.setAdapter(AllToursAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.map:
                return true;

            case R.id.event:
                intent = new Intent(AllToursActivity.this,EventsListActivity.class);
                startActivity(intent);
                return true;

            case R.id.place:
                intent = new Intent(AllToursActivity.this,PlaceListActivity.class);
                startActivity(intent);
                return true;

            case R.id.accommodation:
                intent = new Intent(AllToursActivity.this,AccommodationListActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
