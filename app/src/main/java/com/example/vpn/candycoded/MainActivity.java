package com.example.vpn.candycoded;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    private Candy[] candies;
    private CandyCursorAdapter adapter;
    private CandyDbHelper candyDbHelper = new CandyDbHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        setProgressBarIndeterminateVisibility(true);
        linearLayout = findViewById(R.id.linlaHeaderProgress);
        linearLayout.setVisibility(View.VISIBLE);

        SQLiteDatabase db = candyDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
        adapter = new CandyCursorAdapter(this, cursor);

        ListView listView = this.findViewById(R.id.list_view_candy);
        listView.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(
                "https://vast-brushlands-23089.herokuapp.com/main/api/?format=json",
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e("AsyncHttpClient", "error = " + responseString);
                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "NO internet Connection", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("AsyncHttpClient", "response = " + responseString);


                        /*try {
                            JSONArray temp = new JSONArray(responseString);
                            candies = new Candy[temp.length()];
                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject object = temp.getJSONObject(i);
                                candies[i].name = object.getString("name");
                                candies[i].price = object.getString("price");
                                candies[i].description = object.getString("description");
                                candies[i].imageURL = object.getString("imageURL");
                            }
                            adapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        // Using Gson to put data in
                        Gson gson = new GsonBuilder().create();
                        candies = gson.fromJson(responseString, Candy[].class);

                        // Add candy names to ListView
                        /*adapter.clear();
                        for (Candy candy : candies) {
                            adapter.add(candy.name);
                        }*/

                        // Put data in Local DB
                        addCandiesToDatabase(candies);

                        // updating cursorAdapter with the latest database entries
                        SQLiteDatabase db = candyDbHelper.getWritableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
                        adapter.changeCursor(cursor);

                        // Notify the adapter
                        //adapter.notifyDataSetChanged();
                        // Remove the loading icon
                        linearLayout.setVisibility(View.GONE);

                    }
                }

        );


        TextView textView = this.findViewById(R.id.text_view_title);
        textView.setText(R.string.products_title);

        // An arrayList to feed the listView
        // Aad toast to listItems
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                /*detailIntent.putExtra("candy_name", candies[i].name);
                detailIntent.putExtra("candy_image", candies[i].image);
                detailIntent.putExtra("candy_price", candies[i].price);
                detailIntent.putExtra("candy_desc", candies[i].description);*/
                //Log.d("DetailActivity","Candie details before sending : " + candies[i].image);

                // Insted of passing all details, just pass the index
                detailIntent.putExtra("position", i);
                startActivity(detailIntent);
            }
        });


    }

    public void addCandiesToDatabase(Candy[] candies) {
        SQLiteDatabase db = candyDbHelper.getWritableDatabase();

        for (Candy candy : candies) {
            ContentValues values = new ContentValues();
            values.put(CandyContract.CandyEntry.COLUMN_NAME_NAME, candy.name);
            values.put(CandyContract.CandyEntry.COLUMN_NAME_DESC, candy.description);
            values.put(CandyContract.CandyEntry.COLUMN_NAME_PRICE, candy.price);
            values.put(CandyContract.CandyEntry.COLUMN_NAME_IMAGE, candy.image);

            db.insert(CandyContract.CandyEntry.TABLE_NAME, null, values);
        }
    }

}

