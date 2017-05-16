package com.makeryan.lib.widget.tablayout.listener;

import android.support.annotation.DrawableRes;

public interface ITabBody {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}