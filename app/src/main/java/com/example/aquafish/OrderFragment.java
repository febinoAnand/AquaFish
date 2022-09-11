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

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class OrderFragment extends Fragment {
    private Button tableTabBtn;
    private Button listTabBtn;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {

        SpannableString s = new SpannableString(getResources().getString(R.string.order).toUpperCase());
        s.setSpan(new RelativeSizeSpan(0.8f), 0,s.length(), 0);
        s.setSpan(new TypefaceSpan(getContext(), "unicode.futurab.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        View view = layoutInflater.inflate(R.layout.fragment_order, container, false);
        getActivity().setTitle(s);

        final ViewPager viewPager = view.findViewById(R.id.order_view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new OrderTableFragment(),"Order Table Fragement");
        viewPagerAdapter.addFragment(new OrderListFragment(),"Order List Fragement");
        viewPager.setAdapter(viewPagerAdapter);

        tableTabBtn = view.findViewById(R.id.order_table_tab_btn);
        listTabBtn = view.findViewById(R.id.order_list_tab_btn);
        ImageButton addImgBtn = view.findViewById(R.id.order_add_btn);

        //TODO: add typeface here

        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.dialog_order_details, null);

                //TODO:add typeface for popup dialog fields

                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.show();
            }
        });

        tableTabBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
                selectTableTab();

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
                            selectTableTab();
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

//        ViewPager viewPager = view.findViewById(R.id.order_view_pager);


//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

//        viewPagerAdapter.addFragment(new TraderFragment(),"Trader Fragement");
//        viewPagerAdapter.addFragment(new StockFragment(),"Stock Fragement");
//        viewPagerAdapter.addFragment(new OrderTableFragment(),"Order Table Fragement");
//        viewPagerAdapter.addFragment(new OrderListFragment(),"Order List Fragement");


//        viewPagerAdapter.addFragment(new Fragment(){
//            @Override
//            public void onCreate(Bundle savedInstance) {
//                super.onCreate(savedInstance);
//            }
//
//            @Override
//            public View onCreateView(LayoutInflater layoutInflater1,ViewGroup viewGroup,Bundle bundle){
//                View view = layoutInflater1.inflate(R.layout.trader_adapter_view, viewGroup, false);
//
//                return view;
//            }
//        },"Dynamic Table");





        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void selectTableTab(){
        tableTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_selected));
        listTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_unselected));
        tableTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));
        listTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void selectListTab(){
        tableTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_table_bg_unselected));
        listTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.order_tab_list_bg_selected));
        tableTabBtn.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        listTabBtn.setTextColor(getContext().getResources().getColor(R.color.white));
    }
}
