package com.example.queueingapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment_Queueing extends Fragment {

    private TextView tv_volleyError, tv_site_name, tv_date, tv_time, tv_news, tv_atbay, tv_completed, tv_waiting;
    private String currentDate;
    private LinearLayout layoutData;
    private RecyclerView recyclerViewAtBay;
    private RecyclerView.Adapter atBay_adapter;
    private List<Model_AtBay> atBay_list;
    private RecyclerView recyclerViewBeforeBay;
    private RecyclerView.Adapter beforeBay_adapter;
    private List<Model_BeforeBay> beforeBay_list;
    private List<String> message_list;
    JSONArray jsonArray, jsonArray1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queueing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = df.format(new Date());

        layoutData = view.findViewById(R.id.layoutData);
        tv_volleyError = view.findViewById(R.id.tv_volleyError);
        tv_site_name = view.findViewById(R.id.tv_site_name);
        tv_date = view.findViewById(R.id.tv_date);
        tv_time = view.findViewById(R.id.tv_time);
        tv_news = view.findViewById(R.id.tv_news);
        tv_atbay = view.findViewById(R.id.tv_atbay);
        tv_completed = view.findViewById(R.id.tv_completed);
        tv_waiting = view.findViewById(R.id.tv_waiting);

        tv_site_name.setText(MainActivity.site_name);

        tv_date.setText(currentDate);

        tv_news.setSelected(true);

        recyclerViewAtBay = view.findViewById(R.id.recyclerViewAtBay);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAtBay.setLayoutManager(mLayoutManager);
        recyclerViewAtBay.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAtBay.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        atBay_list = new ArrayList<>();
        message_list = new ArrayList<String>();
        /*atBay_adapter = new Adapter_Atbay(atBay_list, getContext());
        recyclerViewAtBay.setAdapter(atBay_adapter);*/
        getProcessingAtBay();

        recyclerViewBeforeBay = view.findViewById(R.id.recyclerViewBeforeBay);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerViewBeforeBay.setLayoutManager(mLayoutManager1);
        recyclerViewBeforeBay.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeforeBay.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        beforeBay_list = new ArrayList<>();
        /*beforeBay_adapter = new Adapter_BeforeBay(beforeBay_list, getContext());
        recyclerViewBeforeBay.setAdapter(beforeBay_adapter);*/
        getProcessingBeforeBay();

        final Handler someHandler = new Handler(getActivity().getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_time.setText(new SimpleDateFormat("h:mm:ss a", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);

        final Handler someHandler1 = new Handler(getActivity().getMainLooper());
        someHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh1(30000);
                refresh2(30000);
                someHandler1.postDelayed(this, 30000);
            }
        }, 10);

    }

    private void getProcessingAtBay() {

        String URL_ATBAY = SitesActivity.URL + "waiting-queue/" + MainActivity.site_id;

        StringRequest req = new StringRequest(Request.Method.GET, URL_ATBAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        atBay_list.clear();
                        layoutData.setVisibility(View.VISIBLE);
                        tv_volleyError.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            tv_atbay.setText("At Bay: " + jsonObject.getString("processingCount"));
                            tv_completed.setText("Completed: " + jsonObject.getString("completedCount"));
                            tv_waiting.setText("Waiting: " + jsonObject.getString("waitingCount"));

                            JSONArray tickerMessage = jsonObject.getJSONArray("tickerMessage");
                            for (int i = 0; i < tickerMessage.length(); i++) {
                                JSONObject jsonObject1 = tickerMessage.getJSONObject(i);
                                message_list.add(jsonObject1.getString("message"));
                            }
                            tv_news.setText(message_list.toString().replace("[","").replace("]",""));
                            jsonArray = jsonObject.getJSONArray("waiting_at_bay");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONObject order = object.getJSONObject("order");
                                JSONObject order_type_detail = order.getJSONObject("order_type_detail");
                                String order_type = order_type_detail.getString("name");
                                JSONObject product_detail = order.getJSONObject("product_detail");
                                String product = product_detail.getString("name");
                                String qty = order.getString("quantity");
                                JSONObject customer_detail = order.getJSONObject("customer_detail");
                                String customer = customer_detail.getString("name");
                                JSONObject bay_detail = object.getJSONObject("bay");
                                String bay = bay_detail.getString("name");

                                Model_AtBay item = new Model_AtBay(
                                        order_type,
                                        product,
                                        qty,
                                        customer,
                                        bay
                                );
                                atBay_list.add(item);
                            }
                            atBay_adapter = new Adapter_Atbay(atBay_list, getContext());
                            recyclerViewAtBay.setAdapter(atBay_adapter);
                            //beforeBay_adapter.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(req);
        //refresh1(1000);
    }

    private void getProcessingBeforeBay() {

        String URL_BEFOREBAY = SitesActivity.URL + "waiting-queue/" + MainActivity.site_id;

        StringRequest req = new StringRequest(Request.Method.GET, URL_BEFOREBAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        beforeBay_list.clear();
                        layoutData.setVisibility(View.VISIBLE);
                        tv_volleyError.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonArray1 = jsonObject.getJSONArray("waiting_before_bay");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject object = jsonArray1.getJSONObject(i);
                                JSONObject order = object.getJSONObject("has_order");
                                JSONObject order_type_detail = order.getJSONObject("order_type_detail");
                                String order_type = order_type_detail.getString("name");
                                JSONObject product_detail = order.getJSONObject("product_detail");
                                String product = product_detail.getString("name");
                                String qty = order.getString("quantity");
                                JSONObject customer_detail = order.getJSONObject("customer_detail");
                                String customer = customer_detail.getString("name");

                                Model_BeforeBay item = new Model_BeforeBay(
                                        order_type,
                                        product,
                                        qty,
                                        customer
                                );
                                beforeBay_list.add(item);
                            }
                            beforeBay_adapter = new Adapter_BeforeBay(beforeBay_list, getContext());
                            recyclerViewBeforeBay.setAdapter(beforeBay_adapter);
                            //beforeBay_adapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(req);
    }

    private void refresh1(int milliseconds) {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                String URL_ATBAY = SitesActivity.URL + "waiting-queue/" + MainActivity.site_id;

                StringRequest req = new StringRequest(Request.Method.GET, URL_ATBAY,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonArray.length() != jsonObject.getJSONArray("waiting_at_bay").length()) {
                                        getProcessingAtBay();
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

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(req);
            }
        };

        handler.postDelayed(runnable, milliseconds);
    }

    private void refresh2(int milliseconds) {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String URL_ATBAY = SitesActivity.URL + "waiting-queue/" + MainActivity.site_id;

                StringRequest req = new StringRequest(Request.Method.GET, URL_ATBAY,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonArray1.length() != jsonObject.getJSONArray("waiting_before_bay").length()) {
                                        getProcessingBeforeBay();
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

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(req);
            }
        };

        handler.postDelayed(runnable, milliseconds);
    }

    private void HandleVolleyError(VolleyError error) {

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
