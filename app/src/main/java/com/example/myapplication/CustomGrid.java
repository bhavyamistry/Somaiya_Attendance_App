package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter {
    private final String[] subject;
    private Context mContext;


    public CustomGrid(Context mContext, String[] subject) {
        this.mContext = mContext;
        this.subject = subject;
    }

    @Override
    public int getCount() {
        return subject.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_header, null);
            TextView subject_name = grid.findViewById(R.id.subject_name);
            subject_name.setText(subject[i]);
        } else {
            grid = view;
        }

        return grid;
    }
}
