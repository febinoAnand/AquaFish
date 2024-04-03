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
import com.febino.dataclass.TraderDetails;

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
        return list.get(position)._id;
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
        TextView traderTraderIDTxt = view.findViewById(R.id.trader_adapter_traderid);

        traderNameTxt.setTypeface(font);
        traderAliasTxt.setTypeface(font);
        traderLocationTxt.setTypeface(font);
        traderTraderIDTxt.setTypeface(font);

        traderNameTxt.setText(list.get(position).name);
        traderAliasTxt.setText(list.get(position).alias);
        traderLocationTxt.setText(list.get(position).location);
        traderTraderIDTxt.setText(list.get(position).trader_id);



        return view;
    }
    public void updateList(ArrayList<TraderDetails> newTraderArryaList){
        list = newTraderArryaList;
        notifyDataSetChanged();
    }
}
