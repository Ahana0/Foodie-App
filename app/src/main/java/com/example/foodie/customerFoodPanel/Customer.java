package com.example.foodie.customerFoodPanel;

public class Customer {

    private String FirstName,LastName,Password,ConfirmPassword,EmailId,MobileNo,Area,LocalAddress;

    public Customer(){
    }
    // Press Alt+insert


    public Customer(String firstName, String lastName, String password, String confirmPassword, String emailId, String mobileNo, String area, String localAddress) {

        FirstName = firstName;
        LastName = lastName;
        Password = password;
        ConfirmPassword = confirmPassword;
        EmailId = emailId;
        MobileNo = mobileNo;

        Area = area;
        LocalAddress = localAddress;
    }


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    //public String getEmailId() {
       // return EmailId;
   // }

    //public void setEmailId(String emailId) {
       // EmailId = emailId;
    //}


    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }



    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getLocalAddress() {
        return LocalAddress;
    }

    public void setLocalAddress(String localAddress) {
        LocalAddress = localAddress;
    }



}
