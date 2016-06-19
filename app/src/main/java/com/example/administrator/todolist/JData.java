package com.example.administrator.todolist;

import java.net.*;
import java.util.*;
import java.util.Map.*;

public abstract class JData {
    Map<String, String> mapString = null;
    Map<String, Integer> mapInt = null;
    String msg = null;
    Socket soc = null;

    JData(Socket soc, String Request) {
        this.soc = soc;
        String[] datas = Request.split("\n");
        for (String str : datas) {
            String key = str.split(":")[0];
            String value = str.split(":")[1];
            if (key.equals("") || value.equals(""))
                continue;
            if (key.equals("MSG")) {
                this.msg = value;
            } else if (key.equals("ISI") || key.equals("id")) {
                this.mapInt.put(key, Integer.parseInt(value));
            } else {
                this.mapString.put(key, value);
            }
        }
    }

    public void setMSG(String MSG) {
        this.msg = MSG;
    }

    @Override
    public String toString() {
        Iterator<Entry<String, Integer>> intPairs = this.mapInt.entrySet().iterator();
        Iterator<Entry<String, String>> StringPairs = this.mapString.entrySet().iterator();
        StringBuffer response = new StringBuffer();
        response.append("MSG:"+this.msg+"\n");
        for (int i = 0; i < this.mapInt.size(); i++) {
            Entry<String, Integer> entry = intPairs.next();
            response.append(entry.getKey());
            response.append(":");
            response.append(entry.getValue());
            response.append("\n");
        }
        for (int i = 0; i < this.mapString.size(); i++) {
            Entry<String, String> entry = StringPairs.next();
            response.append(entry.getKey());
            response.append(":");
            response.append(entry.getValue());
            response.append("\n");
        }
        // response.append("\n");
        return response.toString();
    }

}
