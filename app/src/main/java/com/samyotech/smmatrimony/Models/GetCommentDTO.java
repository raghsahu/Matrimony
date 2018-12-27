package com.samyotech.smmatrimony.Models;

import java.io.Serializable;

public class GetCommentDTO implements Serializable {

    String id = "";
    String message = "";
    String date = "";
    String sender_name = "";
    String user_pub_id = "";
    String user_pub_id_receiver = "";
    String send_by = "";
    String send_at = "";
    String media = "";
    String chat_type = "";
    String thumb = "";

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getUser_pub_id() {
        return user_pub_id;
    }

    public void setUser_pub_id(String user_pub_id) {
        this.user_pub_id = user_pub_id;
    }

    public String getUser_pub_id_receiver() {
        return user_pub_id_receiver;
    }

    public void setUser_pub_id_receiver(String user_pub_id_receiver) {
        this.user_pub_id_receiver = user_pub_id_receiver;
    }

    public String getSend_by() {
        return send_by;
    }

    public void setSend_by(String send_by) {
        this.send_by = send_by;
    }

    public String getSend_at() {
        return send_at;
    }

    public void setSend_at(String send_at) {
        this.send_at = send_at;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }
}
