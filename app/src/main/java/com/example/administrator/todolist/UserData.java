package com.example.administrator.todolist;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class UserData extends JData {

    public UserData(Socket soc, String Request) {
        // TODO 自动生成的构造函数存根
        super(soc, Request);
    }

    public String getMSG(){
        return this.msg;
    }

    public int getISI(){
        return this.mapInt.get("ISI");
    }

    public int getCode(){
        return this.mapInt.get("code");
    }
}
