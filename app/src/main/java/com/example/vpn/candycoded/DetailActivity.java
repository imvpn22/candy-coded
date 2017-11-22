package com.example.vpn.candycoded;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = DetailActivity.this.getIntent();
        String candyName = "";

        if (intent.hasExtra("candy_name")) {
            candyName = intent.getStringExtra("candy_name");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(candyName);

        TextView textView = this.findViewById(R.id.text_view_name);
        textView.setText(candyName);
    }
}
