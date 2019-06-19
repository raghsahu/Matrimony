package ics.hindu.matrimony.models;

import java.io.Serializable;

/**
 * Created by pushpraj on 20/4/18.
 */

public class PackagesDTO implements Serializable {

    String id = "";
    String title = "";
    String description = "";
    String price = "";
    String subscription_type = "";
    String status = "";
    String subscription_name = "";
    boolean selected = false;

    public PackagesDTO(String id, String title, String description, String price, String subscription_type, String status, String subscription_name, boolean selected) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.subscription_type = subscription_type;
        this.status = status;
        this.subscription_name = subscription_name;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscription_name() {
        return subscription_name;
    }

    public void setSubscription_name(String subscription_name) {
        this.subscription_name = subscription_name;
    }



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
