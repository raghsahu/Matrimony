package com.samyotech.smmatrimony.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageDTO implements Serializable {

    String media_id = "";
    String thumb_url = "";
    String medium_url = "";
    String big_url = "";
    String uploaded = "";
    boolean selected = false;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getMedium_url() {
        return medium_url;
    }

    public void setMedium_url(String medium_url) {
        this.medium_url = medium_url;
    }

    public String getBig_url() {
        return big_url;
    }

    public void setBig_url(String big_url) {
        this.big_url = big_url;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
