package com.example.cmplive.Login;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                AdLogin adLogin = new AdLogin();
                return adLogin;

            case 1:
                TeachLogin teachLogin = new TeachLogin();
                return teachLogin;

            case 2:
                StuLogin stuLogin = new StuLogin();
                return stuLogin;

            default: return null;
        }
    }
    }

