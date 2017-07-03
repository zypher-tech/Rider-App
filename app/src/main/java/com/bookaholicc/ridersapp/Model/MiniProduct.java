package com.bookaholicc.ridersapp.Model;

/**
 * Created by nandhu on 20/6/17.
 *
 *
 */

public class MiniProduct {
    private String productName;
    private String imageURL;
    private int pid;

    public MiniProduct(String productName, String imageURL, int pid) {
        this.productName = productName;
        this.imageURL = imageURL;
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
