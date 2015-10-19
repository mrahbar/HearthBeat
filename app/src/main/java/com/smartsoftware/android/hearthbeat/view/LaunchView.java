package com.smartsoftware.android.hearthbeat.view;

import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Spinner;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:33
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchView {

    @Bind(R.id.launch_download)
    Button downloadButton;

    @Bind(R.id.launch_language_spinner)
    Spinner langugageSpinner;

    public LaunchView(BaseActivity activity) {
        activity.setContentView(getLayout());
        ButterKnife.bind(this, activity);

        Resources resources = activity.getResources();
        Locale current = resources.getConfiguration().locale;
        String languageCode = current.getLanguage()+current.getCountry();
        String[] langcodes = resources.getStringArray(R.array.langcodes);

        for (int i = 0, langcodesLength = langcodes.length; i < langcodesLength; i++) {
            String code = langcodes[i];
            if (TextUtils.equals(code, languageCode)) {
                langugageSpinner.setSelection(i);
                break;
            }
        }
    }

    public int getLayout() {
        return R.layout.activity_launch;
    }
}
