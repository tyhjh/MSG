package com.tyhj.mylogin.umeng;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;;
import com.tyhj.mylogin.main.Home;
import com.tyhj.mylogin.main.Register_;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import custom.MyPublic;
import database.Myslq;
import database.UserInfo;

@EActivity(R.layout.mylogin)
public class MyLogin extends AppCompatActivity {
    private static String INT="012345678912454";
    public static int THEWAY_TO_LOG=1;
    UMShareAPI mShareAPI;
    SHARE_MEDIA platform;
    Animation animation;
    @ViewById
    Button btnWeiXin,btnQQ,btnWeiBo,btLogin;
    @ViewById
    TextView tvsign,tvforgetpas;
    @ViewById
    EditText etUserNumber,etUserPassord;
    @ViewById
    View llLogin;
    @ViewById
    ImageView UserHeadImaegLg;
    //注册
    @Click(R.id.tvsign)
    void sign(){
        Intent intent=new Intent(MyLogin.this, Register_.class);
        startActivity(intent);
    }
    //忘记密码
    @Click(R.id.tvforgetpas)
    void forgetPasword(){

    }
    @ViewById
    ImageView iv_user,iv_pas;
    //QQ登陆
    @Click(R.id.btnQQ)
    void qqLog(){
        platform = SHARE_MEDIA.QQ;
        mShareAPI.isInstall(this, SHARE_MEDIA.QQ);
        THEWAY_TO_LOG=1;
        getRoot();
    }
    //微信登陆
    @Click(R.id.btnWeiXin)
    void wxLog(){
        /*platform = SHARE_MEDIA.WEIXIN;
        mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
        THEWAY_TO_LOG=2;
        getRoot();*/
        MyPublic.Toast(this,"该功能暂时未上线");
    }
    //微博登陆
    @Click(R.id.btnWeiBo)
    void wbLog(){
        platform = SHARE_MEDIA.SINA;
        mShareAPI.isInstall(this, SHARE_MEDIA.SINA);
        THEWAY_TO_LOG=3;
        getRoot();
    }
    //账号密码登陆
    @Click(R.id.btLogin)
    void login(){
        mySnakbar(btLogin, "登陆中",Snackbar.LENGTH_INDEFINITE);
        if(MyPublic.isIntenet(this))
        tryLog();
    }
    @Background
     void tryLog() {
        UserInfo userInfo;
        if(!etUserPassord.getText().toString().trim().equals("")&&!etUserNumber.getText().toString().trim().equals("")){
            try {
                userInfo=new Myslq().logIn(etUserNumber.getText().toString().trim(),etUserPassord.getText().toString().trim());
            }catch (Exception e){
                mySnakbar(btLogin,"网络异常",Snackbar.LENGTH_SHORT);
                userInfo=null;
            }
             if(userInfo!=null) {
                 saveLogIn(userInfo);
                 getString();
                 startActivity();
             }else
                 mySnakbar(btLogin,"账号或密码错误",Snackbar.LENGTH_SHORT);
        }else {
            mySnakbar(btLogin,"账号或密码不能为空",Snackbar.LENGTH_SHORT);
        }

    }
    @Background
    void bgd(){
        animation= AnimationUtils.loadAnimation(this,R.anim.blowup);
        animation();
    }
    @UiThread()
    void animation(){
        btnQQ.startAnimation(animation);
        btnWeiXin.startAnimation(animation);
        btnWeiBo.startAnimation(animation);
    }
    @AfterViews
    void initViews(){
        bgd();
        Picasso.with(MyLogin.this).load(R.drawable.im_user).
                resize(100, 100).centerCrop().into(iv_user);
        Picasso.with(MyLogin.this).load(R.drawable.im_pas).
                resize(100, 100).centerCrop().into(iv_pas);
        UserHeadImaegLg.setOutlineProvider(MyPublic.getOutline(true,20));
        UserHeadImaegLg.setClipToOutline(true);
        Picasso.with(MyLogin.this).load(getString(R.string.defaultheadimage)).into(UserHeadImaegLg);
        tvforgetpas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvforgetpas.setTextColor(Color.YELLOW);
                        break;
                    case MotionEvent.ACTION_UP:
                        tvforgetpas.setTextColor(Color.parseColor("#0b988f"));
                        break;
                }
                return false;
            }
        });
        tvsign.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvsign.setTextColor(Color.YELLOW);
                        break;
                    case MotionEvent.ACTION_UP:
                        tvsign.setTextColor(Color.parseColor("#0b988f"));
                        break;
                }
                return false;
            }
        });
        etUserNumber.addTextChangedListener(textWatcher);
      /*  llLogin.setOutlineProvider(viewOutlineProvider);
        llLogin.setClipToOutline(true);*/
        SharedPreferences sharedPreferences = this.getSharedPreferences("saveLogin", MODE_PRIVATE);
        if(sharedPreferences!=null)
        etUserNumber.setText(sharedPreferences.getString("number",null));
    }
    //获取系统权限
     private void getPermission() {
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.WRITE_APN_SETTINGS, Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(MyLogin.this,mPermissionList, 100);
    }
    //初始化友盟平台
    private void initLog() {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("2220908836","224eaaa6b833d8870473394a183ed083");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105499207", "NytTmn0gqcYN7nBX");
        // QQ和Qzone appid appkey
        mShareAPI = UMShareAPI.get(this);
    }
    //授权
    private void getRoot() {
        mShareAPI.doOauthVerify(MyLogin.this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                mySnakbar(btLogin, "登陆中",Snackbar.LENGTH_INDEFINITE);
                getInfo();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText( getApplicationContext(), "获取权限信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
    //获取个人信息
    public void getInfo(){
        mShareAPI.getPlatformInfo(MyLogin.this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> jo) {
                Log.i("用户信息:xxxxxxxxxxxxxx",jo.toString());
                showImage(jo.get("profile_image_url"));
                String openid = null,location = null,gender = null,screen_name = null,image_url = null;
                try {
                    switch (THEWAY_TO_LOG){
                        case 1:
                            openid=jo.get("openid");
                            location=jo.get("province")+" "+jo.get("city");
                            gender=jo.get("gender");
                            screen_name=jo.get("screen_name");
                            image_url=jo.get("profile_image_url");
                            break;
                        case 3:
                            openid=jo.get("uid");
                            location=jo.get("location");
                            if(jo.get("gender").equals("0"))
                                gender="女";
                            else
                                gender="男";
                            screen_name=jo.get("screen_name");
                            image_url=jo.get("profile_image_url");
                            break;
                        case 2:
                            break;
                    }
                    Log.i("用户信息:","id："+openid+"\nplace："+location+"\nsex："+gender+"\nname："+screen_name+"\nURL："+image_url);
                    final String finalOpenid = openid;
                    final String finalImage_url = image_url;
                    final String finalScreen_name = screen_name;
                    final String finalLocation = location;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(!new Myslq().isUserHad(finalOpenid,null))
                                new Myslq().addUser(finalOpenid,null, finalImage_url, finalScreen_name,null,getString(R.string.signature), finalLocation,null);
                            UserInfo userInfo=new Myslq().logIn(finalOpenid,null);
                            if(userInfo!=null) {
                                saveLogIn(userInfo);
                                getString();
                                startActivity();
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText( getApplicationContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
    @UiThread
    public void startActivity() {
        MyPublic.startActivity(MyLogin.this, Home.class);
        this.finish();
    }

    //删除权限
    private void removeRoot() {
        mShareAPI.deleteOauth(MyLogin.this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermission();
        initLog();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences("saveLogin", MODE_PRIVATE);
        if(sharedPreferences!=null&&sharedPreferences.getString("number",INT).length()<12)
            etUserNumber.setText(sharedPreferences.getString("number",null));
    }

    //轮廓
    ViewOutlineProvider viewOutlineProvider=new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 17);
            //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
        }
    };
    //Toast
    @UiThread
    public  void mySnakbar(View view, String str,int time){
        Snackbar.make(view,str, time).show();
    }
    //登陆状态保存
    public void saveLogIn(UserInfo userInfo){
        SharedPreferences sharedPreferences = this.getSharedPreferences("saveLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("number",userInfo.getNumber());
        editor.putString("password",userInfo.getPassword());
        editor.putString("name",userInfo.getName());
        editor.putString("signature",userInfo.getSignature());
        editor.putString("email",userInfo.getEmail());
        editor.putString("place",userInfo.getPlace());
        editor.putString("snumber",userInfo.getSnumber());
        editor.putString("headImage",userInfo.getUrl());
        editor.putBoolean("canLogin", true);
        editor.commit();

        MyPublic.setUserInfo(new database.UserInfo(sharedPreferences.getString("number", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("headImage", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("signature", null),
                sharedPreferences.getString("place", null),
                sharedPreferences.getString("snumber", null)
        ));
    }
    //SharedPreferences图片保存
    private void savaImage(){
        SharedPreferences sharedPreferences = getSharedPreferences("headImage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ((BitmapDrawable) getResources().getDrawable(R.drawable.icback)).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos2);
        String imageBase64 = Base64.encodeToString(baos2.toByteArray(), Base64.DEFAULT);
        editor.putString("headImage", imageBase64);
        editor.commit();
    }
    //SharedPreferences获取图片
    private void getImage(){
        //获取图片
        SharedPreferences sharedPreferences = getSharedPreferences("headImage", MODE_PRIVATE);
        ImageView imageView = (ImageView) findViewById(R.id.iv_pas);
        byte[] imagByte = Base64.decode(sharedPreferences.getString("productImg",""), Base64.DEFAULT);
        ByteArrayInputStream bais2 = new ByteArrayInputStream(imagByte);
        imageView.setImageDrawable(Drawable.createFromStream(bais2,  "imagByte"));
    }
    public void getString(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("saveLogin", MODE_PRIVATE);
        System.out.println(sharedPreferences.getString("number",null)+
        sharedPreferences.getString("password",null)+
        sharedPreferences.getString("name",null)+
        sharedPreferences.getString("signature",null)+
        sharedPreferences.getString("email",null)+
        sharedPreferences.getString("place",null)+
        sharedPreferences.getString("snumber",null)+
        sharedPreferences.getString("headImage",null)+
        sharedPreferences.getBoolean("canLogin", false)
        );
    }


    //邮件地址内容监听
    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
           if (etUserNumber.getText().toString().length()==11&&MyPublic.isIntenet(MyLogin.this)){
               setHeadImageUrl();
           }
        }
    };
    @UiThread
    public void showImage(String url) {
        Picasso.with(MyLogin.this).load(url).into(UserHeadImaegLg);
    }

    @Background
    public void setHeadImageUrl() {
        String url= new Myslq().getHeadImageUrl(etUserNumber.getText().toString());
        if(!url.equals("null")) {
            showImage(url);
        }
    }
}
