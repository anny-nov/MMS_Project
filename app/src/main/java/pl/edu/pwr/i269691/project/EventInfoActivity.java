package pl.edu.pwr.i269691.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.i269691.project.entity.Accommodation;
import pl.edu.pwr.i269691.project.entity.Event;

public class EventInfoActivity extends AppCompatActivity {
    Fragment mapFragment;

    private TextView name;
    private TextView description;
    private TextView address;

    private Event event;
    private Connector connector = new Connector();

    ImageSlider imageSlider;
    List<SlideModel> slideModelList;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        ActivityCompat.requestPermissions(EventInfoActivity.this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        connector.Connect();

        Intent intent = getIntent();
        event = connector.Get_Event_by_ID(intent.getIntExtra("Chosen",1));

        Bundle bundle = new Bundle();
        bundle.putDouble("Latitude",event.getLatitude());
        bundle.putDouble("Longitude",event.getLongitude());
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout2, mapFragment).commit();

        name = (TextView) findViewById(R.id.event_name);
        description = (TextView) findViewById(R.id.description_event);
        address = (TextView) findViewById(R.id.address_event);
        imageSlider = (ImageSlider) findViewById(R.id.imgSlider_event);
        slideModelList = new ArrayList<>();
        int i = 1;
        for (String img: event.getImg()
        ) {
            slideModelList.add(new SlideModel(img,"Img"+i));
            i++;

        }
        imageSlider.setImageList(slideModelList,true);
        name.setText(event.getName());
        description.setText(event.getDescription());
        address.setText(event.getAddress());
    }
}
