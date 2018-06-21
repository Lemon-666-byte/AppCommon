package com.net;

import android.support.v4.util.ArrayMap;

import com.utils.biz.Biz;
import com.utils.sharedpreferences.SharedConfigs;

import java.util.Map;

/**
 * Created by Yang on 2018/3/14.
 */

public class RequestUtils {

    public static Map<String, Object> getParams() {
        Map<String, Object> params = new ArrayMap<>();
        params.put(SharedConfigs.UserData.SessionKey, Biz.getInstance().getSessionKey());
        params.put(SharedConfigs.UserData.UserId, Biz.getInstance().getUserId());
        return params;
    }

}
