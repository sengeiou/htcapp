package com.htcapp.result;

import java.util.List;

/**
 * Created by Jone on 2018-06-03.
 */
public class PageResult implements Result {

    private Integer current_page;//当前页

    private Object[] data;//每次查询的数据量


    private String first_page_url;//第一页的URL

    private Integer from;//从几开始

    private Integer last_page;//最后一页页码

    private String last_page_url;//最后一页的url

    private String next_page_url;//下一页的url

    private String path;//请求的根路径

    private Integer per_page;//每页数据量

    private String prev_page_url;//之前的url

    private Integer to;//到第几行

    private Integer total;//记录总数量


    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    /*明早上测试一下*/
    /**
     * 传入如下参数，即可完成分页
     * @param perValue 每页的数量
     * @param page  当前页
     * @param path  当前请求路径
     * @param data  请求返回的数据
     * @param count 总数量
     * @return
     */
    public static PageResult build(Integer perValue,
                                   Integer page,
                                   String path,
                                   String search,
                                   List data, Integer count) {

        int flag=0;//1表示需要&

        int end1=path.lastIndexOf("?");
        String onionPath=null;
        String pagePath=null;
        if (end1>0){
            onionPath=path.substring(0,path.lastIndexOf("?"));
        }else {
            onionPath=path;
        }

        if (path.lastIndexOf("?search=")>0){
            flag=1;
        }

        int end_1=path.lastIndexOf("&page");
        int end_2=path.lastIndexOf("page");
        if (end_1>0){
            pagePath=path.substring(0,end_1);
        }else{
            if (end_2>0){
                pagePath=path.substring(0,end_2);
            }else{
                pagePath=path;
            }
        }

        if (end1<0){
            pagePath+="?";
        }
        if (flag>0){
            pagePath+="&";
        }

        PageResult pageResult = new PageResult();

        pageResult.setCurrent_page(page);

        pageResult.setTotal(count);

        pageResult.setPer_page(perValue);

        Integer end=null;
        Integer start=null;
        if (data==null||data.size()==0){
            pageResult.setFrom(null);
            pageResult.setTo(null);
            pageResult.setData(new Object[]{});

        }else {
            start=(page-1)*perValue+1;
            end=start+data.size()-1;
            pageResult.setFrom(start);
            pageResult.setTo(end);
            pageResult.setData(data.toArray());
        }
        int last=count%perValue==0?(count/perValue):(count/perValue+1);
        last=(last==0)? 1 : last;
        pageResult.setLast_page(last);

        pageResult.setPath(onionPath);

        pageResult.setFirst_page_url(pagePath+"page=1");

        pageResult.setLast_page_url(pagePath+"page="+last);

        if (page!=1){
            pageResult.setPrev_page_url(pagePath+"page="+(page-1));
        }
        if(page<last){
            pageResult.setNext_page_url(pagePath+"page="+(page+1));
        }
        return pageResult;
    }
}
