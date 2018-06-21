package com.ui.main.bean;

import java.util.List;

/**
 * @ClassName:RespUpdate
 * @date 2015-12-15
 * @Description:更新实体
 */
public class RespUpdate {

    private String appname;
    private String downurl;
    private String verName;
    private String verCode;
    private String updateTitle;
    private String size;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<UpdateContentEntity> getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(List<UpdateContentEntity> updateContent) {
        this.updateContent = updateContent;
    }

    public List<PreVersionInfoEntity> getPreVersionInfo() {
        return preVersionInfo;
    }

    public void setPreVersionInfo(List<PreVersionInfoEntity> preVersionInfo) {
        this.preVersionInfo = preVersionInfo;
    }

    /**
     * content : 1、优化上传图片
     */

    private List<UpdateContentEntity> updateContent;
    /**
     * content : 无低价拍卖
     */

    private List<PreVersionInfoEntity> preVersionInfo;

    public static class UpdateContentEntity {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class PreVersionInfoEntity {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * 返回最新版本文案信息
     *
     * @return
     */
    public String getInfoByUpdateContent() {
        int length = updateContent.size();
        StringBuilder sb = new StringBuilder();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    sb.append("\n");
                }
                sb.append(updateContent.get(i).getContent());
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "RespUpdate{" +
                "appname='" + appname + '\'' +
                ", downurl='" + downurl + '\'' +
                ", verName='" + verName + '\'' +
                ", verCode='" + verCode + '\'' +
                ", updateTitle='" + updateTitle + '\'' +
                ", size='" + size + '\'' +
                ", updateContent=" + updateContent +
                ", preVersionInfo=" + preVersionInfo +
                '}';
    }
}
