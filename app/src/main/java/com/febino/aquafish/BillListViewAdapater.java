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
import com.febino.dataclass.BillDetails;
import com.febino.dataclass.TraderDetails;

import java.util.ArrayList;

public class BillListViewAdapater extends BaseAdapter implements ListAdapter {

    Context context;

    DataBaseManager dataBaseManager;
    ArrayList<BillDetails> billDetailsArrayList;

    CopyCursor cc;
    public BillListViewAdapater(Context context, ArrayList<BillDetails> billDetailsArrayList, DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
        this.context = context;
        this.billDetailsArrayList = billDetailsArrayList;
        cc = new CopyCursor();
    }
    @Override
    public int getCount() {
        return billDetailsArrayList.size();
    }

    @Override
    public BillDetails getItem(int position) {
        return billDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return billDetailsArrayList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.bill_list_adapter_view, null);
        }

        BillDetails billDetails = billDetailsArrayList.get(position);

        TraderDetails traderDetails = cc.copyTraderFromCursor(dataBaseManager.getTraderInTraderTableByID(billDetails.getTraderID()));

        TextView date = convertView.findViewById(R.id.bill_list_view_bill_date);
        TextView billNo = convertView.findViewById(R.id.bill_list_view_bill_no);
        TextView name = convertView.findViewById(R.id.bill_list_view_name);
        TextView amount = convertView.findViewById(R.id.bill_list_view_amount);

        date.setText(billDetails.getBillDate());
        billNo.setText(""+billDetails.getBillNo());
        name.setText(""+traderDetails.name);
        amount.setText(""+billDetails.getBillAmount());


        return convertView;
    }
}
