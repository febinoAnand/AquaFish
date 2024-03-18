package com.febino.aquafish;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.febino.aquafish.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class StockFragment extends Fragment {

    Button stockIndividualTabBtn,stockTableTabBtn,stockEntryTabBtn;
    ViewPager viewPager;
    ImageButton stockAddBtn;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {
        SpannableString s = new SpannableString(getResources().getString(R.string.stock).toUpperCase());
        s.setSpan(new RelativeSizeSpan(0.8f), 0,s.length(), 0);
        s.setSpan(new TypefaceSpan(getContext(), "unicode.futurab.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final View view = layoutInflater.inflate(R.layout.fragment_stock, container, false);
        getActivity().setTitle(s);


        stockEntryTabBtn = view.findViewById(R.id.stock_entry_tab);
        stockIndividualTabBtn = view.findViewById(R.id.stock_individual_tab);
        stockTableTabBtn = view.findViewById(R.id.stock_table_tab);

        stockAddBtn = view.findViewById(R.id.stock_add_button);

        viewPager = view.findViewById(R.id.stock_view_pager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new StockIndividualFragment(),"Stock Individual Fragement");
        viewPagerAdapter.addFragment(new StockTableFragment(),"Stock Table Fragement");
        viewPagerAdapter.addFragment(new StockEntryFragment(),"Stock Entry Fragement");

        viewPager.setAdapter(viewPagerAdapter);

        stockEntryTabBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                selectEntryTab();
                viewPager.setCurrentItem(2,true);
            }
        });

        stockTableTabBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                selectTableTab();
                viewPager.setCurrentItem(1,true);
            }
        });

        stockIndividualTabBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                selectIndividualTab();
                viewPager.setCurrentItem(0,true);

            }
        });

        stockAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                int viewpagerID = viewPager.getCurrentItem();
                if(viewpagerID == 0)
                    v = layoutInflater.inflate(R.layout.dialog_stock_breed_details, null);
                else
                    v = layoutInflater.inflate(R.layout.dialog_stock_entry_details, null);

                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.show();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        selectIndividualTab();
                        break;
                    case 1:
                        selectTableTab();
                        break;
                    case 2:
                        selectEntryTab();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void selectIndividualTab(){
        stockIndividualTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_selected));
        stockIndividualTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));
        stockTableTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.stock_table_bg_unselected));
        stockTableTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        stockEntryTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_unselected));
        stockEntryTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void selectTableTab(){
        stockIndividualTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_unselected));
        stockIndividualTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        stockTableTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.stock_table_bg_selected));
        stockTableTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));
        stockEntryTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_unselected));
        stockEntryTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void selectEntryTab(){
        stockIndividualTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_unselected));
        stockIndividualTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        stockTableTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.stock_table_bg_unselected));
        stockTableTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        stockEntryTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_selected));
        stockEntryTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));

    }
}
