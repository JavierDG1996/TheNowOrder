package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import infilms.asee.giiis.unex.es.thenoworder.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.drinks, R.string.foods, R.string.desserts};
    private Fragment tabs[];
    private Context mContext;
    //private List<PlaceholderFragment> fragments;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        tabs= new Fragment[3];
        //fragments= new ArrayList<>();
    }
    @Override
    public Fragment getItem(int position) {
      Fragment fragment;
 /*
        switch (position) {
            case 0: {//drinks
                if(fragments.get(0) == null)
                    fragments.add(0,PlaceholderFragment.newInstance());
                else
                    fragment= PlaceholderFragment.newInstance();
            }
            case 1: {//foods
                if(fragments.get(1) == null)
                    fragments.add(1,PlaceholderFragment.newInstance());
                else
                    fragment= fragments.get(1);
            }
            case 2: {//desserts
                if(fragments.get(2) == null)
                    fragments.add(2,PlaceholderFragment.newInstance());
                else
                    fragment= fragments.get(2);
            }
    }*/
        tabs[position]= new PlaceholderFragment(position);
    return tabs[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }

}