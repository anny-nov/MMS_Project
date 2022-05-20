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

import pl.edu.pwr.i269691.project.entity.Accommodation;
import pl.edu.pwr.i269691.project.entity.Event;

public class AccommodationListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private AccommodationAdapter myAdapter;
    public RecyclerView recyclerView;
    Connector connector = new Connector();
    private Integer current=0;
    ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connector.Connect();
        accommodations = connector.Get_Accomodations();
        initRecyclerView();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.accommodation);

    }

    private void initRecyclerView(){
        AccommodationAdapter.OnAccommodationClickListener onAccommodationClickListener = new AccommodationAdapter.OnAccommodationClickListener() {
            @Override
            public void OnTaskClick(Accommodation acc) {
                Log.d("TAG","The choosed place is " + Integer.toString(acc.getId()));
                Intent intent = new Intent(AccommodationListActivity.this,AccomodationInfoActivity.class);
                Intent intent2 = new Intent("Chosen element");
                current = acc.getId();
                intent2.putExtra("Chosen",acc.getId());
                LocalBroadcastManager.getInstance(AccommodationListActivity.this).sendBroadcast(intent2);
                startActivity(intent);
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.list);
        myAdapter = new AccommodationAdapter(this, accommodations, onAccommodationClickListener);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.map:
                intent = new Intent(AccommodationListActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.event:
                intent = new Intent(AccommodationListActivity.this, EventsListActivity.class);
                startActivity(intent);
                return true;

            case R.id.place:
                intent = new Intent(AccommodationListActivity.this, PlaceListActivity.class);
                startActivity(intent);
                return true;

            case R.id.accommodation:
                return true;
        }
        return false;
    }
}