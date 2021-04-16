package com.example.aquafish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatTextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TraderFragment()).commit();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/unicode.futurab.ttf");
        CustomTypefaceSpan typefaceSpan = new CustomTypefaceSpan("", font);

        for (int i = 0; i <bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            SpannableStringBuilder spannableTitle = new SpannableStringBuilder(menuItem.getTitle());
            spannableTitle.setSpan(typefaceSpan, 0, spannableTitle.length(), 0);
            menuItem.setTitle(spannableTitle);
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_trader:
//                        Toast.makeText(MainActivity.this, "Trader", Toast.LENGTH_SHORT).show();
                        selectedFragment = new TraderFragment();
                        break;
                    case R.id.action_order:
//                        Toast.makeText(MainActivity.this, "Order", Toast.LENGTH_SHORT).show();
                        selectedFragment = new OrderFragment();
                        break;
                    case R.id.action_bill:
//                        Toast.makeText(MainActivity.this, "Bill", Toast.LENGTH_SHORT).show();
                        selectedFragment = new BillFragment();
                        break;
                    case R.id.action_stock:
//                        Toast.makeText(MainActivity.this, "Stock", Toast.LENGTH_SHORT).show();
                        selectedFragment = new StockFragment();
                        break;
                    case R.id.action_setting:
//                        Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        selectedFragment = new SettingFragment();
                        break;
                }
                openFragment(selectedFragment);

                return true;
            }
        });

//        ActionBar actionBar = this.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);            //For back button in titlebar
    }


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
//    }

//    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.fragment_container, fragment).commit();
    }
}
