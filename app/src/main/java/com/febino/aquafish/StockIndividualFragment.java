package com.febino.aquafish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.StockDetails;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class StockIndividualFragment extends Fragment {

    DataBaseManager db;

    StockIndividualListViewAdapter stockIndividualListViewAdapter;
    ArrayList<ProductDetails> list = new ArrayList<ProductDetails>();
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);


    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {
        View view = layoutInflater.inflate(R.layout.stock_individual_fragment,container,false);
        //TODO: add typeface for fields
        db = new DataBaseManager(getContext());
        list = new CopyCursor().copyProductListFromCurser(db.getAllProductFromProductTable());
        stockIndividualListViewAdapter = new StockIndividualListViewAdapter(list, getContext());

        ListView breedListView = view.findViewById(R.id.stock_individual_listview);
        breedListView.setAdapter(stockIndividualListViewAdapter);
        breedListView.setDivider(null);

        breedListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                return false;
            }
        });

        return view;

    }

    public void updateList(ProductDetails productDetails){
        list.add(productDetails);
        stockIndividualListViewAdapter.notifyDataSetChanged();
    }
}
