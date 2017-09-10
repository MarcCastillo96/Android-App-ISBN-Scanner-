package com.example.marccastillo.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;



import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    EditText tv1=null;
    EditText tv2=null;
    EditText tv3=null;
    EditText tv4=null;
    EditText tv5=null;
    EditText tv6=null;
    EditText tv7 = null;
Button b1 = null;
    Button b2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv1 = (EditText) findViewById(R.id.tv1);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        tv2 = (EditText)findViewById(R.id.tv2);
        tv3 = (EditText)findViewById(R.id.tv3);
        tv4 = (EditText)findViewById(R.id.tv4);
        tv5 = (EditText)findViewById(R.id.tv5);
        tv6 = (EditText)findViewById(R.id.tv6);
       tv7 = (EditText)findViewById(R.id.tv7);
        b1.setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyTask mt = new MyTask();
                if (tv1.getText().toString().length() <= 0) {
                    tv2.setText("You have to scan a ISBN barcode");
                } else {
                    mt.execute(tv1.getText().toString());
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }// ends on-create

    @Override
    public void onClick(View v) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "EAN_13");// be sure to put the right type here
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                tv1.setText(contents);
                // and anything you want to do with the scan value
            } else if (resultCode == RESULT_CANCELED) {
                //do whatever you want here
            }

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.Scan) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "EAN_13");// be sure to put the right type here
            startActivityForResult(intent, 0);
            return true;
        }
        if (id == R.id.Quit) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "EAN_13");// be sure to put the right type here
            startActivityForResult(intent, 0);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyTask extends AsyncTask<String, Void, String> {
        String kindWeWant = "";
        String idWeWant = "";
        String titleWeWant = "";
        String subTitle = "";
        String dateWeWant = "";
        String publisherWeWant = "";
        String pageCountWeWant = "";
String description = "";
        protected String doInBackground(String... params) {
            String details = "";

            URL url = null;
            InputStream is = null;
            BufferedReader br = null;
            String line;
            String data[] = new String[3];
            try {
                String urlstring =
                        "https://www.googleapis.com/books/v1/volumes?q=" + params[0];
                url = new URL(urlstring);
                is = url.openStream();
                // new json code starts here
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bis = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String temp = "";
                while ((temp = bis.readLine()) != null)
                    sb.append(temp);
                bis.close();
                temp = sb.toString();
                temp = temp.replace("{ }(", "");
                temp = temp.replace("[ ](", "");
                temp = temp.replace(")", "");
                JSONObject jo = new JSONObject(temp);
                JSONArray ja = jo.getJSONArray("items");
                JSONObject book = ja.getJSONObject(0);
                kindWeWant = book.getString("kind");
                idWeWant = book.getString("id");
                JSONObject jo2 = ja.getJSONObject(0).getJSONObject("volumeInfo");
                titleWeWant = jo2.getString("title");
                subTitle = jo2.getString("subtitle");
                dateWeWant = jo2.getString("publishedDate");
                publisherWeWant = jo2.getString("publisher");
                pageCountWeWant = jo2.getString("pageCount");
description = jo2.getString("description");
                System.out.println(titleWeWant);

            } catch (JSONException je) {
                Log.i("info", je.getMessage());
            } catch (MalformedURLException me) {
                Log.i("info", me.getMessage());
            } catch (IOException ioe) {
                Log.i("info", ioe.getMessage());
            }
            return details;
        }
        protected void onPostExecute(String results) {
            tv2.setText("Title: " + String.valueOf(titleWeWant));
            tv3.setText("Subtitle: " + String.valueOf(subTitle));
            tv4.setText("Published Date: " + String.valueOf(dateWeWant));
            tv5.setText("Text Publisher: " + String.valueOf(publisherWeWant));
            tv6.setText("Pages: " + String.valueOf(pageCountWeWant) + " pgs");
            tv7.setText("Texbook Desc: " + String.valueOf(description));


        }//ends onPostExecute

        //protected void onProgressUpdate(Void... values) {

        //}
    }//ends AsyncTask class
}
