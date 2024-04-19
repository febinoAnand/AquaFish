package com.febino.aquafish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.TraderDetails;

import java.util.ArrayList;

public class OrderListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
    private Context context;
    private DataBaseManager dataBaseManager;

    public OrderListViewAdapter(ArrayList<OrderDetails> list, Context context) {
        this.list = list;
        this.context = context;
        dataBaseManager = new DataBaseManager(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public OrderDetails getItem(int position) {
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


        TextView dateTextView = view.findViewById(R.id.listview_fragment_item_1);
        TextView traderNameTextView = view.findViewById(R.id.listview_fragment_item_2);
        TextView itemTextView = view.findViewById(R.id.listview_fragment_item_3);
        TextView boxTextView = view.findViewById(R.id.listview_fragment_item_4);
        TextView kgTextView = view.findViewById(R.id.listview_fragment_item_5);
        TextView amoutTextView = view.findViewById(R.id.listview_fragment_item_6);
        dateTextView.setVisibility(View.GONE);

        CopyCursor cc = new CopyCursor();
        OrderDetails orderDetails = list.get(position);
        TraderDetails traderDetails = cc.copyTraderFromCursor(dataBaseManager.getTraderInTraderTableByID(orderDetails.getTraderID()));
        ProductDetails productDetails = cc.copyProductFromCursor(dataBaseManager.getProductFromProductTableByID(orderDetails.getProductID()));

        dateTextView.setText(orderDetails.getOrderDate());
        traderNameTextView.setText(traderDetails.name);
        itemTextView.setText(productDetails.shortName);
        boxTextView.setText(""+orderDetails.getTotalBox());
        kgTextView.setText(""+orderDetails.getTotalKG());
//        amoutTextView.setText(""+(orderDetails.getTotalBox()*35 + orderDetails.getTotalKG()) * orderDetails.getRatePerKG());
        amoutTextView.setText(""+orderDetails.getRatePerKG());

        return view;
    }


}
