package com.example.akki.daybox_code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class ItemsDisplay extends AppCompatActivity {

    ArrayList<String> item_name=new ArrayList<String>();
    ArrayList<String> item_pic_link=new ArrayList<String>();
    ArrayList<String> item_id=new ArrayList<String>();
    ArrayList<String> item_price=new ArrayList<String>();
    int start=0;
    String category;
    RecyclerView r;
    GridLayoutManager mLayoutManager;
    DbtaskcategoryItems db;
    CustomAdapterItems ca;
    String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_display);
        flag="0";

        category=getIntent().getStringExtra("category");
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(category);
        tb.setTitleTextColor(0XFFFFFFFF);
        tb.setNavigationIcon(R.mipmap.ic_back_button);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        r=(RecyclerView)findViewById(R.id.recyclerviewid);
        mLayoutManager =new GridLayoutManager(this,2);
        r.setLayoutManager(mLayoutManager);
        r.setHasFixedSize(true);
        r.setItemAnimator(new SlideInUpAnimator());
        ca= new CustomAdapterItems(this,item_name,item_pic_link,item_id,item_price,this);

        db=new DbtaskcategoryItems(this,this,item_name,item_pic_link,item_id,item_price,r,true,ca);
        db.execute(category,String.valueOf(start));


        r.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                flag="0";
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();


                if (flag.compareTo("0")==0) {

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        Toast.makeText(ItemsDisplay.this, "Loading More Items", Toast.LENGTH_SHORT).show();
                        flag="1";
                        start = start + 10;
                        db = new DbtaskcategoryItems(ItemsDisplay.this, ItemsDisplay.this, item_name, item_pic_link, item_id, item_price, r, false, ca);
                        db.execute(category, String.valueOf(start));
                        return;

                    }
                }
             //   Toast.makeText(ItemsDisplay.this, flag, Toast.LENGTH_SHORT).show();
            }
        });



    }
}
