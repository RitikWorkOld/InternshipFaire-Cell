package com.ecell.internshipfairendeavour.Employe;

public class applied_intern_md {

    public String answer1;
    public String answer2;
    public String answer3;
    public String companyid;
    public String key;
    public String userid;
    public String status;
    public String username;
    public String internid;
    public String usernumber;
    public String userimg;
    public String useremail;
    public String internid_status;
    public String id;
    public boolean selected;

    public applied_intern_md() {
    }

    public applied_intern_md(String id, String internid, String internid_status, String useremail, String username, String userimg, String usernumber, String answer1, String answer2, String answer3, String companyid, String key, String userid, String status) {
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.internid=internid;
        this.internid_status=internid_status;
        this.companyid = companyid;
        this.key = key;
        this.userid = userid;
        this.username=username;
        this.userimg=userimg;
        this.usernumber=usernumber;
        this.status = status;
        this.useremail=useremail;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternid() {
        return internid;
    }

    public void setInternid(String internid) {
        this.internid = internid;
    }

    public String getInternid_status() {
        return internid_status;
    }

    public void setInternid_status(String internid_status) {
        this.internid_status = internid_status;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
