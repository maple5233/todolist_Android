package com.maple;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class UserData extends JData {

    public UserData(Socket soc, String Request) {
        // TODO 自动生成的构造函数存根
        super(soc, Request);
    }

    public int tryLogin() {
        // TODO 自动生成的方法存根
        String un = this.mapString.get("userName");
        String up = this.mapString.get("userPass");
        int result = 2;// 2 找不到用户
        try {
            String userData;
            BufferedReader bfr = new BufferedReader(new FileReader(ConstString.SRC_TO_ROOT + "db/user.txt"));
            while (!(userData = bfr.readLine()).equals("")) {
                String userName = userData.split(" ----- ")[0];
                String userPass = userData.split(" ----- ")[1];
                if (un.equals(userName)) {
                    if (up.equals(userPass)) {
                        this.mapInt.put("ISI", Integer.parseInt(userData.split(" ----- ")[2])); // 放置返回结果
                        bfr.close();
                        result = 0;// 成功返回0
                        return result;
                    }
                    result++;// 1 密码不对
                    bfr.close();
                    this.mapInt.put("code", 1);
                    return result;
                }
            }
            bfr.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        this.mapInt.put("code", 2);
        return result;
    }

    @SuppressWarnings("resource")
    public int trySignUp() {
        // TODO 自动生成的方法存根
        String un = this.mapString.get("userName");
        String up = this.mapString.get("userPass");

        int result;
        final String USERNAME_PATTERN = "^[a-zA-Z]{1}[a-zA-Z0-9_]{3,15}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(un);

        if (!matcher.matches()) {
            result = 2;
            this.mapInt.put("code", 2);
            return result;// 2 用户名格式不合法
        }

        if (up.length() <= 5 || up.length() >= 10) {
            result = 3;
            this.mapInt.put("code", 3);
            return result;// 3 用户密码长度不合法
        }

        try {
            String userData;
            BufferedReader bfr = new BufferedReader(new FileReader(ConstString.SRC_TO_ROOT + "db/user.txt"));
            while (!(userData = bfr.readLine()).equals("")) {
                String userName = userData.split(" ----- ")[0];
                if (un.equals(userName)) {
                    result = 1;
                    this.mapInt.put("code", 1);
                    return result;// 1 用户已经存在
                }
            }
            BufferedWriter bfw = new BufferedWriter(new FileWriter(ConstString.SRC_TO_ROOT + "db/user.txt", true));
            int ISI = -(int) (un.hashCode() + up.hashCode() + System.currentTimeMillis() * 100);// 随机生成ISI
            this.mapInt.put("ISI", ISI);
            result = 0; // 0 成功
            bfw.write(un + " ----- " + up + " ----- " + ISI + "\n");
            return result;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return result = 4;// 未知错误
    }
}
