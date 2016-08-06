package com.tyhj.mylogin.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import custom.CircleRefreshLayout;
@EActivity(R.layout.activity_user_info)
public class UserInfo extends Activity{
    private List<String> networkImages;
    String[] images = {"http://ac-fgtnb2h8.clouddn.com/b64e13e0e0021c71.jpg",
            "http://ac-fgtnb2h8.clouddn.com/5e88ec1c9147083c.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://ac-fgtnb2h8.clouddn.com/bd96d6384f330055.jpg",
            "http://ac-fgtnb2h8.clouddn.com/cc3e9b7f9481604c.jpg",
            "http://ac-fgtnb2h8.clouddn.com/e52fbbc9c6af6013.jpg",
            "http://ac-fgtnb2h8.clouddn.com/f03d60ac828a5658.jpg",
            "http://ac-fgtnb2h8.clouddn.com/e648c64178b92d6a.jpg"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @ViewById
    ConvenientBanner convenientBanner;
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
                Toast.makeText(UserInfo.this,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class NetworkImageHolderView implements Holder<String>{
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
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
