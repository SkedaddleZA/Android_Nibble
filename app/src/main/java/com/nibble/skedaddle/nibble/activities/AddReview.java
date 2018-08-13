package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nibble.skedaddle.nibble.R;

import java.util.HashMap;
import java.util.Map;

public class AddReview extends AppCompatActivity {

    private String[] restaurantfulldetails, customerdetails;
    private String rating = "1";
    private ImageView iv1, iv2, iv3, iv4, iv5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        final Intent addReview = getIntent();
        restaurantfulldetails = addReview.getStringArrayExtra("restaurantdetails");
        customerdetails = addReview.getStringArrayExtra("customerdetails");

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);

        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("star", R.drawable.star);
        map.put("nostar", R.drawable.nostar);



        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("nostar"));
                iv3.setImageResource(map.get("nostar"));
                iv4.setImageResource(map.get("nostar"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("nostar"));
                iv4.setImageResource(map.get("nostar"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("star"));
                iv4.setImageResource(map.get("nostar"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("star"));
                iv4.setImageResource(map.get("star"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("star"));
                iv4.setImageResource(map.get("star"));
                iv5.setImageResource(map.get("star"));
            }
        });



    }
}
