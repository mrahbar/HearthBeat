package com.smartsoftware.android.hearthbeat.chrome;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.smartsoftware.android.hearthbeat.main.WebviewActivity;


/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 02.10.2015
 * Time: 21:15
 * Email: mrahbar.azad@gmail.com
 *
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {

    @Override
    public void openUri(Activity activity, Uri uri) {
        startFallback(activity, uri);
    }

    public static void startFallback(Activity activity, Uri uri) {
        Intent intent = new Intent(activity, WebviewActivity.class);
        intent.putExtra(WebviewActivity.EXTRA_URL, uri.toString());
        activity.startActivity(intent);
        activity.finish();
    }
}
