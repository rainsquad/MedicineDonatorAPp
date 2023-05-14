package com.example.medicinedonator.User.Core;

import java.util.List;

public class Medicine {



    private  String _id;
    private  String Name;

    private  String Description;

    private  String Category;

    private  int Quantity;

    private  String MFD;

    private  String ExpDate;

    private  String User;

    private String Picture;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public String get_id() {
        return _id;
    }

    public String getPicture() {
        return Picture;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategory() {
        return Category;
    }



    public String getMFD() {
        return MFD;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public String getUser() {
        return User;
    }

    public String getName() {
        return Name;
    }


    public Medicine(String Name, String Description, String Category, int Quantity, String MFD, String ExpDate, String _id,String picture) {
        this.Name = Name;
        this.Description = Description;
        this.Category = Category;
        this.Quantity = Quantity;
        this.MFD = MFD;
        this.ExpDate = ExpDate;
        this._id = _id;
        this.Picture = picture;

    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
