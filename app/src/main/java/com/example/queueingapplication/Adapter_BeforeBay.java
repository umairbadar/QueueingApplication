package com.example.queueingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_BeforeBay extends RecyclerView.Adapter<Adapter_BeforeBay.ViewHolder> {

    List<Model_BeforeBay> list;
    Context context;

    public Adapter_BeforeBay(List<Model_BeforeBay> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beforebay, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_BeforeBay item = list.get(position);

        holder.tv_order_type.setText(item.getOrder_type());
        holder.tv_product.setText(item.getProduct());
        holder.tv_qty.setText(item.getQty());
        holder.tv_customer.setText(item.getCustomer());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_order_type,tv_product,tv_qty,tv_customer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_type = itemView.findViewById(R.id.tv_order_type);
            tv_product = itemView.findViewById(R.id.tv_product);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_customer = itemView.findViewById(R.id.tv_customer);
        }
    }
}
