package com.example.akki.daybox_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ArrayList<String> items_category=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView l=(ListView)findViewById(R.id.catagorylistview);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Categories");
        tb.setTitleTextColor(0XFFFFFFFF);

        Dbtaskcategory db=new Dbtaskcategory(this,this,l,items_category);
        db.execute();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(Home.this,ItemsDisplay.class);
                intent.putExtra("category",items_category.get(i));
                startActivity(intent);

            }
        });

    }
}
