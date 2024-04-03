package com.febino.aquafish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.StockDetails;

import java.util.ArrayList;


public class StockIndividualListViewAdapter extends BaseAdapter {
    ArrayList<ProductDetails> list = new ArrayList<ProductDetails>();
    Context context;

    public StockIndividualListViewAdapter(ArrayList<ProductDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ProductDetails getItem(int position) {
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
            view = layoutInflater.inflate(R.layout.stock_individual_listview,null);

            ProductDetails productDetails = list.get(position);

            TextView breedName = view.findViewById(R.id.stock_individual_listview_breed_txt);
            TextView shortName = view.findViewById(R.id.stock_individual_listview_total_box_txt);
            TextView description = view.findViewById(R.id.stock_individual_listview_total_kg_txt);

            breedName.setText(productDetails.productName);
            shortName.setText(productDetails.shortName);
            description.setText(productDetails.description);

        }


        return view;
    }
}
