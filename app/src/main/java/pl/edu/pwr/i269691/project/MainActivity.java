package pl.edu.pwr.i269691.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Fragment mapFragment = new MapFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent;
        intent = new Intent(MainActivity.this, AllToursActivity.class);
        startActivity(intent);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mapFragment).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.map);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.map:
                return true;

            case R.id.event:
                intent = new Intent(MainActivity.this,EventsListActivity.class);
                startActivity(intent);
                return true;

            case R.id.place:
                intent = new Intent(MainActivity.this,PlaceListActivity.class);
                startActivity(intent);
                return true;

            case R.id.accommodation:
                intent = new Intent(MainActivity.this,AccommodationListActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}