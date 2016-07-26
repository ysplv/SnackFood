package com.example.administrator.mysnack.entity;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class HeadDetailEntity {
    private String title;
    private String img_url;
    private double current;
    private double prime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getPrime() {
        return prime;
    }

    public void setPrime(double prime) {
        this.prime = prime;
    }
}
