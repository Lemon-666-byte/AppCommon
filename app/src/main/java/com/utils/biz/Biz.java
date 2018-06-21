package com.utils.biz;

import com.blankj.utilcode.util.Utils;
import com.utils.sharedpreferences.SharedConfigs;
import com.utils.sharedpreferences.SharedUtils;

/**
 * 状态记录业务类
 */
public class Biz {

    private static Biz instance;

    /**
     * 单例模式
     *
     * @return StatusRecordBiz
     */
    public static Biz getInstance() {
        if (instance == null) {
            synchronized (Biz.class) {
                if (instance == null) {
                    instance = new Biz();
                }
            }
        }
        return instance;
    }

    /**
     * 保存用户sessionKey
     */
    public void setSessionKey(String sessionKey) {
        SharedUtils.saveCommon(Utils.getApp(), SharedConfigs.UserData.SessionKey, sessionKey);
    }

    /**
     * 获取用户sessionKey
     *
     * @return sessionKey
     */
    public String getSessionKey() {
        return SharedUtils.getCommon(Utils.getApp(), SharedConfigs.UserData.SessionKey);
    }

    /**
     * 保存用户id
     */
    public void setUserId(int userId) {
        SharedUtils.saveIntegerCommon(Utils.getApp(), SharedConfigs.UserData.UserId, userId);
    }

    /**
     * 获取用户id
     *
     * @return userId
     */
    public int getUserId() {
        return SharedUtils.getIntegerCommon(Utils.getApp(), SharedConfigs.UserData.UserId);
    }

    /**
     * 保存用户名
     */
    public void setUserName(String userName) {
        SharedUtils.saveCommon(Utils.getApp(), SharedConfigs.UserData.UserName, userName);
    }

    /**
     * 获取用户名
     *
     * @return userName
     */
    public String getUserName() {
        return SharedUtils.getCommon(Utils.getApp(), SharedConfigs.UserData.UserName);
    }

    /**
     * 保存用户登录名
     */
    public void setUserAccountName(String accountName) {
        SharedUtils.saveCommon(Utils.getApp(), SharedConfigs.UserData.AccountName, accountName);
    }

    /**
     * 获取用户登录名
     *
     * @return AccountName
     */
    public String getUserAccountName() {
        return SharedUtils.getCommon(Utils.getApp(), SharedConfigs.UserData.AccountName);
    }

    /**
     * 保存用户的密码
     */
    public void setPassword(String passWord) {
        SharedUtils.saveCommon(Utils.getApp(), SharedConfigs.UserData.PassWord, passWord);
    }

    /**
     * 获取用户的密码
     *
     * @return passWord
     */
    public String getPassword() {
        return SharedUtils.getCommon(Utils.getApp(), SharedConfigs.UserData.PassWord);
    }

}
