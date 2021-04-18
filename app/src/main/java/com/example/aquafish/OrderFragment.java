package com.example.aquafish;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class OrderFragment extends Fragment {
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


        ViewPager viewPager = view.findViewById(R.id.order_view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new TraderFragment(),"Trader Fragement");
        viewPagerAdapter.addFragment(new StockFragment(),"Stock Fragement");

        viewPager.setAdapter(viewPagerAdapter);


        return view;
    }

}
