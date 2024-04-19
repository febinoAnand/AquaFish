package com.febino.aquafish;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.febino.aquafish.R;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance) {
        SpannableString s = new SpannableString(getResources().getString(R.string.settings).toUpperCase());
        s.setSpan(new RelativeSizeSpan(0.8f), 0,s.length(), 0);
        s.setSpan(new TypefaceSpan(getContext(), "unicode.futurab.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        View view = layoutInflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle(s);
        TextView appversionTxt = view.findViewById(R.id.setting_app_version_txt);
        appversionTxt.setText(BuildConfig.VERSION_NAME);
        return view;
    }
}
