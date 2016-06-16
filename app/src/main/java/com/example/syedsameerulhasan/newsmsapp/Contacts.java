package com.example.syedsameerulhasan.newsmsapp;

/**
 * Created by Syed Sameer Ul Hasan on 09-Jun-16.
 */
public class Contacts {
    private int _id;
    private String _contactnumber;

    public Contacts(){
    }


    public Contacts(String contactnumber){
        this._contactnumber = contactnumber;
    }

    public Contacts(int id, String contactnumber){
        this._id = id;
        this._contactnumber = contactnumber;
    }



    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_contactnumber(String _contactnumber) {
        this._contactnumber = _contactnumber;
    }

    public int get_id() {
        return _id;
    }

    public String get_contactnumber() {
        return _contactnumber;
    }
}
