package com.example.foodie;

public class Chef {
    private String Area, ConfirmPassword, Emailid, Fname, House, Lname, Mobile, Password, Pincode;

    public Chef(String area, String confirmPassword, String emailid, String fname, String house, String lname, String mobile, String password, String pincode) {
        this.Area = area;
        ConfirmPassword = confirmPassword;
        Emailid = emailid;
        Fname = fname;
        House = house;
        Lname = lname;
        Mobile = mobile;
        Password = password;
        Pincode = pincode;

    }
    public Chef() {
    }

    public String getArea () {
        return Area;
    }

    public String getConfirmPassword () {
        return ConfirmPassword;
    }

    public String getEmailid () {
        return Emailid;
    }

    public String getFname () {
        return Fname;
    }

    public String getHouse () {
        return House;
    }

    public String getLname () {
        return Lname;
    }

    public String getMobile () {
        return Mobile;
    }

    public String getPassword () {
        return Password;
    }

    public String getPincode () {
        return Pincode;
    }


}
