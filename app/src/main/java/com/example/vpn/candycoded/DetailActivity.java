package com.example.vpn.candycoded;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = DetailActivity.this.getIntent();

        /*String candyName = "";
        if (intent!=null && intent.hasExtra("candy_name")) {
            candyName = intent.getStringExtra("candy_name");
        }*/

        if (intent.hasExtra("position")) {
            // define a position
            int position = intent.getIntExtra("position", 0);

            // query all candies and move our cursor to current position
            CandyDbHelper candyDbHelper = new CandyDbHelper(this);
            SQLiteDatabase db = candyDbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            cursor.moveToPosition(position);

            // get value in column
            // int columnIndex = cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_NAME);
            String candyName = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_NAME));
            String candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_PRICE));
            String candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_DESC));
            String candyImage = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_IMAGE));

            // Update in the View
            TextView textViewName = (TextView) this.findViewById(R.id.detail_name);
            textViewName.setText(candyName);

            ImageView imageView = (ImageView) this.findViewById(R.id.detail_image);
            Picasso.with(this).load(candyImage).into(imageView);

            TextView textViewPrice = (TextView) this.findViewById(R.id.detail_price);
            textViewPrice.setText("$" + candyPrice);

            TextView textViewDesc = (TextView) this.findViewById(R.id.detail_desc);
            textViewDesc.setText(candyDesc);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(candyName);

        }


        //Log.d("DetailActivity", "Intent Data: " + candyImage + ", " + candyPrice + ", " + candyDesc);

    }
}
