package com.appsaga.visitrack;

import java.io.Serializable;


public class Visitor implements Serializable {
    private String Name,Phone,Email,CheckIn,HostName,HostEmail,HostPhone,timestamp,CheckOut;
    String key;
    public Visitor(String name, String phone, String email, String checkout,String checkIn, String hostName, String hostEmail, String hostPhone, String timestamp) {
        Name = name;
        Phone = phone;
        CheckOut=checkout;
        Email = email;
        CheckIn = checkIn;
        HostName = hostName;
        HostEmail = hostEmail;
        HostPhone = hostPhone;
        this.timestamp = timestamp;
    }
    public Visitor(){}
    public Visitor(Visitor v,String key)
    {
        Name=v.getName();
        CheckOut=v.getCheckOut();
        Phone=v.getPhone();
        Email=v.getEmail();
        CheckIn=v.getCheckIn();
        this.key=key;
        HostEmail=v.getHostEmail();
        HostName=v.getHostName();
        HostPhone=v.getHostPhone();
        timestamp=v.getTimestamp();
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(String checkIn) {
        CheckIn = checkIn;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getHostEmail() {
        return HostEmail;
    }

    public void setHostEmail(String hostEmail) {
        HostEmail = hostEmail;
    }

    public String getHostPhone() {
        return HostPhone;
    }

    public void setHostPhone(String hostPhone) {
        HostPhone = hostPhone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCheckOut() {
        return CheckOut;
    }

    public void setCheckOut(String checkOut) {
        CheckOut = checkOut;
    }
}
