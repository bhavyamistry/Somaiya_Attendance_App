package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder2> {
    private List<Subject> list;
    private Context context;

    public Adapter2(List<Subject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Adapter2.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_data, parent, false);
        Adapter2.ViewHolder2 holder = new Adapter2.ViewHolder2(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Adapter2.ViewHolder2 holder, int position) {
        Subject subject = list.get(position);
        holder.textView.setText(subject.getSubject_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder2(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview2);
        }
    }
}

