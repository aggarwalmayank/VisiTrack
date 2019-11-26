package com.appsaga.visitrack;

import android.widget.EditText;

import java.io.Serializable;

public class Host implements Serializable {

    private String Name;
    private String Email;
    private String Phone;
    String key;

    public Host(String name, String email, String phone) {
        Email = email;
        Name = phone;
        Phone = name;
    }



    public Host(Host host) {
        Name =host.getName();
        Email= host.getEmail();
        Phone= host.getPhone();
    }

    public Host() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
