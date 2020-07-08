package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttendanceGrid extends BaseAdapter {
    private final String[] attendance;
    private Context mContext;


    public AttendanceGrid(Context mContext, String[] attendance) {
        this.mContext = mContext;
        this.attendance = attendance;
    }

    @Override
    public int getCount() {
        return attendance.length;
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

        } else {
            grid = view;
        }

        return grid;
    }
}
