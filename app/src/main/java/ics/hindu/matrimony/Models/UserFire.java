package ics.hindu.matrimony.Models;



public class UserFire {
    public String name;
    public String email;
    public String avata;
    public Status status;
    public Message message;

    public UserFire(){
        status = new Status();
        message = new Message();
        status.isOnline = false;
        status.timestamp = 0;
        message.idReceiver = "0";
        message.idSender = "0";
        message.text = "";
        message.timestamp = 0;
    }

}
