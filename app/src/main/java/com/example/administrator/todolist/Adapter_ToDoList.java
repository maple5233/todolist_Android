package com.example.administrator.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/19.
 */
public class Adapter_ToDoList extends RecyclerView.Adapter<Adapter_ToDoList.ViewHolder> {
    String[] datas;
    Adapter_ToDoList(String[] datas){
        this.datas=datas;
    }

    // LayoutManager所调用的View对象 将被渲染成列表
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_todolist,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    // 将数据，监听器绑定到每个元素
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.btn.setText(datas[position]);
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"I am clicked!",Toast.LENGTH_LONG).show();
            }
        });
    }
    // 获取数据的数量
    @Override
    public int getItemCount() {
        return datas.length;
    }
    // 自定义的内部类ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button btn;
        public ViewHolder(View view){
            super(view);
            btn = (Button) view.findViewById(R.id.id_todoListTitle);
        }
    }
}
