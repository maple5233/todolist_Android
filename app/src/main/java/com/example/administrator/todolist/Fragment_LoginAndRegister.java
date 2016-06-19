package com.example.administrator.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.Socket;


public class Fragment_LoginAndRegister extends Fragment {

    private static final int NEED_MORE_INFO = 0;
    private Button btn_login;
    private Button btn_register;
    private EditText text_userName;
    private EditText text_userPass;
    private int ISI = 0;

    public Fragment_LoginAndRegister() {
        // Required empty public constructor
    }

    private Handler hand = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEED_MORE_INFO: {
                    Toast.makeText(getActivity(), "信息不全Orz", Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login_and_register, container, false);
        // 获取界面组件
        btn_login = (Button) rootView.findViewById(R.id.button_login);
        btn_register = (Button) rootView.findViewById(R.id.button_register);
        text_userName = (EditText) rootView.findViewById(R.id.userName);
        text_userPass = (EditText) rootView.findViewById(R.id.userPass);
        // 绑定事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    InputStream in = null;
                    OutputStream out = null;
                    StringBuffer response = new StringBuffer();// 线程安全
                    String line = "";

                    @Override
                    public void run() {
                        try {
                            Log.d("hjy", "onClick");
                            String usn = text_userName.getText().toString();
                            String usp = text_userPass.getText().toString();
                            if (usn.length() == 0 || usp.length() == 0) {
                                Message message = new Message();
                                message.what = NEED_MORE_INFO;
                                hand.sendMessage(message);
                                return;
                            }
                            Log.d("hjy", "try");
                            Socket socket = new Socket("10.0.2.2", 8080);
                            Log.d("hjy", "getSocket");
                            in = socket.getInputStream();
                            out = socket.getOutputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(in)); // 字节流
                            Log.d("hjy", "getStream");
                            StringBuffer request = new StringBuffer();
                            request.append("MSG:Login\n");
                            request.append("userName:" + usn + "\n");
                            request.append("userPass:" + usp + "\n\n");
                            String req = request.toString();

                            UserData userData = new UserData(socket, req);
                            out.write(userData.toString().getBytes());
                            while (true) {
                                line = br.readLine();
                                if (line.length() == 0) {
                                    break;
                                }
                                response.append(line + '\n');
                            }
//                            Log.d("hjy", "");
                            String res = response.toString();
                            Log.d("hjy", res);
                            userData = new UserData(socket, res);
                            int code = userData.getCode();
                            if (code != 0) {
                                Log.e("hjy", code + "");
                            } else {

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }


}


/**
 * Rename parameter arguments, choose names that match
 * the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 * <p/>
 * private static final String ARG_PARAM1 = "param1";
 * private static final String ARG_PARAM2 = "param2";
 * <p/>
 * Rename and change types of parameters
 * <p/>
 * private String mParam1;
 * private String mParam2;
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 * <p/>
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 * <p/>
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 */

/**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Fragment_LoginAndRegister.
 * <p/>
 *
 * public static Fragment_LoginAndRegister newInstance(String param1, String param2) {
 * Fragment_LoginAndRegister fragment = new Fragment_LoginAndRegister();
 * Bundle args = new Bundle();
 * args.putString(ARG_PARAM1, param1);
 * args.putString(ARG_PARAM2, param2);
 * fragment.setArguments(args);
 * return fragment;
 * }
 *
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * if (getArguments() != null) {
 * mParam1 = getArguments().getString(ARG_PARAM1);
 * mParam2 = getArguments().getString(ARG_PARAM2);
 * }
 * }
 */