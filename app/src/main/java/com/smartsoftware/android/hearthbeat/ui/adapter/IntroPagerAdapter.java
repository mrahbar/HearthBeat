package com.smartsoftware.android.hearthbeat.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.09.2014
 * Time: 23:48
 * Email: mrahbar.azad@gmail.com
 */
public class IntroPagerAdapter extends PagerAdapter {

    public interface PageFactory {
        View onPageCreated(ViewGroup pager, int position);
    }

    private int count;
    private final PageFactory pageFactory;

    public IntroPagerAdapter(int count, PageFactory pageFactory) {
        this.count = count;
        this.pageFactory = pageFactory;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object instantiateItem(ViewGroup pager, int position) {
        View view = null;

        if (pageFactory != null) {
            view = pageFactory.onPageCreated(pager, position);
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup pager, int position, Object view) {
        pager.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
