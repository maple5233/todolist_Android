package com.maple;

import java.io.*;
import java.net.*;

public class ToDoList extends JData {

    public ToDoList(Socket soc, String Request) {
        // TODO 自动生成的构造函数存根
        super(soc, Request);
    }

    public void get() {
        Integer ISI = this.mapInt.get("ISI");
        String isi = ISI.toString();
        try {
            BufferedReader bfr_get = new BufferedReader(new FileReader(ConstString.SRC_TO_ROOT + "db/ToDoList.txt"));
            String toDoList = "";
            while (!(toDoList = bfr_get.readLine()).equals("")) {
                String[] toDoListDatas = toDoList.split(" ----- ");
                boolean isDel = this.isDel(isi, toDoListDatas[1]);
                if (!isDel) {
                    this.mapString.put("title " + toDoListDatas[2], toDoListDatas[1]); // 放置返回结果
                }
            }
            bfr_get.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return;
    }

    @SuppressWarnings("resource")
    private boolean isDel(String isi, String id) {
        // TODO 自动生成的方法存根
        try {
            BufferedReader bfr_del = new BufferedReader(new FileReader(ConstString.SRC_TO_ROOT + "db/del_toDoList.txt"));
            String toDoList = "";
            while (!(toDoList = bfr_del.readLine()).equals("")) {
                String[] toDoListDatas = toDoList.split(" ----- ");
                if (isi.equals(toDoListDatas[0]) && id.equals(toDoListDatas[0])) {
                    return true;
                }
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return false;
    }

    public int getContent() {
        String ISI = this.mapInt.get("ISI").toString();
        String id = this.mapInt.get("id").toString();
        boolean isDel = this.isDel(ISI, id);
        if (isDel) {
            this.mapInt.put("code", 1);
            return 1;
        }
        try {
            BufferedReader bfr_get = new BufferedReader(new FileReader(ConstString.SRC_TO_ROOT + "db/ToDoList.txt"));
            String toDoList = "";
            while (!(toDoList = bfr_get.readLine()).equals("")) {
                String[] toDoListDatas = toDoList.split(" ----- ");

                if (toDoListDatas[0].equals(ISI) && toDoListDatas[1].equals(id)) {// 找到对象
                    this.mapString.put("title", toDoListDatas[2]); // 放置返回结果
                    this.mapString.put("text", toDoListDatas[3]); // 放置返回结果
                    if (toDoListDatas.length > 4) {// 有src
                        this.mapString.put("src", toDoListDatas[4]); // 放置返回结果
                    } else {
                        this.mapString.put("src", ""); // 放置返回结果
                    }
                }
            }
            bfr_get.close();
            return 0;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return 0;
    }

    public void getPic() {
        String ISI = this.mapInt.get("ISI").toString();
        String id = this.mapInt.get("id").toString();
        String src = this.mapString.get("src");
        this.setMSG("OK");
        boolean isDel = this.isDel(ISI, id);
        if (isDel) {
            this.mapInt.put("code", 2);// 用户已经被删除了
            this.setMSG("ERROR");
            try {
                OutputStream outputStream = this.soc.getOutputStream();
                outputStream.write(this.toString().getBytes());
                outputStream.write(-1);
                outputStream.flush();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            return;
        }
        File pic = new File(ConstString.SRC_TO_ROOT + "images/" + ISI + "_" + id + "_" + src);
        if (!pic.exists()) {
            this.mapInt.put("code", 1);// 找不到图片
            this.setMSG("ERROR");
            try {
                OutputStream outputStream = this.soc.getOutputStream();
                outputStream.write(this.toString().getBytes());
                outputStream.write(-1);
                outputStream.flush();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            return;
        } else {
            try {
                OutputStream outputStream = this.soc.getOutputStream();
                FileInputStream in = new FileInputStream(pic);
                byte[] buffer = new byte[8 * 1024];
                while ((in.read(buffer)) != -1) {
                    outputStream.write(buffer);
                }
                outputStream.write(-1);
                outputStream.flush();
                outputStream.close();
                in.close();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        return;
    }

    public void postContent() {
        String ISI = this.mapInt.get("ISI").toString();
        String title = this.mapString.get("title");
        String text = this.mapString.get("text");
        String src = this.mapString.get("src");
        try {
            BufferedReader bfr_get = new BufferedReader(new FileReader(ConstString.SRC_TO_ROOT + "db/ToDoList.txt"));
            BufferedWriter bfw = new BufferedWriter(new FileWriter(ConstString.SRC_TO_ROOT + "db/ToDoList.txt", true));
            int id = 0;
            String toDoList = "";
            while (!(toDoList = bfr_get.readLine()).equals("")) {
                id++;
            }
            this.mapInt.put("id", id);
            this.setMSG("OK");
            String connect = " ----- ";
            toDoList = ISI + connect + id + connect + title + connect + text + connect + src + "\n";
            bfw.write(toDoList);
            bfw.close();
            bfr_get.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void postPic() {
        String ISI = this.mapInt.get("ISI").toString();
        String id = this.mapInt.get("id").toString();
        String src = this.mapString.get("src");
        File pic = new File(ConstString.SRC_TO_ROOT + "images/" + ISI + "_" + id + "_" + src);
        if (!pic.exists()) {
            try {
                pic.createNewFile();
                InputStream inputStream = this.soc.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(pic);
                byte[] buffer = new byte[8 * 1024];
                while ((inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer);
                }
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    public void putToDolist() {
        delToDoList();
        postContent();
        return;
    }

    public int delToDoList() {
        String ISI = this.mapInt.get("ISI").toString();
        String id = this.mapInt.get("id").toString();
        boolean isDel = this.isDel(ISI, id);
        if (isDel) {
            this.mapInt.put("code", 1); // 重复删除
            return 1;
        } else {
            try {
                BufferedWriter bfw = new BufferedWriter(new FileWriter(ConstString.SRC_TO_ROOT + "db/ToDoList.txt", true));
                String toDoList = "";
                String connect = " ----- ";
                toDoList = ISI + connect + id;
                bfw.write(toDoList + '\n');
                bfw.flush();
                bfw.close();
                return 0;
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        this.mapInt.put("code", -4);
        return -4;
    }

}
