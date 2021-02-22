package com.assignment.projectassignment3;

import android.icu.text.CaseMap;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> firstFragment = new ArrayList<>();
    private final List<String> Titles = new ArrayList<>();
    private FragmentManager fragmentmanager;
    private Map<Integer, String> mFragmentTags;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentmanager = fm;
        mFragmentTags = new HashMap<Integer, String>();

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return fragmentmanager.findFragmentByTag(tag);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return firstFragment.get(position);
    }

    @Override
    public int getCount() {
        return Titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles.get(position);
    }

    public void AddFragment(Fragment fragment, String title) {
        firstFragment.add(fragment);
        Titles.add(title);
    }
}