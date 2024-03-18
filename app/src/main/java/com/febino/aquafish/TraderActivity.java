package com.febino.aquafish;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;

import com.febino.aquafish.R;

public class TraderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader);
        SpannableString s = new SpannableString("TRADER");
        s.setSpan(new TypefaceSpan(this, "futur.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(s);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
