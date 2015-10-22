package com.smartsoftware.android.hearthbeat.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.ui.widget.AccessibleLinearLayout;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:33
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchView  {

    @Bind(R.id.progress_view_container)
    AccessibleLinearLayout progressView;

    @Bind(R.id.launch_language_spinner)
    Spinner languageSpinner;

    private LaunchViewListener listener;

    public interface LaunchViewListener {
        void onLaunchMainScreen();
        void onStartDownload(String locale);
        void setContentView(int id);
        void bindViews(LaunchView view);
        Context getContext();
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
            initializeLanguageSpinner();
            progressView.setStealTouchEvent(true);
        }
    }

    private void initializeLanguageSpinner() {
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

    @OnClick(R.id.launch_bottom_button_continue)
    void onClickContinueButton(View v) {
        boolean setupFinished = listener.getPrefs().getBoolean(PrefKeys.SETUP_FINISHED, false);
        if (setupFinished) {
            listener.onLaunchMainScreen();
        } else {
            new MaterialDialog.Builder(listener.getContext())
                    .content(R.string.launch_download_skipped_message)
                    .positiveText(android.R.string.ok)
                    .cancelable(true)
                    .onPositive((materialDialog, dialogAction) -> listener.onLaunchMainScreen())
                    .show();
        }
    }

    @OnClick(R.id.launch_download)
    void onClickDownloadButton(View v) {
        final int position = languageSpinner.getSelectedItemPosition();
        String[] langcodes = listener.getResources().getStringArray(R.array.langcodes);
        onStartDownload(langcodes[position]);
    }

    private void onStartDownload(String langcode) {
        changeProgressVisibility(true);
        listener.onStartDownload(langcode);
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
