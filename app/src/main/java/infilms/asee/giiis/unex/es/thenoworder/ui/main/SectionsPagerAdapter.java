package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

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
    private FragmentManager fm;
    private List<Product> drinks;
    private List<Product> foods;
    private List<Product> desserts;

    private PlaceholderFragment fragmentFood;
    private PlaceholderFragment fragmentDrink;
    private PlaceholderFragment fragmentDessert;

    public SectionsPagerAdapter(Context context, FragmentManager fm, List<Product> drinks, List<Product> foods, List<Product> desserts) {
        super(fm);
        mContext = context;
        this.fm=fm;
        if(this.drinks == null)
            this.drinks = drinks;
        if(this.foods == null)
            this.foods = foods;
        if(this.desserts == null)
            this.desserts = desserts;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0: {//drinks
                if(fragmentDrink == null) {
                    if(fm.getFragments().size() == 0)
                        fragmentDrink = PlaceholderFragment.newInstance(drinks);
                    else
                        fragmentDrink=(PlaceholderFragment) fm.getFragments().get(0);
                }
                fragment=fragmentDrink;
                break;
            }

            case 1: {//foods
                if(fragmentFood == null) {
                    if(fm.getFragments().size() == 0)
                        fragmentFood = PlaceholderFragment.newInstance(foods);
                    else
                        fragmentFood=(PlaceholderFragment) fm.getFragments().get(1);
                }
                fragment=fragmentFood;
                break;
            }

            case 2: {//desserts
                if(fragmentDessert == null) {
                    if(fm.getFragments().size() == 0)
                        fragmentDessert = PlaceholderFragment.newInstance(desserts);
                    else
                        fragmentDessert=(PlaceholderFragment) fm.getFragments().get(2);
                }
                fragment=fragmentDessert;
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

    public void UpdateFragmentFood(List<Product> food){
        if(fragmentFood==null) {
            fragmentFood= (PlaceholderFragment) getItem(1);
        }
        fragmentFood.UpdateData((ArrayList<Product>) food);
    }
    public void UpdateFragmentDrink(List<Product> drink){
        if(fragmentDrink==null) {
        fragmentDrink= (PlaceholderFragment) getItem(0);
        }
       fragmentDrink.UpdateData((ArrayList<Product>) drink);
    }
    public void UpdateFragmentDessert(List<Product> dessert){
        if(fragmentDessert==null) {
            fragmentDessert= (PlaceholderFragment) getItem(2);
        }
        fragmentDessert.UpdateData((ArrayList<Product>) dessert);
    }
    public PlaceholderFragment getFragmentFood() {
        return fragmentFood;
    }

    public PlaceholderFragment getFragmentDrink() {
        return fragmentDrink;
    }

    public PlaceholderFragment getFragmentDessert() {
        return fragmentDessert;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        if (position >= getCount()) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
    }
}