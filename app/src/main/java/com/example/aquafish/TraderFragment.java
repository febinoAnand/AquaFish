package com.example.aquafish;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.dialog_trader_detail, null);

                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/unicode.futurab.ttf");
                TextView dialogHeader = v.findViewById(R.id.trader_header_textview);
                TextView traderNameTextView = v.findViewById(R.id.trader_name_textview);
                TextView traderAliasTextView = v.findViewById(R.id.trader_alias_textview);
                TextView traderLocationTextView = v.findViewById(R.id.trader_location_textview);
                TextView traderMobileTextView = v.findViewById(R.id.trader_phone_textview);

                EditText traderNameEditText = v.findViewById(R.id.trader_name_edit);
                EditText traderAliasEditText = v.findViewById(R.id.trader_alias_edit);
                EditText traderLocationEditText = v.findViewById(R.id.trader_location_edit);
                EditText traderMobileEditText = v.findViewById(R.id.trader_phone_edit);


                Button traderOKBtn = v.findViewById(R.id.trader_dialog_ok_btn);
                Button traderCancelBtn = v.findViewById(R.id.trader_dialog_cancel_btn);



                dialogHeader.setTypeface(font);
                dialogHeader.setText(dialogHeader.getText().toString().toUpperCase());
                traderOKBtn.setTypeface(font);
                traderCancelBtn.setTypeface(font);


                font = Typeface.createFromAsset(getContext().getAssets(), "fonts/futura medium bt.ttf");
                traderNameTextView.setTypeface(font);
                traderAliasTextView.setTypeface(font);
                traderLocationTextView.setTypeface(font);
                traderMobileTextView.setTypeface(font);

                traderNameEditText.setTypeface(font);
                traderAliasEditText.setTypeface(font);
                traderLocationEditText.setTypeface(font);
                traderMobileEditText.setTypeface(font);


                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.show();


//                Toast.makeText(getContext(), "Trader Name addition", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
