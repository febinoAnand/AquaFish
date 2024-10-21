package com.febino.aquafish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.febino.dataclass.TraderDetails;

import java.util.ArrayList;

public class BillTraderSelectAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<TraderDetails> arrayList;
    Context context;
    public BillTraderSelectAdapter(ArrayList<TraderDetails> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public TraderDetails getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position)._id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.trader_adapter_view_short,null);
        }

        TraderDetails traderDetails = arrayList.get(position);

        TextView name = v.findViewById(R.id.trader_adapter_name);
//        TextView alias = v.findViewById(R.id.trader_adapter_alias);
        TextView traderID = v.findViewById(R.id.trader_adapter_traderid);
//        TextView traderLocation = v.findViewById(R.id.trader_adapter_location);

        name.setText(traderDetails.name);
//        alias.setText(traderDetails.alias);
        traderID.setText(traderDetails.trader_id);
//        traderLocation.setText(traderDetails.location);

        return v;
    }
}
