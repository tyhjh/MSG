package com.tyhj.mylogin.homefragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tyhj.mylogin.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import custom.CircleRefreshLayout;
import mAdapter.MyExpandableListViewAdapter;
import view.Child;
import view.Group;

@EFragment(R.layout.expandlist)
public class Linkman extends Fragment {
    private View view;
    private MyExpandableListViewAdapter mExpAdapter;
    private List<Group> listGroup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.expandlist,null);
        return view;
    }
    @ViewById
    ExpandableListView expendlist;
    @ViewById
    CircleRefreshLayout refresh;
    @UiThread(delay = 2500)
    void stopRefresh(){
        refresh.finishRefreshing();
    }
    @AfterViews
    void afterView(){
        initView();
        initData();
        refresh.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        // do something when refresh starts
                        stopRefresh();
                    }

                    @Override
                    public void completeRefresh() {
                        // do something when refresh complete
                    }
                });
    }
    private void initData() {
        listGroup=new ArrayList<Group>();

        //第一组
        Group group=new Group();
        group.setGroupName("我的好友");
        List<Child> listChild=new ArrayList<Child>();
        for(int i=0;i<6;i++){
            Child child=new Child();
            child.setUsername("我的好友"+i);
            child.setHeadphoto("http://ac-fgtnb2h8.clouddn.com/a4aa2d81f01cc068.jpg");
            child.setMood("今天心情不错哈~");
            if(i<3)
                child.setOnline_status("1");
            else
                child.setOnline_status("0");
            listChild.add(child);
        }
        group.setChildList(listChild);
        listGroup.add(group);

        //第二组
        group=new Group();
        group.setGroupName("我的同学");
        listChild=new ArrayList<Child>();
        for(int i=0;i<8;i++){
            Child child=new Child();
            child.setUsername("我的同学"+i);
            child.setHeadphoto("http://ac-fgtnb2h8.clouddn.com/844c35000ef4c843.jpg");
            child.setMood("今天心情不错哈~");
            if(i<3)
                child.setOnline_status("1");
            else
                child.setOnline_status("0");
            listChild.add(child);
        }
        group.setChildList(listChild);
        listGroup.add(group);

        //第三组
        group=new Group();
        group.setGroupName("我的朋友");
        listChild=new ArrayList<Child>();
        for(int i=0;i<5;i++){
            Child child=new Child();
            child.setUsername("我的朋友"+i);
            child.setHeadphoto("http://ac-fgtnb2h8.clouddn.com/cc3e9b7f9481604c.jpg");
            child.setMood("今天心情不错哈~");
            if(i<4)
                child.setOnline_status("1");
            else
                child.setOnline_status("0");
            listChild.add(child);
        }
        group.setChildList(listChild);
        listGroup.add(group);

        mExpAdapter = new MyExpandableListViewAdapter(getActivity(), listGroup);
        expendlist.setAdapter(mExpAdapter);

    }
    private void initView() {
        expendlist.setGroupIndicator(null);
        expendlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
    }
}
