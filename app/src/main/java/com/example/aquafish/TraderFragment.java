package com.example.aquafish;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class TraderFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {

        SpannableString s = new SpannableString(getResources().getString(R.string.trader).toUpperCase());
        s.setSpan(new RelativeSizeSpan(0.8f), 0,s.length(), 0);
        s.setSpan(new TypefaceSpan(getContext(), "unicode.futurab.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        View view = layoutInflater.inflate(R.layout.fragment_trader, container, false);

        FragmentActivity fa = getActivity();
        fa.setTitle(s);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/futura medium bt.ttf");
        EditText searchEdit = view.findViewById(R.id.trader_search_edit);
        ImageButton traderAddBtn = (ImageButton) view.findViewById(R.id.trader_add_button);

        searchEdit.setTypeface(font);


        ArrayList<TraderDetails> list = new ArrayList<TraderDetails>();

        for(int i=0;i<15;i++){
            TraderDetails traderDetails = new TraderDetails();
            traderDetails.setName("Ram"+i);
            traderDetails.set_id(i);
            traderDetails.setAlias("Ravi");
            traderDetails.setCreateDate("15.12.21");
            traderDetails.setLocation("Erode");
            list.add(traderDetails);
        }


//        traderDetails = new TraderDetails();
//        traderDetails.set_id(2);
//        traderDetails.setName("Kumar");
//        traderDetails.setAlias("Palani");
//        traderDetails.setCreateDate("15.12.21");
//        traderDetails.setLocation("Pallipalayam");
//        list.add(traderDetails);

        TraderAdapter traderAdapter = new TraderAdapter(list, getContext());
        ListView traderListView = (ListView) view.findViewById(R.id.trader_list_view);
        traderListView.setAdapter(traderAdapter);
        traderListView.setDivider(null);

        traderAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Trader Name addition", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
