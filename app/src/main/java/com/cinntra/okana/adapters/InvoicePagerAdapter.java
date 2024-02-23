package com.cinntra.okana.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class InvoicePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragmentList;
    String tabs[];
    private String mSearchTerm;
    public InvoicePagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragmentList, String tabs[]) {
    super(fm);
    this.fragmentList = fragmentList;
    this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
      {
    return fragmentList.size();
      }
    @Override
    public CharSequence getPageTitle(int position)
      {
    return tabs[position];
      }
}
