package com.smartsoftware.android.hearthbeat.ui.view;

import android.content.res.Resources;
import android.view.ViewGroup;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.ui.ScreenContainer;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:32
 * Email: mrahbar.azad@gmail.com
 */
public class DecksView {

    private ScreenContainer screenContainer;
    private DecksViewListener listener;
    private ViewGroup viewContainer;

    public interface DecksViewListener {
        Resources getResources();
    }

    public DecksView(DecksViewListener listener) {
        this.listener = listener;
        screenContainer = new ScreenContainer();
    }

    public void bind(BaseActivity activity) {
        viewContainer = screenContainer.bind(activity);
        activity.getLayoutInflater().inflate(R.layout.activity_main, viewContainer);
    }
}
