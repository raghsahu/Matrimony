package com.samyotech.matrimony.Models;

import java.util.ArrayList;


public class ListFriend {
    private ArrayList<Friend> listFriend;

    public ArrayList<Friend> getListFriend() {
        return listFriend;
    }

    public ListFriend(){
        listFriend = new ArrayList<>();
    }

    public String getAvataById(String id){
        for(Friend friend: listFriend){
            if(id.equals(friend.id)){
                return friend.avata;
            }
        }
        return "";
    }
    public Friend getByEmail(String id){
        for(Friend friend: listFriend){
            if(id.equals(friend.email)){
                return friend;
            }
        }
        return new Friend();
    }

    public void setListFriend(ArrayList<Friend> listFriend) {
        this.listFriend = listFriend;
    }
}
