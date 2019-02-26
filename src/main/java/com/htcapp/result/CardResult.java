package com.htcapp.result;

/**
 * Created by Jone on 2018-06-14.
 */
public class CardResult  implements Result{

    private String code;
    private Boolean deleted;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public static CardResult build(String code,Boolean deleted){
        CardResult result = new CardResult();
        result.setCode(code);
        result.setDeleted(deleted);
        return result;
    }
}
