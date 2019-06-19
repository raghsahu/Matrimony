package ics.hindu.matrimony.Models;

import java.io.Serializable;

/**
 * Created by amit on 7/9/17.
 */

public class LanguageDTO implements Serializable {

        String id = "";
        String language_name = "";
        String created_at = "";
        String updated_at = "";

    public LanguageDTO(String language_name) {
        this.language_name = language_name;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLanguage_name() {
            return language_name;
        }

        public void setLanguage_name(String language_name) {
            this.language_name = language_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
}