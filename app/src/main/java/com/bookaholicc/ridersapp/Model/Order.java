package com.bookaholicc.ridersapp.Model;



import java.security.Timestamp;
import java.util.List;

/**
 * Created by nandhu on 20/6/17.
 *
 */

public class Order  {

    private boolean isAccepted;
    private List<MiniProduct> mList;
    private boolean orderStatus;
    private int orderId;
    private int amount;
    private int userId;
    private  String firstName;
    private String lastName;
    private String phoneNumber;
    private double orderLat;
    private double orderLon;
    private Timestamp orderInsertedAt;
    private Timestamp orderAcceptedAt;
    private Timestamp riderAcceptedAt;
    private Timestamp dispatchedAt;
    private Timestamp returnDate;
    private Timestamp deliveredAt;
    private Timestamp returnedAt;
    private Timestamp returnCondtion;
    private int deliveryStatus;


    public Order(List<MiniProduct> mProduct, int amount, String phoneNumber, double orderLat, double orderLon, String userName, int orderId, int userId) {
        this.mList = mProduct;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
        this.orderLat = orderLat;
        this.orderLon = orderLon;
        this.firstName = userName;
        this.userId = userId;
        this.orderId = orderId;
    }


    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public List<MiniProduct> getmList() {
        return mList;
    }

    public void setmList(List<MiniProduct> mList) {
        this.mList = mList;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getOrderLat() {
        return orderLat;
    }

    public void setOrderLat(double orderLat) {
        this.orderLat = orderLat;
    }

    public double getOrderLon() {
        return orderLon;
    }

    public void setOrderLon(double orderLon) {
        this.orderLon = orderLon;
    }

    public Timestamp getOrderInsertedAt() {
        return orderInsertedAt;
    }

    public void setOrderInsertedAt(Timestamp orderInsertedAt) {
        this.orderInsertedAt = orderInsertedAt;
    }

    public Timestamp getOrderAcceptedAt() {
        return orderAcceptedAt;
    }

    public void setOrderAcceptedAt(Timestamp orderAcceptedAt) {
        this.orderAcceptedAt = orderAcceptedAt;
    }

    public Timestamp getRiderAcceptedAt() {
        return riderAcceptedAt;
    }

    public void setRiderAcceptedAt(Timestamp riderAcceptedAt) {
        this.riderAcceptedAt = riderAcceptedAt;
    }

    public Timestamp getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Timestamp dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Timestamp getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(Timestamp returnedAt) {
        this.returnedAt = returnedAt;
    }

    public Timestamp getReturnCondtion() {
        return returnCondtion;
    }

    public void setReturnCondtion(Timestamp returnCondtion) {
        this.returnCondtion = returnCondtion;
    }
}
