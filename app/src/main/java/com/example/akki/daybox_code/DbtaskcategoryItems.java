package com.example.akki.daybox_code;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Akki on 03-06-2017.
 */

public class DbtaskcategoryItems extends AsyncTask<String,Void,String>

{
    Context ctx;
    Activity activity;
    String Jo,json_string;
    ProgressDialog mProgress;
    ArrayList<String> item_names;
    ArrayList<String> item_pic_link;
    ArrayList<String> item_id;
    CustomAdapterItems ca;
    ArrayList<String> item_price;
    RecyclerView r;
    String start;
    Boolean ini;

    public DbtaskcategoryItems(Context ctx,Activity activity,ArrayList<String> item_names,ArrayList<String> item_pic_link,
            ArrayList<String> item_id,ArrayList<String> item_price,RecyclerView r,Boolean ini,CustomAdapterItems ca) {
        this.ctx=ctx;
        this.activity=activity;
        this.item_names=item_names;
        this.item_id=item_id;
        this.ca=ca;
        this.item_pic_link=item_pic_link;
        this.item_price=item_price;
        this.ini=ini;
        this.r=r;
    }

    @Override
    protected String doInBackground(String... params) {
        String category=params[0];
        start=params[1];

        String ip_url="http://api.smartprix.com/simple/v1?type=search&key=NVgien7bb7P5Gsc8DWqc&start="+start+"&category="+category;

        try {

            URL url = new URL(ip_url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

            InputStream IS=httpURLConnection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(IS));
            StringBuilder sb=new StringBuilder();

            while((Jo=br.readLine())!=null)
            {
                sb.append(Jo+"\n");

            }

            br.close();
            IS.close();
            httpURLConnection.disconnect();
            String s=sb.toString().trim();
            //    Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            return s;

        }

        catch (Exception e) {
            e.printStackTrace();
        }


        return "Error";
    }

    @Override
    protected void onPreExecute() {
        if(ini) {
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Loading... ");
            mProgress.show();
            mProgress.setCancelable(false);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        json_string=result;

        JSONObject j,j1;
        JSONArray jsonArray;
        int count = 0;
        String t;


        try {

            j = new JSONObject(json_string);
            if(j.get("request_status").equals("SUCCESS")) {
                j1=j.getJSONObject("request_result");
                jsonArray = j1.getJSONArray("results");

                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
         //      Toast.makeText(ctx," " + t +" " , Toast.LENGTH_LONG).show();

                    t= jo.getString("name");
                    item_names.add(t);
                    t= jo.getString("id");
                    item_id.add(t);
                    t= jo.getString("img_url");
                    item_pic_link.add(t);
                    t=jo.getString("price");
                    item_price.add(t);


                    count++;
                }

                if(ini)
                {
                    r.setAdapter(ca);
                }
                else {
                    ca.notifyDataSetChanged();
                }



                if(ini)
             {
                 mProgress.dismiss();}
            }

            else{
                Toast.makeText(ctx,"Server Error Please Try Again",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(ini)
        {mProgress.dismiss();}
    }
}

