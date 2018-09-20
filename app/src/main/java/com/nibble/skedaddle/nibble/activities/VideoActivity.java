package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.nibble.skedaddle.nibble.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private String[] customerdetails;
    private RelativeLayout bHome, bBookings, bProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final Intent video = getIntent();
        customerdetails = video.getStringArrayExtra("customerdetails");

        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bProfile);
        videoView = findViewById(R.id.videoView);


        //Menu Bar functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(VideoActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                VideoActivity.this.startActivity(Home);
                VideoActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(VideoActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                VideoActivity.this.startActivity(Bookings);
                VideoActivity.this.finish();
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(VideoActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                VideoActivity.this.startActivity(myprofile);
                VideoActivity.this.finish();
            }
        });
        //



        String path = "android.resource://" + getPackageName() + "/" + R.raw.videotut;
        videoView.setVideoURI(Uri.parse(path));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();


    }
}
