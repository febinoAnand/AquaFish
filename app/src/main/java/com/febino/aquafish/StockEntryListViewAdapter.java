package com.febino.aquafish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.febino.dataclass.StockDetails;

import java.util.ArrayList;

public class StockEntryListViewAdapter extends BaseAdapter {

    ArrayList<StockDetails> list = new ArrayList<StockDetails>();
    Context context;

    public StockEntryListViewAdapter(ArrayList<StockDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StockDetails getItem(int position) {
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
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_fragment_item, null);



        }

        return view;
    }
}