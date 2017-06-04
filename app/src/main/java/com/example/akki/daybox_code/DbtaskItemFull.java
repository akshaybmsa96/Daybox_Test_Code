package com.example.akki.daybox_code;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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
 * Created by Akki on 04-06-2017.
 */

public class DbtaskItemFull extends AsyncTask<String,Void,String>

{
    Context ctx;
    Activity activity;
    String Jo,json_string;
    ProgressDialog mProgress;
    ArrayList<String> allprice=new ArrayList<String>();
    ArrayList<String> buylogo=new ArrayList<String>();
    ArrayList<String> buylink=new ArrayList<String>();

    public DbtaskItemFull(Context ctx,Activity activity) {
        this.ctx=ctx;
        this.activity=activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String id=params[0];

        String ip_url="http://api.smartprix.com/simple/v1?type=product_full&key=NVgien7bb7P5Gsc8DWqc&id="+id;

      //  String ip_url="http://api.smartprix.com/simple/v1?type=product_full&key=NVgien7bb7P5Gsc8DWqc&id=2179";

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

        JSONObject j,j1;
        JSONArray jsonArray;;
        String t;
        int count=0;


        try {

            j = new JSONObject(json_string);
            if(j.get("request_status").equals("SUCCESS")) {
                j1=j.getJSONObject("request_result");

                    //      Toast.makeText(ctx," " + t +" " , Toast.LENGTH_LONG).show();

                   Intent i=new Intent(ctx,ItemFull.class);
                t= j1.getString("name");
                i.putExtra("name",t);
                t= j1.getString("price");

                i.putExtra("price",t);
                t= j1.getString("img_url");
                i.putExtra("img_url",t);
                t= j1.getString("link");
                i.putExtra("link",t);

                jsonArray=j1.getJSONArray("prices");
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);

                    t = jo.getString("price");
                    allprice.add(t);

                    t = jo.getString("logo");
                    buylogo.add(t);

                    t = jo.getString("link");
                    buylink.add(t);


                    //    Toast.makeText(ctx,"" + t + " " + id + " ", Toast.LENGTH_LONG).show();

                    count++;
                }
                i.putStringArrayListExtra("logo",buylogo);
                i.putStringArrayListExtra("prices",allprice);
                i.putStringArrayListExtra("links",buylink);

                ctx.startActivity(i);
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
