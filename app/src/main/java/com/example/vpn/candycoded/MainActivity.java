package com.example.vpn.candycoded;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> candy_list;
    ArrayAdapter<String> adapter;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        setProgressBarIndeterminateVisibility(true);
        linearLayout = findViewById(R.id.linlaHeaderProgress);
        candy_list = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_candy,
                R.id.text_view_candy,
                candy_list
        );
        linearLayout.setVisibility(View.VISIBLE);
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
                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject object = temp.getJSONObject(i);
                                candy_list.add(object.getString("name"));
                            }
                            adapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        // Using Gson to put data in
                        Gson gson = new GsonBuilder().create();
                        Candy[] candies = gson.fromJson(responseString, Candy[].class);

                        // Add candy names to ListView
                        adapter.clear();
                        for (Candy candy : candies) {
                            adapter.add(candy.name);
                        }

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
                detailIntent.putExtra("candy_name", candy_list.get(i));
                startActivity(detailIntent);
            }
        });


    }

}

