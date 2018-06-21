package com.ui.main.bean;


/**
 * @author yang
 */
public class User {
    private int UserID;
    private String UserName;
    private String SessionKey;
    private String RoleIds;
    private String RoleNames;
    private String Phone;
    private int OrganizationID;
    private String OrganizationName;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getRoleIds() {
        return RoleIds;
    }

    public void setRoleIds(String roleIds) {
        RoleIds = roleIds;
    }

    public String getRoleNames() {
        return RoleNames;
    }

    public void setRoleNames(String roleNames) {
        RoleNames = roleNames;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(int organizationID) {
        OrganizationID = organizationID;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", UserName='" + UserName + '\'' +
                ", SessionKey='" + SessionKey + '\'' +
                ", RoleIds='" + RoleIds + '\'' +
                ", RoleNames='" + RoleNames + '\'' +
                ", Phone='" + Phone + '\'' +
                ", OrganizationID=" + OrganizationID +
                ", OrganizationName='" + OrganizationName + '\'' +
                '}';
    }
}
