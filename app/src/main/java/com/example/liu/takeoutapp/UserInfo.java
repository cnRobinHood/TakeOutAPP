package com.example.liu.takeoutapp;

public class UserInfo {
    private String phone;
    private String locate;
    private String foodNum;
    private int status;//0表示没有进行任何操作，1表示打了电话，2表示发了短信

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }
}
