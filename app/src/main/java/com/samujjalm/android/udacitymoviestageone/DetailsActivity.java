package com.samujjalm.android.udacitymoviestageone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String image = bundle.getString("image");
        String rating = bundle.getString("rating");
        String synopsis = bundle.getString("synopsis");
        String releaseDate  = bundle.getString("release_date");

        //initialize and set the image description
        textView = (TextView) findViewById(R.id.title);
        textView.setText(Html.fromHtml(title));
        textView = (TextView)findViewById(R.id.rating);
        textView.setText(Html.fromHtml(rating));
        textView = (TextView)findViewById(R.id.synopsis);
        textView.setText(Html.fromHtml(synopsis));
        textView = (TextView)findViewById(R.id.release_date);
        textView.setText(Html.fromHtml(releaseDate));

        //Set image url
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        Picasso.with(this).load(image).into(imageView);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
