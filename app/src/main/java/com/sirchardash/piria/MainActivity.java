package com.sirchardash.piria;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    private BottomNavigationView bottomNavigation;
    private boolean backDisabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((PiriaApplication) getApplicationContext()).applicationComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(this::switchActiveFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, museumsFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (backDisabled) {
            super.moveTaskToBack(true);
            return;
        }

        super.onBackPressed();
        setNavbarVisibility(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
    }

    private boolean switchActiveFragment(MenuItem item) {
        Fragment selectedFragment = navbarIdToFragment.get(item.getItemId());

        if (selectedFragment == null) {
            return false;
        }

        setNavbarVisibility(selectedFragment);

        navigateTo(selectedFragment, false);

        return true;
    }

    public void navigateTo(Fragment fragment, boolean backSupported) {
        FragmentTransaction replace = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (backSupported) {
            replace.addToBackStack(null);
        }

        replace.commit();
        setNavbarVisibility(fragment);
    }

    private void setNavbarVisibility(Fragment activeFragment) {
        bottomNavigation.setVisibility(
                activeFragment instanceof NavbarDockedFragment
                        ? View.VISIBLE
                        : View.INVISIBLE
        );
    }

    public void setBackDisabled(boolean backDisabled) {
        this.backDisabled = backDisabled;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        bottomNavigation.getMenu().getItem(0).setTitle(R.string.museums_navbar);
        bottomNavigation.getMenu().getItem(1).setTitle(R.string.tours_navbar);
        bottomNavigation.getMenu().getItem(2).setTitle(R.string.profile_navbar);
        profileFragment.updateInterface();
        museumsFragment.updateInterface();
    }

}