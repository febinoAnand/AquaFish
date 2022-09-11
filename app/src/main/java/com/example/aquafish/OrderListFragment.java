package com.example.aquafish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class OrderListFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater1, ViewGroup viewGroup, Bundle bundle){
        View view = layoutInflater1.inflate(R.layout.order_list_fragment, viewGroup, false);

        ListView orderListView = view.findViewById(R.id.order_list_tab_listview);

        ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
        for(int i =0;i<30;i++) list.add(new OrderDetails());

        orderListView.setAdapter(new OrderListViewAdapter(list, getContext()));


        return view;
    }


}
