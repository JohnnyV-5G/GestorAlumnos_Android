package com.john.gestoralumnos.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.john.gestoralumnos.R;
import com.john.gestoralumnos.student.StudentFragment;
import com.john.gestoralumnos.subject.SubjectFragment;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>Section Page Adapter</h2>
 * <p>Aqui se va crear el fragmente y sus parametros. A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 *version 1.0
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    /**
     * @param context
     * @param fm
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        StudentFragment sf;
        SubjectFragment subFrag;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return sf = new StudentFragment();
        } else {
            System.out.println("Returning subject fragment");
            return subFrag = new SubjectFragment();
        }
    }

    /**
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}