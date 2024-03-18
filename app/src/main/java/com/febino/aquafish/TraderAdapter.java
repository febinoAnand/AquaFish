package com.febino.aquafish;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.febino.aquafish.R;

import java.util.ArrayList;

public class TraderAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<TraderDetails> list;
    private Context context;

    public TraderAdapter(ArrayList<TraderDetails> list, Context context){
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.trader_adapter_view, null);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/unicode.futurab.ttf");



        TextView traderNameTxt = view.findViewById(R.id.trader_adapter_name);
        TextView traderAliasTxt = view.findViewById(R.id.trader_adapter_alias);
        TextView traderLocationTxt = view.findViewById(R.id.trader_adapter_location);

        traderNameTxt.setTypeface(font);
        traderAliasTxt.setTypeface(font);
        traderLocationTxt.setTypeface(font);

        traderNameTxt.setText(list.get(position).getName());
        traderAliasTxt.setText(list.get(position).getAlias());
        traderLocationTxt.setText(list.get(position).getLocation());



        return view;
    }
}
