package com.sirchardash.piria;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.sirchardash.piria.databinding.ActivityMainBinding;

import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private final MuseumsFragment museumsFragment = new MuseumsFragment();
    private final ToursFragment toursFragment = new ToursFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    private final Map<Integer, Fragment> navbarIdToFragment = Map.of(
            R.id.navbar_museums, museumsFragment,
            R.id.navbar_tours, toursFragment,
            R.id.navbar_profile, profileFragment
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((PiriaApplication) getApplicationContext()).applicationComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(this::switchActiveFragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, museumsFragment).commit();
    }

    private boolean switchActiveFragment(MenuItem item) {
        Fragment selectedFragment = navbarIdToFragment.get(item.getItemId());

        if (selectedFragment == null) {
            return false;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();

        return true;
    }

}