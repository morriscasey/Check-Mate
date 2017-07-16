package com.checkmate.checkmate;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String mUser;
    private Map<String, Fragment> mFragmentMap;
    private Fragment mCurrentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportActionBar().setTitle("Home");
                    mCurrentFragment = mFragmentMap.get("home");
                    transaction
                            .replace(R.id.placeholder, mCurrentFragment)
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    getSupportActionBar().setTitle("Check In");
                    mCurrentFragment = mFragmentMap.get("checkin");
                    transaction
                            .replace(R.id.placeholder, mCurrentFragment)
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    getSupportActionBar().setTitle("Settings");
                    mCurrentFragment = mFragmentMap.get("settings");
                    transaction.replace(R.id.placeholder, mCurrentFragment)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add user
        mUser = getIntent().getStringExtra("USER");

        // App Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize map of Fragments
        mFragmentMap = new HashMap<String, Fragment>();
        mFragmentMap.put("home", new HomeFragment());
        mFragmentMap.put("checkin", new CheckInFragment());
        mFragmentMap.put("settings", new SettingsFragment());

        if (savedInstanceState != null) {
            //Restore the fragment's instance and Title
            mCurrentFragment = getFragmentManager().getFragment(savedInstanceState, "currentFragment");
            getSupportActionBar().setTitle(savedInstanceState.getString("fragment_title"));
        }else{
            // Default Fragment and Title is Home
            mCurrentFragment = mFragmentMap.get("home");
            getSupportActionBar().setTitle("Home");
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction
                .replace(R.id.placeholder, mCurrentFragment)
                .commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance and App Bar Title
        getFragmentManager().putFragment(outState, "currentFragment", mCurrentFragment);
        outState.putString("fragment_title", getSupportActionBar().getTitle().toString());

    }

}
