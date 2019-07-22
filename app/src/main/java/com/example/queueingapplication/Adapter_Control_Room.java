package com.example.queueingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_Control_Room extends RecyclerView.Adapter<Adapter_Control_Room.ViewHolder> {

    List<Model_ChamberDetails> chamberDetails_list;
    RecyclerView.Adapter adapter;
    List<Model_Control_Room> mycontrolroom;
    Context context;
    String order_id;

    public Adapter_Control_Room(List<Model_Control_Room> mycontrolroom, Context context) {
        this.mycontrolroom = mycontrolroom;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control_room, parent, false);
        return new Adapter_Control_Room.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final Model_Control_Room item = mycontrolroom.get(i);
        holder.type.setText(item.getType());
        holder.product.setText(item.getProduct());
        holder.quantity.setText(item.getQuantity() + " (liters)");
        holder.sapref.setText(item.getSapref());
        holder.tank.setText(item.getTank());
        holder.tv_bayArm.setText(item.getBay() + " - " + item.getArm());
        holder.vehicle.setText(item.getVehicle());
        holder.batch.setText(item.getBatch());
        holder.customer.setText(item.getCustomer());

        holder.progressBar.setProgress(Integer.parseInt(item.getProgress()));
        holder.tv_progress.setText(item.getProgress() +"% " + item.getType());

        if (item.getSkully().equals("1")){
            holder.tv_skully.
                    setCompoundDrawablesWithIntrinsicBounds( R.drawable.green_circle, 0, 0, 0);
        } else {
            holder.tv_skully.
                    setCompoundDrawablesWithIntrinsicBounds( R.drawable.red_circle, 0, 0, 0);
        }
        if (item.getOverFill().equals("1")){
            holder.tv_overFill.
                    setCompoundDrawablesWithIntrinsicBounds( R.drawable.green_circle, 0, 0, 0);
        } else {
            holder.tv_overFill.
                    setCompoundDrawablesWithIntrinsicBounds( R.drawable.red_circle, 0, 0, 0);
        }

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(context);
        holder.recyclerViewChamberDetails.setLayoutManager(mLayoutManager1);
        holder.recyclerViewChamberDetails.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerViewChamberDetails.addItemDecoration(new DividerItemDecoration(context, 1));
        chamberDetails_list = new ArrayList<>();

        String chamber_name = item.getChamber_name();
        String fillable_qty = item.getFillable_qty();

        if (chamber_name.contains(",")){
            String[] names = chamber_name.split(",");
            String[] qty = fillable_qty.split(",");
            for (int j = 0; j < names.length; j++){
                Model_ChamberDetails inner_item = new Model_ChamberDetails(
                        names[j],
                        qty[j],
                        "###"
                );
                chamberDetails_list.add(inner_item);
            }
        } else {
            Model_ChamberDetails inner_item = new Model_ChamberDetails(
                    chamber_name,
                    fillable_qty,
                    "###"
            );
            chamberDetails_list.add(inner_item);
        }
        adapter = new Adapter_ChamberDetails(chamberDetails_list,context);
        holder.recyclerViewChamberDetails.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        return mycontrolroom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewChamberDetails;
        TextView tv_progress,type, product, quantity, sapref, tank, tv_bayArm, vehicle, batch, customer,tv_skully,tv_overFill;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            product = itemView.findViewById(R.id.product);
            quantity = itemView.findViewById(R.id.quantity);
            sapref = itemView.findViewById(R.id.sapref);
            tank = itemView.findViewById(R.id.tank);
            tv_bayArm = itemView.findViewById(R.id.tv_bayArm);
            vehicle = itemView.findViewById(R.id.vehicle);
            batch = itemView.findViewById(R.id.batch);
            customer = itemView.findViewById(R.id.customer);
            tv_skully = itemView.findViewById(R.id.tv_skully);
            tv_overFill = itemView.findViewById(R.id.tv_overFill);
            tv_progress = itemView.findViewById(R.id.tv_progress);
            recyclerViewChamberDetails = itemView.findViewById(R.id.recyclerViewChamberDetails);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
