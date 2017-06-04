package com.example.akki.daybox_code;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_full);
        CustomAdapterItemFull ca;
        RecyclerView r;

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Full Description");
        tb.setTitleTextColor(0XFFFFFFFF);
        tb.setNavigationIcon(R.mipmap.ic_back_button);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final String name,imgurl,link,price;
        ArrayList<String> buylink,logolink,buyprice;
        ImageView im=(ImageView)findViewById(R.id.imageViewid);
        TextView tvname,tvprice,tvdes;
        tvname=(TextView)findViewById(R.id.itemtextid);
        tvprice=(TextView)findViewById(R.id.pricetextid);
        tvdes=(TextView)findViewById(R.id.typetextid);

        name=getIntent().getStringExtra("name");
        imgurl=getIntent().getStringExtra("img_url");
        price=getIntent().getStringExtra("price");
        link=getIntent().getStringExtra("link");

        buylink=getIntent().getStringArrayListExtra("links");
        logolink=getIntent().getStringArrayListExtra("logo");
        buyprice=getIntent().getStringArrayListExtra("prices");


        Picasso.with(this).load(imgurl).into(im);

        tvname.setText(name);
        tvprice.setText("₹ "+ price+"/-");
        tvdes.setText("Link : Click here");

        ca=new CustomAdapterItemFull(this,buylink,buyprice,logolink,this);
        r=(RecyclerView)findViewById(R.id.recyclerviewid);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setHasFixedSize(true);
        r.setAdapter(ca);

        if(buylink.isEmpty())
        {
            r.setVisibility(View.INVISIBLE);
        }

        tvdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });
    }
}
