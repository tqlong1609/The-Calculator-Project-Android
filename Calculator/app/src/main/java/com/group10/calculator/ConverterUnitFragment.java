package com.group10.calculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * class control fragment when pageviewer changed
 */
class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}


public class ConverterUnitFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_converter_unit,container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = view.findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
        return view;
    }

    /**
     * This is set fragment on each tablayout
     * @param viewPager : container in fragment, use to change other tab
     */

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());

        UnitConverter converter = new UnitConverter();

        UnitFragment frLength = new UnitFragment();
        SendDataToChild("LENGTH", frLength);
        adapter.addFragment(frLength, "LENGTH");

        UnitFragment frArea = new UnitFragment();
        SendDataToChild("AREA", frArea);
        adapter.addFragment(frArea, "AREA");

        UnitFragment frWeight = new UnitFragment();
        SendDataToChild("WEIGHT", frWeight);
        adapter.addFragment(frWeight, "WEIGHT");

        UnitFragment frVolume = new UnitFragment();
        SendDataToChild("VOLUME", frVolume);
        adapter.addFragment(frVolume, "VOLUME");

        viewPager.setAdapter(adapter);
    }

    /**
     * This is function send data to children
     * @param unit : unit each tab layout
     * @param fr : fragment children
     */
    private void SendDataToChild(String unit, UnitFragment fr)
    {
        UnitConverter converter = new UnitConverter();
        converter.Confirm("0",unit,0);
        Bundle bundle = new Bundle();
        bundle.putString("UNIT",unit);
        bundle.putStringArray("SIGN",converter.GetArrName().clone());
        fr.setArguments(bundle);
    }
}
