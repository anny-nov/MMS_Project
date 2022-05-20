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

import pl.edu.pwr.i269691.project.entity.Event;
import pl.edu.pwr.i269691.project.entity.Place;

public class EventsListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private EventAdapter myAdapter;
    public RecyclerView recyclerView;
    Connector connector = new Connector();
    private Integer current=0;
    ArrayList<Event> events = new ArrayList<Event>();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connector.Connect();
        events = connector.Get_Events();
        initRecyclerView();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.event);

    }

    private void initRecyclerView(){
        EventAdapter.OnEventClickListener onEventClickListener = new EventAdapter.OnEventClickListener() {
            @Override
            public void OnTaskClick(Event event) {
                Log.d("TAG","The choosed place is " + Integer.toString(event.getId()));
                Intent intent = new Intent(EventsListActivity.this,EventInfoActivity.class);
                Intent intent2 = new Intent("Chosen element");
                current = event.getId();
                intent2.putExtra("Chosen",event.getId());
                LocalBroadcastManager.getInstance(EventsListActivity.this).sendBroadcast(intent2);
                startActivity(intent);
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.list);
        myAdapter = new EventAdapter(this, events, onEventClickListener);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.map:
                intent = new Intent(EventsListActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.event:
                return true;

            case R.id.place:
                intent = new Intent(EventsListActivity.this, PlaceListActivity.class);
                startActivity(intent);
                return true;

            case R.id.accommodation:
                intent = new Intent(EventsListActivity.this, AccommodationListActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}