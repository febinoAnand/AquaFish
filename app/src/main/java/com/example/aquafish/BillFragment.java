package com.example.aquafish;

import android.annotation.SuppressLint;
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
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BillFragment extends Fragment {
    private Button generateTabBtn,listTabBtn;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {
        SpannableString s = new SpannableString(getResources().getString(R.string.bill).toUpperCase());
        s.setSpan(new RelativeSizeSpan(0.8f), 0,s.length(), 0);
        s.setSpan(new TypefaceSpan(getContext(), "unicode.futurab.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        View view = layoutInflater.inflate(R.layout.fragment_bill, container, false);
        getActivity().setTitle(s);

        final ViewPager viewPager = view.findViewById(R.id.bill_view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(new BillGenerateFragment(),"Bill Generate Fragment");
        viewPagerAdapter.addFragment(new BillListFragment(),"Bill List Fragement");
        viewPager.setAdapter(viewPagerAdapter);

        generateTabBtn = view.findViewById(R.id.bill_generate_tab_btn);
        listTabBtn = view.findViewById(R.id.bill_list_tab_btn);

        //TODO: add typeface here



        generateTabBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
                selectGenerateTab();

            }
        });

        listTabBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, true);
                selectListTab();
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
                        selectGenerateTab();
                        break;

                    case 1:
                        selectListTab();
                        break;

                    default:
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
    private void selectGenerateTab(){
        generateTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_selected));
        listTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_unselected));
        generateTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));
        listTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void selectListTab(){
        generateTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_unselected));
        listTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_selected));
        generateTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        listTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));
    }
}
