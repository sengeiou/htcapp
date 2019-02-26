package com.htcapp.service.push;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 极光推送数据：
 *         用于创建推送的数据
 */
public class JiGuangPushData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;//推送的用户主键
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer key;//表示用户动作：1进入 2出去
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer fid;//表示订单信息

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public static JiGuangPushData build(Integer id, Integer key){
       return JiGuangPushData.build(id,key,null);
    }
    public static JiGuangPushData build(Integer id,Integer key,Integer fid){
        JiGuangPushData jiGuangPushData=new JiGuangPushData();
        jiGuangPushData.setId(id);
        jiGuangPushData.setKey(key);
        jiGuangPushData.setFid(fid);
        return jiGuangPushData;
    }
}
