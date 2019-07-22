package com.example.queueingapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Control_Room extends Fragment {

    private RecyclerView controlroom;
    private RecyclerView.Adapter adapter;
    private List<Model_Control_Room> mycontrolroom;
    View view;
    private TextView tv_volleyError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control_room, container, false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_volleyError = view.findViewById(R.id.tv_volleyError);
        controlroom = view.findViewById(R.id.recyclerviewmyorder);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        controlroom.setLayoutManager(mLayoutManager);
        controlroom.setItemAnimator(new DefaultItemAnimator());
        controlroom.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        mycontrolroom = new ArrayList<>();
        getdata();

        final Handler someHandler1 = new Handler(getActivity().getMainLooper());
        someHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                getdata();
                someHandler1.postDelayed(this, 30000);
            }
        }, 10);
    }

    public void getdata() {
        String URL_SITES = SitesActivity.URL + "control-room";
        StringRequest req = new StringRequest(Request.Method.POST, URL_SITES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        controlroom.setVisibility(View.VISIBLE);
                        tv_volleyError.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("controlRoom");
                                JSONArray jsonArray1 = jsonObject.getJSONArray("chamberDetail");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    JSONObject object1 = jsonArray1.getJSONObject(i);
                                    JSONArray jsonArray2 = object1.getJSONArray("list");
                                    JSONObject jsonObject1 = jsonArray2.getJSONObject(0);
                                    String order_id = object.getString("order_id");
                                    String id = jsonObject1.getString("order_id");
                                    String chamber_name = object.getString("chamber_name");
                                    String fillable_qty = object.getString("fillable_qty");
                                    JSONObject innerObject1 = object.getJSONObject("order");
                                    JSONObject innerObject2 = innerObject1.getJSONObject("order_type_detail");
                                    JSONObject innerObject3 = innerObject1.getJSONObject("product_detail");
                                    JSONObject innerObject9 = object.getJSONObject("batch_controller");
                                    JSONObject innerObject10 = innerObject1.getJSONObject("customer_detail");

                                    JSONObject innerobject4 = object.getJSONObject("tank");
                                    JSONObject innerobject5 = object.getJSONObject("bay");
                                    JSONObject innerobject6 = object.getJSONObject("arm");
                                    JSONObject innerobject7 = object.getJSONObject("vehicle");
                                    JSONObject innerobject8 = innerobject7.getJSONObject("has_lorry");
                                    String customer = innerObject10.getString("name");
                                    String batch1 = innerObject9.getString("batch_controller_name");
                                    String looryname = innerobject8.getString("name");
                                    String drivername = innerobject7.getString("driver_name");
                                    String plate_no = innerobject7.getString("plate_no");
                                    String armname = innerobject6.getString("name");
                                    String bay = innerobject5.getString("name");
                                    String tankname = innerobject4.getString("name");
                                    String order_type = innerObject2.getString("name");
                                    String product = innerObject3.getString("name");
                                    String qty = innerObject1.getString("quantity");
                                    String sapref = innerObject1.getString("sap_reference");

                                    if (order_id.equals(id)) {
                                        String skully = jsonObject1.getString("skully");
                                        String overFill = jsonObject1.getString("overFill");
                                        String progress = jsonObject1.getString("progress");
                                        Model_Control_Room item = new Model_Control_Room(
                                                skully,
                                                overFill,
                                                progress,
                                                fillable_qty,
                                                chamber_name,
                                                order_id,
                                                order_type,
                                                product,
                                                qty,
                                                sapref,
                                                tankname,
                                                bay,
                                                armname,
                                                plate_no,
                                                looryname,
                                                drivername,
                                                batch1,
                                                customer

                                        );
                                        mycontrolroom.add(item);
                                    }
                                }

                                adapter = new Adapter_Control_Room(mycontrolroom, getContext());
                                controlroom.setAdapter(adapter);
                                //adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getContext(), msg,
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("siteId", MainActivity.site_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(req);
    }

    public void HandleVolleyError(VolleyError error) {

        controlroom.setVisibility(View.GONE);
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