package pl.edu.pwr.i269691.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.i269691.project.entity.Accommodation;

public class AccomodationInfoActivity extends AppCompatActivity {

    Fragment mapFragment;

    private TextView name;
    private TextView description;
    private TextView address;
    private RatingBar totalRate;

    private Button button;
    private EditText commentText;
    private EditText userText;
    private RatingBar ratingBar;
    private Accommodation accommodation;
    private Connector connector = new Connector();

    ImageSlider imageSlider;
    List<SlideModel> slideModelList;

    private CommentAdapter myAdapter;
    public RecyclerView recyclerView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_info);
        ActivityCompat.requestPermissions(AccomodationInfoActivity.this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        connector.Connect();

        Intent intent = getIntent();
        accommodation = connector.Get_Accommodation_by_ID(intent.getIntExtra("Chosen",1));

        Bundle bundle = new Bundle();
        bundle.putDouble("Latitude",accommodation.getLatitude());
        bundle.putDouble("Longitude",accommodation.getLongitude());
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout2, mapFragment).commit();

        name = (TextView) findViewById(R.id.Accommodation_name);
        description = (TextView) findViewById(R.id.description_acc);
        totalRate = (RatingBar) findViewById(R.id.ratingTotal_acc);
        address = (TextView) findViewById(R.id.address_acc);
        button = (Button) findViewById(R.id.send_comment_acc);
        commentText = (EditText) findViewById(R.id.comment_input_acc);
        userText = (EditText) findViewById(R.id.username_acc);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_acc);
        imageSlider = (ImageSlider) findViewById(R.id.imgSlider_acc);
        slideModelList = new ArrayList<>();
        initRecyclerView();
        int i = 1;
        for (String img: accommodation.getImg()
        ) {
            slideModelList.add(new SlideModel(img,"Img"+i));
            i++;

        }
        imageSlider.setImageList(slideModelList,true);

        name.setText(accommodation.getName());
        description.setText(accommodation.getDescription());
        address.setText(accommodation.getAddress());
        accommodation.CountRating();
        totalRate.setRating(accommodation.getRating());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String username = userText.getText().toString();
                    String comment = commentText.getText().toString();
                    int rating = (int)ratingBar.getRating();
                    connector.Post_RatingAcc(username,comment,rating, accommodation.getId());
                    Log.e("Comments","Comments size is " + accommodation.getComment().size());
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.comment_view_acc);
        myAdapter = new CommentAdapter(this, accommodation.getComment());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    }
