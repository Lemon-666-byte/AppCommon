package com.ui.main.bean;

import java.io.Serializable;

/**
 * @author yang
 */
public class UploadPicture implements Serializable {
    private int ID;
    private String PhotoName;
    private String OriginalUrl;
    private String DefaultUrl;
    private int IsVerify;
    private int CreateUserID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPhotoName() {
        return PhotoName;
    }

    public void setPhotoName(String photoName) {
        PhotoName = photoName;
    }

    public String getOriginalUrl() {
        return OriginalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        OriginalUrl = originalUrl;
    }

    public String getDefaultUrl() {
        return DefaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        DefaultUrl = defaultUrl;
    }

    public int getIsVerify() {
        return IsVerify;
    }

    public void setIsVerify(int isVerify) {
        IsVerify = isVerify;
    }

    public int getCreateUserID() {
        return CreateUserID;
    }

    public void setCreateUserID(int createUserID) {
        CreateUserID = createUserID;
    }
}
