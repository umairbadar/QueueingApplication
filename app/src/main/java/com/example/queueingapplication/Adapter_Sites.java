package com.example.queueingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Adapter_Sites extends RecyclerView.Adapter<Adapter_Sites.ViewHolder> {

    List<Model_Sites> site_list;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Adapter_Sites(List<Model_Sites> site_list, Context context) {
        this.site_list = site_list;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPre",Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sites, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model_Sites site = site_list.get(position);

        holder.tv_site_name.setText(site.getSite_name());
        holder.tv_manager_name.setText(site.getManager_name());

        holder.layout_sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity.class);
                editor = sharedPreferences.edit();
                editor.putString("site_id",site.getSite_id());
                editor.putString("site_name",site.getSite_name());
                editor.apply();
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return site_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout_sites;
        TextView tv_site_name,tv_manager_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //TextViews
            tv_site_name = itemView.findViewById(R.id.tv_site_name);
            tv_manager_name = itemView.findViewById(R.id.tv_manager_name);

            //Linear Layout
            layout_sites = itemView.findViewById(R.id.layout_sites);
        }
    }
}
