package com.ecell.internshipfairendeavour.Admin;


public class Admin {
    public String name;
    public String email;
    public String contactn;
    public String id;
    public String website;

    public String profileimg;


    public String descrip;
    public String cmpid;





    public Admin(){

    }

    public Admin(String cmpid, String name, String email, String contactn, String id, String profileimg, String website, String descrip) {
        this.name = name;
        this.email = email;


        this.contactn = contactn;
this.website=website;

        this.id = id;
this.descrip=descrip;

this.cmpid=cmpid;





        this.profileimg = profileimg;




    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmpid() {
        return cmpid;
    }

    public void setCmpid(String cmpid) {
        this.cmpid = cmpid;
    }



    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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







    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }


}