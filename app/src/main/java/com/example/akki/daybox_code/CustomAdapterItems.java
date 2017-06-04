package com.example.akki.daybox_code;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Akki on 04-06-2017.
 */

public class CustomAdapterItems extends RecyclerView.Adapter<CustomAdapterItems.ViewHolder> {

    private Context context;
    private ArrayList<String> iname;
    private ArrayList<String> ipic;
    private ArrayList<String> iid;
    ArrayList<String> item_price;
    Activity activity;

    public CustomAdapterItems(Context context, ArrayList<String> name, ArrayList<String> ilink, ArrayList<String> iid, ArrayList<String> item_price,Activity activity) {

        this.context = context;
        this.iname = name;
        this.ipic = ilink;
        this.item_price=item_price;
        this.iid = iid;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv.setText(iname.get(position) + "\n \n Rs. " + item_price.get(position));
        //Toast.makeText(context,name.get(position),Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(ipic.get(position)).into(holder.im);

    }

    @Override
    public int getItemCount() {

        return iname.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView im;
        TextView tv;


        public ViewHolder(View view) {
            super(view);

            im = (ImageView) view.findViewById(R.id.imageView);
            tv = (TextView) view.findViewById(R.id.textView);

            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
           //   Toast.makeText(context,iid.get(getPosition()),Toast.LENGTH_SHORT).show();
                DbtaskItemFull db=new DbtaskItemFull(context,activity);
                db.execute(iid.get(getPosition()));
        }


    }
}

