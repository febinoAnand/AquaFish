package com.example.aquafish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
