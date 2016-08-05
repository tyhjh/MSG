package com.tyhj.mylogin.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tyhj.myfist_2016_6_29.MyTime;
import com.tyhj.mylogin.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import custom.MyPublic;
import custom.StatusBarUtil;
import database.*;
import database.UserInfo;
import sendemail.SendEmai;

/**
 * Created by _Tyhj on 2016/7/29.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@EActivity(R.layout.register)
public class Register extends Activity {
    private  String AUTH_CODE=null;
   private int count=30;
    private static  boolean CANUSNUMBER = false;
    private static  boolean CANUSEEMAIL = false;
    private static  boolean CANGETAUTHCODE = true;
    private static String TEXTTYPE_FANGTING_BLACK="fonts/fangtin_black.TTF";
    private static String TEXTTYPE_FANGTING_SMALL="fonts/fangtin_caoxi.TTF";
    private static String TEXTTYPE_YANHEI_LIGHT="fonts/yahei_light.ttc";
    private static String TEXTTYPE_YANHEI="fonts/yahei.ttf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil statusBarUtil=new StatusBarUtil();
        StatusBarUtil.setColor(this, Color.parseColor("#00000000"));
        //typeface = Typeface.createFromAsset(this.getAssets(),TEXTTYPE_YANHEI_LIGHT);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @ViewById
    EditText etRegisterNumber,etRegisterEmail,etRegisterPassword,etRegisterAuthCode,etRegisterName;
    @ViewById
    Button btGetAuthCode;
    @ViewById
    ImageView ivRegisterBack;
    @ViewById
    View flRegisterBack;
    //注册按钮
    @Background
    void gb(){
        if(isFinishedMessageCorrect()){
            registerNow();
        }
    }
    @Click(R.id.btRegister)
    void tryRegister() {
          gb();
    }
    //验证码计时中
    @UiThread
    void change(){
        btGetAuthCode.setText(count+"可后重新获取");
    }
    //可以获取验证码
    @UiThread
    void review(){
        btGetAuthCode.setText("获取验证码");
    }
    //Toast
    @UiThread
    public  void myToast(Context context, String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
    //获取验证码时间限制
    @Background
    void setCount(){
        while (!CANGETAUTHCODE){
            try {
                change();
                Thread.sleep(1000);
                count--;
                if(count==0){
                    CANGETAUTHCODE=true;
                    count=30;
                    review();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //获取邮箱验证码
    @Click(R.id.btGetAuthCode)
    void getAuthCode(){
        if(CANGETAUTHCODE&&MyPublic.isIntenet(this)&&isEmail(etRegisterEmail.getText().toString().trim())&&!CANUSEEMAIL){
            CANGETAUTHCODE=false;
            sendEmail();
            setCount();
        }else if(CANUSEEMAIL){
            myToast(Register.this, "该邮箱已被注册");
        }
    }
    //返回
    @Click(R.id.ivRegisterBack)
    void back(){
        Register.this.finish();
    }
    //初始化布局
    @AfterViews
    void afterView(){
        etRegisterNumber.addTextChangedListener(numberTextWatch);
        etRegisterEmail.addTextChangedListener(textWatcher);
        flRegisterBack.setOutlineProvider(viewOutlineProvider);
        flRegisterBack.setClipToOutline(true);
    }
    @FocusChange({R.id.etRegisterNumber,R.id.etRegisterEmail, R.id.etRegisterPassword,R.id.etRegisterAuthCode,R.id.etRegisterName})
     void focusChangedOnSomeTextViews(EditText hello, boolean hasFocus) {
        if(hasFocus){
            hello.setSelectAllOnFocus(true);

        }
    }
    //是否正确完成注册信息
    private boolean isFinishedMessageCorrect() {
        if (!etRegisterNumber.getText().toString().trim().equals("")&&
                !etRegisterEmail.getText().toString().trim().equals("")&&
                !etRegisterPassword.getText().toString().trim().equals("")&&
                !etRegisterAuthCode.getText().toString().trim().equals("")&&
                !etRegisterAuthCode.getText().toString().trim().equals("")){
            if(isMobileNO(etRegisterNumber.getText().toString().trim())
                    &&isEmail(etRegisterEmail.getText().toString().trim())
                    &&isAuthCodeOk()&&MyPublic.isIntenet(this)){
                    if(!CANUSNUMBER
                    &&!CANUSEEMAIL){
                return true;
            }else {
               if(CANUSNUMBER){
                    myToast(this,"该手机已被注册");
                }else if(CANUSEEMAIL){
                    myToast(this,"此邮箱已被注册");
                }
                return false;
            }
            }
            return false;
        }else {
            myToast(this,"请完成注册信息");
            return false;
        }
    }
    //开始注册
    private void registerNow() {
        try {
            new Myslq().addUser(etRegisterNumber.getText().toString().trim(),
                    etRegisterPassword.getText().toString().trim(),null,
                    etRegisterName.getText().toString().trim(),etRegisterEmail.getText().toString().trim(),null,"中国",null);
            saveLogIn(new UserInfo(etRegisterNumber.getText().toString().trim(),null,null,null,null,null,null,null));
            myToast(this,"注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            myToast(this,"失败，请稍后再试");
        }

    }
    //手机号是否正确
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        boolean is=m.matches();
        if(!is)
            myToast(this,"请输入正确的手机号码");
        return is;
    }
    //邮箱是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        boolean is=m.matches();
        if(!is)
            myToast(this,"请输入正确的Email地址");
        return is;
    }
    //邮箱验证码是否正确
    private boolean isAuthCodeOk(){
        if(AUTH_CODE!=null){
            if(AUTH_CODE.equals(etRegisterAuthCode.getText().toString().trim())){
                return true;
            }else {
                myToast(this,"验证码错误");
                return false;
            }
        }else {
            myToast(this,"请重新获取验证码");
            return false;
        }

    }
    //发送邮箱验证码
    public void sendEmail(){
            new Thread(new Runnable() {
            @Override
            public void run() {
                MyTime myTime=new MyTime();
                Random random = new Random(Integer.parseInt(myTime.getMinute()+myTime.getSecond()));//指定种子数字
                AUTH_CODE=random.nextInt(100)+"";
                AUTH_CODE+=random.nextInt(100);
                AUTH_CODE+=random.nextInt(100);
                String str=etRegisterName.getText().toString().trim();
                if(str.equals("")){
                    str=etRegisterNumber.getText().toString().trim();
                }
                new SendEmai(etRegisterEmail.getText().toString().trim(),AUTH_CODE,Register.this,str);
            }
        }).start();

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
            if(etRegisterEmail.getText().toString().contains(".com")||etRegisterEmail.getText().toString().contains(".cn")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CANUSEEMAIL = new Myslq().isEmailHad(etRegisterEmail.getText().toString(), Register.this);
                        if (CANUSEEMAIL)
                            myToast(Register.this, "该邮箱已被注册");
                    }
                }).start();
            }
        }
    };
    TextWatcher numberTextWatch=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(etRegisterNumber.getText().toString().length()==11){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CANUSNUMBER=new Myslq().isUserHad(etRegisterNumber.getText().toString(),Register.this);
                        if(CANUSNUMBER)
                            myToast(Register.this,"该手机已被注册");
                    }
                }).start();
            }
        }
    };
    //轮廓
    ViewOutlineProvider viewOutlineProvider=new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
            outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
            //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
        }
    };

    //登陆状态保存
    public void saveLogIn(database.UserInfo userInfo){
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
    }
}
