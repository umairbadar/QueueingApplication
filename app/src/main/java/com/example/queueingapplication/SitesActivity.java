package com.example.queueingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SitesActivity extends AppCompatActivity {

    private RecyclerView site_list;
    private RecyclerView.Adapter adapter;
    private List<Model_Sites> sites;
    public static String URL = "http://172.16.10.202/api/";
    private LinearLayout layoutData;
    private TextView tv_volleyError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);

        tv_volleyError = findViewById(R.id.tv_volleyError);
        layoutData = findViewById(R.id.layoutData);
        site_list = findViewById(R.id.site_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        site_list.setLayoutManager(mLayoutManager);
        site_list.setItemAnimator(new DefaultItemAnimator());
        site_list.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        sites = new ArrayList<>();
        getSites();
    }

    public void getSites(){

        String URL_SITES = URL +  "sites-list";
        StringRequest req = new StringRequest(Request.Method.GET, URL_SITES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv_volleyError.setVisibility(View.GONE);
                        layoutData.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("sites");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject innerObject = jsonArray.getJSONObject(i);
                                    String site_id = innerObject.getString("id");
                                    String site_name = innerObject.getString("name");
                                    JSONObject userObject = innerObject.getJSONObject("has_user");
                                    String manager_name = userObject.getString("name");

                                    Model_Sites data = new Model_Sites(
                                            site_id,
                                            site_name,
                                            manager_name
                                    );
                                    sites.add(data);
                                }
                                adapter = new Adapter_Sites(sites,getApplicationContext());
                                site_list.setAdapter(adapter);
                            } else {
                                Toast.makeText(getApplicationContext(),msg,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        HandleVolleyError(error);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(req);
    }

    public void HandleVolleyError(VolleyError error) {

        layoutData.setVisibility(View.GONE);
        tv_volleyError.setVisibility(View.VISIBLE);

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            tv_volleyError.setText("Connection Error:\nPlease Check your Internet Connection");

        } else if (error instanceof AuthFailureError) {

            tv_volleyError.setText("Auth Failure Error");

        } else if (error instanceof ServerError) {

            tv_volleyError.setText("Server Error:\nServer isn't Responding, Please try again later");

        } else if (error instanceof NetworkError) {

            tv_volleyError.setText("Network Error");

        } else if (error instanceof ParseError) {

            tv_volleyError.setText("JSON Parsing Error");

        }

    }
}
