package com.gimbal.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Request.Method;

public class MyActivity extends Activity {
    // Log tag
    private static final String TAG = MyActivity.class.getSimpleName();

    // Movies json url
    //private static final String url = "http://www.json-generator.com/api/json/get/cfesldWfaW?indent=2";
    //private static final String url = "http://www.json-generator.com/api/json/get/cfNrPrtrsi?indent=2";
    private static String url = "http://freezeadmin.herokuapp.com/api/store?storeName=";

    private ProgressDialog pDialog;
    private List<Product> products = new ArrayList<Product>();
    private ListView listView;
    private CustomJSONAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomJSONAdapter(this, products);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        Log.d("StoreId: ",getIntent().getExtras().get("StoreId").toString());

        String query = getIntent().getExtras().get("StoreId").toString();

        try {
            query = URLEncoder.encode(query, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       String RequestUrl = url + query;

        Log.d("URL",url);

        // changing action bar color
        //getActionBar().setBackgroundDrawable(
                //new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj

        JsonArrayRequest movieReq = new JsonArrayRequest(RequestUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        Log.d("Response: ", response.toString());

                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);

                                Product product = new Product();
                                product.setThumbnailUrl(obj.getString("url"));
                                product.setBrand(obj.getString("storeName"));
                                product.setName(obj.getString("deal"));
                                //product.setUPC(obj.getString("upc"));
                                //product.setSeller(obj.getString("seller"));
                                //product.setPrice(obj.getString("price"));


                                // adding product to products array
                                products.add(product);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        /*
        JsonObjectRequest movieReq = new JsonObjectRequest(Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                       // for (int i = 0; i < response.length(); i++) {
                            try {

                                //JSONObject obj = response.get();

                                Log.d("URL: ",response.getString("url"));
                                Log.d("Store: ",response.getString("storeName"));
                                Log.d("deal: ",response.getString("deal"));

                                Product product = new Product();
                                product.setThumbnailUrl(response.getString("url"));
                                product.setBrand(response.getString("storeName"));
                                product.setName(response.getString("deal"));
                                //product.setUPC(obj.getString("upc"));
                                //product.setSeller(obj.getString("seller"));
                                //product.setPrice(obj.getString("price"));


                                // adding product to products array
                                products.add(product);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                      //  }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        }); */

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

}
