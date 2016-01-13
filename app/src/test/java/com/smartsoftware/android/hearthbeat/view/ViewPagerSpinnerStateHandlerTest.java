package com.smartsoftware.android.hearthbeat.view;

import android.support.v4.view.ViewPager;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 02.11.2015
 * Time: 15:26
 * Email: mrahbar.azad@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewPagerSpinnerStateHandlerTest {

    @Mock
    ViewPager viewPager;

    @Mock
    Spinner spinner;

    private CollectionView.ViewPagerSpinnerStateHandler stateHandler;

    @Before
    public void setup() {
        stateHandler = new CollectionView.ViewPagerSpinnerStateHandler(spinner, viewPager);
    }

    private void changeViewPagerPage(int position) {
        stateHandler.onPageSelected(position);
    }

    private void changeSpinnerIndex(int position) {
        stateHandler.onItemSelected(null, null, position, -1);
    }
}