// Path: app/src/main/java/com/example/findob/DetailedViewActivity.java

package com.example.lostandfound;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailedViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        ImageView imageView = findViewById(R.id.detailed_image);
        TextView titleView = findViewById(R.id.detailed_title);
        TextView descriptionView = findViewById(R.id.detailed_description);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");

        Glide.with(this).load(imageUrl).into(imageView);
        titleView.setText(title);
        descriptionView.setText(description);
    }
}
