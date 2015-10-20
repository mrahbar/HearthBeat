package com.smartsoftware.android.hearthbeat.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.ui.AccessibleLinearLayout;
import com.smartsoftware.android.hearthbeat.ui.adapter.IntroPagerAdapter;

import java.util.Locale;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.OnClick;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:33
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchView implements ViewPager.OnPageChangeListener {

    @Bind(R.id.launch_viewpager)
    ViewPager viewPager;

    @BindDimen(R.dimen.touchfriendly_normal_size)
    float bottomButtonBarHeight;

    @Bind(R.id.launch_bottom_button_bar)
    LinearLayout bottomButtonBar;

    @Bind(R.id.progress_view_container)
    AccessibleLinearLayout progressView;

    private LaunchViewListener listener;
    private Spinner languageSpinner;

    public interface LaunchViewListener {
        void onLaunchMainScreen();
        void onStartDownload(String locale);
        void setContentView(int id);
        void bindViews(LaunchView view);
        LayoutInflater getLayoutInflater();
        Resources getResources();
        Prefs getPrefs();
    }

    public LaunchView(LaunchViewListener listener) {
        this.listener = listener;
        boolean setupFinished = listener.getPrefs().getBoolean(PrefKeys.SETUP_FINISHED, false);

        if (setupFinished) {
            listener.onLaunchMainScreen();
        } else {
            listener.setContentView(R.layout.activity_launch);
            listener.bindViews(this);

            bottomButtonBar.setTranslationY(bottomButtonBarHeight);
            progressView.setStealTouchEvent(true);
            initializeViewpager();
        }
    }

    private void initializeViewpager() {
        viewPager.setAdapter(new IntroPagerAdapter(2, (pager, position) -> {
            View view = null;
            LayoutInflater layoutInflater = listener.getLayoutInflater();

            switch (position) {
                case 0:
                    view = layoutInflater.inflate(R.layout.activity_launch_page1, pager, false);
                    pager.addView(view);
                    break;
                case 1:
                    view = layoutInflater.inflate(R.layout.activity_launch_page2, pager, false);
                    languageSpinner = (Spinner) view.findViewById(R.id.launch_language_spinner);
                    view.findViewById(R.id.launch_download).setOnClickListener(v -> onStartDownload());
                    initializeLanguageSpinner(languageSpinner);
                    pager.addView(view);
                    break;
            }

            return view;
        }));
        viewPager.addOnPageChangeListener(this);
    }

    private void initializeLanguageSpinner(Spinner languageSpinner) {
        Resources resources = listener.getResources();
        Locale current = resources.getConfiguration().locale;
        String languageCode = current.getLanguage()+current.getCountry();
        String[] langcodes = resources.getStringArray(R.array.langcodes);

        for (int i = 0, langcodesLength = langcodes.length; i < langcodesLength; i++) {
            String code = langcodes[i];
            if (TextUtils.equals(code, languageCode)) {
                languageSpinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float factor = Math.abs(positionOffset);
        if (position == 0) {
            bottomButtonBar.setTranslationY(bottomButtonBarHeight - bottomButtonBarHeight * factor);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            bottomButtonBar.setY(bottomButtonBarHeight);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @OnClick(R.id.launch_bottom_button)
    void onClickBottomButton(View v) {
        listener.onLaunchMainScreen();
    }

    private void onStartDownload() {
        final int position = languageSpinner.getSelectedItemPosition();
        String[] langcodes = listener.getResources().getStringArray(R.array.langcodes);
        changeProgressVisibility(true);
        listener.onStartDownload(langcodes[position]);
    }

    public void changeProgressVisibility(final boolean show) {
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate()
                .setDuration(listener.getResources().getInteger(android.R.integer.config_shortAnimTime))
                .alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
    }


    public void onDownloadFailed(){
        changeProgressVisibility(false);
    }
}
