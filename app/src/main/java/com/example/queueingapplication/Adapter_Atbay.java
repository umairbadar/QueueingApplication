package com.example.queueingapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Atbay extends RecyclerView.Adapter<Adapter_Atbay.ViewHolder> {

    List<Model_AtBay> list;
    Context context;

    public Adapter_Atbay(List<Model_AtBay> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_atbay, parent,false);

        Log.i("ViewHolder","Invoked");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_AtBay item = list.get(position);

        holder.tv_order_type.setText(item.getOder_type());
        holder.tv_product.setText(item.getProduct());
        holder.tv_qty.setText(item.getQty());
        holder.tv_customer.setText(item.getCustomer());
        holder.tv_bay.setText(item.getBay());

        Log.i("onBindViewHolder","Invoked");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_order_type,tv_product,tv_qty,tv_customer,tv_bay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_type = itemView.findViewById(R.id.tv_order_type);
            tv_product = itemView.findViewById(R.id.tv_product);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_customer = itemView.findViewById(R.id.tv_customer);
            tv_bay = itemView.findViewById(R.id.tv_bay);
        }
    }
}
