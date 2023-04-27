package com.example.androidsession_07;

public class Friend {

    private int imageId;

    private String nickName;

    private String msg;

    public Friend(int imageId, String nickName, String msg) {
        this.imageId = imageId;
        this.nickName = nickName;
        this.msg = msg;
    }

    public int getImageId() {
        return imageId;
    }


    public String getNickName() {
        return nickName;
    }


    public String getMsg() {
        return msg;
    }

}
