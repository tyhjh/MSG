package com.tyhj.mylogin.homefragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import java.util.ArrayList;
import java.util.List;

import custom.MyPublic;
import database.EssayInfo;
import database.UserInfo;
import mAdapter.EssayAdapter;

public class Last extends Fragment implements EssayAdapter.ExpendImage{
    private View view;
    private RecyclerView rvFeed;
    private List<EssayInfo> essayInfos;
    private ImageView ivEssayExpend;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_last,null);
        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        ivEssayExpend= (ImageView) view.findViewById(R.id.ivEssayExpend);
        setupFeed();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupFeed() {
        essayInfos=new ArrayList<EssayInfo>();

        EssayInfo essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),
                "http://ac-fgtnb2h8.clouddn.com/bd96d6384f330055.jpg",
                MyPublic.getUserInfo().getName(),"这个同学很懒",20,false);
        essayInfos.add(essayInfo);

        essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),
                "http://ac-fgtnb2h8.clouddn.com/5e88ec1c9147083c.jpg",
                MyPublic.getUserInfo().getName(),"这个同学很菜",20,false);
        essayInfos.add(essayInfo);

        essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),
                "http://ac-fgtnb2h8.clouddn.com/f03d60ac828a5658.jpg",
                MyPublic.getUserInfo().getName(),"这个同学很6",20,false);
        essayInfos.add(essayInfo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);
        EssayAdapter feedAdapter = new EssayAdapter(getActivity(),essayInfos);
        feedAdapter.setCallback(this);
        rvFeed.setAdapter(feedAdapter);
    }

    @Override
    public void callBack(String str) {
        Dialog dialog=new Dialog(getActivity(),R.style.Dialog_Fullscreen);
        dialog.setCancelable(true);
       /* AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);*/
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialogessayimage,null);
        dialog.setContentView(view);
        dialog.create();
     /*   builder.setView(view);
        builder.create();*/
        ivEssayExpend= (ImageView) view.findViewById(R.id.ivEssayExpend);
        Picasso.with(getActivity()).load(str).into(ivEssayExpend);
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        dialog.getWindow().setAttributes(p);     //设置生效
        dialog.show();
    }
}
