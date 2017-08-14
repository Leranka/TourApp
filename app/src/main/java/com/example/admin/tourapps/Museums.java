package com.example.admin.tourapps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

/**
 * Created by Admin on 8/1/2017.
 */

public class Museums extends AppCompatActivity {

    TextView description, name;
    ImageView mainImage, mapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.museums);

        Firebase.setAndroidContext(this);
        mainImage = (ImageView) findViewById(R.id.imageView);
        mapImage = (ImageView) findViewById(R.id.mapsImage);

        description = (TextView) findViewById(R.id.descTxt);
        name = (TextView) findViewById(R.id.nameTxt);

        Intent i = getIntent();
        Pojo museums = (Pojo) i.getSerializableExtra("select");

        description.setText(museums.getDescription());
        name.setText(museums.getName());


        Glide.with(this)
                .load(museums.getImage())
                .into(mainImage);

        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=an+address+city"));
                startActivity(intent);
            }
        });
    }

}

