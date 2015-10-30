package com.smartsoftware.android.hearthbeat.ui;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 30.10.2015
 * Time: 20:02
 * Email: mrahbar.azad@gmail.com
 */
public interface ActivityView {
    int getLayout();
    void bind(BaseActivity activity);
}
