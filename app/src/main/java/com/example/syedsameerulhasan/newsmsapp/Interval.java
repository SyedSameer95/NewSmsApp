package com.example.syedsameerulhasan.newsmsapp;

/**
 * Created by Syed Sameer Ul Hasan on 15-Jun-16.
 */
public class Interval {

    private int _id;
    private String _timeinterval;

    public Interval(){
    }

    public Interval(String timeinterval) {
        this._timeinterval = timeinterval;
    }

    public Interval(int _id, String _timeinterval) {
        this._id = _id;
        this._timeinterval = _timeinterval;
    }

    public int get_id() {
        return _id;
    }

    public String get_timeinterval() {
        return _timeinterval;
    }
}
