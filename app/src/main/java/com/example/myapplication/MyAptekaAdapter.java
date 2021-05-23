package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAptekaAdapter extends ArrayAdapter<Apteka> {

    final private Apteka[] arr;

    public MyAptekaAdapter(Context context, Apteka[] arr) {
        super(context, R.layout.item_apteka, arr);
        this.arr = arr;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Apteka apteka = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_apteka, null);
        }
        ((TextView) convertView.findViewById(R.id.name_apteka)).setText(apteka.name_apteka);
        ((TextView) convertView.findViewById(R.id.price_apteka)).setText(apteka.price);
        ((TextView) convertView.findViewById(R.id.address)).setText(apteka.address);
        ((TextView) convertView.findViewById(R.id.telefon)).setText(apteka.telefon);
        return convertView;
    }

    @Override
    public Apteka getItem(int position) {
        return arr[position];
    }
}
