package com.example.admin.tourapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Admin on 7/28/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Pojo> spacecrafts;


    public CustomAdapter(Context c, ArrayList<Pojo> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;

    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        }

        TextView nameTxt = convertView.findViewById(R.id.nameTxt);
        TextView descTxt = convertView.findViewById(R.id.descTxt);
        ImageView imgView = convertView.findViewById(R.id.imageView);

        //image
        final Pojo s = (Pojo) this.getItem(position);
        Glide.with(c)
                .load(s.getImage())
                .into(imgView);


        nameTxt.setText(s.getName());
        descTxt.setText(s.getDescription());

        return convertView;

    }
}
