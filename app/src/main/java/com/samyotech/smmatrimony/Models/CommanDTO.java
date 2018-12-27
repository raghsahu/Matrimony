package com.samyotech.smmatrimony.Models;

import java.io.Serializable;

public class CommanDTO implements Serializable{
    String id;
    String name;
    String state_id = "";
    boolean isSelected;
    public CommanDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CommanDTO(String id, String name, String state_id) {
        this.id = id;
        this.name = name;
        this.state_id = state_id;
    }


    public CommanDTO(String id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
