package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class pack_adapter extends RecyclerView.Adapter<pack_adapter.ViewHolder>{

    Context context;
    ArrayList<pack_test> arrayList;
    pack_adapter.OnItemClickListener onItemClickListener;
    public  pack_adapter(Context context,ArrayList<pack_test> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public pack_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent ,int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pack_list,parent,false);
        return new pack_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pack_adapter.ViewHolder holder ,int position) {

        holder.pname.setText(MessageFormat.format(" {0} ",arrayList.get(position).getpname()));
        holder.isRecieve.setText(arrayList.get(position).getisRecieve());
        holder.pcontent.setText(arrayList.get(position).getpcontent());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(arrayList.get(position)));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pname,isRecieve,pcontent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.list_item_name);
            pcontent = itemView.findViewById(R.id.list_item_pcontent);
            isRecieve = itemView.findViewById(R.id.list_item_isrecieve);
        }
    }
    public void setOnItemClickListener(pack_adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener =onItemClickListener;
    }

    public interface OnItemClickListener{
        View.OnClickListener onClick(pack_test pack_test);
    }
}
