package com.fear.passcrypter;


public class Credentials {

    private int _id;
    private String _username;
    private String _pass;
    private String _title;
    private boolean _switchcond = false;
    private int _key;

    public Credentials(){
    }

    public Credentials(String username, String pass, String title) {
        this._username = username;
        this._pass = pass;
        this._title = title;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public void set_switchcond(boolean _switchcond) {
        this._switchcond = _switchcond;
    }

    public void set_key(int _key) {
        this._key = _key;
    }

    public int get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public String get_pass() {
        return _pass;
    }

    public String get_title() {
        return _title;
    }

    public boolean get_switchcond() {
        return _switchcond;
    }

    public int get_key() {
        return _key;
    }

}
