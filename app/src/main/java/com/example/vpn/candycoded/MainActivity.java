package com.example.vpn.candycoded;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = this.findViewById(R.id.text_view_title);
        textView.setText(R.string.products_title);

        // An arrayList to feed the listView
        final ArrayList<String> candy_list = new ArrayList<>();
        candy_list.add("Tropical Wave");
        candy_list.add("Berry Bouncer");
        candy_list.add("Grape Gummer");
        candy_list.add("Apple of My Eye");
        candy_list.add("ROYGBIV Spinner");
        candy_list.add("Much Minty");
        candy_list.add("So Fresh");
        candy_list.add("Sassy Sandwich Cookie");
        candy_list.add("Uni-pop");
        candy_list.add("Strawberry Surprise");
        candy_list.add("Wish Upon a Star");
        candy_list.add("Planetory Pops");
        candy_list.add("Watermelon Like Whoa");
        candy_list.add("Twist 'n' Shout");
        candy_list.add("Beary Squad Goals");

        // Create an Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_candy,
                R.id.text_view_candy,
                candy_list
        );
        ListView listView = this.findViewById(R.id.list_view_candy);
        listView.setAdapter(adapter);

        // Add toast on app load
        Context context = this;
        String text = "App loaded";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

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
