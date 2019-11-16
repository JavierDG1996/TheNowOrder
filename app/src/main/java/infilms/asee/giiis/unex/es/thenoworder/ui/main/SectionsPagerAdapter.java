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
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.drinks, R.string.foods, R.string.desserts};
    private final Context mContext;
    private List<Product> drinks;
    private List<Product> foods;
    private List<Product> desserts;


    public SectionsPagerAdapter(Context context,
                                FragmentManager fm,
                                List<Product> drinks,
                                List<Product> foods,
                                List<Product> desserts ) {
        super(fm);
        mContext = context;
        this.drinks = drinks;
        this.foods = foods;
        this.desserts = desserts;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:{//drinks
                fragment = PlaceholderFragment.newInstance(drinks);
                break;
            }

            case 1:{//foods
                fragment = PlaceholderFragment.newInstance(foods);

                break;
            }

            case 2:{//desserts
                fragment = PlaceholderFragment.newInstance(desserts);

                break;
            }
        }

        return fragment;
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