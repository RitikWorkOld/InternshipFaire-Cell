package com.ecell.internshipfairendeavour.Admin;


public class User_admin {
    public String name;
    public String email;
    public String contactn;
    public String uid;
    public String password;
    public String profileimg;
    public String profilestatus;
    public String officialstatus;
    public String code;
    public String logostatus;
    public String endvrid;
    public int noi;
    public boolean selected;

    public User_admin(){
    }

    public User_admin(String name, String email, String contactn, String uid, String password, String profileimg, String profilestatus, String officialstatus, String code, String logostatus, String endvrid, int noi, boolean selected) {
        this.name = name;
        this.email = email;
        this.contactn = contactn;
        this.uid = uid;
        this.password = password;
        this.profileimg = profileimg;
        this.profilestatus = profilestatus;
        this.officialstatus = officialstatus;
        this.code = code;
        this.logostatus = logostatus;
        this.endvrid = endvrid;
        this.noi = noi;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactn() {
        return contactn;
    }

    public void setContactn(String contactn) {
        this.contactn = contactn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getProfilestatus() {
        return profilestatus;
    }

    public void setProfilestatus(String profilestatus) {
        this.profilestatus = profilestatus;
    }

    public String getOfficialstatus() {
        return officialstatus;
    }

    public void setOfficialstatus(String officialstatus) {
        this.officialstatus = officialstatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogostatus() {
        return logostatus;
    }

    public void setLogostatus(String logostatus) {
        this.logostatus = logostatus;
    }

    public String getEndvrid() {
        return endvrid;
    }

    public void setEndvrid(String endvrid) {
        this.endvrid = endvrid;
    }

    public int getNoi() {
        return noi;
    }

    public void setNoi(int noi) {
        this.noi = noi;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}