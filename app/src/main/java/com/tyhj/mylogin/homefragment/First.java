package com.tyhj.mylogin.homefragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mAdapter.SimpleAdapter;

@EFragment(R.layout.activity_user_info)
public class First extends Fragment {
    private View view;
    private List<String> networkImages;
    private List<String> mDatas;
    private SimpleAdapter mAdapter;
    String[] images = {"http://ac-fgtnb2h8.clouddn.com/4233508e35d9d761.jpg",
            "http://ac-fgtnb2h8.clouddn.com/5e88ec1c9147083c.jpg",
            "http://ac-fgtnb2h8.clouddn.com/bd96d6384f330055.jpg",
            "http://ac-fgtnb2h8.clouddn.com/cc3e9b7f9481604c.jpg",
            "http://ac-fgtnb2h8.clouddn.com/e52fbbc9c6af6013.jpg",
            "http://ac-fgtnb2h8.clouddn.com/f03d60ac828a5658.jpg",
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_user_info,null);
        return view;
    }

    @ViewById
    ConvenientBanner convenientBanner;
    @ViewById
    RecyclerView rclv;
    @AfterViews
    void afterView(){
        //网络加载例子
        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
                .setPageIndicator(new int[]{R.drawable.point1, R.drawable.point2})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //自动翻页
        convenientBanner.startTurning(3000);
        //点击事件
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        rclv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        break ;
                    case 1:
                        rclv.setLayoutManager(new GridLayoutManager(getActivity(),3));
                        break ;
                    case 2:
                        rclv.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));
                        break ;
                    case 3:
                        mAdapter.addData(1);
                        break ;
                    case 4:
                        mAdapter.deleteData(1);
                        break ;
                }
            }
        });

        recycleView();
    }

    private void recycleView() {
        mDatas=new ArrayList<String>();
        for(int i='A';i<='z';i++){
            mDatas.add(""+(char)i);
        }
        mAdapter=new SimpleAdapter(getActivity(),mDatas);
        rclv.setAdapter(mAdapter);
        //设置布局管理
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rclv.setLayoutManager(linearLayoutManager);
        //分割线
        //rclv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        rclv.setItemAnimator(new DefaultItemAnimator());
    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Picasso.with(context).load(data).into(imageView);
        }
    }

}
