package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    Fragment fragment1,fragment2,fragment3,fragment4;
    FragmentManager manager;
    int transaction;
    int i;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout1=findViewById(R.id.聊天);
        linearLayout2=findViewById(R.id.通讯录);
        linearLayout3=findViewById(R.id.发现);
        linearLayout4=findViewById(R.id.我);
        fragment1=new wx_BlankFragment();
        fragment2=new txl_BlankFragment();
        fragment3=new fx_BlankFragment();
        fragment4=new w_BlankFragment();
        manager=getSupportFragmentManager();
        inital();
        fragmentHide();
        showfragment(fragment1);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);


    }

    public void onClick(View view) {
        fragmentHide();
        if(view.getId()==R.id.聊天)
        {
            showfragment(fragment1);
        }
        else if(view.getId()==R.id.通讯录)
        {
            showfragment(fragment2);
        }
        else if(view.getId()==R.id.发现)
        {
            showfragment(fragment3);
        }
        else if(view.getId()==R.id.我)
        {
            showfragment(fragment4);
        }

    }

    private void inital() {
        int transaction = manager.beginTransaction()
        .add(R.id.id_content, fragment1)
        .add(R.id.id_content, fragment2)
        .add(R.id.id_content, fragment3)
        .add(R.id.id_content, fragment4)
        .commit();
    }
    private void showfragment(Fragment fragment) {
        int transaction = manager.beginTransaction()
                .show(fragment)
                .commit();
    }

    public void fragmentHide(){
        int transaction=manager.beginTransaction()
                .hide(fragment1)
                .hide(fragment2)
                .hide(fragment3)
                .hide(fragment4)
                .commit();
    }

}