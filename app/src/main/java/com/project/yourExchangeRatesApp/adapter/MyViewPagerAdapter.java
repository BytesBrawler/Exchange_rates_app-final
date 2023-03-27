package com.project.yourExchangeRatesApp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.yourExchangeRatesApp.HomeFragment;
import com.project.yourExchangeRatesApp.notificationFragment;
import com.project.yourExchangeRatesApp.profileFragment;
import com.project.yourExchangeRatesApp.trendsFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new trendsFragment();
            case 2:
                return new notificationFragment();
            case 3:
                return new profileFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }
}