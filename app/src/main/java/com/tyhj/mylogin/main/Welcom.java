package com.tyhj.mylogin.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tyhj.mylogin.R;
import com.tyhj.mylogin.umeng.MyLogin_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import custom.MyPublic;

@EActivity(R.layout.activity_welcom)
public class Welcom extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @UiThread(delay = 1000)
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
            startActivity(new Intent(Welcom.this, UserInfo.class));
            this.finish();
        } else {
            startActivity(new Intent(Welcom.this, MyLogin_.class));
            this.finish();
        }
    }
    @AfterViews
    void after(){
        bg();
    }
}
