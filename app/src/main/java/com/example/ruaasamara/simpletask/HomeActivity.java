package com.example.ruaasamara.simpletask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.GradientDrawable;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
public class HomeActivity extends AppCompatActivity {
    Button follow, contact;
    Toolbar toolbar;
    GradientDrawable blue, red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        follow = (Button) findViewById(R.id.follow);
        contact = (Button) findViewById(R.id.contact);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        blue = (GradientDrawable) follow.getBackground();
        blue.setColor(ContextCompat.getColor(this, R.color.buttonBlue));
        red = (GradientDrawable) contact.getBackground();
        red.setColor(ContextCompat.getColor(this, R.color.buttonRed));




        follow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
