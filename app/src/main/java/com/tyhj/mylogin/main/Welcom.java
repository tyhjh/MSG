package com.tyhj.mylogin.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.roger.match.library.MatchButton;
import com.roger.match.library.MatchTextView;
import com.tyhj.mylogin.R;
import com.tyhj.mylogin.umeng.MyLogin_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import custom.MyPublic;
import database.Myslq;

@EActivity(R.layout.activity_welcom)
public class Welcom extends Activity {
    MatchTextView mMatchTextView;
    MatchButton matchButton;
    Boolean isFinish=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMysql();
    }
    @Click(R.id.mbt)
    void log(){
        mMatchTextView.hide();
        matchButton.hide();
        matchButton.setClickable(false);
       // Snackbar.make(matchButton,"数据加载中",Snackbar.LENGTH_INDEFINITE).show();
        bg();
    }
    @AfterViews
    void afterView(){
        matchButton= (MatchButton) findViewById(R.id.mbt);
        mMatchTextView= (MatchTextView) findViewById(R.id.mtv);
        mMatchTextView.setText("Tyhj Message");

    }
    @UiThread(delay = 1500)
    void bg () {
        SharedPreferences sharedPreferences = this.getSharedPreferences("saveLogin", MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.getString("password", null) != null) {
            MyPublic.setUserInfo(new database.UserInfo(sharedPreferences.getString("number", null),
                    sharedPreferences.getString("password", null),
                    sharedPreferences.getString("headImage", null),
                    sharedPreferences.getString("name", null),
                    sharedPreferences.getString("email", null),
                    sharedPreferences.getString("signature", null),
                    sharedPreferences.getString("place", null),
                    sharedPreferences.getString("snumber", null)
            ));
            startActivity(new Intent(Welcom.this, Home.class));
            overridePendingTransition(R.anim.out, R.anim.enter);
        } else {
            startActivity(new Intent(Welcom.this, MyLogin_.class));
            //overridePendingTransition(R.anim.out, R.anim.enter);
        }
        if (isFinish) {
            System.out.println("成功关闭1！");
            finishActivity();
        }else
            isFinish=true;
    }
    @UiThread
    void finishActivity(){
        this.finish();
    }
    @Background
    void initMysql(){
        MyPublic.setMyslq(new Myslq());
        if(isFinish) {
            System.out.println("成功关闭2！");
            finishActivity();
        }else
            isFinish=true;
    }
}
