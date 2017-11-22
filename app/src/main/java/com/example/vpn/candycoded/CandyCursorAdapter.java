package com.example.vpn.candycoded;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by vpn on 11/22/17.
 */

public class CandyCursorAdapter extends CursorAdapter {
    public CandyCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(
                R.layout.list_item_candy, viewGroup, false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.text_view_candy);
        String candyName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        textView.setText(candyName);
    }
}
