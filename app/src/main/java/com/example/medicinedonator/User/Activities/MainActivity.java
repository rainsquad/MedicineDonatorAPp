package com.example.medicinedonator.User.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Fragments.DonateFragment;
import com.example.medicinedonator.User.Fragments.HomeFragment;
import com.example.medicinedonator.User.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();

    SearchFragment searchFragment = new SearchFragment();
    DonateFragment donateFragment = new DonateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return;

                    case R.id.Donate:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, donateFragment).commit();
                        return;

                    case R.id.Search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                        return;



                }
                return;
            }
        });
    }
}