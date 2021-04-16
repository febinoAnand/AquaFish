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
        searchEdit.setTypeface(font);
        searchEdit.setText("Test");

        return view;
    }
}
