package com.smartsoftware.android.hearthbeat.ui.widget;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 23.10.2015
 * Time: 21:40
 * Email: mrahbar.azad@gmail.com
 */
public class CardViewAware extends ImageViewAware {

    private int width, height;

    public CardViewAware(ImageView imageView, int width, int height) {
        super(imageView);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
