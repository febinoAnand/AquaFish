package com.example.aquafish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class StockIndividualFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {
        View view = layoutInflater.inflate(R.layout.stock_individual_fragment,container,false);
        //TODO: add typeface for fields

        ArrayList<StockDetails> list = new ArrayList<StockDetails>();

        for(int i=0;i<10;i++)
        list.add(new StockDetails());

        ListView breedListView = view.findViewById(R.id.stock_individual_listview);
        breedListView.setAdapter(new StockIndividualListViewAdapter(list, getContext()));

        return view;

    }
}
