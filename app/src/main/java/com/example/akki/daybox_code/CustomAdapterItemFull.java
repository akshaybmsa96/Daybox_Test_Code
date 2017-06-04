package com.example.akki.daybox_code;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Akki on 04-06-2017.
 */

public class CustomAdapterItemFull extends RecyclerView.Adapter<CustomAdapterItemFull.ViewHolder> {

    private Context context;
    private ArrayList<String> link;
    private ArrayList<String> buypiclogo;
    ArrayList<String> item_price;
    Activity activity;

    public CustomAdapterItemFull(Context context, ArrayList<String> link, ArrayList<String> item_price,ArrayList<String> buypiclogo,Activity activity) {

        this.context = context;
        this.link = link;
        this.buypiclogo = buypiclogo;
        this.item_price=item_price;
        this.activity = activity;
    }


    @Override
    public CustomAdapterItemFull.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.buy_from_layout, parent, false);
        CustomAdapterItemFull.ViewHolder holder = new CustomAdapterItemFull.ViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterItemFull.ViewHolder holder, int position) {

        holder.tv.setText("Rs. " + item_price.get(position));
        //Toast.makeText(context,name.get(position),Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(buypiclogo.get(position)).into(holder.im);

    }

    @Override
    public int getItemCount() {

        return link.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView im;
        TextView tv;
        Button button;


        public ViewHolder(View view) {
            super(view);

            im = (ImageView) view.findViewById(R.id.imageView);
            tv = (TextView) view.findViewById(R.id.textView);
            button=(Button)view.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(link.get(getPosition())));
                    context.startActivity(i);
                }
            });



        }


    }
}
