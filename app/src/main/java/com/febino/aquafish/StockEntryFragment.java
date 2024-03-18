package com.febino.aquafish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.febino.aquafish.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class StockEntryFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {
        View view = layoutInflater.inflate(R.layout.stock_entry_fragment,container,false);
        //TODO: add typeface for fields

        ArrayList<StockDetails> list = new ArrayList<StockDetails>();

        for(int i=0;i<10;i++) list.add(new StockDetails());

        ListView entryList = view.findViewById(R.id.stock_entry_listview);
        entryList.setAdapter(new StockEntryListViewAdapter(list, getContext()));

        return view;

    }
}
