package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyProductAdapter extends ArrayAdapter<MyProduct> {

    final private MyProduct[] arr;

    public MyProductAdapter(Context context, MyProduct[] arr) {
        super(context, R.layout.item, arr);
        this.arr = arr;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyProduct product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
        }
        ((TextView) convertView.findViewById(R.id.name_item)).setText(product.name);
        ((TextView) convertView.findViewById(R.id.price_item)).setText(product.price);
        return convertView;
    }

    @Override
    public MyProduct getItem(int position) {
        return arr[position];
    }
}

