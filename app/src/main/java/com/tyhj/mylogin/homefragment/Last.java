package com.tyhj.mylogin.homefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyhj.mylogin.R;

import java.util.ArrayList;
import java.util.List;

import custom.MyPublic;
import database.EssayInfo;
import database.UserInfo;
import mAdapter.EssayAdapter;

public class Last extends Fragment {
    private View view;
    private RecyclerView rvFeed;
    private List<EssayInfo> essayInfos;
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
        return view;
    }
    private void setupFeed() {
        essayInfos=new ArrayList<EssayInfo>();

        EssayInfo essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),  "http://ac-fgtnb2h8.clouddn.com/bd96d6384f330055.jpg",MyPublic.getUserInfo().getName(),"这个同学很懒",20);
        essayInfos.add(essayInfo);

        essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),  "http://ac-fgtnb2h8.clouddn.com/5e88ec1c9147083c.jpg",MyPublic.getUserInfo().getName(),"这个同学很菜",20);
        essayInfos.add(essayInfo);

        essayInfo=new EssayInfo(14108413,MyPublic.getUserInfo().getUrl(),  "http://ac-fgtnb2h8.clouddn.com/f03d60ac828a5658.jpg",MyPublic.getUserInfo().getName(),"这个同学很6",20);
        essayInfos.add(essayInfo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);
        EssayAdapter feedAdapter = new EssayAdapter(getActivity(),essayInfos);
        rvFeed.setAdapter(feedAdapter);
    }
}
