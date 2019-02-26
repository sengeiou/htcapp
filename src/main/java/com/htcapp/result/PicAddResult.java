package com.htcapp.result;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Jone on 2018-06-14.
 */
public class PicAddResult implements Result {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatar;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static PicAddResult build(String avatar,String url,String message){
        PicAddResult picAddResult=new PicAddResult();
        picAddResult.setAvatar(avatar);
        picAddResult.setUrl(url);
        picAddResult.setMessage(message);
        return picAddResult;
    }
}
