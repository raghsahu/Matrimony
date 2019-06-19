package ics.hindu.matrimony.Models;

import java.io.Serializable;

public class ProfileForDTO implements Serializable {
    String id;
    String type;

    public ProfileForDTO(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
