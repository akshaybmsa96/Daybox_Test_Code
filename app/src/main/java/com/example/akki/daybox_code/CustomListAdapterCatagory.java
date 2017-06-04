package com.example.akki.daybox_code;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akki on 03-06-2017.
 */
public class CustomListAdapterCatagory extends ArrayAdapter<String> {

    Context context;
    Activity activity;
    ArrayList<String> categorynames;

    public CustomListAdapterCatagory(Context context, Activity activity, ArrayList<String> categorynames) {
        super(context, R.layout.catagory_layout, categorynames);
        this.context = context;
        this.categorynames=categorynames;
        this.activity = activity;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.catagory_layout, null, true);
        TextView tv=(TextView)rowView.findViewById(R.id.catagorytextxview);

        tv.setText(categorynames.get(position));

        return rowView;
    }

}