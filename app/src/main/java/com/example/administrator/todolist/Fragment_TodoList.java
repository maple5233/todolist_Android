package com.example.administrator.todolist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_TodoList extends Fragment {


    public Fragment_TodoList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todolist, container, false);
        // 获得 recyclerView 引用
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // 设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 数据
        String datas[] = {"备忘录标题1", "备忘录标题2", "备忘录标题3", "备忘录标题4", "备忘录标题5", "备忘录标题6",
                "备忘录标题7", "备忘录标题8", "备忘录标题9", "备忘录标题10", "备忘录标题11", "备忘录标题12"};


        // 配置适配器
        Adapter_ToDoList mAdapter = new Adapter_ToDoList(datas);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }


}
