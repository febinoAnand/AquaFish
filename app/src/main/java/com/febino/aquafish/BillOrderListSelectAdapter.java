package com.febino.aquafish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.ProductDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BillOrderListSelectAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<OrderDetails> orderDetailsArrayList;
    Context context;
    DataBaseManager dataBaseManager;

    CopyCursor copyCursor;

    public BillOrderListSelectAdapter(ArrayList<OrderDetails> orderDetailsArrayList, Context context, DataBaseManager dataBaseManager) {
        this.context = context;
        this.orderDetailsArrayList = orderDetailsArrayList;
        this.dataBaseManager = dataBaseManager;
        copyCursor = new CopyCursor();
    }

    @Override
    public int getCount() {
        return orderDetailsArrayList.size();
    }

    @Override
    public OrderDetails getItem(int position) {
        return orderDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderDetailsArrayList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.bill_order_list_select_adapter, null);

        }

        OrderDetails orderDetails = orderDetailsArrayList.get(position);
        ProductDetails productDetails = copyCursor.copyProductFromCursor(dataBaseManager.getProductFromProductTableByID(orderDetails.getProductID()));

        TextView date = v.findViewById(R.id.bill_generate_add_order_date);
        TextView breed = v.findViewById(R.id.bill_generate_add_order_breed);
        TextView box = v.findViewById(R.id.bill_generate_add_order_box);
        TextView kg = v.findViewById(R.id.bill_generate_add_order_kg);
        TextView rate = v.findViewById(R.id.bill_generate_add_order_amount);
        ImageView checkbtn = v.findViewById(R.id.bill_generate_add_order_checkbox);


        date.setText(""+orderDetails.getOrderDate());
        breed.setText(""+productDetails.productName);
        box.setText(""+orderDetails.getTotalBox());
        kg.setText(""+orderDetails.getTotalKG());
        rate.setText(""+orderDetails.getRatePerKG());

        checkbtn.setImageResource(!orderDetails.isSelected ? R.drawable.baseline_check_box_outline_blank_30 : R.drawable.baseline_check_box_30);


        return v;
    }
}
