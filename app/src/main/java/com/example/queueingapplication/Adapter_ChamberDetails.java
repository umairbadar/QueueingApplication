package com.example.queueingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_ChamberDetails extends RecyclerView.Adapter<Adapter_ChamberDetails.ViewHolder> {

    List<Model_ChamberDetails> list;
    Context context;

    public Adapter_ChamberDetails(List<Model_ChamberDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chamber_details, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model_ChamberDetails item = list.get(position);

        holder.tv_chamber_name.setText(item.getChamber_name());
        holder.tv_fillable_volume.setText(item.getFillable_volume());
        holder.tv_set_volume.setText(item.getSetVolume());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_chamber_name,tv_fillable_volume,tv_set_volume;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_chamber_name = itemView.findViewById(R.id.tv_chamber_name);
            tv_fillable_volume = itemView.findViewById(R.id.tv_fillable_volume);
            tv_set_volume = itemView.findViewById(R.id.tv_set_volume);
        }
    }
}
