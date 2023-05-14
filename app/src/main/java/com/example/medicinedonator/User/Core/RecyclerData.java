package com.example.medicinedonator.User.Core;

public class RecyclerData {
    // string variables for our data
    // make sure that the variable name
    // must be similar to that of key value
    // which we are getting from our json file.
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;
    private String District;
    private String Address;
    private String Category;
    private String Password;

    private  String _id;

    public String get_id() {
        return _id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDistrict() {
        return District;
    }

    public String getAddress() {
        return Address;
    }

    public String getCategory() {
        return Category;
    }

    public String getPassword() {
        return Password;
    }

    public RecyclerData(String FirstName, String LastName, String Email, String Phone, String District, String Address, String Category, String Password,String _id) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.District = District;
        this.Address = Address;
        this.Category = Category;
        this.Password = Password;
        this._id =_id;
    }
}
