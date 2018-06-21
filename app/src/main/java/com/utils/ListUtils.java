package com.utils;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * 集合 工具类
 */
public class ListUtils {

    public static int getSize(@Nullable List list) {
        return list != null && list.size() >= 1 ? list.size() : 0;
    }
}