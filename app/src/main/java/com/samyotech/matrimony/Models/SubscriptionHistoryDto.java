package com.samyotech.matrimony.Models;

public class SubscriptionHistoryDto {
    String currency_type = "";
    String id = "";
    String order_id = "";
    String txn_id = "";
    String user_pub_id = "";
    String subscription_type = "";
    String subscription_start_date = "";
    String subscription_end_date = "";
    String price = "";
    String subscription_name = "";

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getUser_pub_id() {
        return user_pub_id;
    }

    public void setUser_pub_id(String user_pub_id) {
        this.user_pub_id = user_pub_id;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

    public String getSubscription_start_date() {
        return subscription_start_date;
    }

    public void setSubscription_start_date(String subscription_start_date) {
        this.subscription_start_date = subscription_start_date;
    }

    public String getSubscription_end_date() {
        return subscription_end_date;
    }

    public void setSubscription_end_date(String subscription_end_date) {
        this.subscription_end_date = subscription_end_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubscription_name() {
        return subscription_name;
    }

    public void setSubscription_name(String subscription_name) {
        this.subscription_name = subscription_name;
    }
}
