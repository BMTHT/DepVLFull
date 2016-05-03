/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.bean;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author BuiNgocTruong
 */
public class Image {
    private int imgId;
    private int userId;
    private String imgDescribe;
    private Date imgDate;
    private String theme;
    private Float point;
    private String imgUrl;

    public Image() {
    }

    public Image(int imgId, int userId, String imgDescribe, Date imgDate, String theme, Float point, String imgUrl) {
        this.imgId = imgId;
        this.userId = userId;
        this.imgDate = imgDate;
        this.imgDescribe = imgDescribe;
        this.theme = theme;
        this.point = point;
        this.imgUrl = imgUrl;
    }

    public Image(int imgId, int userId, Date imgDate, String theme, String imgUrl) {
        this.imgId = imgId;
        this.userId = userId;
        this.imgDate = imgDate;
        this.theme = theme;
        this.imgUrl = imgUrl;
    }

    public Image(String imgDescribe, Date imgDate, String theme, Float point, String imgUrl) {
        this.imgDate = imgDate;
        this.imgDescribe = imgDescribe;
        this.theme = theme;
        this.point = point;
        this.imgUrl = imgUrl;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getImgDate() {
        return imgDate;
    }

    public void setImgDate(long imgDate) {
        Date date = new Date(imgDate);
        this.imgDate = date;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Float getPoint() {
        return point;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public String getImgDescribe() {
        return imgDescribe;
    }

    public void setImgDescribe(String imgDescribe) {
        this.imgDescribe = imgDescribe;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public String getImageDateToString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(imgDate);
    }
}
