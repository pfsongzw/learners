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
        myList.add("沃尔玛");
        myList.add("沙特阿美公司");
        myList.add("国家电网有限公司");
        myList.add("亚马逊");
        myList.add("中国石油天然气集团有限公司");
        myList.add("中国石油化工集团有限公司");
        myList.add("埃克森美孚");
        myList.add("苹果公司");
        myList.add("壳牌公司");
        myList.add("联合健康集团");
        myList.add("CVS Health公司");
        myList.add("托克集团");
        myList.add("中国建筑集团有限公司");
        myList.add("伯克希尔－哈撒韦公司");
        myList.add("大众公司");
        myList.add("Uniper公司");
        myList.add("Alphabet公司");
        myList.add("麦克森公司");
        myList.add("丰田汽车公司");
        myList.add("道达尔能源公司");
        myList.add("嘉能可");
        myList.add("英国石油公司");
        myList.add("雪佛龙");
        myList.add("美源伯根公司");
        myList.add("三星电子");
        myList.add("开市客");
        myList.add("鸿海精密工业股份有限公司");
        myList.add("中国工商银行股份有限公司");
        myList.add("中国建设银行股份有限公司");
        myList.add("微软");


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