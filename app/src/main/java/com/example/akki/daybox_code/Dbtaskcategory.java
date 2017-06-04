package com.example.akki.daybox_code;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Akki on 03-06-2017.
 */

public class Dbtaskcategory extends AsyncTask<String,Void,String>

{
    Context ctx;
    Activity activity;
    ListView l;
    String Jo,json_string;
    ProgressDialog mProgress;
    ArrayList<String> items_category;
    CustomListAdapterCatagory ca;

    public Dbtaskcategory(Context ctx,Activity activity,ListView l,ArrayList<String> items_category) {
        this.ctx=ctx;
        this.activity=activity;
        this.l=l;
        this.items_category=items_category;
    }

    @Override
protected String doInBackground(String... params) {

        String ip_url="http://api.smartprix.com/simple/v1?type=categories&key=NVgien7bb7P5Gsc8DWqc";

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
        mProgress = new ProgressDialog(ctx);
        mProgress.setMessage("Loading... ");
        mProgress.show();
        mProgress.setCancelable(false);
        }

@Override
protected void onPostExecute(String result) {
        json_string=result;

        JSONObject j;
        JSONArray jsonArray;
        int count = 0;
        String t;


        try {

            j = new JSONObject(json_string);
             if(j.get("request_status").equals("SUCCESS")) {
                 jsonArray = j.getJSONArray("request_result");
                 ca = new CustomListAdapterCatagory(ctx, activity, items_category);

                 while (count < jsonArray.length()) {
                     t = jsonArray.getString(count);
                     //    Toast.makeText(ctx," " + t +" " , Toast.LENGTH_LONG).show();

                     items_category.add(t);


                     count++;
                 }

                 l.setAdapter(ca);
                 mProgress.dismiss();
             }

            else{
                 Toast.makeText(ctx,"Server Error Please Try Again",Toast.LENGTH_SHORT).show();
             }

        } catch (JSONException e) {
        e.printStackTrace();
        }
    mProgress.dismiss();
        }
        }
