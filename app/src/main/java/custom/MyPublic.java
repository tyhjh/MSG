package custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.loopj.android.http.*;
import com.tyhj.myfist_2016_6_29.MyTime;
import com.tyhj.mylogin.R;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import database.Myslq;
import database.UserInfo;
import cz.msebera.android.httpclient.Header;

/**
 * Created by _Tyhj on 2016/7/30.
 */
public  class MyPublic {
    //用户信息
    private static UserInfo userInfo;
    //数据库
    private static Myslq myslq;
    private static ViewOutlineProvider viewOutlineProvider;
    public static UserInfo getUserInfo() {
        return userInfo;
    }
    public static void setUserInfo(UserInfo userInfo) {
        MyPublic.userInfo = userInfo;
    }
    private static int IMAGE_SIZE=2000;
    //是否有网络
    public static boolean isIntenet(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi||internet){
            return true;
        }else {
            MyPublic.Toast(context,context.getString(R.string.nointernet));
            return false;
        }
    }
    //是否有WIFI
    public static boolean isWifi(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        return con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    }
    //Toast
    public static void Toast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
    //轮廓
    public static ViewOutlineProvider getOutline(boolean b, final int x){
        if(b) {
           return  new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    //outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }else {
            return new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }

    }
    //Intent
    public static void startActivity(Context context,Class activity){
        Intent intent=new Intent(context,activity);
        context.startActivity(intent);
    }
    //上传文件
    public static void UploadFile(File file, final String name, final Context context, int fileType){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                stream.write(b, 0, b.length);
            }
            stream.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("file",img);
        params.put("fileName",name);
        params.put("fileType",fileType);
        client.post(context.getString(R.string.savafileservlet), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast(context,"成功");
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast(context,"失败");
            }
        });
    }
    //时间
    public static String getTime(){
        MyTime myTime=new MyTime();
        return myTime.getYear()+myTime.getMonth_()+myTime.getWeek_()+myTime.getDays()+myTime.getHour()+myTime.getMinute()+myTime.getSecond();
    }
    //保存文件到本地

    public static void  savaFile(String url, String name,Handler handler,Context context){
        saveBitmapFile(returnBitMap(url),name,handler,context);
    }

    /**
     * 获取ImageView图片并保存
     */
    private void getImageBitmap(String path,String name){
        //imageView1.buildDrawingCache(true);
        //imageView1.buildDrawingCache();
        //Bitmap bitmap = imageView1.getDrawingCache();
        //imageView1.setDrawingCacheEnabled(false);
    }
    private static Bitmap returnBitMap(String path) {
        Bitmap bitmap = null;
        try {
            java.net.URL url = new URL(path);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static void saveBitmapFile(Bitmap bm, String name, Handler handler,Context context) {
        if(bm==null)
            return;
        File f1 = new File(Environment.getExternalStorageDirectory()+context.getString(R.string.savaphotopath));
        if(!f1.exists()){
            f1.mkdirs();
        }
        File imageFile = new File(Environment.getExternalStorageDirectory()+context.getString(R.string.savaphotopath),name);
        if(imageFile.exists()){
            handler.sendEmptyMessage(1);
            return;
        }
        int options = 80;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > IMAGE_SIZE) {
            baos.reset();
            options -= 10;
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //初始化数据库

    public static void setMyslq(Myslq myslq) {
        MyPublic.myslq = myslq;
    }
    public static Myslq getMyslq() {
        return myslq;
    }
}
