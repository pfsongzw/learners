package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class fx_BlankFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<String> myList=new ArrayList<>();
    private Context context;
    private adapter_contact adapter;

    public fx_BlankFragment() {
        // Required empty public constructor
    }

    private void initData(){
        myList.add("亚特兰大老鹰");
        myList.add("夏洛特黄蜂");
        myList.add("迈阿密热火");
        myList.add("奥兰多魔术");
        myList.add("华盛顿奇才");
        myList.add("波士顿凯尔特人");
        myList.add("布鲁克林篮网");
        myList.add("纽约尼克斯");
        myList.add("费城76人");
        myList.add("多伦多猛龙");
        myList.add("芝加哥公牛");
        myList.add("克里夫兰骑士");
        myList.add("底特律活塞");
        myList.add("印第安纳步行者");
        myList.add("密尔沃基雄鹿");
        myList.add("达拉斯独行侠");
        myList.add("休斯顿火箭");
        myList.add("孟菲斯灰熊");
        myList.add("新奥尔良鹈鹕");
        myList.add("圣安东尼奥马刺");
        myList.add("丹佛掘金");
        myList.add("明尼苏达森林狼");
        myList.add("俄克拉荷马城雷霆");
        myList.add("波特兰开拓者");
        myList.add("犹他爵士");
        myList.add("金州勇士");
        myList.add("洛杉矶快船");
        myList.add("洛杉矶湖人");
        myList.add("菲尼克斯太阳");
        myList.add("萨克拉门托国王");

    }

    private void initView3() {
        context=this.getActivity();
        adapter_contact adapter = new adapter_contact(context);

        RecyclerView rcv_1 = recyclerView.findViewById(R.id.rcv3);

        LinearLayoutManager manager_3 = new LinearLayoutManager(context);
        manager_3.setOrientation(LinearLayoutManager.VERTICAL);


        rcv_1.setLayoutManager(manager_3);
        rcv_1.setHasFixedSize(true);
        rcv_1.setAdapter(adapter);

        adapter.setVerticalDataList(myList);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment3, container, false);
        recyclerView=view.findViewById(R.id.rcv3);
        initData();
        initView3();

        return view;
    }
}
