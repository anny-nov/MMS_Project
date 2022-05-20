package pl.edu.pwr.i269691.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.i269691.project.entity.Comment;
import pl.edu.pwr.i269691.project.entity.Place;

public class PlaceInfoActivity extends AppCompatActivity {

    FirebaseStorage storage;

    Fragment mapFragment;

    private ImageView imagePlayPause;
    private TextView timer;
    private SeekBar player;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    String url;
    Uri video_uri;

    private TextView name;
    private TextView description;
    private TextView address;
    private RatingBar totalRate;

    private Button button;
    private EditText commentText;
    private EditText userText;
    private RatingBar ratingBar;
    private Place place;
    private Connector connector = new Connector();

    VideoView video;
    MediaController mediaController;
    ImageSlider imageSlider;
    List<SlideModel> slideModelList;

    private CommentAdapter myAdapter;
    public RecyclerView recyclerView;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        ActivityCompat.requestPermissions(PlaceInfoActivity.this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        connector.Connect();

        Intent intent = getIntent();
        place = connector.Get_Place_by_ID(intent.getIntExtra("Chosen",1));

        Bundle bundle = new Bundle();
        bundle.putDouble("Latitude",place.getLatitude());
        bundle.putDouble("Longitude",place.getLongitude());
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mapFragment).commit();

        name = (TextView) findViewById(R.id.Place_name);
        description = (TextView) findViewById(R.id.description);
        totalRate = (RatingBar) findViewById(R.id.ratingTotal);
        address = (TextView) findViewById(R.id.address);
        imagePlayPause = (ImageView) findViewById(R.id.imagePlay);
        button = (Button) findViewById(R.id.send_comment);
        commentText = (EditText) findViewById(R.id.comment_input);
        userText = (EditText) findViewById(R.id.username);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        timer = (TextView) findViewById(R.id.timer);
        player = (SeekBar) findViewById(R.id.player);
        mediaPlayer = new MediaPlayer();
        imageSlider = (ImageSlider) findViewById(R.id.imgSlider);
        slideModelList = new ArrayList<>();
        Log.e("Comments","Comments size is " + place.getComment().size());
        initRecyclerView();
        int i = 1;
        for (String img: place.getImg()
             ) {
            slideModelList.add(new SlideModel(img,"Img"+i));
            i++;

        }
        imageSlider.setImageList(slideModelList,true);

        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://cityguide-anya.appspot.com").child("bridge.mp4");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                String text_url = uri.toString().replaceAll(" ","%20");
                video_uri=Uri.parse(text_url);
                Log.i("video",video_uri.toString());
            }});
        video = (VideoView) findViewById(R.id.videoView);
        video.setVideoURI(video_uri);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.requestFocus(0);
        video.start();

        player.setMax(100);
        prepareMediaPlayer();
        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    handler.removeCallbacks(Updater);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.ic_play);
                } else
                {
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.ic_pause);
                    UpdateSeekBar();
                }
            }
        });
        prepareMediaPlayer();
        player.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                timer.setText(SecondsForTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                player.setSecondaryProgress(i);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.setProgress(0);
                imagePlayPause.setImageResource(R.drawable.ic_play);
                timer.setText(R.string.zero);
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });

        name.setText(place.getName());
        description.setText(place.getDescription());
        address.setText(place.getAddress());
        place.CountRating();
        totalRate.setRating(place.getRating());
            button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String username = userText.getText().toString();
                    String comment = commentText.getText().toString();
                    int rating = (int)ratingBar.getRating();
                    connector.Post_RatingPlace(username,comment,rating,place.getId());
                    Log.e("Comments","Comments size is " + place.getComment().size());
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }

    private void prepareMediaPlayer()
    {
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://cityguide-anya.appspot.com").child(place.getAudio());
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    url = uri.toString();
                    //Handle whatever you're going to do with the URL here
                }
            });
            mediaPlayer.setDataSource(url.replaceAll(" ","%20"));
            mediaPlayer.prepare();
        } catch (Exception exception)
        {
            Toast.makeText(this,"This attraction does not contain audio guide!",Toast.LENGTH_SHORT).show();
        }
    }

    private String SecondsForTimer(long millisecs)
    {
        String timerString = "";
        String secondString;
        int hours = (int)(millisecs/(1000*60*60));
        int minutes = (int)(millisecs % (1000*60*60))/(1000*60);
        int seconds = (int)((millisecs % (100*60*60)) % (1000*60) / 1000);

        if(hours>0)
        {
            timerString=hours+":";
        }
        if(seconds<10)
        {
            secondString="0"+seconds;
        }
        else {
            secondString = ""+seconds;
        }
        timerString=timerString+minutes+":"+secondString;
        return timerString;
    }

    private Runnable Updater = new Runnable() {
        @Override
        public void run() {
            UpdateSeekBar();
            long CurrentDuration = mediaPlayer.getCurrentPosition();
            timer.setText(SecondsForTimer(CurrentDuration));
        }
    };

    private void UpdateSeekBar()
    {
        if(mediaPlayer.isPlaying())
        {
            player.setProgress((int)(((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration())*100));
            handler.postDelayed(Updater,1000);
        }
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.comment_view);
        myAdapter = new CommentAdapter(this, place.getComment());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}