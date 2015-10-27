package com.smartsoftware.android.hearthbeat.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 05.11.2014
 * Time: 21:00
 * Email: mrahbar.azad@gmail.com
 */
public class ImageLoaderUtils {

    public enum ImageType {
        CARD_IMAGE
    }

    public static final DisplayImageOptions CARD_IMAGE_NORMAL = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .extraForDownloader(ImageType.CARD_IMAGE)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new FadeInBitmapDisplayer(300))
            .build();

}
