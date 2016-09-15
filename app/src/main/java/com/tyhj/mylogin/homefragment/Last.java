package com.tyhj.mylogin.homefragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tyhj.myfist_2016_6_29.MyTime;
import com.tyhj.mylogin.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import custom.MyPublic;
import database.EssayInfo;
import database.UserInfo;
import mAdapter.EssayAdapter;
import yalantis.PullToRefreshView;

public class Last extends Fragment implements EssayAdapter.ExpendImage{
    private View view;
    private RecyclerView rvFeed;
    private List<EssayInfo> essayInfos;
    private ImageView ivEssayExpend;
    private Button saveImage;
    PullToRefreshView mPullToRefreshView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_last,null);
        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        setupFeed();
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 1500);
            }
        });
    }

    private void setupFeed() {
        essayInfos=new ArrayList<EssayInfo>();

        EssayInfo essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),
                "http://ac-fgtnb2h8.clouddn.com/bd96d6384f330055.jpg",
                MyPublic.getUserInfo().getName(),"这个同学很懒",20,false,"123");
        essayInfos.add(essayInfo);

        essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),
                "http://ac-fgtnb2h8.clouddn.com/5e88ec1c9147083c.jpg",
                MyPublic.getUserInfo().getName(),"这个同学很菜",20,false,"456");
        essayInfos.add(essayInfo);

        essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),
                "http://ac-fgtnb2h8.clouddn.com/f03d60ac828a5658.jpg",
                MyPublic.getUserInfo().getName(),"这个同学很6",20,false,"789");
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
    public void callBack(final EssayInfo essayInfo) {
        Dialog dialog=new Dialog(getActivity(),R.style.Dialog_Fullscreen);
        dialog.setCancelable(true);
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialogessayimage,null);
        dialog.setContentView(view);
        dialog.create();
        ivEssayExpend= (ImageView) view.findViewById(R.id.ivEssayExpend);
        saveImage= (Button) view.findViewById(R.id.btSavaEssayIv);
        Picasso.with(getActivity()).load(essayInfo.getIvEssay()).into(ivEssayExpend);
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        dialog.getWindow().setAttributes(p);     //设置生效
        dialog.show();
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyPublic.isIntenet(getActivity()))
                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyPublic.savaFile(essayInfo.getIvEssay(),essayInfo.getTvLz()+essayInfo.getTime()+getString(R.string.imageFormat),handler,getActivity());
                    }
                }).start();
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                   Toast.makeText(getContext(), "已保存", Toast.LENGTH_SHORT).show();
                   break;
           }
        }
    };
}
