package com.febino.aquafish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.WindowManager;


import com.febino.aquafish.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction().add(R.id.fragment_container, new TraderFragment(), "LIST").commit();
//        }
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
